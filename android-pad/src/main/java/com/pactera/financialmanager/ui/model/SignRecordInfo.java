package com.pactera.financialmanager.ui.model;

import com.baidu.mapapi.map.BitmapDescriptor;

/**
 * 签到记录
 * 
 * @author JAY
 * 
 */
public class SignRecordInfo {
	public String SIGNINID;// 序号
	public String CREATEUSERNAME;// 创建人姓名
	public String WORDESC;// 工作内容
	public String SIGNINADD;// 签到地址
	public String CREATEUSERID;// 创建人id
	public String CREATE_TIME;// 签到时间
	public String CUSTID;// 客户ID
	public String CUSTNAME="";// 客户姓名
	public String LONGITUDE;// 精度
	public String LATITUDE;// 维度
	public int markerIndex;//索引

	public String USERNAME;
	public String USERID;

	public String color;
	public BitmapDescriptor icon_maker;

	@Override
	public String toString() {
		return "&WORDESC=" + WORDESC + "&SIGNINADD=" + SIGNINADD + "&CUSTID=" + CUSTID
				+ "&LONGITUDE=" + LONGITUDE + "&LATITUDE=" + LATITUDE;
	}

}
