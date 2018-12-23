package com.pactera.financialmanager.callback;

/**
 * 建档中创建资本构成的dialog和主页面之间的进行回调的接口
 */
public interface BookbuildingZBGCinfoCallback {
    public void bookbuildingzbgcInfo(
            Boolean isEdit,
            String info1, String info2, String info3, String info4,
            String info5, String info6, String info7, String info8,
            String info9, String info10);
}
