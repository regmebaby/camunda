package de.ancud.camunda.connector.sql.dao.util;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import de.ancud.camunda.connector.sql.dao.external.OracleTestDataSourceFactoryImpl;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;

/**
 * @author bnmaxim.
 */
public class OracleTestDataSourceProvider implements TestDataSourceProvider {

    private final SqlConnectorDAO dao;

    public OracleTestDataSourceProvider() {
        SqlConnectorDataSourceFactory dataSourceFactory = new OracleTestDataSourceFactoryImpl();
        dao = new SqlConnectorDAOImpl(dataSourceFactory);
    }

    public SqlConnectorDAO getDao() {
        return dao;
    }
}
