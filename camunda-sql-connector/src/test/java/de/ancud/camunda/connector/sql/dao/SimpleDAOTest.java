package de.ancud.camunda.connector.sql.dao;

import de.ancud.camunda.connector.sql.SqlConnector;
import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import de.ancud.camunda.connector.sql.util.TestDataSourceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
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

//        StpCallDTO excRate = new StpCallDTO();
//        excRate.setDataType(Types.NUMERIC);
//        //excRate.setName("result");
//        excRate.setStpParamType("out");

        double excRateVal = 0.5;
        double fixedCostVal = 1001;

        StpCallDTO excRate = new StpCallDTO();
        excRate.setDataType(Types.DOUBLE);
        excRate.setName("EXC_RATE");
        excRate.setValue(String.valueOf(excRateVal));

        StpCallDTO fixedCost = new StpCallDTO();
        fixedCost.setDataType(Types.DOUBLE);
        fixedCost.setName("FIXED_COST");
        fixedCost.setValue(String.valueOf(fixedCostVal));

        params.add(excRate);
        params.add(fixedCost);

        Map<String, Object> res = dao.callStoredProcedure("SELECT_ORDERS", params);
        System.out.println("res = " + res);
        BigDecimal otherCurrency = (BigDecimal)res.get("OTHER_CURRENCY");
        BigDecimal sum = (BigDecimal)res.get("SUM_PAR");
        BigDecimal costTotal = (BigDecimal)res.get("COST_TOTAL");

        Assert.assertEquals("Expected exactly 3 result in map",3, res.size());
        Assert.assertEquals(sum.multiply(new BigDecimal(excRateVal)), otherCurrency);
        Assert.assertEquals(sum.add(new BigDecimal(fixedCostVal)), costTotal);
    }
}
