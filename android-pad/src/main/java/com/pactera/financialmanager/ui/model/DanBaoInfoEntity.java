package com.pactera.financialmanager.ui.model;

import java.io.Serializable;
/**
 * 对外担保信息返回的数据javabean
 */
public class DanBaoInfoEntity implements Serializable {
	private String pkid;//主键
	private String WARRANT_NAME;//担保人名称
	private String INSURE_MAN;//担保人
	private String WARRANT_CONTRACT_NO;//担保合同编号
	private String BUSI_INDUSTRY;//业务种类
	private String START_DT;//起始日期
	private String EXPIRED_DT;//到期日期
	private String WARRANT_AMOUNT;//担保金额
	private String BUSI_ORG;//经办机构
	private String DES;//备注
	public String getPkid() {
		return pkid;
	}
	public void setPkid(String pkid) {
		this.pkid = pkid;
	}
	public String getWARRANT_NAME() {
		return WARRANT_NAME;
	}
	public void setWARRANT_NAME(String wARRANT_NAME) {
		WARRANT_NAME = wARRANT_NAME;
	}

	public String getINSURE_MAN() {
		return INSURE_MAN;
	}

	public void setINSURE_MAN(String INSURE_MAN) {
		this.INSURE_MAN = INSURE_MAN;
	}

	public String getWARRANT_CONTRACT_NO() {
		return WARRANT_CONTRACT_NO;
	}
	public void setWARRANT_CONTRACT_NO(String wARRANT_CONTRACT_NO) {
		WARRANT_CONTRACT_NO = wARRANT_CONTRACT_NO;
	}
	public String getBUSI_INDUSTRY() {
		return BUSI_INDUSTRY;
	}
	public void setBUSI_INDUSTRY(String bUSI_INDUSTRY) {
		BUSI_INDUSTRY = bUSI_INDUSTRY;
	}
	public String getSTART_DT() {
		return START_DT;
	}
	public void setSTART_DT(String sTART_DT) {
		START_DT = sTART_DT;
	}
	public String getEXPIRED_DT() {
		return EXPIRED_DT;
	}
	public void setEXPIRED_DT(String eXPIRED_DT) {
		EXPIRED_DT = eXPIRED_DT;
	}
	public String getWARRANT_AMOUNT() {
		return WARRANT_AMOUNT;
	}
	public void setWARRANT_AMOUNT(String wARRANT_AMOUNT) {
		WARRANT_AMOUNT = wARRANT_AMOUNT;
	}
	public String getBUSI_ORG() {
		return BUSI_ORG;
	}
	public void setBUSI_ORG(String bUSI_ORG) {
		BUSI_ORG = bUSI_ORG;
	}
	public String getDES() {
		return DES;
	}
	public void setDES(String dES) {
		DES = dES;
	}
	
	public DanBaoInfoEntity(String pkid, String wARRANT_NAME,
			String wARRANT_CONTRACT_NO, String bUSI_INDUSTRY, String sTART_DT,
			String eXPIRED_DT, String wARRANT_AMOUNT, String bUSI_ORG,
			String dES) {
		super();
		this.pkid = pkid;
		WARRANT_NAME = wARRANT_NAME;
		WARRANT_CONTRACT_NO = wARRANT_CONTRACT_NO;
		BUSI_INDUSTRY = bUSI_INDUSTRY;
		START_DT = sTART_DT;
		EXPIRED_DT = eXPIRED_DT;
		WARRANT_AMOUNT = wARRANT_AMOUNT;
		BUSI_ORG = bUSI_ORG;
		DES = dES;
	}
	
	public DanBaoInfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "DanBaoInfoEntity [pkid=" + pkid + ", WARRANT_NAME="
				+ WARRANT_NAME + ", WARRANT_CONTRACT_NO=" + WARRANT_CONTRACT_NO
				+ ", BUSI_INDUSTRY=" + BUSI_INDUSTRY + ", START_DT=" + START_DT
				+ ", EXPIRED_DT=" + EXPIRED_DT + ", WARRANT_AMOUNT="
				+ WARRANT_AMOUNT + ", BUSI_ORG=" + BUSI_ORG + ", DES=" + DES
				+ "]";
	}
}
