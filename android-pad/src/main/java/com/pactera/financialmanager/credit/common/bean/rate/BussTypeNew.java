package com.pactera.financialmanager.credit.common.bean.rate;

/**
 * Created by admin on 2017-7-23.
 */

public class BussTypeNew {
    private String TypeName;
    private String SortNo;
    private String TypeProductML;
    private String key;
    private String value;

    public BussTypeNew(String typeName, String sortNo, String typeProductML, String key, String value) {
        TypeName = typeName;
        SortNo = sortNo;
        TypeProductML = typeProductML;
        this.key = key;
        this.value = value;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getSortNo() {
        return SortNo;
    }

    public void setSortNo(String sortNo) {
        SortNo = sortNo;
    }

    public String getTypeProductML() {
        return TypeProductML;
    }

    public void setTypeProductML(String typeProductML) {
        TypeProductML = typeProductML;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
