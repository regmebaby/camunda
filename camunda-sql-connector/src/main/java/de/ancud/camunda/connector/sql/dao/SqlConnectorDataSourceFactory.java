package de.ancud.camunda.connector.sql.dao;

import javax.sql.DataSource;

/**
 * @author bnmaxim.
 */
public interface SqlConnectorDataSourceFactory {
    DataSource getDataSource();
}
