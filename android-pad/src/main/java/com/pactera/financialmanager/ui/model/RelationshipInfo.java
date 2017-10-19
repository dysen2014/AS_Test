package com.pactera.financialmanager.ui.model;

/**
 * 客户关系人
 * 
 * @author JAY
 * 
 */
public class RelationshipInfo {

	public String pkid = "";// 主键
	public String custid = "";// 客户ID
	public String rel_name = "";// 关系人姓名
	public String rel_type = "";// 关系人类型
	public String sex = "";// 性别
	public String birth_day = "";// 出生日期
	public String rel_phone = "";// 联系电话
	public String rel_cer_no = "";// 关系人证件号码
	public String education = "";// 教育程度
	public String work_unit = "";// 工作单位
	public String work_profession = "";// 单位类别
	public String position = "";// 职位
	public String occupation = "";// 职业
	public String entry_date = "";// 入职时间
	public String main_earning = "";// 主要经济来源
	public String other_earning = "";// 其他经济来源
	public String monthly_income = "";// 月收入
	public String is_client = "";// 是否为本行客户
	public String is_holder = "";// 是否为本行股东

	@Override
	public String toString() {
		return "{\"pkid\":\""+pkid+"\",\"custid\":\"" + custid + "\",\"rel_name\":\"" + rel_name
				+ "\",\"rel_type\":\"" + rel_type + "\",\"sex\":\"" + sex+ "\",\"position\":\"" + position
				+ "\",\"birth_day\":\"" + birth_day + "\",\"rel_phone\":\""
				+ rel_phone + "\",\"rel_cer_no\":\"" + rel_cer_no
				+ "\",\"education\":\"" + education + "\",\"work_unit\":\""
				+ work_unit + "\",\"work_profession\":\"" + work_profession
				+ "\",\"occupation\":\"" + occupation + "\",\"entry_date\":\""
				+ entry_date + "\",\"main_earning\":\"" + main_earning
				+ "\",\"other_earning\":\"" + other_earning
				+ "\",\"monthly_income\":\"" + monthly_income
				+ "\",\"is_client\":\"" + is_client + "\",\"is_holder\":\""
				+ is_holder + "\"}";
	}
}
