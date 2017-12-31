package de.ancud.camunda.connector.sql.validator;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import de.ancud.camunda.connector.sql.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Checks whether the current request contains either a SELECT-statement or a Stored Procedure as a parameter.
 *
 * @author bnmaxim.
 */
public class RequestValidator {

    private static final Logger Log = LoggerFactory.getLogger(RequestValidator.class.getName());

    private final RequestTypeChecker checker = new RequestTypeChecker();

    /**
     * Checks which input-parameter has been set. If both an SQL-Statement AND a Stored Procedure Call are found,
     * return false
     *
     * @param requestParams
     * @return
     */
    public boolean validateRequest(Map<String, Object> requestParams) {
        Log.debug("Validating request...");
        if (requestParams != null) {
            return validateSqlStatement(requestParams) != validateStoredProcedureCall(requestParams);//XOR
        }
        return false;
    }

    /**
     * Checks if the select statement starts with "select"
     *
     * @param requestParams
     * @return
     */
    private boolean validateSqlStatement(Map<String, Object> requestParams) {
        Log.debug("Validating request...");
        if (checker.isSqlSelect(requestParams)) {
            String val = (String) requestParams.get(ConnectorKeys.INPUT_KEY_SQL_SELECT);
            Log.debug("Submitted select: " + val);
            return StringUtils.startsWithIgnoreCase(val, Constants.SELECT);
        }
        if (checker.isSqlUpdate(requestParams)) {
            String val = (String) requestParams.get(ConnectorKeys.INPUT_KEY_SQL_UPDATE);
            Log.debug("Submitted update: " + val);
            return StringUtils.startsWithIgnoreCase(val, Constants.INSERT) || StringUtils.startsWithIgnoreCase(val, Constants.UPDATE) || StringUtils.startsWithIgnoreCase(val, Constants.DELETE) || StringUtils.startsWithIgnoreCase(val, Constants.MERGE);
        }
        return false;
    }

    /**
     * Only checks if the parameter with the expected key has been set. Additional plausibility checks should be
     * added in the future
     *
     * @param requestParams
     * @return
     */
    private boolean validateStoredProcedureCall(Map<String, Object> requestParams) {
        return checker.isStoredProcedureCall(requestParams);
    }
}
