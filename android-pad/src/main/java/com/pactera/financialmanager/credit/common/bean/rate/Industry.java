package com.pactera.financialmanager.credit.common.bean.rate;


import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2017-7-31.
 */
public class Industry extends DataSupport {
    private String codeno ;
    private String itemno ;
    private String etemname ;
    public Industry(String codeno, String itemno, String etemname){
        this.codeno = codeno;
        this.itemno = itemno;
        this.etemname = etemname;
    }
    public void setCodeno(String codeno){
        this.codeno = codeno;
    }
    public void setItemno(String itemno){
        this.itemno = itemno;
    }
    public void setEtemname(String etemname){
        this.etemname = etemname;
    }
    public String getCodeno(){
        return codeno;
    }
    public String getItemno(){
        return itemno;
    }
    public String getEtemname(){
        return etemname;
    }
}
