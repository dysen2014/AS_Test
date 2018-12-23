package com.pactera.financialmanager.credit.common.bean.warn;

/**
 * Created by dysen on 2017/9/5.
 */

public class WarnCrmParamBean {



	public class TitleBean{

		/**
		 * TITLENAME : 贷款预约
		 * ID : 1
		 * TYPE : dkyy
		 * rows : 0
		 */

		private String TITLENAME;
		private String ID;
		private String TYPE;
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

		public String getTYPE() {
			return TYPE;
		}

		public void setTYPE(String TYPE) {
			this.TYPE = TYPE;
		}

		public String getRows() {
			return rows;
		}

		public void setRows(String rows) {
			this.rows = rows;
		}
	}

	public class TabBean{

		/**
		 * TITLENAME : 账户异动
		 * ID :
		 * SUBTYPE : zhyd
		 * rows : 0
		 */

		private String TITLENAME;
		private String ID;
		private String SUBTYPE;
		private String rows;
		private String TYPE;

		public String getTYPE() {
			return TYPE;
		}

		public void setTYPE(String TYPE) {
			this.TYPE = TYPE;
		}

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

	public static class creditCount{


		/**
		 * ItemNo : 01001
		 * ItemName : 逾期贷款
		 * count : 10
		 */

		private String ItemNo;
		private String ItemName;
		private String count;

		public String getItemNo() {
			return ItemNo;
		}

		public void setItemNo(String ItemNo) {
			this.ItemNo = ItemNo;
		}

		public String getItemName() {
			return ItemName;
		}

		public void setItemName(String ItemName) {
			this.ItemName = ItemName;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}
	}

}
