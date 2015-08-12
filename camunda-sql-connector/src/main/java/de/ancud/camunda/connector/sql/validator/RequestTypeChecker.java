package de.ancud.camunda.connector.sql.validator;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author bnmaxim.
 */
public class RequestTypeChecker {

    /**
     * Checks whether the parameters contain a parameter with the key @ConnectorKeys.INPUT_KEY_SQL_SELECT
     * @param requestParams
     * @return true if the params contain an sql
     */
    public boolean isSqlSelect(Map<String, Object> requestParams){
        return requestParams.keySet().contains(ConnectorKeys.INPUT_KEY_SQL_SELECT);
    }

    public boolean isStoredProcedureCall(Map<String, Object> requestParams){
        return requestParams.keySet().contains(ConnectorKeys.INPUT_KEY_SQL_STP);
    }
}
