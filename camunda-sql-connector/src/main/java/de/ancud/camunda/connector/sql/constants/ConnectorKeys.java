package de.ancud.camunda.connector.sql.constants;

/**
 * @author bnmaxim.
 */
public class ConnectorKeys {

    public static final String INPUT_KEY_SQL_SELECT = "select";
    public static final String INPUT_KEY_SQL_STP = "stp";

    //Config keys for properties coming from the process
    public static final String INPUT_KEY_DB_URL="db.conf.url";
    public static final String INPUT_KEY_DB_USERNAME="db.conf.username";
    public static final String INPUT_KEY_DB_PASSWORD="db.conf.password";
    public static final String INPUT_KEY_DB_DRIVER_CLASSNAME="db.conf.driver.classname";

    public static final String OUTPUT_KEY_QUERY_RESULT = "result";

    //config keys for the internal db connection properties object
    public static final String PROP_KEY_URL = "sql.connector.url";
    public static final String PROP_KEY_USERNAME = "sql.connector.username";
    public static final String PROP_KEY_PASSWORD = "sql.connector.password";
    public static final String PROP_KEY_DRIVER_CLASSNAME = "sql.connector.driver.classname";

}
