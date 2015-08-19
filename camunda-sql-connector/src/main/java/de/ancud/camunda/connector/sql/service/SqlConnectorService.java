package de.ancud.camunda.connector.sql.service;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDataSourceFactoryImpl;
import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import de.ancud.camunda.connector.sql.mapper.StpCallParameterMapper;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Service layer for the sql connector. Calls the underlying DAO and handles most of the necessary data transformations
 *
 * @author bnmaxim.
 */
public class SqlConnectorService {
    private SqlConnectorDAO dao;

    /**
     * All the service needs to initialize itself are the database connection properties, which it uses to create an
     * instance of its own DAO.
     *
     * @param connectionProps need to contain jdbcURL/connection string, username, password and driverClassName
     */
    public SqlConnectorService(Properties connectionProps) {
        SqlConnectorDataSourceFactoryImpl dataSourceFactory = new SqlConnectorDataSourceFactoryImpl(
                connectionProps);
        this.dao = new SqlConnectorDAOImpl(dataSourceFactory);
    }

    /**
     * Execute an SQL Select query against the database configured with its own DAO instance. (See constructor)
     *
     * Example return value: 2 rows from database, JSON formatted.
     * [{username:'JaneDoe' , password:'29ab2a6bb9f2e8c43171dcc27249db84'},
     *  {username:'JohnDoe' , password:'38f9de6361367cee719c723a4ed2489a'}]
     *
     * @param selectQuery Expects a complete and valid sql select statement. No dynamic parameter values allowed.
     * @return a list of result rows, each consisting of multiple key-value pairs. The key corresponds to the column
     * name, the value to the value from the database.
     */
    public List<Map<String, Object>> executeSelectQuery(String selectQuery) {
        return this.dao.select(selectQuery);
    }

    /**
     * Executes a stored procedure call
     *
     * @param procedureName
     * @param params
     * @return
     */
    public Map<String, Object> executeStpCall(String procedureName, String params) {
        StpCallParameterMapper parameterMapper = new StpCallParameterMapper();
        List<StpCallDTO> dtoParamList = parameterMapper.map(params);

        return this.dao.callStoredProcedure(procedureName, dtoParamList);
    }


}
