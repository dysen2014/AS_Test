package com.pactera.financialmanager.util;

/**
 * 各种码值类
 * 
 * @author JAY
 * 
 */
public class Catevalue {
	/** 性别 */
	public static String[] sexID = { "01", "02", "03" };
	public static String[] sexStr = { "男", "女", "未知" };

//	/** 个人证件类型 */
//	public static String[] identTypePersonID = { "01", "02", "03", "04", "05",
//			"06", "07", "08", "09", "10", "11" };
//	public static String[] identTypePersonStr = { "居民身份证", "临时身份证", "护照",
//			"户口簿", "军人身份证", "武装警察身份证", "港澳台居民往来内地通行证", "外交人员身份证", "外国人居留许可证",
//			"边民出入境通行证", "其它" };

//	/** 企业证件类型 */
//	public static String[] identTypeCompanyID = { "20", "21", "22", "23", "24",
//			"25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
//			"36", "37", "38", "39", "40", "41", "42", "43", "44" };
//	public static String[] identTypeCompanyStr = { "客户群", "企业法人营业执照", "企业营业执照",
//			"政府人事部门或编制委员会的批文", "事业单位法人登记证书", "财政部门同意其开户的证明", "社会团体登记证书",
//			"上级主管部门批文或证明", "工会法人资格证书", "民办非企业登记证书", "驻华机构登记证", "个体工商户营业执照",
//			"企业名称预先核准通知书", "组织机构代码证", "国税登记证", "地税登记证", "批准证书", "进出口业务资格证书",
//			"外贸易经营者备案登记表", "金融许可证", "外汇业务核准件", "外汇登记证", "开户许可证", "办学许可证", "relevance_other" };


//	/** 学历 */
//	public static String[] educationID = { "1", "2", "3", "4", "5", "6", "7",
//			"8", "9" };
//	public static String[] educationStr = { "研究生", "大学本科", "大学专科", "中等专业学校",
//			"技术学校", "高中", "初中", "小学", "文盲或半文盲" };

//	/** 教育程度 */
//	public static String[] eduID = { "0", "1", "2", "3", "4" };
//	public static String[] eduStr = { "名誉博士", "博士", "硕士", "学士", "relevance_other" };

//	/** 个人客户住房状况 */
//	public static String[] houseConditionID = { "0", "1", "2", "3", "4" };
//	public static String[] houseConditionStr = { "自置", "按揭", "亲属楼宇", "租房",
//			"共有住宅" };

//	/** 签约银行 */
//	public static String[] signBankID = {"00", "01", "02", "03", "04", "05", "06",
//			"07", "08", "09", "10", "11" ,"12"};
//	public static String[] signBankStr = {"湖北农信社", "中国银行", "中国工商银行", "中国建设银行",
//			"中国农业银行", "招商银行", "中信银行", "华夏银行", "广发银行", "湖北银行", "汉口银行",
//			"武汉农村商业银行", "其它银行"};

//	/** 资本构成信息股东类型 */
//	public static String[] stock_typeID = { "01", "02" };
//	public static String[] stock_typeStr = { "企业", "个人" };

//	/** 个人客户职务 */
//	public static String[] dutyID = { "0", "1", "2", "3", "4" };
//	public static String[] dutyStr = { "高级领导", "中级领导", "一般员工", "未知" };

//	/** 个人客户客户类型 */
//	public static String[] custClassID = { "01", "02", "03", "04", "05", "06",
//			"07", "08", "09" };
//	public static String[] custClassStr = { "农村经营大户", "家庭农场", "一般农户", "外出创业人员",
//			"外出务工人员", "公务员、事业单位员工", "社区居民", "个体工商户", "relevance_other" };

//	/** 关键成员关系类型 */
//	public static String[] reltypeID = { "0101", "0102", "0103", "0104",
//			"0105", "0106", "0107", "0108", "0109", "0110" };
//	public static String[] reltypeStr = { "法人代表/单位负责人", "公司董事长",
//			"公司总经理/厂长/CEO", "财务主管/CFO", "授权经办人", "部门经理", "董事", "监事", "实际控制人",
//			"股东" };

//	/** 个人客户关系人类型 */
//	public static String[] relationshipID = { "0301", "0302", "0303", "0304",
//			"0305", "0306", "0309" };
//	public static String[] relationshipStr = { "夫妻关系", "兄弟姐妹关系", "父母关系",
//			"子女关系", "祖父母", "孙子女关系", "relevance_other" };

//	/** 风险能力承受 */
//	public static String[] riskBearID = { "01", "02", "03", "04", "05" };
//	public static String[] riskBearStr = { "保守型", "谨慎型", "稳健型", "进取型", "激进型" };

//	/** 个人客户不动产类型 */
//	public static String[] estateTypeID = { "01", "02", "03", "04", "05", "06",
//			"07", "08" };
//	public static String[] estateTypeStr = { "房产", "土地", "土地使用权", "房屋及建筑物",
//			"专利权", "商标权", "著作权", "relevance_other" };

//	/** 个人客户动产类型 */
//	public static String[] movableTypeID = { "01", "02", "03", "04", "05",
//			"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16" };
//	public static String[] movableTypeStr = { "车辆", "贵重金属", "机器设备", "有价证券",
//			"股权", "现钞", "基金", "期货", "个人借贷（借出)", "遗产", "赠予", "赡养费", "收藏品",
//			"家用电器", "家具", "交通工具" };

//	/** 区域类型 */
//	public static String[] areaID = { "01", "02", "03", "04" };
//	public static String[] areaStr = { "社区", "园区", "农区", "商区" };

//	/** 健康状态 */
//	public static String[] healthStatusID = { "01", "02", "03" };
//	public static String[] healthStatusStr = { "良好", "健康", "一般" };

//	/** 企业类型 */
//	public static String[] companyTypeID = { "100", "100110", "100120",
//			"100130", "100140", "100150", "100160", "100170", "100170171",
//			"100170174", "100180", "100190", "200", "200210", "200210211",
//			"200210212", "200220", "200220221", "200220222", "200230",
//			"200230231", "200230232", "200240", "200240241", "200240242", "29",
//			"300", "300310", "300320", "300330", "300340", "39", "400",
//			"400410", "400420", "90" };
//
//	public static String[] companyTypeStr = { "内资企业", "国有企业", "集体企业", "股份合作企业",
//			"联营企业", "有限责任公司", "股份有限公司", "私营企业", "私营独资企业", "私营有限责任公司", "乡镇企业",
//			"其他企业", "港、澳、台商投资企业", "合资经营企业(港或澳、台资)", "港、澳与内地合资企业", "台湾与内地合资企业",
//			"合作经营企业(港或澳、台资)", "港、澳与内地合作企业", "台湾与内地合作企业", "港、澳、台商独资经营企业",
//			"港、澳独资经营企业", "台湾独资经营企业", "港、澳、台商投资股份有限公司", "港、澳投资股份有限公司",
//			"台商投资股份有限公司", "其他港澳台投资", "外商投资企业", "中外合资经营企业", "中外合作经营企业",
//			"外资(独资)企业", "外商投资股份有限公司", "其他国外投资", "个体经营", "个体工商户", "个人合伙", "relevance_other" };

//	/** 电话类型 */
//	public static String[] phoneID = { "01", "02", "03" };
//	public static String[] phoneStr = { "手机", "座机", "办公电话" };

//	/** 数据来源 */
//	public static String[] dataSourceTypeID = { "COR", "CMIS", "CCS", "EBK",
//			"P99" };
//	public static String[] dataSourceTypeStr = { "核心", "信贷", "信用卡", "网银", "理财" };

//	/** 个人客户地址类型 */
//	public static String[] addrTypeID = { "01", "02", "03" };
//	public static String[] addrTypeStr = { "家庭住址", "办公地址", "经营地址" };

//	/** 个人客户联系类型 */
//	public static String[] conTypeID = { "01", "02", "03", "04" };
//	public static String[] conTypeStr = { "QQ", "微信", "电子邮件", "传真" };

//	/** 个人客户存款期限 */
//	public static String[] depositPeriodID = { "01", "02", "03", "04", "05",
//			"06" };
//	public static String[] depositPeriodStr = { "3个月", "6个月", "12个月", "24个月",
//			"36个月", "60个月" };

//	/** 行外贷款方式 */
//	public static String[] loanTypeID = { "01", "02", "03", "04" };
//	public static String[] loanTypeStr = { "抵押", "质押", "保证", "信用" };

//	/** 个人客户他行存款信息 */
//	public static String[] depositTypeID = { "0", "1" };
//	public static String[] depositTypeStr = { "活期", "定期" };

//	/** 行外产品签约类型 */
//	public static String[] signTypeID = { "01", "02", "03", "04", "05", "06",
//			"07", "08", "09", "10", "11" };
//	public static String[] signTypeStr = { "个人手机银行", "商务手机银行", "个人网银", "POS机",
//			"电话银行", "短信银行", "微信银行", "第三方支付", "水费", "电费", "relevance_other" };

//	/** 电子产品需求 */
//	public static String[] kindTypeID = { "01", "02", "03", "04", "05" };
//	public static String[] kindTypeStr = { "无银行卡贷记卡存折", "手机银行卡乐付网银",
//			"电话银行支付宝短信", "银行贷记卡中间业务", "relevance_other" };

//	/** 贷款种类 */
//	public static String[] loanCateTypeID = { "01", "02", "03" };
//	public static String[] loanCateTypeStr = { "贷款", "银行承兑", "relevance_other" };

//	/** 贷款用途 */
//	public static String[] loanUseTypeID = { "01", "02", "03", "04" };
//	public static String[] loanUseTypeStr = { "购房", "购车", "经济周转", "relevance_other" };

//	/** 可提供的担保方式 */
//	public static String[] warrantTypeID = { "01", "02", "03", "04", "05",
//			"06", "07", "08", "09", "10" };
//	public static String[] warrantTypeStr = { "信用", "保证", "担保人", "抵押", "质押",
//			"质押加阶段性保证", "抵押加保证", "质押加保证", "质押加阶段性保证", "relevance_other" };
//
//	/** 周转期限 */
//	public static String[] turnOverTypeID = { "01", "02", "03", "04", "05" };
//	public static String[] turnOverTypeStr = { "一年", "二年", "三年", "五年", "五年及以上" };

//	/** 贷款需求状态 */
//	public static String[] loanStatusTypeID = { "01", "02", "03" };
//	public static String[] loanStatusTypeStr = { "未受理", "已受理", "已受理" };

