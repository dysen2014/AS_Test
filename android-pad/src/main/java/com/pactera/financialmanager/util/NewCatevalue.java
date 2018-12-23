package com.pactera.financialmanager.util;

import android.content.Context;

import com.pactera.financialmanager.db.CatevalueDao;
import com.pactera.financialmanager.entity.CatevalueEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 各种码值类
 * 
 * @author JAY
 * 
 */
public class NewCatevalue {

	public static String credit_acc_status = "credit_acc_status";// 信用卡账户状态
	public static String credit_frz_status = "credit_frz_status";// 信用卡冻结原因
	public static String credit_audit_pro = "credit_audit_pro";// 信用卡审批进度
	public static String eventType = "eventType";// 提醒类型
	public static String ent_other_rel = "ent_other_rel";// 关联关系
	public static String CONTACT_SEX = "CONTACT_SEX";// 性别
	public static String signBank = "signBank";// 签约银行
	public static String orgLayOutType_03 = "orgLayOutType_03";// 首页布局类型
	public static String orgLayOutType2 = "orgLayOutType2";// 首页布局类型
	public static String successFlag = "successFlag";// 成功标志
	public static String archiveStatus = "archiveStatus";// 建档状态
	public static String userLoginStatus = "userLoginStatus";// 用户登录状态
	public static String money_settle_type = "money_settle_type";// 常用资金结算方式
	public static String CERTTPALL = "CERTTPALL";// 所以证件类型
	public static String credit_card_status = "credit_card_status";// 信用卡卡状态
	public static String fkstatus = "fkstatus";// 处理状态
	public static String isFirstSelected = "isFirstSelected";// 是否首选
	public static String isValid = "isValid";// 是否有效
	public static String relType = "relType";// 关系人类型
	public static String CUSTVALUELEVEL = "CUSTVALUELEVEL";// 个人客户价值评级
	public static String ent_cust_value_level = "ent_cust_value_level";// 个人客户价值评级
	public static String csStatus = "csStatus";// 执行状态
	public static String RETNFS = "RETNFS";// 还款方式
	public static String LayOutType1 = "LayOutType1";// 首页布局类型
	public static String subStatus = "subStatus";// 提交状态
	public static String estateType = "estateType";// 不动产类型
	public static String appointReqName = "appointReqName";// 金融需求名称
	public static String depositBank = "depositBank";// 存款银行
	public static String reglious_fate = "reglious_fate";// 宗教信仰
	public static String product_acct_type = "product_acct_type";// 产品类型
	public static String product_int_cat = "product_int_cat";// 产品子类型
	public static String fltType = "fltType";// 报表类型
	public static String serveMode = "serveMode";// 服务方式
	public static String serveType = "serveType";// 客户服务类型
	public static String evrType = "evrType";// 事件类型
	public static String fltType2 = "fltType2";// 报表类型
	public static String fltType3 = "fltType3";// 报表类型
	public static String product_class = "product_class";// 所属分类
	public static String product_status = "product_status";// 产品状态
	public static String product_cust_type = "product_cust_type";// 客户类型
	public static String product_dlyh = "product_dlyh";// 代理银行
	public static String product_cpzl = "product_cpzl";// 产品种类
	public static String product_cplx = "product_cplx";// 产品类型
	public static String product_sykhlx = "product_sykhlx";// 适用客户类型
	public static String product_cpfxdj = "product_cpfxdj";// 产品风险等级
	public static String product_zjtx = "product_zjtx";// 资金投向
	public static String product_bus_scope = "product_bus_scope";// 业务范围
	public static String sale_way = "sale_way";// 销售渠道
	public static String udayStatus = "udayStatus";// 工作日状态
	public static String imports = "import";// 重要性
	public static String urgency = "urgency";// 紧急程度
	public static String ntcType = "ntcType";// 通知类型
	public static String custType = "custType";// 客户类型
	public static String custLayOutType = "custLayOutType";// 首页布局类型
	public static String orgLayOutType = "orgLayOutType";// 首页布局类型
	public static String prjStatus = "prjStatus";// 营销活动状态
	public static String traces = "traces";// 跟踪内容
	public static String loanType = "loanType";// 贷款方式
	public static String contactType = "contactType";// 联系方式
	public static String contactResult = "contactResult";// 联系结果
	public static String saleProduct = "saleProduct";// 联系结果
	public static String saleResult = "saleResult";// 营销结果
	public static String custResponse = "custResponse";// 客户意见
	public static String brLevel = "brLevel";// 机构层级
	public static String ifP = "ifP";// 岗位公私标志
	public static String prjType = "prjType";// 营销活动类型
	public static String userComment = "userComment";// 客户经理评价
	public static String failReason = "failReason";// 失败原因
	public static String nextWork = "nextWork";// 下一步营销安排
	public static String gardenType = "gardenType";// 园区类型
	public static String commerceType = "commerceType";// 商区类型
	public static String filingStatus = "filingStatus";// 建档状态
	public static String transType = "transType";// 异动类型
	public static String EDUEXPERIENCE = "EDUEXPERIENCE";// 最高学历
	public static String fin_status = "fin_status";// 状态
	public static String debitCardType = "debitCardType";// 贵宾卡级别
	public static String loan_five_lv = "loan_five_lv";// 贷款五级分类
	public static String loan_four_lv = "loan_four_lv";// 贷款四级形态
	public static String signType = "signType";// 签约类型
	public static String sign_sp = "sign_sp";// 签约渠道
	public static String hw_sign_status = "hw_sign_status";// 签约状态
	public static String loanCate = "loanCate";// 贷款种类
	public static String depositPeriod = "depositPeriod";// 存款期限
	public static String profession = "profession";// 职业
	public static String dataSourceType = "dataSourceType";// 数据来源
	public static String addrType = "addrType";// 地址类型
	public static String conType = "conType";// 联系类型
	public static String sendType = "sendType";// 发送方式
	public static String prjSuccMark = "prjSuccMark";// 营销活动参数
	public static String movableType = "movableType";// 动产类型
	public static String companyProperty = "companyProperty";// 单位性质
	public static String CRCYCD = "CRCYCD";// 币种
	public static String loyal_level = "loyal_level";// 忠诚度
	public static String orgLayOutType_01 = "orgLayOutType_01";// 首页布局类型
	public static String loanCateType = "loanCateType";// 种类
	public static String loanUseType = "loanUseType";// 用途
	public static String warrantType = "warrantType";// 可提供的担保方式
	public static String out_warrant_type = "out_warrant_type";// 业务种类
	public static String turnOverType = "turnOverType";// 周转期限
	public static String complainType = "complainType";// 类型
	public static String CUSTLEVEL = "CUSTLEVEL";// 客户VIP等级
	public static String tradeMode = "tradeMode";// 交易方式
	public static String depositCate = "depositCate";// 种类
	public static String appointCate = "appointCate";// 预约管理需求类型
	public static String key_mem_type = "key_mem_type";// 关系类型
	public static String custClass = "custClass";// 客户类别
	public static String custProperty = "custProperty";// 客户性质
	public static String cultivate_dire = "cultivate_dire";// 培植方向
	public static String Marriage = "Marriage";// 婚姻状况
	public static String DUTY = "DUTY";// 职务
	public static String depositType = "depositType";// 存款类型
	public static String psnOrOrgFlag = "psnOrOrgFlag";// 对公对私标记
	public static String stock_type = "stock_type";// 股东类型
	public static String custClassType = "custClassType";// 客户类别
	public static String CERTTP = "CERTTP";// 证件类型     个人情况
	public static String C_CERTTP = "C_CERTTP";// 证件类型   团体及组织情况
	public static String busi_condition = "busi_condition";// 经营状况
	public static String ent_reg_inf_type = "ent_reg_inf_type";// 注册信息类型
	public static String ent_break_type = "ent_break_type";// 违约类型
	public static String ent_break_status = "ent_break_status";// 当前状态
	public static String psn_other_cate = "psn_other_cate";// 种类
	public static String psn_rel_busi_info = "psn_rel_busi_info";// 与我行业务往来信息
	public static String acci_type = "acci_type";// 事件类型
	public static String sportType1 = "sporttype1";// 兴趣爱好
	public static String sportType= "sportType";
	public static String isFlag = "isFlag";// 是否标志
	public static String req_cate_type = "req_cate_type";// 需求分类
	public static String estate_name_type = "estate_name_type";// 不动产名称
	public static String psn_social_position = "psn_social_position";// 社会职务
	public static String amunsementType = "amunsementType";// 客户爱好
	public static String accountStatus = "accountStatus";// 客户账户状态
	public static String bondType = "bondType";// 债劵类型
	public static String cst_type = "cst_type";// 客户类型
	public static String attachType = "attachType";// 附件类型
	public static String grouppro = "grouppro";// 群组性质
	public static String grouptype = "grouptype";// 群组种类
	public static String yorn = "yorn";// 是否
	public static String native_place = "native_place";// 籍贯
	public static String riskBear = "riskBear";// 风险能力承受
	public static String brStatus = "brStatus";// 机构状态
	public static String approveAdvice = "approveAdvice";// 审批意见
	public static String applyStatus1 = "applyStatus1";// 申领状态1
	public static String otherLayOutType = "otherLayOutType";// 首页布局类型
	public static String userValidStatus = "userValidStatus";// 员工状态
	public static String userLockStatus = "userLockStatus";// 用户是否锁定
	public static String tempType = "tempType";// 短信模板类型
	public static String smstmpStatus = "smstmpStatus";// 短信模板状态
	public static String smsStatus = "smsStatus";// 短信任务状态
	public static String smsStatusreply = "smsStatusreply";// 短信任务状态
	public static String group_rel_type = "group_rel_type";// 关联关系
	public static String evrType_psn = "evrType_psn";// 事件类型
	public static String evrType_ent = "evrType_ent";// 事件类型
	public static String enterScale = "enterScale";// 企业规模
	public static String industy_sec = "industy_sec";// 行业类型二
	public static String phoneType = "phoneType";// 联系类型
	public static String orgLayOutType_05 = "orgLayOutType_05";// 首页布局类型
	public static String houseCondition = "houseCondition";// 住房状况
	public static String my_bank_prd = "my_bank_prd";// 使用我行产品
	public static String corptp = "corptp";// 所属行业
	public static String healthStatus = "healthStatus";// 健康状况
	public static String fundingType = "fundingType";// 出资方式
	public static String sysPara = "sysPara";// 系统参数
	public static String fltOp = "fltOp";// 过滤条件符
	public static String nation_code = "nation_code";// 民族
	public static String finFavoriteType = "finFavoriteType";// 投资偏好
	public static String signinfo_status = "signinfo_status";// 签约信息签约状态
	public static String signinfo_type = "signinfo_type";// 签约信息签约类型
	public static String areaType = "areaType";// 区域类型
	public static String busiStatus = "busiStatus";// 商机状态
	public static String OrgType = "OrgType";// 企业性质
	public static String orgLayOutType_07 = "orgLayOutType_07";// 首页布局类型
	public static String A_CERTTP = "A_CERTTP";// 证件类型


