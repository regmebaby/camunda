package de.ancud.camunda.connector.sql.dao.impl;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.SqlConnectorDataSourceFactory;
import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.ArrayList;
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


    public Map<String, Object> callStoredProcedure(String stpName, List<StpCallDTO> sqlParams) {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        MapSqlParameterSource values = new MapSqlParameterSource();

        for (StpCallDTO dto : sqlParams) {
            params.add(new SqlParameter(dto.getName(), dto.getDataType()));
            values.addValue(dto.getName(), dto.getValue());
        }

        SimpleJdbcCall stpCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(stpName);
        stpCall.declareParameters(params.toArray(new SqlParameter[params.size()]));
        return stpCall.execute(values);
    }
}
