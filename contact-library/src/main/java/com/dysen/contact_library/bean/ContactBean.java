package com.dysen.contact_library.bean;

import com.dysen.contact_library.utils.PinYinUtils;

import java.io.Serializable;

/**
 * 描述: 封装联系人列表信息
 */

public class ContactBean implements Serializable {
    //姓名
    private String name;
    //拼音
    private String pinyin;
    //拼音首字母
    private String headerWord;
    //证件号
    private String CustomerType;
    private String CustomerID;
    private String CertID;

    public ContactBean(String name) {
        this.name = name;
        this.pinyin = PinYinUtils.getPinyin(name);
        headerWord = pinyin.substring(0, 1);
    }

    public ContactBean(String name, String certID) {
        this.name = name;
        CertID = certID;
        this.pinyin = PinYinUtils.getPinyin(name);
        headerWord = pinyin.substring(0, 1);
    }

    public ContactBean setPinyin(String pinyin) {
        if (!pinyin.equals(""))
        this.pinyin = pinyin;
        return this;
    }

    public ContactBean setHeaderWord(String headerWord) {
        this.headerWord = headerWord;
        return this;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public ContactBean setCustomerType(String customerType) {
        CustomerType = customerType;
        return this;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public ContactBean setCustomerID(String customerID) {
        CustomerID = customerID;
        return this;
    }

    public String getCertID() {
        return CertID;
    }

    public ContactBean setCertID(String certID) {
        CertID = certID;
        return this;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderWord() {
        return headerWord;
    }
}
