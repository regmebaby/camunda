package de.ancud.camunda.connector.sql.dto;

/**
 * @author bnmaxim.
 */
public class StpCallDTO {
    private String name;
    private Object value;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
