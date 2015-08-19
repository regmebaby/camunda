package de.ancud.camunda.connector.sql.impl;

import de.ancud.camunda.connector.sql.constants.ConnectorKeys;
import org.camunda.connect.impl.AbstractConnectorResponse;
import org.camunda.connect.spi.ConnectorResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class SqlResponseImpl extends AbstractConnectorResponse implements ConnectorResponse {

    public SqlResponseImpl() {

    }

    @Override
    protected void collectResponseParameters(Map<String, Object> responseParameters) {
    }

    public void addResponseParameter(Object value) {
        if (responseParameters == null) {
            responseParameters = new HashMap<String, Object>();
        }
        responseParameters.put(ConnectorKeys.OUTPUT_KEY_QUERY_RESULT, value);
    }

    public void addSqlResultParameter(Object value) {
        this.addResponseParameter(value);
    }
}
