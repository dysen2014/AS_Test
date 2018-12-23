package com.pactera.financialmanager.credit.main.service.search;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by lenovo on 2017/7/13.
 */

public class Arrays extends AppCompatActivity{
    private String customerType_name;
    private String customerType_value;
    private String cardType_name;
    private String cardType_value;
    private String level_name;
    private String level_value;
    private String TypeNo_name;
    private String TypeNo_value;
    private String classifyResult_name;
    private String classifyResult_value;
    public String customerType(String customerType_name, String[] items){
        String[] customer_type = {"010","030"};
        for(int i=0;i<items.length;i++){
            if(customerType_name.equals (items[i])){
                customerType_value = customer_type[i];
            }
        }
        return customerType_value;
    }
    protected String getCustomerType_name(String customerType_value){
        String[] items = {"040", "010","030"};
        String[] customer_type = {"农户客户","对公客户","个人客户"};
        for(int i=0;i<items.length;i++){
            if(customerType_value.equals(items[i])){
                customerType_name = customer_type[i];
            }
        }
        return  customerType_name;
    }
    public String cardType(String cardType_name, String[] items){
        String[] card_type = {"0","010","020","030","090","095","1","2","3","4","5","6","7","8","9","X"};
        for(int i=0;i<items.length;i++){
            if(cardType_name.equals (items[i])){
                cardType_value = card_type[i];
            }
        }
        return cardType_value;
    }
    protected String getLevel(String level_name,String[] items){
        String[] level = {"AAA","AA+","AA","AA-","level_a+","level_a","level_a-","BBB+","BBB","BBB-","BB","level_b","level_c","D"};
        for(int i=0;i<items.length;i++){
            if(level_name.equals (items[i])){
                level_value = level[i];
            }
        }
        return level_value;
    }
    protected String getTypeNo_value(String TypeNo_name, String[] items1, String[] items2){
        for(int i=0;i<items1.length;i++){
            if(TypeNo_name.equals(items1[i])){
                TypeNo_value = items2[i];
            }
        }
        return  TypeNo_value;
    }
    protected String getClassifyResult_value(String classifyResult_name, String[] items){
        String[] classifyResult = {"01","02","03","04","05","0101","0102","0103","0201","0202","0203","0301","0302","0401","0501"};
        for(int i=0;i<items.length;i++){
            if(classifyResult_name.equals(items[i])){
                classifyResult_value = classifyResult[i];
            }
        }
        return  classifyResult_value;
    }
}
