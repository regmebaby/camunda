package de.ancud.camunda.connector.sql.init;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import de.ancud.camunda.connector.sql.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author bnmaxim.
 */
public class DataSourceConfig {


    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class.getName());

    /**
     * This method prioritizes DB-Configuration sources as follows:
     * config-Parameters from the ConnectorRequest (Process) come first,
     * followed by JNDI-Resources referenced in the params.
     * The last (fallback) source is the DB configured in the
     * properties file from the classpath.
     *
     * @param requestParams
     * @return The Config Properties if found, or null if no config information is available
     */
    public Properties getDBConnectionInfo(Map<String, Object> requestParams) {
        Properties props;
        if ((props = this.extractPropsFromRequest(requestParams)) != null) {
            return props;
        }
        if ((props = this.extractPropsFromJNDI(requestParams)) != null) {
            return props;
        }
        return this.extractPropsFromClasspath();
    }

    private Properties extractPropsFromRequest(Map<String, Object> requestParams) {
        try {
            Properties props = new Properties();
            props.put(ConnectorKeys.PROP_KEY_URL, getProp(requestParams, ConnectorKeys.INPUT_KEY_DB_URL));
            props.put(ConnectorKeys.PROP_KEY_USERNAME, getProp(requestParams, ConnectorKeys.INPUT_KEY_DB_USERNAME));
            props.put(ConnectorKeys.PROP_KEY_PASSWORD, getProp(requestParams, ConnectorKeys.INPUT_KEY_DB_PASSWORD));
            props.put(ConnectorKeys.PROP_KEY_DRIVER_CLASSNAME, getProp(requestParams, ConnectorKeys
                    .INPUT_KEY_DB_DRIVER_CLASSNAME));
            return props;
        } catch (MissingPropertyException mpe) {
            log.debug("No DB Connection Info from live process data, proceeding...");
        }
        return null;
    }

    /**
     * TODO
     *
     * @param requestParams
     * @return
     */
    private Properties extractPropsFromJNDI(Map<String, Object> requestParams) {
        return null;
    }

    /**
     * Reads the datasource config from a properties file in the Classpath
     *
     * @return the properties object, otherwise null
     */
    private Properties extractPropsFromClasspath() {
        try {
            Properties props = new Properties();
            props.load(DataSourceConfig.class.getClassLoader().getResourceAsStream(Constants
                    .DATASOURCE_CONFIG_FILE_NAME));
            return props;
        } catch (IOException e) {
            log.error("Failed to read the database config from classpath because: ", e);
        }
        return null;
    }

    /**
     * Checks if a non-empty String parameter with the
     * expected key is contained in the params-Map and returns it.
     * Throws an exception in case the param cannot be found or is null.
     *
     * @param params
     * @param keyNameExpected
     * @return
     * @throws MissingRequiredPropertiesException
     */
    private String getProp(Map<String, Object> params, String keyNameExpected) throws
            MissingRequiredPropertiesException {
        if (params.keySet().contains(keyNameExpected)) {
            String val = (String) params.get(keyNameExpected);
            if (!val.equals("")) {
                return val;
            }
        }
        throw new MissingPropertyException(keyNameExpected);
    }


    /**
     * meant for internal usage only
     */
    private static class MissingPropertyException extends IllegalStateException {

        private final String prop;

        public MissingPropertyException(String expectedProp) {
            this.prop = expectedProp;
        }

        @Override
        public String getMessage() {
            return "Failed to locate required property " + this.prop;
        }
    }
}
