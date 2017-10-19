package com.pactera.financialmanager.credit.common.bean;

/**
 * Created by dysen on 2017/8/31.
 */

public class CreditLoginBean {

	String UserID;//登陆用户id，
	String UserName;//登陆用户名，
	String OrgID;//登陆机构id，
	String OrgName;//登陆机构名，
//	String result;//反馈信息（Y表示成功；非Y,会有说明，例如;//信贷系统中不存在证件号为5114231987031300117 的的用户！）

	public String getUserID() {
		return UserID;
	}

	public CreditLoginBean setUserID(String userID) {
		UserID = userID;
		return this;
	}

	public String getUserName() {
		return UserName;
	}

	public CreditLoginBean setUserName(String userName) {
		UserName = userName;
		return this;
	}

	public String getOrgID() {
		return OrgID;
	}

	public CreditLoginBean setOrgID(String orgID) {
		OrgID = orgID;
		return this;
	}

	public String getOrgName() {
		return OrgName;
	}

	public CreditLoginBean setOrgName(String orgName) {
		OrgName = orgName;
		return this;
	}

//	public String getResult() {
//		return result;
//	}
//
//	public CreditLoginBean setResult(String result) {
//		this.result = result;
//		return this;
//	}
}
