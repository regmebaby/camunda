package de.ancud.camunda.connector.sql.impl;

import de.ancud.camunda.connector.sql.validator.RequestValidator;
import de.ancud.camunda.connector.sql.SqlConnector;
import de.ancud.camunda.connector.sql.SqlRequest;
import de.ancud.camunda.connector.sql.SqlResponse;
import org.camunda.connect.impl.AbstractConnectorRequest;

/**
 * @author bnmaxim.
 */
public class SqlRequestImpl extends AbstractConnectorRequest<SqlResponse> implements SqlRequest {
    public SqlRequestImpl(SqlConnector sqlConnector) {
        super(sqlConnector);
    }

    @Override
    protected boolean isRequestValid() {
        RequestValidator validator = new RequestValidator();
        return validator.validateRequest(this.getRequestParameters());
    }
}
