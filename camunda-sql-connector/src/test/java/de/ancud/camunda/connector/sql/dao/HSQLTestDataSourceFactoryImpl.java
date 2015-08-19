package de.ancud.camunda.connector.sql.dao;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

/**
 * @author bnmaxim.
 */
public class HSQLTestDataSourceFactoryImpl implements SqlConnectorDataSourceFactory {


    public DataSource getDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;TRACE_LEVEL_SYSTEM_OUT=2");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
}
