package com.pactera.financialmanager.ui.model;

/**
 * Created by Tiger on 2018/3/8.
 */

public class CreditFwMenu {


    /**
     * PARENT_MENU_CODE : 服务
     * MENU_NAME : 利率测试
     * MENU_PICTURE_ID : llcs
     * MENU_CODE : 02001
     */

    private String PARENT_MENU_CODE;
    private String MENU_NAME;
    private String MENU_PICTURE_ID;
    private String MENU_CODE;
    private String MENU_MESSAGE;

    public String getMENU_MESSAGE() {
        return MENU_MESSAGE;
    }

    public void setMENU_MESSAGE(String MENU_MESSAGE) {
        this.MENU_MESSAGE = MENU_MESSAGE;
    }

    public String getPARENT_MENU_CODE() {
        return PARENT_MENU_CODE;
    }

    public void setPARENT_MENU_CODE(String PARENT_MENU_CODE) {
        this.PARENT_MENU_CODE = PARENT_MENU_CODE;
    }

    public String getMENU_NAME() {
        return MENU_NAME;
    }

    public void setMENU_NAME(String MENU_NAME) {
        this.MENU_NAME = MENU_NAME;
    }

    public String getMENU_PICTURE_ID() {
        return MENU_PICTURE_ID;
    }

    public void setMENU_PICTURE_ID(String MENU_PICTURE_ID) {
        this.MENU_PICTURE_ID = MENU_PICTURE_ID;
    }

    public String getMENU_CODE() {
        return MENU_CODE;
    }

    public void setMENU_CODE(String MENU_CODE) {
        this.MENU_CODE = MENU_CODE;
    }
}
