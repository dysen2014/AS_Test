package com.pactera.financialmanager.ui.model;

/**
 * 客户基本信息
 * 
 * @author JAY
 * 
 */
public class PersonInfo {
	public String custID = "";// 客户ID
	public String idv_chn_nm = "";// 客户名称
	public String CUST_TYPE = "";// 客户类型
	public String IDV_BRTH_DT_GL = "";// 出生日期(公历)
	public String IDV_MAR_ST_TP_ID = "";// 婚姻状况
	public String IDV_EDU_TP_ID = "";// 教育程度
	public String SECURITY_INFO = "";// 社保号码
	public String house_info = "";// 住房状况
	public String SOCIAL_POSITION = "";// 社会职务
	public String WORK_SKILL = "";// 劳动技能
	public String RELIGION = "";// 宗教信仰
	public String CUST_TAG = "";// 员工标示
	public String STOCKHOLDER = "";// 是否为我行股东
	public String WORKCORP = "";// 单位名称
	public String PROFESSION = "";// 职业
	public String WORKADD = "";// 工作地址
	public String HEADSHIP = "";// 职务
	public String SERVICE_YEAR = "";// 工作年限
	public String FAMILY_ANUAL_INCOME = "";// 年收入
	public String SALARY_BANK = "";// 工资代发行
	public String IS_WORK = "";// 是否务工
	public String IS_WORK_LEADER = "";// 是否务工带头人
	public String WORK_LEADER_PHONE = "";// 务工带头人电话
	public String WORK_LEADER_NAME = "";// 务工带头人姓名
	public String MERCHANT_NAME = "";// 商户名称
	public String BUSI_SCALE = "";// 经营规模
	public String busi_project = "";// 经营项目
	public String is_rel_farm = "";// 是否涉农
	public String is_rel_restrict = "";// 是否限制性行业
	public String has_patent = "";// 是否有专利
	public String patent_desc = "";// 专利描述
	public String busi_addr = "";// 经营地址
	public String invest_money = "";// 投入资金
	public String IDV_HLT_ST_TP_ID = "";// 健康状况
	public String HOBBY = "";// 兴趣爱好
	public String year_earning = "";// 年经营收入
    public String NATIVEADD = "";//身份证地址
	public String UNITKIND = "";//单位所属行业
	public String OPEN_TIME = "";//开业日期
	public String NATIVE_PLACE = "";// 籍贯
	public String IDV_NATIONALITY ="";// 民族
	public String phone_no="";//联系电话
	@Override
	public String toString() {
		return "{\"custID\":\"" + custID + "\",\"idv_chn_nm\":\"" + idv_chn_nm
				+ "\",\"CUST_TYPE\":\"" + CUST_TYPE
				+ "\",\"NATIVEADD\":\"" + NATIVEADD
				+ "\",\"UNITKIND\":\"" + UNITKIND
				+ "\",\"OPEN_TIME\":\"" + OPEN_TIME
				+ "\",\"NATIVE_PLACE\":\"" + NATIVE_PLACE
				+ "\",\"IDV_NATIONALITY\":\"" + IDV_NATIONALITY
				+ "\",\"phone_no\":\"" + phone_no
				+ "\",\"IDV_BRTH_DT_GL\":\"" + IDV_BRTH_DT_GL
				+ "\",\"IDV_MAR_ST_TP_ID\":\"" + IDV_MAR_ST_TP_ID
				+ "\",\"IDV_EDU_TP_ID\":\"" + IDV_EDU_TP_ID
				+ "\",\"SECURITY_INFO\":\"" + SECURITY_INFO
				+ "\",\"house_info\":\"" + house_info
				+ "\",\"SOCIAL_POSITION\":\"" + SOCIAL_POSITION
				+ "\",\"WORK_SKILL\":\"" + WORK_SKILL + "\",\"RELIGION\":\""
				+ RELIGION + "\",\"CUST_TAG\":\"" + CUST_TAG
				+ "\",\"STOCKHOLDER\":\"" + STOCKHOLDER + "\",\"WORKCORP\":\""
				+ WORKCORP + "\",\"PROFESSION\":\"" + PROFESSION
				+ "\",\"WORKADD\":\"" + WORKADD + "\",\"HEADSHIP\":\""
				+ HEADSHIP + "\",\"SERVICE_YEAR\":\"" + SERVICE_YEAR
				+ "\",\"FAMILY_ANUAL_INCOME\":\"" + FAMILY_ANUAL_INCOME
				+ "\",\"SALARY_BANK\":\"" + SALARY_BANK + "\",\"IS_WORK\":\""
				+ IS_WORK + "\",\"IS_WORK_LEADER\":\"" + IS_WORK_LEADER
				+ "\",\"WORK_LEADER_PHONE\":\"" + WORK_LEADER_PHONE
				+ "\",\"WORK_LEADER_NAME\":\"" + WORK_LEADER_NAME
				+ "\",\"MERCHANT_NAME\":\"" + MERCHANT_NAME
				+ "\",\"BUSI_SCALE\":\"" + BUSI_SCALE
				+ "\",\"busi_project\":\"" + busi_project
				+ "\",\"is_rel_farm\":\"" + is_rel_farm
				+ "\",\"is_rel_restrict\":\"" + is_rel_restrict
				+ "\",\"has_patent\":\"" + has_patent + "\",\"patent_desc\":\""
				+ patent_desc + "\",\"busi_addr\":\"" + busi_addr
				+ "\",\"invest_money\":\"" + invest_money
				+ "\",\"IDV_HLT_ST_TP_ID\":\"" + IDV_HLT_ST_TP_ID
				+ "\",\"HOBBY\":\"" + HOBBY + "\",\"year_earning\":\""
				+ year_earning + "\"}";
	}

}
