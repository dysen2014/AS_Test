package com.pactera.financialmanager.credit.common.bean.http;

/**
 * Created by dysen on 2017/8/28.
 */

public class BeanHeader {

	private String NAME;
	private String ID;

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public class AfterManagementBean{

		/**
		 * NAME : 检查截止日
		 * ID : InspectDate
		 * SORT : 0
		 */

		private String NAME;
		private String ID;
		private int SORT;

		public String getNAME() {
			return NAME;
		}

		public void setNAME(String NAME) {
			this.NAME = NAME;
		}

		public String getID() {
			return ID;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public int getSORT() {
			return SORT;
		}

		public void setSORT(int SORT) {
			this.SORT = SORT;
		}
	}
}
