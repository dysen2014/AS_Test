package com.pactera.financialmanager.credit.common.bean;

/**
 * Created by dysen on 2017/8/31.
 */

public class CreditLoginBean {

	/**
	 * UserID : E071323
	 * UserName : 杨敏
	 * OrgID : 110599079901
	 * OrgName : 秭归农村商业银行营业部
	 * Approvals : Y
	 * return : Y
	 */

	private String UserID;
	private String UserName;
	private String OrgID;
	private String OrgName;
	private String Approvals;
	private String returnX;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String UserID) {
		this.UserID = UserID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getOrgID() {
		return OrgID;
	}

	public void setOrgID(String OrgID) {
		this.OrgID = OrgID;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String OrgName) {
		this.OrgName = OrgName;
	}

	public String getApprovals() {
		return Approvals;
	}

	public void setApprovals(String Approvals) {
		this.Approvals = Approvals;
	}

	public String getReturnX() {
		return returnX;
	}

	public void setReturnX(String returnX) {
		this.returnX = returnX;
	}
}
