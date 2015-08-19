package de.ancud.camunda.connector.sql.dao.util;

import de.ancud.camunda.connector.sql.dao.SqlConnectorDAO;
import de.ancud.camunda.connector.sql.dao.impl.SqlConnectorDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author bnmaxim.
 */
public interface  TestDataSourceProvider {

    public SqlConnectorDAO getDao();

}
