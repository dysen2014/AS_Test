package com.pactera.financialmanager.util;

/**
 * 
 * @do {错误码定义}()
 * @author Milo Zhang
 * @date 2012-4-19 下午02:33:01
 */
public class RetCode {
	// webservice头文件相关
	public static final String DATAPACKET = "dataPacket";
	public static final String DATAPACKET_VERSION = "version";
	public static final String DATAPACKET_VERSION_VALUE = "1.0";

	// 验证通过
	public static final String VALLDATE_PASS = "0000";
	// 返回码
	public static final String DATAPACKET_RET_CODE = "retCode";
	/**************** 用户级错误 ****************************/
	// 验证参数为空
	public static final String ERR_USER_ARGS_NULL = "1001";
	// 用户号为空
	public static final String ERR_USER_CODE_NULL = "1002";
	// 密码为空
	public static final String ERR_USER_PSWD_NULL = "1003";
	// 交易号为空
	public static final String ERR_USER_TRANS_CODE_NULL = "1004";
	// 渠道号为空
	public static final String ERR_USER_CHNL_CODE_NULL = "1005";
	// 设备号为空
	public static final String ERR_USER_SERI_CODE_NULL = "1006";
	// 机构号为空
	public static final String ERR_USER_BRCODE_NULL = "1007";
	// 客户号为空
	public static final String ERR_USER_CUSTCODE_NULL = "1008";
	// 时间为空
	public static final String ERR_USER_TIME_NULL = "1009";
	// 开始时间为空
	public static final String ERR_USER_BEGINTIME_NULL = "1010";
	// 结束时间为空
	public static final String ERR_USER_ENDTIME_NULL = "1011";
	// 时间格式错误
	public static final String ERR_USER_TIME_FORMART = "1012";
	// 开始时间不能小于结束时间
	public static final String ERR_USER_TIME_LESS_THAN = "1013";
	// 开始时间不能大于结束时间
	public static final String ERR_USER_TIME_GREAT_THAN = "1014";
	// 格式错误
	public static final String ERR_USER_FORMART = "1015";
	// 非法登陆1（用户名不存在）
	public static final String ERR_USER_CODE_NO = "1016";
	// 非法登陆2（密码错误）
	public static final String ERR_USER_PSWD = "1017";
	// 非法登陆3（非法设备）
	public static final String ERR_USER_ILL_DEV = "1018";
	// 非法登陆4（用户名或密码错误）
	public static final String ERR_USER_ILL = "1019";
	// 非法登录5（设备未入库）{您使用的设备未在BMC中登记}
	public static final String ERR_USER_NO_DEV = "1020";
	// 非法登录6（设备未领用）-- padStatus -- 01
	public static final String ERR_USER_NOBIND_DEV = "1021";
	// 非法登录7（设备维修中）-- padStatus -- 03
	public static final String ERR_USER_MEND_DEV = "1022";
	// 非法登录8(设备报废) -- padStatus -- 04
	public static final String ERR_USER_REJECT_DEV = "1023";
	// 非法登录9（设备挂失） -- padStatus -- 05
	public static final String ERR_USER_LOSS_DEV = "1024";
	// 非法登录10（用户失效） -- userStatus -- 02
	public static final String ERR_USER_STATUS_INVI = "1025";
	// 非法登录11（用户未启用） -- userStatus -- 03
	public static final String ERR_USER_STATUS_UNUSE = "1026";
	// 非法登录12（用户离职） -- workStatus -- 02
	public static final String ERR_USER_LEAVEL = "1027";
	// 非法登录13（密码失效） --
	public static final String ERR_USER_PSWD_STATUS = "1028";
	// 非法登录14（密码错误达到限制） --
	public static final String ERR_USER_PSWD_ILL_DAY = "1029";
	// 非法登录14（密码错误达到累积限制） --
	public static final String ERR_USER_PSWD_ILL_COUNT = "1030";
	// 库存数量小于兑换数量
	public static final String ERR_USER_STOCK_NUM = "1031";
	// 客户积分不足
	public static final String ERR_CUST_STOCK_NUM = "1032";
	// 登录用户所在机构和设备领用用户机构不同(不能跨机构使用)
	public static final String ERR_BRCODE_NOT_SAMEAS_PADUSER_BRCODE = "1033";
	// 登录用户未与设备绑定
	public static final String ERR_BRCODE_NOT_SAMEAS_PADUSER_USER = "1035";
	// 无该功能权限
	public static final String ERR_FUNCTION_RIGHT = "1034";
	// 单点登录校验出错 helin 20130530
	public static final String ERR_SINGLELOGIN_ERROR = "1037";
	// 单点登录校验未通过 helin 20130530
	public static final String ERR_SINGLELOGIN_NOT_PASS = "1038";
	// PAD未被任何机构领用 helin 20130530
	public static final String ERR_PAD_NOT_SAMEAS_BRCODE = "1039";
	/********************** 系统级错误 **********************/
	// 非法网络（非3G网络）
	public static final String ERR_SYS_NETWORK = "2000";
	// 返回数据为空
	public static final String ERR_SYS_RETURN_ROW_NULL = "2001";
	// 客户不存在
	public static final String ERR_SYS_RETURN_CUST_NULL = "2018";
	// 返回多个客户
	public static final String ERR_SYS_RETURN_STATUS = "6002";
	// 客户端链接超时
	public static final String ERR_SYS_CLIENT_TIMEOUT = "2002";
	// BMC服务器链接超时
	public static final String ERR_SYS_BMC_TIMEOUT = "2003";
	// OCRM服务器链接超时
	public static final String ERR_SYS_OCRM_TIMEOUT = "2004";
	// 外围系统链接超时
	public static final String ERR_SYS_OUTSYS_TIMEOUT = "2005";
	// 客户端系统错误
	public static final String ERR_SYS_CLIENT = "2006";
	// BMC 系统错误
	public static final String ERR_SYS_BMC = "2007";
	// OCRM系统错误
	public static final String ERR_SYS_OCRM = "2008";
	// 外围系统错误
	public static final String ERR_SYS_OUT = "2009";
	// 分页错误
	public static final String ERR_SYS_PAGE = "2010";
	// XML报文转换错误
	public static final String ERR_SYS_XML_TO_STR = "2011";
	// 字符串转换为XML报文错误
	public static final String ERR_SYS_STR_TO_XML = "2012";
	/********************** DB级错误 **********************/
	// 客户端数据库访问失败
	public static final String ERR_DB_CLIENT = "3001";
	// BMC数据库访问失败
	public static final String ERR_DB_BMC = "3002";
	// OCRM数据库访问失败
	public static final String ERR_DB_OCRM = "3003";
	// 外围数据库访问失败
	public static final String ERR_DB_OUT = "3004";
	// IAM应急标志表没有数据
	public static final String ERR_DB_IAM_NULL = "3005";
	// 客户证件号码已存在
	public static final String ERR_USER_CERTCODE_EXIST = "3006";

}