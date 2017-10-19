package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 只有四个字段的javabean
 */
public class FourFieldEntity implements Serializable{
	private String info1;
	private String info2;
	private String info3;
	private String info4;

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

	public FourFieldEntity(String info1, String info2, String info3,
			String info4) {
		super();
		this.info1 = info1;
		this.info2 = info2;
		this.info3 = info3;
		this.info4 = info4;
	}

	public FourFieldEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "FourFieldEntity [info1=" + info1 + ", info2=" + info2
				+ ", info3=" + info3 + ", info4=" + info4 + "]";
	}

}
