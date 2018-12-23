package com.pactera.financialmanager.ui.model;

/**
 * Created by Tiger on 2018/3/14.
 */

public class QtMenu {
    /**
     * MENU_NAME : 客户建档
     * MENU_PICTURE_ID : khjd
     * MENU_CODE : khjd
     */

    private String MENU_NAME;
    private String MENU_PICTURE_ID;
    private String MENU_CODE;
    private String MENU_MESSAGE;

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

    public String getMENU_MESSAGE() {
        return MENU_MESSAGE;
    }

    public void setMENU_MESSAGE(String MENU_MESSAGE) {
        this.MENU_MESSAGE = MENU_MESSAGE;
    }
}
