package de.ancud.camunda.connector.sql.validator;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class RequestValidatorTest {

    private final RequestValidator validator = new RequestValidator();

    @Test
    public void testValidSelectQuery() throws Exception {
        Map<String, Object> requestParams0 = new HashMap<String, Object>();
        Map<String, Object> requestParams1 = new HashMap<String, Object>();
        requestParams0.put(ConnectorKeys.INPUT_KEY_SQL_SELECT, "SelEct");
        requestParams1.put(ConnectorKeys.INPUT_KEY_SQL_SELECT, "select");

        Assert.assertTrue("Expected an entry starting with select (case insensitive) ", validator.validateRequest
                (requestParams0));
        Assert.assertTrue("Expected an entry starting with select (case insensitive) ", validator.validateRequest
                (requestParams1));
    }

    @Test
    public void testValidSTPCall() throws Exception {
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put(ConnectorKeys.INPUT_KEY_SQL_STP, "{call fictionalTestProcedure()}");

        Assert.assertTrue("Expected the params to contain a value for the param " + ConnectorKeys.INPUT_KEY_SQL_STP,
                validator.validateRequest
                (requestParams));
    }

    @Test
    public void testInvalidSelectQuery() throws Exception {
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put(ConnectorKeys.INPUT_KEY_SQL_SELECT, "Insert");

        Assert.assertFalse("Expected invalid result for Select Param with value \"insert\" ", validator.validateRequest
                (requestParams));
    }

    @Test
    public void testInvalidSTPCall() throws Exception {
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put(ConnectorKeys.INPUT_KEY_SQL_SELECT, "SELECT");
        requestParams.put(ConnectorKeys.INPUT_KEY_SQL_STP, "{call fictionalTestProcedure()}");

        Assert.assertFalse("Expected invalid result for params containing both Select Query and Stored Procedure Call" +
                " ", validator.validateRequest
                (requestParams));
    }
}
