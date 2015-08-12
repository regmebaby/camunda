package de.ancud.camunda.connector.sql.dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author bnmaxim.
 */
public class OracleTestDataSourceFactoryImpl implements SqlConnectorDataSourceFactory {


    public DataSource getDataSource() {
        String username = "humphrey";
        String password = "test";
        String url = "jdbc:oracle:thin:@localhost:49161:xe";
        String driverClassName = "oracle.jdbc.driver.OracleDriver";
        DriverManagerDataSource ds = new DriverManagerDataSource(url, username,password);
        ds.setDriverClassName(driverClassName);
        return ds;
    }
}
