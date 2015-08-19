package de.ancud.camunda.connector.sql.mapper;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Types;

/**
 * @author bnmaxim.
 */
public class SqlTypeMapperTest {

    private final SqlTypeMapper mapper = new SqlTypeMapper();

    @Test
    public void testPositiveResults() throws Exception {
        Assert.assertEquals("Expected DOUBLE to return ", Types.DOUBLE, mapper.getSqlType("DOUBLE"));
        Assert.assertEquals("Expected Double to return " + Types.DOUBLE + " (case insensitivity)", Types.DOUBLE, mapper
                .getSqlType
                        ("Double"));
        Assert.assertEquals("Expected Null to return " + Types.NULL, Types.NULL, mapper.getSqlType("null"));
    }

    /**
     * If the data type cannot be matched, throw an exception
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void testNegativeResults() throws Exception {
        mapper.getSqlType("Douple");
    }
}
