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

    public String getCustomerName() {
        return CustomerName;
    }

    public BusinessListItem setCustomerName(String customerName) {
        CustomerName = customerName;
        return this;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public BusinessListItem setBusinessName(String businessName) {
        BusinessName = businessName;
        return this;
    }

    public String getBusinessSum() {
        return BusinessSum;
    }

    public BusinessListItem setBusinessSum(String businessSum) {
        BusinessSum = businessSum;
        return this;
    }

    public String getBalance() {
        return Balance;
    }

    public BusinessListItem setBalance(String balance) {
        Balance = balance;
        return this;
    }

    public String getOverDueBalance() {
        return OverDueBalance;
    }

    public BusinessListItem setOverDueBalance(String overDueBalance) {
        OverDueBalance = overDueBalance;
        return this;
    }

    public String getInterestBalance() {
        return InterestBalance;
    }

    public BusinessListItem setInterestBalance(String interestBalance) {
        InterestBalance = interestBalance;
        return this;
    }
}
