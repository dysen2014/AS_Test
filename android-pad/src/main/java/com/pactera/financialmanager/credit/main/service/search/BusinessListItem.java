package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by lenovo on 2017/7/31.
 */

public class BusinessListItem {
    public String CustomerName;
    public String BusinessName;
    public String BusinessSum;
    public String Balance;
    public String OverDueBalance;
    public String InterestBalance;
    public BusinessListItem(String CustomerName,String BusinessName,String BusinessSum,String Balance,String OverDueBalance,String InterestBalance){
        this.CustomerName = CustomerName;
        this.BusinessName = BusinessName;
        this.BusinessSum = BusinessSum;
        this.Balance = Balance;
        this.OverDueBalance = OverDueBalance;
        this.InterestBalance = InterestBalance;
    }
}
