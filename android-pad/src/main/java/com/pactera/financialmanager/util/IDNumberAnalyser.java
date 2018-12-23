package com.pactera.financialmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xh on 2015/12/16.
 */
public class IDNumberAnalyser {

    private String idNumber;

    private boolean isLegal;

    private Date birthday;

    private Integer gender;

    private static final SimpleDateFormat PARSER = new SimpleDateFormat("yyyyMMdd");

    public IDNumberAnalyser(String idNumber) {
        this.idNumber = idNumber;

        if (idNumber == null || (idNumber.length() != 18 && idNumber.length() != 15)) {
            isLegal = false;
            return;
        }

        if (idNumber.length() == 15) {
            String cardNum18 = CheckIdCardUtils.conver15CardTo18(idNumber);
            if (cardNum18 == null) {
                isLegal = false;
                return;
            } else {
                idNumber = cardNum18;
            }
        } else {
            if (!(isLegal = CheckIdCardUtils.validateIdCard18(idNumber))) {
                return;
            }
        }

        try {
            birthday = PARSER.parse(idNumber.substring(6, 6 + 8));
        } catch (ParseException e) {
            isLegal = false;
            return;
        }

        {
            int c = idNumber.charAt(16) - '0';
            gender = c % 2 == 1 ? 1 : 2;
        }

        isLegal = true;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public Date birthday() {
        return isLegal() ? birthday : null;
    }

    // 1：male 2: femal
    public Integer gender() {
        return isLegal() ? gender : null;
    }

    private boolean checkMod11() {
        // TODO tobe done
        return true;
    }
}
