package de.ancud.camunda.connector.sql.mapper;

import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import org.camunda.commons.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bnmaxim.
 */
public class SqlConnectorParameterMapper {

    /**
     * Deserializes the inputParameter into a Map<> which should be of the form {nametype: 'value'}
     * @param inParams
     * @return
     */
    public List<StpCallDTO> map(String inParams) {
        List<StpCallDTO> params = new ArrayList<StpCallDTO>();

        return params;
    }
}
