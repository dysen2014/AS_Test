package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by lenovo on 2017/7/26.
 */

public class InfoItem {
    public String getKeyName() {
        return KeyName;
    }

    public InfoItem setKeyName(String keyName) {
        KeyName = keyName;
        return this;
    }

    public String getValue() {
        return Value;
    }

    public InfoItem setValue(String value) {
        Value = value;
        return this;
    }

    public String KeyName;
    public String Value;
    public InfoItem(String KeyName,String Value){
        this.KeyName = KeyName;
        this.Value = Value;
    }
}