	/** 联络渠道 */
	public static String[] serveModelID = { "01", "02", "03", "04", "05", "06",
			"07", "08" };
	public static String[] serveModelStr = { "行内到访", "登门拜访", "电话", "短信",
			"电子邮件", "传真", "信函", "其它" };

	/** 服务类型 */
	public static String[] serveTypeID = { "01", "02", "03", "04" };
	public static String[] serveTypeStr = { "生日关怀", "节日问候", "活动开展", "业务交流" };

	/** 执行状态 */
	public static String[] csstatusID = { "1", "0" };
	public static String[] csstatusStr = { "未执行", "已执行" };

//	/** 培植方向 */
//	public static String[] cultivateDirctID = { "01", "02", "03", "04" };
//	public static String[] cultivateDirctStr = { "一般关注", "定期回访", "重点跟进", "培植发展" };

	/** 客户类型 */
	public static String[] custypeID = { "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10" };
	public static String[] custypeStr = { "农村经营大户", "家庭农场", "一般农户", "外出创业人员",
			"外出务工人员", "个体工商户/小微企业主", "公务员", "事业单位员工", "社区居民", "relevance_other" };

	/** 个人客户培植方向 */
	public static String[] cultivate_direID = { "01", "02", "03", "04" };
	public static String[] cultivate_direStr = { "一般关注", "定期回访", "重点跟进", "培植发展" };

//	/** 需求分类 */
//	public static String[] customerTypeID = { "01", "02", "03" };
//	public static String[] customerTypeStr = { "综合类客户", "信贷类客户", "结算类客户" };

//	/** 客户评级 */
//	public static String[] custTypeID = { "0", "1", "2", "3", "4", "5" };
//	public static String[] custTypeStr = { "大众客户", "一星客户", "二星客户", "三星客户",
//			"四星客户", "五星客户" };
	
//	/** 异动类型 */
//	public static String[] tranTypeID = { "1", "2" };
//	public static String[] tranTypeStr = { "转出", "转入" };

//	/** 提醒类型 */
//	public static String[] eventTypeID = {"01", "02"};
//	public static String[] eventTypeStr = {"升级", "降级"};
	
//	/** 营销活动类型 */
//	public static String[] actTypeID = {"01", "02", "03", "06"};
//	public static String[] actTypeStr = {"理财销售", "产品推广", "客户回馈", "其它市场活动"};

//	/**
//	 * 客户类型码表，及值
//	 */
//	public static String[] customerStyleID = { "01", "02", "03",
//			"04", "05", "06", "07", "08" };
//	public static String[] customerStyleStr = { "农业产业化龙头企业",
//			"农民专业合作社", "垄断优势企业", "一般企业", "国家机关", "学校", "医院", "其他事业单位" };
//
//	/**
//	 * 企业规模
//	 */
//	public static String[] companyMoudleID = { "0", "1", "2" };
//	public static String[] companyMoudleStr = { "大型", "中型", "小微企业" };

//	/**
//	 * 所属行业
//	 */
//	public static String[] corptpID =  { "level_a", "level_b", "level_c", "D", "E",
//			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
//			"S" };
//	public static String[] corptpStr = { "农、林、牧、渔业", "采矿业", "制造业",
//			"电力、燃气及水的生产供应业", "建筑业", "交通运输、仓储和邮政业", "信息传输、计算机服务及软件业", "批发和零售业",
//			"住宿和餐饮业", "金融业", "房地产业", "租赁和商务服务业", "科学研究、技术服务和地址勘查业",
//			"水利、环境和公共设施管理", "居民服务和其他服务业", "教育业", "卫生、社会保障和社会福利业", "文化、教育和娱乐业",
//			"公共管理和社会组织" };
	
//	/**
//	 * 经营状况
//	 */
//	public static String[] businessStateID={"01","02","03","04"};
//	public static String[] businessStateStr={"正常经营","经营困难","关停倒闭","relevance_other"};
	
	/**贵宾卡级别**/
	public static String[] cust_psn_sf_cordID={};
	

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

	/**
	 * 根据value值寻找对于code
	 * 
	 * @param name
	 * @param codeArray
	 * @param valueArray
	 * @return
	 */
	public static String getCode(String name, String[] codeArray,
			String[] valueArray) {
		String code = "";
		int index = -1;
		for (int i = 0; i < valueArray.length; i++) {
			if (valueArray[i].equals(name.trim())) {
				index = i;
				break; // 找到后结束for循环
			}
		}

		// 防止数组越界
		if (index >= 0 && index < codeArray.length) {
			code = codeArray[index];
		}
		return code;
	}

	public static String getName(String code, String[] codeArray,
			String[] valueArray) {
		String value = "";
		int index = -1;
		for (int i = 0; i < codeArray.length; i++) {
			if (codeArray[i].equals(code.trim())) {
				index = i;
				break; // 找到后结束for循环
			}
		}

		// 防止数组越界
		if (index >= 0 && index < valueArray.length) {
			value = valueArray[index];
		}
		return value;
	}
}
