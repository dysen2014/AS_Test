package com.pactera.financialmanager.ui.model;


public class QueryDeployInfo {

    //{"APPLYSTATUS":"01","DES":"柔柔弱弱人人呃呃还会","APPLYREASON":"99",
    // "DEPLOYID":"AD00008Q","APPROVEUSERNAME":"","APPROVEBRNAME":"蕲春彭思支行",
    // "APPLYTIME":"2016-07-26 18:29:47","retCode":"0000"}

    public String DEPLOYID;//申领编号
    //    01 : 待审批
//    03 : 审批通过
//    04 : 审批拒绝
    public String APPLYSTATUS;//申领状态
    public String APPLYTIME;//申请时间
    public String APPROVEUSERNAME;//审批人
    public String APPROVEBRNAME;//审批机构

    public String APPLYREASON;//申请理由
    public String DES;

}