package com.example.lenovo.query;

/**
 * Created by lenovo on 2017/7/25.
 */

public class LoanAfterItem {
    public String SerialNo;
    public String BusinessType;
    public String ArtificialNo;
    public String OccurTypeName;
    public String BusinessTypeName;
    public String Currency;
    public String BusinessSum;
    public String ActualPutOutSum;
    public String BailRatio;
    public String Balance;
    public String OverdueBalance;
    public String PutOutDate;
    public String Maturity;
    public String OperateOrgName;
    public LoanAfterItem(String SerialNo, String BusinessType, String ArtificialNo, String OccurTypeName, String BusinessTypeName, String Currency, String BusinessSum, String ActualPutOutSum, String BailRatio, String Balance, String OverdueBalance,String PutOutDate,String Maturity,String OperateOrgName) {
        this.SerialNo = SerialNo;
        this.BusinessType = BusinessType;
        this.ArtificialNo = ArtificialNo;
        this.OccurTypeName = OccurTypeName;
        this.BusinessTypeName = BusinessTypeName;
        this.Currency = Currency;
        this.BusinessSum = BusinessSum;
        this.ActualPutOutSum = ActualPutOutSum;
        this.BailRatio = BailRatio;
        this.Balance = Balance;
        this.OverdueBalance = OverdueBalance;
        this.PutOutDate = PutOutDate;
        this.Maturity = Maturity;
        this.OperateOrgName = OperateOrgName;
    }
}
