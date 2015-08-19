package de.ancud.camunda.connector.sql.mapper;

import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author bnmaxim.
 */
public class StpCallParameterMapperTest {

    private StpCallParameterMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mapper = new StpCallParameterMapper();

    }

    @Test
    public void testValidMapping() throws Exception {
        String validStpParams = "[{\"name\":\"EXC_RATE\", \"dataType\":\"8\", \"value\": \"0.5\"}, " +
                "{\"name\":\"FIXED_COST\", \"dataType\":\"8\", \"value\":\"1001\"}]";
        List<StpCallDTO> res = mapper.map(validStpParams);

        System.out.println("res = " + res);
        Assert.assertEquals("Expected two elements in the deserialized list",2, res.size());

    }

    @Test
    public void testInValidMapping() throws Exception {
        String validStpParams = "{\"name\":\"EXC_RATE\", \"dataType\":\"8\", \"value\": \"0.5\"}, ";

        List<StpCallDTO> res = mapper.map(validStpParams);

        System.out.println("res = " + res);
        Assert.assertTrue(res!=null && res.size()==0);

    }
}
