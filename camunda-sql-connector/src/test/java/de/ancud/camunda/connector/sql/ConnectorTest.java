package de.ancud.camunda.connector.sql;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import de.ancud.camunda.connector.sql.dao.util.H2DBTestDataSourceProvider;
import de.ancud.camunda.connector.sql.impl.SqlConnectorProviderImpl;
import org.camunda.connect.spi.ConnectorResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class ConnectorTest {

    private SqlConnectorProviderImpl provider;
    private H2DBTestDataSourceProvider testDataSourceProvider;

    @Before
    public void setUp() throws Exception {
        //init new InMemory Database
        testDataSourceProvider = new H2DBTestDataSourceProvider();
        provider = new SqlConnectorProviderImpl();
    }

    /**
     * Simulates a Connector call with a SQL Statement against the embedded database.
     *
     * @throws Exception
     */
    @Test
    public void testDatabaseSelectResultsPositive() throws Exception {
        SqlConnector conn = provider.createConnectorInstance();
        SqlRequest sqlRequest = conn.createRequest();
        sqlRequest.getRequestParameters().put(ConnectorKeys.INPUT_KEY_SQL_SELECT, "select * from DEMO_CUSTOMERS where" +
                " Customer_ID in (1,2)");

        ConnectorResponse response = sqlRequest.execute();

        Assert.assertTrue(response.getResponseParameters() != null);
        Assert.assertTrue(response.getResponseParameter(ConnectorKeys.OUTPUT_KEY_QUERY_RESULT) != null);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) response.getResponseParameter(ConnectorKeys
                .OUTPUT_KEY_QUERY_RESULT);
        Assert.assertEquals(2, rows.size());

    }

    @Test(expected = RuntimeException.class)
    public void testInvalidSelectRequest() throws Exception {
        SqlConnector conn = provider.createConnectorInstance();;
        SqlRequest sqlRequest = conn.createRequest();
        sqlRequest.getRequestParameters().put(ConnectorKeys.INPUT_KEY_SQL_SELECT, "insert into DEMO_CUSTOMERS VALUES " +
                "(1, 'Ringo', 'Richards', '2010-01-01',  parsedatetime('11-05-2012 16:25:52.69',\n" +
                "'dd-MM-yyyy hh:mm:ss.SS'), 32)" +
                "");
        ConnectorResponse response = sqlRequest.execute();


    }

    @After
    public void tearDown() throws Exception {
        testDataSourceProvider.resetData();
    }
}
