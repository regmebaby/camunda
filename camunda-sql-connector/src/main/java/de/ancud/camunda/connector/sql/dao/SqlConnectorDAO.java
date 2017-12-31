package de.ancud.camunda.connector.sql.dao;

import de.ancud.camunda.connector.sql.dto.StpCallDTO;

import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public interface SqlConnectorDAO {
    void setDataSourceFactory(SqlConnectorDataSourceFactory ds);

    List<Map<String, Object>> select(String query);

    int update(String pUpdateQuery);

    Map<String, Object> callStoredProcedure(String stpName, List<StpCallDTO> sqlParams);

    int execute(String pExecuteQuery);
}
