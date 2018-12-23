package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 从服务端请求得到的pad排队信息的JavaBean
 */
public class CustomerInfo implements Serializable{

	/**
	 * 来访客户信息
	 */
	private String queueNum;  //排队号码
	private String otherLine; //分流
	private String cnName;    //客户姓名
	private String sex;       //客户性别
	private String birthDay;  //客户生日
	private String waitTime;  //等待时间
	private String rank;      //客户评级      这在pad上显示的是图片，但返回的是代表级别的一个String类型的字段
	private String cardLevel; //贵宾卡级别
	private String manager;   //所属客户经理
	private String customerLabel; //客户标签
	
	public CustomerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomerInfo(String queueNum, String otherLine, String cnName,
			String sex, String birthDay, String waitTime, String rank,
			String cardLevel, String manager, String customerLabel) {
		super();
		this.queueNum = queueNum;
		this.otherLine = otherLine;
		this.cnName = cnName;
		this.sex = sex;
		this.birthDay = birthDay;
		this.waitTime = waitTime;
		this.rank = rank;
		this.cardLevel = cardLevel;
		this.manager = manager;
		this.customerLabel = customerLabel;
	}
	
	public String getQueueNum() {
		return queueNum;
	}
	public void setQueueNum(String queueNum) {
		this.queueNum = queueNum;
	}
	
	public String getOtherLine() {
		return otherLine;
	}
	public void setOtherLine(String otherLine) {
		this.otherLine = otherLine;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getCustomerLabel() {
		return customerLabel;
	}
	public void setCustomerLabel(String customerLabel) {
		this.customerLabel = customerLabel;
	}

	@Override
	public String toString() {
		return "CustomerInfo [queueNum=" + queueNum + ", otherLine="
				+ otherLine + ", cnName=" + cnName + ", sex=" + sex
				+ ", birthDay=" + birthDay + ", waitTime=" + waitTime
				+ ", rank=" + rank + ", cardLevel=" + cardLevel + ", manager="
				+ manager + ", customerLabel=" + customerLabel + "]";
	}
	
}
