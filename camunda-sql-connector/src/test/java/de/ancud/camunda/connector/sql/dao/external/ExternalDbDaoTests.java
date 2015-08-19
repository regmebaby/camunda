package de.ancud.camunda.connector.sql.dao.external;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.util.OracleTestDataSourceProvider;
import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class ExternalDbDaoTests {

    @Test
    public void testSimpleStoredProcedureCall() throws Exception {
        OracleTestDataSourceProvider dataSourceProvider = new OracleTestDataSourceProvider();
        SqlConnectorDAO dao = dataSourceProvider.getDao();

        List<StpCallDTO> params = new ArrayList<StpCallDTO>();

//        StpCallDTO excRate = new StpCallDTO();
//        excRate.setDataType(Types.NUMERIC);
//        //excRate.setName("result");
//        excRate.setStpParamType("out");

        double excRateVal = 0.5;
        double fixedCostVal = 1001;

        StpCallDTO excRate = new StpCallDTO();
        excRate.setDataType("8");
        excRate.setName("EXC_RATE");
        excRate.setValue(String.valueOf(excRateVal));

        StpCallDTO fixedCost = new StpCallDTO();
        fixedCost.setDataType("8");
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
