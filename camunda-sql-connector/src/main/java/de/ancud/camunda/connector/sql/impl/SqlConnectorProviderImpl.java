package de.ancud.camunda.connector.sql.impl;

import de.ancud.camunda.connector.sql.SqlConnector;
import de.ancud.camunda.connector.sql.SqlConnectorProvider;

/**
 * @author bnmaxim.
 */
public class SqlConnectorProviderImpl implements SqlConnectorProvider {

    public String getConnectorId() {
        return SqlConnector.ID;
    }

    public SqlConnectorImpl createConnectorInstance() {
        return new SqlConnectorImpl(SqlConnector.ID);
    }
}
