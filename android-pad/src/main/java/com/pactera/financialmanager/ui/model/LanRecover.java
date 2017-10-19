package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 贷款回收情况实体类
 * @author Administrator
 *
 */
public class LanRecover implements Serializable{

	private static final long serialVersionUID = 8108187110376129889L;

	private String PSN_LON_CREDIT_AMT; 		// 个人当年到期贷款总金额          
	
	private String PSN_LON_Y_DEP_MADD;		// 个人当年到期贷款收回总金额      
	
	private String PSN_LON_Y_DEP_N_MADD;	// 个人当年到期贷款未收回总金额    
	 
	private String ENT_LON_CREDIT_AMT;		// 对公当年到期贷款总金额       
	
	private String ENT_LON_Y_DEP_MADD;		// 对公当年到期贷款收回总金额   
	
	private String ENT_LON_Y_DEP_N_MADD;	// 对公当年到期贷款未收回总金额 

	public String getPSN_LON_CREDIT_AMT() {
		return PSN_LON_CREDIT_AMT;
	}

	public void setPSN_LON_CREDIT_AMT(String pSN_LON_CREDIT_AMT) {
		PSN_LON_CREDIT_AMT = pSN_LON_CREDIT_AMT;
	}

	public String getPSN_LON_Y_DEP_MADD() {
		return PSN_LON_Y_DEP_MADD;
	}

	public void setPSN_LON_Y_DEP_MADD(String pSN_LON_Y_DEP_MADD) {
		PSN_LON_Y_DEP_MADD = pSN_LON_Y_DEP_MADD;
	}

	public String getPSN_LON_Y_DEP_N_MADD() {
		return PSN_LON_Y_DEP_N_MADD;
	}

	public void setPSN_LON_Y_DEP_N_MADD(String pSN_LON_Y_DEP_N_MADD) {
		PSN_LON_Y_DEP_N_MADD = pSN_LON_Y_DEP_N_MADD;
	}

	public String getENT_LON_CREDIT_AMT() {
		return ENT_LON_CREDIT_AMT;
	}

	public void setENT_LON_CREDIT_AMT(String eNT_LON_CREDIT_AMT) {
		ENT_LON_CREDIT_AMT = eNT_LON_CREDIT_AMT;
	}

	public String getENT_LON_Y_DEP_MADD() {
		return ENT_LON_Y_DEP_MADD;
	}

	public void setENT_LON_Y_DEP_MADD(String eNT_LON_Y_DEP_MADD) {
		ENT_LON_Y_DEP_MADD = eNT_LON_Y_DEP_MADD;
	}

	public String getENT_LON_Y_DEP_N_MADD() {
		return ENT_LON_Y_DEP_N_MADD;
	}

	public void setENT_LON_Y_DEP_N_MADD(String eNT_LON_Y_DEP_N_MADD) {
		ENT_LON_Y_DEP_N_MADD = eNT_LON_Y_DEP_N_MADD;
	}

	public LanRecover(String pSN_LON_CREDIT_AMT, String pSN_LON_Y_DEP_MADD,
			String pSN_LON_Y_DEP_N_MADD, String eNT_LON_CREDIT_AMT,
			String eNT_LON_Y_DEP_MADD, String eNT_LON_Y_DEP_N_MADD) {
		super();
		PSN_LON_CREDIT_AMT = pSN_LON_CREDIT_AMT;
		PSN_LON_Y_DEP_MADD = pSN_LON_Y_DEP_MADD;
		PSN_LON_Y_DEP_N_MADD = pSN_LON_Y_DEP_N_MADD;
		ENT_LON_CREDIT_AMT = eNT_LON_CREDIT_AMT;
		ENT_LON_Y_DEP_MADD = eNT_LON_Y_DEP_MADD;
		ENT_LON_Y_DEP_N_MADD = eNT_LON_Y_DEP_N_MADD;
	}

	public LanRecover() {
		super();
	}
	
}
