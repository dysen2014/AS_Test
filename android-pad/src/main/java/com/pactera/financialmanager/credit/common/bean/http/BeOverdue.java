package com.pactera.financialmanager.credit.common.bean.http;

/**
 * Created by dysen on 2017/8/4.
 */

public class BeOverdue {

	/**
	 * ItemNo : 010
	 * name : 金额(逾期)
	 * count : 0
	 */

	private String itemno;
	private String name;
	private String count;

	public String getItemno() {
		return itemno;
	}

	public BeOverdue setItemno(String itemno) {
		this.itemno = itemno;
		return this;
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
