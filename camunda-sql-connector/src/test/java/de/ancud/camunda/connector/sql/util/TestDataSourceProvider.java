package de.ancud.camunda.connector.sql.util;

import de.ancud.camunda.connector.sql.dao.HSQLTestDataSourceFactoryImpl;
import de.ancud.camunda.connector.sql.dao.OracleTestDataSourceFactoryImpl;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author bnmaxim.
 */
public class TestDataSourceProvider {
    public static final String TEST_CONFIG_SQL = "test-config.sql";
    private static SqlConnectorDAO dao;

    protected TestDataSourceProvider(){

    }

    /**
     * Initializes a new datasource if none has been initialized and fills it with the test data stored in
     * test-config.sql
     * @return
     * @throws SQLException
     */
    public static SqlConnectorDAO getDaoAndInitH2DB() throws
            SQLException {
        if (dao == null){
            SqlConnectorDataSourceFactory dataSourceFactory = new HSQLTestDataSourceFactoryImpl();
            dao = new SqlConnectorDAOImpl(dataSourceFactory);

            Connection connection = dataSourceFactory.getDataSource().getConnection();
            InputStreamResource isr = new InputStreamResource(TestDataSourceProvider.class.
                    getClassLoader().getResourceAsStream(TEST_CONFIG_SQL));

            ScriptUtils.executeSqlScript(connection, isr);
        }
        return dao;
    }

    public static SqlConnectorDAO getDaoAndInitOracleDB() throws
            SQLException {
        if (dao == null){
            SqlConnectorDataSourceFactory dataSourceFactory = new OracleTestDataSourceFactoryImpl();
            dao = new SqlConnectorDAOImpl(dataSourceFactory);
        }
        return dao;
    }

}
