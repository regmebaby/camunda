package de.ancud.camunda.connector.sql;

import org.camunda.connect.spi.Connector;

/**
 * @author bnmaxim.
 */
public interface SqlConnector extends Connector<SqlRequest> {

    String ID = "sql-connector";
}
