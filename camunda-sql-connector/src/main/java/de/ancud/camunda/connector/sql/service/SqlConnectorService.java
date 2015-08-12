package de.ancud.camunda.connector.sql.service;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDataSourceFactoryImpl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author bnmaxim.
 */
public class SqlConnectorService {
    private SqlConnectorDAO dao;

    public SqlConnectorService(Properties connectionProps) {
        SqlConnectorDataSourceFactoryImpl dataSourceFactory = new SqlConnectorDataSourceFactoryImpl(
                connectionProps);
        this.dao = new SqlConnectorDAOImpl(dataSourceFactory);
    }

    public List<Map<String, Object>> executeSelectQuery(String selectQuery) {
        return this.dao.select(selectQuery);
    }

}
