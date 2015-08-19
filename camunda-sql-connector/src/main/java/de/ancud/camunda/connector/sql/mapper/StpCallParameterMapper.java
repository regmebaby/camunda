package de.ancud.camunda.connector.sql.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.ancud.camunda.connector.sql.dto.StpCallDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bnmaxim.
 */
public class StpCallParameterMapper {

    private static final Logger Log = LoggerFactory.getLogger(StpCallParameterMapper.class.getName());

    /**
     * Deserializes the inputParameter into a Map<> which should be of the form {nametype: 'value'}
     *
     * @param inParams
     * @return
     */
    public List<StpCallDTO> map(String inParams) {
        List<StpCallDTO> params = new ArrayList<StpCallDTO>();
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        try {
            params = mapper.readValue(inParams, typeFactory.constructCollectionType(List.class, StpCallDTO
                    .class));
        } catch (IOException e) {
            Log.debug("Failed to deserialize stored procedure parameters because ", e);
        }

        return params;
    }
}
