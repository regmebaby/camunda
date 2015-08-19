package de.ancud.camunda.connector.sql.dao;

import de.ancud.camunda.connector.sql.dao.util.H2DBTestDataSourceProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class SimpleDAOTest {

    private H2DBTestDataSourceProvider dataSourceProvider;

    @Before
    public void setUp() throws Exception {
         dataSourceProvider = new H2DBTestDataSourceProvider();
    }

    @Test
    public void testSimpleSelect() throws SQLException {

        SqlConnectorDAO dao = dataSourceProvider.getDao();

        List<Map<String, Object>> mapList = dao.select("select * from DEMO_CUSTOMERS");

        Assert.assertEquals(2, mapList.size());
        Map<String, Object> row0 = mapList.get(0);
        Assert.assertEquals("Peter", (String) row0.get("FIRST_NAME"));
        Assert.assertEquals(new Integer(22), (Integer) row0.get("HEIGHT"));
        Assert.assertEquals(Date.valueOf("2015-01-01"), (Date) row0.get("GRADUATED"));
    }

    @After
    public void tearDown() throws Exception {
        dataSourceProvider.resetData();

    }
}
