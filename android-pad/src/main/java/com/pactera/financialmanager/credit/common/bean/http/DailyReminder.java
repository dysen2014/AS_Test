package com.pactera.financialmanager.credit.common.bean.http;

/**
 * Created by dysen on 2017/7/20.
 * 提醒参数
 */

public class DailyReminder {

	/**
	 * ItemNo : 010
	 * name : 日常提醒(贷后)
	 * count : 0
	 */

	private String ItemNo;
	private String name;
	private String count;

	public String getItemNo() {
		return ItemNo;
	}

	public void setItemNo(String ItemNo) {
		this.ItemNo = ItemNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
