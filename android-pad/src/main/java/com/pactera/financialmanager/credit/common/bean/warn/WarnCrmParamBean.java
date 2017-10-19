package com.pactera.financialmanager.credit.common.bean.warn;

/**
 * Created by dysen on 2017/9/5.
 */

public class WarnCrmParamBean {
	/**
	 * TITLENAME : 贷款
	 * ID : 1
	 * SUBTYPE : dk
	 * rows : 5
	 */

	private String TITLENAME;
	private String ID;
	private String SUBTYPE;
	private String rows;

	public String getTITLENAME() {
		return TITLENAME;
	}

	public void setTITLENAME(String TITLENAME) {
		this.TITLENAME = TITLENAME;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getSUBTYPE() {
		return SUBTYPE;
	}

	public void setSUBTYPE(String SUBTYPE) {
		this.SUBTYPE = SUBTYPE;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
	public static class DefaultParamBean {
		/**
		 * TITLENAME : 贷款
		 * ID : 1
		 * SUBTYPE : dk
		 * rows : 5
		 */

		private String TITLENAME;
		private String ID;
		private String SUBTYPE;
		private String rows;

		public String getTITLENAME() {
			return TITLENAME;
		}

		public void setTITLENAME(String TITLENAME) {
			this.TITLENAME = TITLENAME;
		}

		public String getID() {
			return ID;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public String getSUBTYPE() {
			return SUBTYPE;
		}

		public void setSUBTYPE(String SUBTYPE) {
			this.SUBTYPE = SUBTYPE;
		}

		public String getRows() {
			return rows;
		}

		public void setRows(String rows) {
			this.rows = rows;
		}
	}
	public static class TransactionParamBean{

	}
	public static class CareParamBean{

	}

	public static class FinancialNeedsParamBean{

	}

	public static class ProductParamBean{

	}
}
