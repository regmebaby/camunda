package de.ancud.camunda.connector.sql.mapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class SqlTypeMapper {

    private static final Logger Log = LoggerFactory.getLogger(SqlTypeMapper.class.getName());
    private final Map<String, Integer> jdbcTypeMap = new HashMap<String, Integer>();

    public SqlTypeMapper() {
        for (Field field : Types.class.getFields()) {
            try {
                this.jdbcTypeMap.put(field.getName(), (Integer) field.get(null));
            } catch (IllegalAccessException e) {
                //skip
            }
        }
    }

    /**
     * Converts the String DataType to the internal int representation, as defined in java.sql.Types.
     * Example: calling the Method with sqlType="DOUBLE" (or, for that matter, "Double") will return
     * the int value of 8
     *
     * @param sqlType valid String representation of java.sql.Types type
     * @return the value corresponding to the input parameter name, as defined in java.sql.Types.
     */
    public int getSqlType(String sqlType) {
        Integer sqlTypeInteger = jdbcTypeMap.get(StringUtils.upperCase(sqlType));
        if (sqlTypeInteger == null) {
            Log.debug("Failed to map the Supplied SQL data type to a java.sql.Types type " + sqlType);
            throw new RuntimeException("Incorrect SQL data type supplied: " + sqlType);
        }
        return sqlTypeInteger;
    }
}
