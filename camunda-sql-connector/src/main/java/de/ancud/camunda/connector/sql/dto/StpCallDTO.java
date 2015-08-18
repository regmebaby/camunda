package de.ancud.camunda.connector.sql.dto;

/**
 * @author bnmaxim.
 */
public class StpCallDTO {
    private String name;
    private String value;
    private int dataType;
    private StpParamType stpParamType;

    public StpParamType getStpParamType() {
        return stpParamType;
    }

    public void setStpParamType(String ptString) {
        this.stpParamType = StpParamType.getStpParamType(ptString);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
