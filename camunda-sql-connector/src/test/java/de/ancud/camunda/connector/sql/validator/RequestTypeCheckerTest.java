package de.ancud.camunda.connector.sql.validator;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author bnmaxim.
 */
public class RequestTypeCheckerTest {

    private RequestTypeChecker requestTypeChecker;
    private HashMap<String, Object> requestParams;

    @Before
    public void setUp() throws Exception {
        this.requestTypeChecker = new RequestTypeChecker();
        this.requestParams = new HashMap<String, Object>();
    }

    @Test
    public void testValidIsSelectCall() throws Exception {
        requestParams.put("select", null);

        assertTrue("Request contains a select, expected the checker to answer TRUE", this.requestTypeChecker
                .isSqlSelect(requestParams));

    }

    @Test
    public void testInvalidIsSelectCall() throws Exception {
        requestParams.put(ConnectorKeys.INPUT_KEY_SQL_STP, null);

        assertFalse("Request does not contain a SELECT statement, expected the checker to answer FALSE", this
                .requestTypeChecker
                .isSqlSelect(requestParams));

    }

    @Test
    public void testValidIsStoredProcedureCall() throws Exception {
        requestParams.put(ConnectorKeys.INPUT_KEY_SQL_STP, null);

        assertTrue("Request contains an STP call, expected the checker to answer TRUE", this.requestTypeChecker
                .isStoredProcedureCall(requestParams));

    }

    @Test
    public void testInvalidIsStoredProcedureCall() throws Exception {
        requestParams.put("select", null);

        assertFalse("Request does not contain an STP call, expected the checker to answer FALSE", this
                .requestTypeChecker
                .isStoredProcedureCall(requestParams));
    }
}