	//金融需求
	public static String CHLTYPE = "chltype";// 渠道
	public static String CERTTYPE = "CERTTYPE";// 证件类型
	public static String DEMDTYP = "DEMDTYP";// 需求类型（主类）
	public static String SUBDEMDTYP = "SUBDEMDTYP";//需求类型（子类）
	public static String STATUS = "STATUS";// 处理状态


	public static String kindType = "kindType";// 电子产品新--对私
	public static String kindTypeEn = "kindTypeEn";// 电子产品新--对公

	public static String applyForDeploy = "applyForDeploy";// 申请调配






	/**
	 * 根据码值获取
	 * 
	 * @param context
	 *            当前activity
	 * @param CatevalueCode
	 *            码值类型
	 * @param index
	 *            码值
	 * @return 码值所对应信息
	 */
	public static String getLabel(Context context, String CatevalueCode,
			String index) {
		String returnInfo = "";
		CatevalueDao theCatevalue = new CatevalueDao(context);
		returnInfo = theCatevalue.getLabel(CatevalueCode, index);
		theCatevalue.closeDataBase();
		return returnInfo;
	}

	/**
	 * 查询大类所有码值信息
	 * 
	 * @param context
	 *            当前activity
	 * @param CatevalueCode
	 *            码值大类
	 * @return
	 */
	public static List<CatevalueEntity> getCatevalue(Context context,
			String CatevalueCode) {
		CatevalueDao theCatevalue = new CatevalueDao(context);
		List<CatevalueEntity> returnInfo = new ArrayList<CatevalueEntity>();
		returnInfo = theCatevalue.getCatevalue(CatevalueCode);
		theCatevalue.closeDataBase();
		return returnInfo;
	}

	/**
	 * 返回码值
	 * 
	 * @param index
	 * @param key
	 *            码值ID
	 * @param value
	 *            码值
	 * @return
	 */
	public static String getValue(String index, String[] key, String[] value) {
		if (index == null) {
			return "";
		}

		for (int i = 0; i < key.length; i++) {

			try {
				if (index.equals(key[i])) {
					return value[i];
				}
			} catch (Exception e) {
				return "";
			}

		}
		return "";
	}
	public static String getValue(String index, List<CatevalueEntity> item) {
		if (index == null) {
			return "";
		}

		for (int i = 0; i < item.size(); i++) {

			try {
				if (index.equals(item.get(i).getValue())) {
					return item.get(i).getLabel();
				}
			} catch (Exception e) {
				return "";
			}

		}
		return "";
	}
}
