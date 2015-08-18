package de.ancud.camunda.connector.sql.dto;

/**
 * @author bnmaxim.
 */
public enum StpParamType {
    IN("in"),IN_OUT("inOut"),OUT("out");

    private String strVal;

    private StpParamType(String strVal) {
        this.strVal = strVal;
    }
    
    public static StpParamType getStpParamType(String str) {
        for (StpParamType pt : StpParamType.values()){
            if (pt.getStrVal().equals(str)){
                return pt;
            }
        }
        return null;
    }

    public String getStrVal() {
        return strVal;
    }
}
