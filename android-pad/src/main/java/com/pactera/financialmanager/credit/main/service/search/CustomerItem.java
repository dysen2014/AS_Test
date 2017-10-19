package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by lenovo on 2017/7/14.
 */

public class CustomerItem{
    public String IrscreditLevel;
    public String CustomerName;
    public String CertTypeName;
    public String MobilePhone;
    public CustomerItem(String IrscreditLevel,String CustomerName,String CertTypeName,String MobilePhone){
        this.IrscreditLevel = IrscreditLevel;
        this.CustomerName = CustomerName;
        this.CertTypeName = CertTypeName;
        this.MobilePhone = MobilePhone;
    }
}
