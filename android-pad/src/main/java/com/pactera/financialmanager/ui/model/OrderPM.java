package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户经理排名
 * @author Administrator
 *
 */
public class OrderPM implements Serializable{

	private static final long serialVersionUID = -7882140446196217331L;

	private String OWNERID;		// 客户经理id
	
	private String OWNERNAME;	// 客户经理名字
	
	private String BAL;			// 金额 
	
	private String RANK;		// 排名  

	public String getOWNERID() {
		return OWNERID;
	}

	public void setOWNERID(String oWNERID) {
		OWNERID = oWNERID;
	}

	public String getOWNERNAME() {
		return OWNERNAME;
	}

	public void setOWNERNAME(String oWNERNAME) {
		OWNERNAME = oWNERNAME;
	}

	public String getBAL() {
		return BAL;
	}

	public void setBAL(String bAL) {
		BAL = bAL;
	}

	public String getRANK() {
		return RANK;
	}

	public void setRANK(String rANK) {
		RANK = rANK;
	}

	public OrderPM(String oWNERID, String oWNERNAME, String bAL, String rANK) {
		super();
		OWNERID = oWNERID;
		OWNERNAME = oWNERNAME;
		BAL = bAL;
		RANK = rANK;
	}

	public OrderPM() {
		super();
	}
	
}
