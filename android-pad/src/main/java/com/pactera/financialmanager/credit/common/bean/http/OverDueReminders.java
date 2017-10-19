package com.pactera.financialmanager.credit.common.bean.http;

/**
 * Created by dysen on 2017/7/21.
 * 逾期 bean
 */

public class OverDueReminders {

		/**
		 * BalanceItemNo : 030
		 * name : 逾期 7天（含）以内
		 * OverDayItemNo : 07010
		 * count : 0
		 */

		private String BalanceItemNo;
		private String name;
		private String OverDayItemNo;
		private String count;

	public String getBalanceItemNo() {
		return BalanceItemNo;
	}

	public OverDueReminders setBalanceItemNo(String balanceItemNo) {
		BalanceItemNo = balanceItemNo;
		return this;
	}

	public String getName() {
		return name;
	}

	public OverDueReminders setName(String name) {
		this.name = name;
		return this;
	}

	public String getOverDayItemNo() {
		return OverDayItemNo;
	}

	public OverDueReminders setOverDayItemNo(String overDayItemNo) {
		OverDayItemNo = overDayItemNo;
		return this;
	}

	public String getCount() {
		return count;
	}

	public OverDueReminders setCount(String count) {
		this.count = count;
		return this;
	}
}
