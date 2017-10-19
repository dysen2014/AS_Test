package com.example.lenovo.query;

/**
 * Created by lenovo on 2017/7/18.
 */

public class KeymanItem {
    public String CertName;
    public String CertID;
    public String CustomerName;
    public String RelationName;
    public String UserName;
    public String OrgName;
    public String InputDate;
    public String UpdateDate;
    public String Telephone;
    public String OfficeTime;
    public String PractitionerTime;


    public KeymanItem(String CertName, String CertID, String CustomerName, String RelationName, String UserName, String OrgName, String InputDate, String UpdateDate, String Telephone, String OfficeTime, String PractitionerTime) {
        this.CertName = CertName;
        this.CertID = CertID;
        this.CustomerName = CustomerName;
        this.RelationName = RelationName;
        this.UserName = UserName;
        this.OrgName = OrgName;
        this.InputDate = InputDate;
        this.UpdateDate = UpdateDate;
        this.Telephone = Telephone;
        this.OfficeTime = OfficeTime;
        this.PractitionerTime = PractitionerTime;
    }
}

