package com.pactera.financialmanager.credit.common.bean.rate;

/**
 * Created by admin on 2017-7-23.
 */

public class BussType {
    private String TypeName;
    private String TypeNo;
    private String SortNo;
    private String key;
    private String value;

    public BussType(String TypeName, String TypeNo, String SortNo, String key, String value){
        this.TypeName = TypeName;
        this.TypeNo = TypeNo;
        this.SortNo = SortNo;
        this.key = key;
        this.value = value;
    }

    public String getTypeName(){
        return TypeName;
    }
    public void setTypeName(){
        this.TypeName = TypeName;
    }

    public String getTypeNo(){
        return TypeNo;
    }

    public void setTypeNo(){
        this.TypeNo = TypeNo;
    }

    public String getSortNo(){
        return SortNo;
    }

    public void setSortNo(){
        this.SortNo = SortNo;
    }

    public String getkey(){
        return key;
    }

    public void setkey(){
        this.key = key;
    }

    public String getvalue(){
        return value;
    }

    public void setvalue(){
        this.value = value;
    }
}
