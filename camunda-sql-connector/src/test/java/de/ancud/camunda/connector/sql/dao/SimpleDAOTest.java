package de.ancud.camunda.connector.sql.dao;

import de.ancud.camunda.connector.sql.SqlConnector;
import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import de.ancud.camunda.connector.sql.util.TestDataSourceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class SimpleDAOTest {

    @Test
    public void testSimpleSelect() throws SQLException {
        SqlConnectorDAO dao = TestDataSourceProvider.getDaoAndInitH2DB();

        List<Map<String, Object>> mapList = dao.select("select * from DEMO_CUSTOMERS");

        Assert.assertEquals(2, mapList.size());
        Map<String, Object> row0 = mapList.get(0);
        Assert.assertEquals("Peter", (String) row0.get("FIRST_NAME"));
        Assert.assertEquals(new Integer(22), (Integer) row0.get("HEIGHT"));
        Assert.assertEquals(Date.valueOf("2015-01-01"), (Date) row0.get("GRADUATED"));
    }

    @Test
    public void testSimpleStoredProcedureCall() throws Exception {
        SqlConnectorDAO dao = TestDataSourceProvider.getDaoAndInitOracleDB();

        List<StpCallDTO> params = new ArrayList<StpCallDTO>();

        StpCallDTO outParam = new StpCallDTO();
        outParam.setDataType(Types.NUMERIC);
        outParam.setName("result");
        outParam.setStpParamType("out");

        Map<String, Object> res = dao.callStoredProcedure("SUM_ORDERS", params);
        System.out.println("res = " + res);

        Assert.assertEquals("Expected exactly one result in map",1, res.size());
    }
}
