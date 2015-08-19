package de.ancud.camunda.connector.sql.dao.util;

import de.ancud.camunda.connector.sql.dao.external.OracleTestDataSourceFactoryImpl;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;

import java.sql.SQLException;

/**
 * @author bnmaxim.
 */
public class OracleTestDataSourceProvider implements TestDataSourceProvider {

    public OracleTestDataSourceProvider() {
        SqlConnectorDataSourceFactory dataSourceFactory = new OracleTestDataSourceFactoryImpl();
        dao =  new SqlConnectorDAOImpl(dataSourceFactory);
    }

    private SqlConnectorDAO dao;

    public SqlConnectorDAO getDao() {
        return dao;
    }
}
