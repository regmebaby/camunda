package de.ancud.camunda.connector.sql.dao.impl;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author bnmaxim.
 */
public class SqlConnectorDataSourceFactoryImpl implements SqlConnectorDataSourceFactory {

    private final DriverManagerDataSource ds;

    public SqlConnectorDataSourceFactoryImpl(String username, String password, String url, String driverClassName) {
        ds = new DriverManagerDataSource(url, username, password);
        ds.setDriverClassName(driverClassName);

    }

    public SqlConnectorDataSourceFactoryImpl(Properties props) {
        this(props.getProperty(ConnectorKeys.PROP_KEY_USERNAME),
                props.getProperty(ConnectorKeys.PROP_KEY_PASSWORD),
                props.getProperty(ConnectorKeys.PROP_KEY_URL),
                props.getProperty(ConnectorKeys.PROP_KEY_DRIVER_CLASSNAME));

    }

    public DataSource getDataSource() {
        return ds;
    }
}
