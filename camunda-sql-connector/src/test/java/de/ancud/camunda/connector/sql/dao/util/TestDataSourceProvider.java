package de.ancud.camunda.connector.sql.dao.util;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;

/**
 * @author bnmaxim.
 */
public interface TestDataSourceProvider {

    SqlConnectorDAO getDao();

}
