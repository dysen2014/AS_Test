package com.pactera.financialmanager.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 保存pad请求排队信息返回的数据的JavaBean
 */
public class PadQueueInfoBean implements Serializable{
	
	private static final long serialVersionUID = 2102364607910174778L;
	
	private String machineCode;//叫号机机器号
	private String serialNo;//叫号数据流水号
	private String queueCode;//排队号码
	private String queueType;//排队业务类型
	private String windowNo;//叫号窗口号
	private String operatorNo;//叫号柜员编号
	private String custCode;//客户号
	private String custCHNName;//客户姓名

	private String custLevel;//综合评级
	private String vipLevel;//贵宾卡级别
	private String custSex;//性别
	private String birthMonDay;//生日(月日)
	private String brID;//机构号
	private String onwerID;//客户经理ID
	private String ownerCode;//客户经理用户号
	private String ownerName;//所属客户经理
	private String ownerPhone;//客户经理手机
	private String beginTime;//取号时间
	private String callTime;//叫号时间
	private String endTime;//业务办理结束时间
	private String newQueueType;//分流业务类型
	private String shuntTime;//分流时间
	private String lastUpdateTime;//本条记录最后更新时间


	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getQueueCode() {
		return queueCode;
	}
	public void setQueueCode(String queueCode) {
		this.queueCode = queueCode;
	}
	public String getQueueType() {
		return queueType;
	}
	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}
	public String getWindowNo() {
		return windowNo;
	}
	public void setWindowNo(String windowNo) {
		this.windowNo = windowNo;
	}
	public String getOperatorNo() {
		return operatorNo;
	}
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustCHNName() {
		return custCHNName;
	}
	public void setCustCHNName(String custCHNName) {
		this.custCHNName = custCHNName;
	}

	public String getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
	public String getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getCustSex() {
		return custSex;
	}
	public void setCustSex(String custSex) {
		this.custSex = custSex;
	}
	public String getBirthMonDay() {
		return birthMonDay;
	}
	public void setBirthMonDay(String birthMonDay) {
		this.birthMonDay = birthMonDay;
	}
	public String getBrID() {
		return brID;
	}
	public void setBrID(String brID) {
		this.brID = brID;
	}
	public String getOnwerID() {
		return onwerID;
	}
	public void setOnwerID(String onwerID) {
		this.onwerID = onwerID;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getNewQueueType() {
		return newQueueType;
	}
	public void setNewQueueType(String newQueueType) {
		this.newQueueType = newQueueType;
	}
	public String getShuntTime() {
		return shuntTime;
	}
	public void setShuntTime(String shuntTime) {
		this.shuntTime = shuntTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public PadQueueInfoBean(String machineCode, String serialNo,
			String queueCode, String queueType, String windowNo,
			String operatorNo, String custCode, String custCHNName,
			String custLevel, String vipLevel, String custSex,
			String birthMonDay, String brID, String onwerID, String ownerCode,
			String ownerName, String ownerPhone, String beginTime,
			String callTime, String endTime, String newQueueType,
			String shuntTime, String lastUpdateTime) {
		super();
		this.machineCode = machineCode;
		this.serialNo = serialNo;
		this.queueCode = queueCode;
		this.queueType = queueType;
		this.windowNo = windowNo;
		this.operatorNo = operatorNo;
		this.custCode = custCode;
		this.custCHNName = custCHNName;
		this.custLevel = custLevel;
		this.vipLevel = vipLevel;
		this.custSex = custSex;
		this.birthMonDay = birthMonDay;
		this.brID = brID;
		this.onwerID = onwerID;
		this.ownerCode = ownerCode;
		this.ownerName = ownerName;
		this.ownerPhone = ownerPhone;
		this.beginTime = beginTime;
		this.callTime = callTime;
		this.endTime = endTime;
		this.newQueueType = newQueueType;
		this.shuntTime = shuntTime;
		this.lastUpdateTime = lastUpdateTime;
	}

	public boolean isShunted() {
		return !TextUtils.isEmpty(shuntTime) && !TextUtils.isEmpty(newQueueType);
	}

	public static final int FINISH = 3;
	public static final int CALLED = 2;
	public static final int QUEUEING = 1;

	public int queueStatus() {
		if (!TextUtils.isEmpty(endTime)) {
			return FINISH;
		}
		if (!TextUtils.isEmpty(callTime)) {
			return CALLED;
		}
		return QUEUEING;
	}

	public int getWaitTimeInSecond() {
		try {
			Date sTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime);
			long t = System.currentTimeMillis() - sTime.getTime();
			if (t < 0) return 0;
			return (int)(t / 1000);

		} catch (ParseException e) {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "PadQueueInfoBean [machineCode=" + machineCode + ", serialNo="
				+ serialNo + ", queueCode=" + queueCode + ", queueType="
				+ queueType + ", windowNo=" + windowNo + ", operatorNo="
				+ operatorNo + ", custCode=" + custCode + ", custCHNName="
				+ custCHNName + ", custLevel="
				+ custLevel + ", vipLevel=" + vipLevel + ", custSex=" + custSex
				+ ", birthMonDay=" + birthMonDay + ", brID=" + brID
				+ ", onwerID=" + onwerID + ", ownerCode=" + ownerCode
				+ ", ownerName=" + ownerName + ", ownerPhone=" + ownerPhone
				+ ", beginTime=" + beginTime + ", callTime=" + callTime
				+ ", endTime=" + endTime + ", newQueueType=" + newQueueType
				+ ", shuntTime=" + shuntTime + ", lastUpdateTime="
				+ lastUpdateTime + "]";
	}
	
	public PadQueueInfoBean() {
		super();
	}

	public String uniqKeyInBranchBank() {
		return machineCode + "|" + serialNo;
	}
}
