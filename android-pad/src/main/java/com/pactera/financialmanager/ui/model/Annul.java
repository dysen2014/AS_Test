package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

public class Annul implements Serializable{

	private static final long serialVersionUID = -3355897564168502739L;

	private String CST_ID;
	
	private String CST_NAME;
	
	private String ACT_NO;
	
	private String CANCEL_DT;
	
	private String BRID;

	public String getCST_ID() {
		return CST_ID;
	}

	public void setCST_ID(String cST_ID) {
		CST_ID = cST_ID;
	}

	public String getCST_NAME() {
		return CST_NAME;
	}

	public void setCST_NAME(String cST_NAME) {
		CST_NAME = cST_NAME;
	}

	public String getACT_NO() {
		return ACT_NO;
	}

	public void setACT_NO(String aCT_NO) {
		ACT_NO = aCT_NO;
	}

	public String getCANCEL_DT() {
		return CANCEL_DT;
	}

	public void setCANCEL_DT(String cANCEL_DT) {
		CANCEL_DT = cANCEL_DT;
	}

	public String getBRID() {
		return BRID;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public Annul() {
		super();
	}

	public Annul(String cST_ID, String cST_NAME, String aCT_NO,
			String cANCEL_DT, String bRID) {
		super();
		CST_ID = cST_ID;
		CST_NAME = cST_NAME;
		ACT_NO = aCT_NO;
		CANCEL_DT = cANCEL_DT;
		BRID = bRID;
	}
	
}
