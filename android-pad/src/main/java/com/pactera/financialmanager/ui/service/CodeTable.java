package com.pactera.financialmanager.ui.service;

import com.pactera.financialmanager.R;

public final class CodeTable {

	private CodeTable() {
	}

	/** 排号机客户级别-普通客户 */
	public static final String[] HF_CRANK_ORDINARY_CUSTOMER = { "" };
	/** 排号机客户级别-VIP客户 */
	public static final String[] HF_CRANK_VIP_CUSTOMER = { "CA", "TG", "03",
			"04", "05" }; //
	public static final Integer[] HF_CRANK_VIP_CUSTOMER_ICON = {
			R.drawable.hf_custlevel11, R.drawable.hf_custlevel12,
			R.drawable.hf_custlevel13, R.drawable.hf_custlevel14,
			R.drawable.hf_custlevel15 };
	//
	public static final Integer[] HF_CRANK_VIP_CUSTOMER_ICON_OTHER = {
		R.drawable.hf_custlevel32, R.drawable.hf_custlevel33,
		R.drawable.hf_custlevel24, R.drawable.hf_custlevel25,
		R.drawable.hf_custlevel26};
	/** 性别 */
	public static final String[] HF_CRANK_CUSTOMER_GENDEER = { "1", "2", "9",
			"" };
	public static final String[] HF_CRANK_CUSTOMER_GENDEER_NAME = { "男", "女",
			"未登记", "未登记" };
	/** stateflag */
	public static final String[] HF_CRANK_CUSTOMER_STATEFLAG = { "0", "1", "2",
			"3" }; // "等候"，"正在办理"，"办理完成"，"办理完成"
	// public static final String[] HF_CRANK_CUSTOMER_STATEFLAG_NAME =
	// {"等候","已叫号"};

	/** 进入贵宾客户原因 */
	public static final String[] HF_CUSTOMERMORE_RSN = { "DP", "LN", "IV", "MN" };
	public static final String[] HF_CUSTOMERMORE_RSN_NAME = { "存款达标", "贷款达标",
			"投资达标", "手工增加" };
	/** 最高贵宾卡类型 */
	public static final String[] HF_CUSTOMERMORE_CARDTYPE = { "", "5", "6", "9" };
	public static final String[] HF_CUSTOMERMORE_CARDTYPE_NAME = { "无贵宾卡",
			"金卡", "白金卡", "钻石卡" };
	/** 客户级别 */
	public static final String[] HF_CUSTOMERMORE_RANK = { "TG", "CA", "03",
			"04", "05", "" };
	public static final String[] HF_CUSTOMERMORE_RANK_NAME = { "目标客户", "可指派客户",
			"金卡", "白金卡", "钻石卡", "普通客户" };
	/** 服务信息 */
	public static final String[] HF_CUSTOMERMORE_SERVCE = { "", "1" };
}
