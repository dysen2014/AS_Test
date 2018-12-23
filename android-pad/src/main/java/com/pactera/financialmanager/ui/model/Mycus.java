package com.pactera.financialmanager.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的建档客户
 * 
 * @author Administrator
 *
 */
public class Mycus {
	/** 客户ID */
	private String cusID;

	/** 客户中文名称 */
	private String cusname;

	/** 性别 */
	private String sex;

	/** 客户级别 */
	private String rateValue;

	/** 培植方向 */
	private String cultivateDirct;

	/** 客户类型 */
	private String cusType;

	/** 建档时间 */
	private String createTime;

	private String updateTime;

	private String companyname;

	private String guimo;

	private String xingzhi;

	public String getCusID() {
		return cusID;
	}

	public void setCusID(String cusID) {
		this.cusID = cusID;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRateValue() {
		return rateValue;
	}

	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
	}

	public String getCultivateDirct() {
		return cultivateDirct;
	}

	public void setCultivateDirct(String cultivateDirct) {
		this.cultivateDirct = cultivateDirct;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getGuimo() {
		return guimo;
	}

	public void setGuimo(String guimo) {
		this.guimo = guimo;
	}

	public String getXingzhi() {
		return xingzhi;
	}

	public void setXingzhi(String xingzhi) {
		this.xingzhi = xingzhi;
	}

	public static List<Mycus> getTestDatas() {
		List<Mycus> mycus = new ArrayList<Mycus>();
		for (int i = 0; i < 10; i++) {
			Mycus cus = new Mycus();
			cus.setCusname("张三");
			cus.setSex("男");
			cus.setRateValue("高级客户");
			cus.setCultivateDirct("培植发展");
			cus.setCusType("对公客户");
			cus.setCreateTime("2014-09-04");
			cus.setUpdateTime("2015-10-14");
			cus.setCompanyname("***公司");
			cus.setGuimo("大型企业");
			cus.setXingzhi("民营");
			mycus.add(cus);
		}
		return mycus;
	}
}
