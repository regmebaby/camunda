package de.ancud.camunda.connector.sql;

import org.camunda.connect.spi.CloseableConnectorResponse;

import java.io.Closeable;

/**
 * @author bnmaxim.
 */
public interface SqlResponse extends CloseableConnectorResponse, Closeable {
}
