package de.ancud.camunda.connector.sql.dao.impl;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class SqlConnectorDAOImpl implements SqlConnectorDAO {
    private static Logger Log = LoggerFactory.getLogger(SqlConnectorDAOImpl.class.getName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SqlConnectorDataSourceFactory dataSourceFactory;

    public SqlConnectorDAOImpl(SqlConnectorDataSourceFactory dataSourceFactory) {
        try {
            setDataSourceFactory(dataSourceFactory);
            dataSource = this.dataSourceFactory.getDataSource();
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (Throwable e) {
            Log.error("Failed to initialize DAO because ", e);
        }

    }

    public void setDataSourceFactory(SqlConnectorDataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public List<Map<String, Object>> select(String select) {
        return jdbcTemplate.queryForList(select);
    }

}
