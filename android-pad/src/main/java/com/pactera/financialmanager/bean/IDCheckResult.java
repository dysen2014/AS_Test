package com.pactera.financialmanager.bean;

import java.io.Serializable;

/**
 * Created by dysen on 2017/7/27.
 */

public class IDCheckResult implements Serializable {
    public final boolean pass;

    public final String id;

    public final String name;

    public final String custNo;

    public final Integer applyType;

    public IDCheckResult(boolean pass, String id, String name, String custNo, Integer applyType) {
        this.pass = pass;
        this.id = id;
        this.name = name;
        this.custNo = custNo;
        this.applyType = applyType;
    }
}
