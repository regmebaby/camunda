package de.ancud.camunda.connector.sql.dao.util;

import de.ancud.camunda.connector.sql.dao.HSQLTestDataSourceFactoryImpl;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author bnmaxim.
 */
public class H2DBTestDataSourceProvider implements TestDataSourceProvider {

    public static final String TEST_CONFIG_SQL = "test-config.sql";
    private static Logger Log = LoggerFactory.getLogger(SqlConnectorDAOImpl.class.getName());
    private SqlConnectorDAO dao;
    private Connection connection;

    public H2DBTestDataSourceProvider() {
        try {
            SqlConnectorDataSourceFactory dataSourceFactory = new HSQLTestDataSourceFactoryImpl();
            this.dao = new SqlConnectorDAOImpl(dataSourceFactory);

            this.connection = dataSourceFactory.getDataSource().getConnection();
            InputStreamResource isr = new InputStreamResource(H2DBTestDataSourceProvider.class.
                    getClassLoader().getResourceAsStream(TEST_CONFIG_SQL));

            ScriptUtils.executeSqlScript(connection, isr);
        } catch (SQLException e) {
            Log.error("Failed to instantiate Test datasource because ", e);
        }
    }

    public SqlConnectorDAO getDao() {
        return this.dao;
    }

    public void resetData() throws SQLException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("TRUNCATE TABLE DEMO_CUSTOMERS");
            preparedStatement.execute();
            this.connection.commit();
        } finally {
            this.connection.close();
        }

    }
}
