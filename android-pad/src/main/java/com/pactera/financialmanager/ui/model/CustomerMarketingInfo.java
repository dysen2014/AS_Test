package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

public class CustomerMarketingInfo implements Serializable {
	/**
	 * 客户营销信息
	 */

	private static final long serialVersionUID = -1887117695982564552L;
	public String custID; // 客户ID
	public String prdtID; // 产品代码
	public String prdtNam; // 产品名称
	public String prdtRsn; // 推荐原因
	public String appMSG; // 营销话术
}
