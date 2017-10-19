package com.example.lenovo.query;

/**
 * Created by lenovo on 2017/7/27.
 */

public class EntOwnerItem {
    public String CustomerName;
    public String RelationShipName;
    public String InvestmentProp;
    public String CurrencyTypeName;
    public String OughtSum;
    public String InvestmentSum;
    public String InvestDate;
    public String OrgName;
    public String UserName;
    public EntOwnerItem(String CustomerName,String RelationShipName,String InvestmentProp,String CurrencyTypeName,String OughtSum,String InvestmentSum,String InvestDate,String OrgName,String UserName){
        this.CustomerName = CustomerName;
        this.RelationShipName = RelationShipName;
        this.InvestmentProp = InvestmentProp;
        this.CurrencyTypeName = CurrencyTypeName;
        this.OughtSum = OughtSum;
        this.InvestmentSum = InvestmentSum;
        this.InvestDate = InvestDate;
        this.OrgName = OrgName;
        this.UserName = UserName;
    }
}
