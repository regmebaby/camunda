package de.ancud.camunda.connector.sql.impl;

import de.ancud.camunda.connector.sql.SqlConnector;
import de.ancud.camunda.connector.sql.SqlRequest;
import de.ancud.camunda.connector.sql.SqlResponse;
import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import de.ancud.camunda.connector.sql.init.DataSourceConfig;
import de.ancud.camunda.connector.sql.service.SqlConnectorService;
import de.ancud.camunda.connector.sql.validator.RequestTypeChecker;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.ConnectorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author bnmaxim.
 */
public class SqlConnectorImpl extends AbstractConnector<SqlRequest, SqlResponse> implements SqlConnector {

    private static Logger _log = LoggerFactory.getLogger(SqlConnectorImpl.class.getName());
    private final RequestTypeChecker checker = new RequestTypeChecker();
    private final DataSourceConfig dataSourceConfig = new DataSourceConfig();
    private SqlConnectorService connectorService;

    public SqlConnectorImpl() {
        this(SqlConnector.ID);
    }

    public SqlConnectorImpl(String connectorId) {
        super(connectorId);
    }

    public SqlRequest createRequest() {
        return new SqlRequestImpl(this);
    }

    public ConnectorResponse execute(SqlRequest request) {
        Map<String, Object> params = request.getRequestParameters();

        SqlResponseImpl sqlResponse = new SqlResponseImpl();

        if (checker.isSqlSelect(params)) {
            String selectQuery = (String) params.get(ConnectorKeys.INPUT_KEY_SQL_SELECT);
            List<Map<String, Object>> sqlResult = getSqlConnectorService(params).executeSelectQuery(selectQuery);
            sqlResponse.addSqlResultParameter(sqlResult);
        } else if (checker.isSqlUpdate(params)) {
            String updateQuery = (String) params.get(ConnectorKeys.INPUT_KEY_SQL_UPDATE);
            int sqlResult = getSqlConnectorService(params).executeUpdateQuery(updateQuery);
            sqlResponse.addSqlResultParameter(sqlResult);
        }

        return sqlResponse;
    }

    private SqlConnectorService getSqlConnectorService(Map<String, Object> params) {
        if (this.connectorService == null) {
            Properties connProps = dataSourceConfig.getDBConnectionInfo(params);
            this.connectorService = new SqlConnectorService(connProps);
        }
        return this.connectorService;
    }
}
