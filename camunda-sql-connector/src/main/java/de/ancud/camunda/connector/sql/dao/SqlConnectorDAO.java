package de.ancud.camunda.connector.sql.dao;

import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public interface SqlConnectorDAO {
    void setDataSourceFactory(SqlConnectorDataSourceFactory ds);

    List<Map<String, Object>> select(String query);
}
