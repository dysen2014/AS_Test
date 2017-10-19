package com.pactera.financialmanager.ui.model;
/**
 * 贷款逾期的 javabean  7个字段的
 */
public class SevenFieldEntity {
	private String info1;        	
	private String info2;      	
	private String info3;  	
	private String info4; 	
	private String info5;	
	private String info6;	
	private String info7;
	public String getInfo1() {
		return info1;
	}
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	public String getInfo2() {
		return info2;
	}
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	public String getInfo3() {
		return info3;
	}
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	public String getInfo4() {
		return info4;
	}
	public void setInfo4(String info4) {
		this.info4 = info4;
	}
	public String getInfo5() {
		return info5;
	}
	public void setInfo5(String info5) {
		this.info5 = info5;
	}
	public String getInfo6() {
		return info6;
	}
	public void setInfo6(String info6) {
		this.info6 = info6;
	}
	public String getInfo7() {
		return info7;
	}
	public void setInfo7(String info7) {
		this.info7 = info7;
	}
	
	public SevenFieldEntity() {
		super();
	}
	
	public SevenFieldEntity(String info1, String info2, String info3,
			String info4, String info5, String info6, String info7) {
		super();
		this.info1 = info1;
		this.info2 = info2;
		this.info3 = info3;
		this.info4 = info4;
		this.info5 = info5;
		this.info6 = info6;
		this.info7 = info7;
	}
	
	@Override
	public String toString() {
		return "DaikuanyuqiEntity [info1=" + info1 + ", info2=" + info2
				+ ", info3=" + info3 + ", info4=" + info4 + ", info5=" + info5
				+ ", info6=" + info6 + ", info7=" + info7 + "]";
	}      
	

}
