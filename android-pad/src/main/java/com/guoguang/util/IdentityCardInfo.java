package com.guoguang.util;

import java.util.Date;

public class IdentityCardInfo {
	private boolean result;
	private String resultMsg;
	private String name;
	private String sex;
	private String nation;
	private Date birth;
	private String address;
	private String issueDepartment;
	private String number;
	private byte[] photo;
	private String startDate;
	private String endDate;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	private Date usefulLifeEnd;
	private Date usefulLifeStart;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIssueDepartment() {
		return issueDepartment;
	}
	public void setIssueDepartment(String issueDepartment) {
		this.issueDepartment = issueDepartment;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getUsefulLifeEnd() {
		return usefulLifeEnd;
	}
	public void setUsefulLifeEnd(Date usefulLifeEnd) {
		this.usefulLifeEnd = usefulLifeEnd;
	}
	public Date getUsefulLifeStart() {
		return usefulLifeStart;
	}
	public void setUsefulLifeStart(Date usefulLifeStart) {
		this.usefulLifeStart = usefulLifeStart;
	}

}
