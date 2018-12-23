package com.pactera.financialmanager.util;

/**
 * 接口参数
 * 
 * @author JAY
 * 
 */
public class InterfaceInfo {

	public static final String NEW_LOGIN = "T000001";// 登录
	
	public static final String CHECK_VERSION = "T000059";// 检查新版本
	
	/**
	 * 客户建档
	 */
	public static final String CusArchivingStepOne_forCom = "T011101";// 建档关键信息创建（对公）
	public static final String CusArchivingStepOne_forPer = "T001101";// 建档关键信息创建（对私）
	public static final String CusArchivingStepTwo_forPer = "T001102";// 创建客户区域信息（对私）
	public static final String CusArchivingStepTwo_forCom = "T011149";// 创建客户区域信息（对公）
	public static final String CusArchivingStepTwo_Get_forPer = "T001162";// 查询客户区域信息（对私）
	public static final String CusArchivingStepTwo_Get_forCom = "T001162";// 查询客户区域信息（对公）11/22确认接口更改为T011150
	public static final String CusArchivingStepTwo_City_Get = "T001165";// 客户信息-所属区域
	public static final String CusArchiving_DeletePic = "T001158";// 文件删除
	public static final String CusArchiving_UpPic = "T001157";// 图片上传
	public static final String CusArchiving_UpAddress="T000216";//上传地址
	
	/**
	 * 对私客户建档
	 */
	public static final String PersonArchiving_Get = "T001161";// 查询客户关键信息
	public static final String PersonInfo_Get = "T001163";// 查询客户基本信息
	public static final String PersonInfo_Creat = "T001103";// 客户基本信息
	public static final String HomeInfo_HomeInfo_Get = "T001164";// 查询客户家庭情况
	public static final String HomeInfo_HomeInfo_Creat = "T001104";// 客户家庭情况
	public static final String HomeInfo_Contacts_Get = "T001105";// 客户关系人信息查询
	public static final String HomeInfo_Contacts_Creat = "T001106";// 客户关系人信息创建
	public static final String HomeInfo_Contacts_Delete = "T001107";// 客户关系人信息删除
	public static final String HomeInfo_Contacts_Update = "T001108";// 客户关系人信息编辑
	public static final String HomeInfo_Movable_Get = "T001109";// 动产信息查询
	public static final String HomeInfo_Movable_Creat = "T001110";// 动产信息创建
	public static final String HomeInfo_Movable_Delete = "T001111";// 动产信息删除
	public static final String HomeInfo_Movable_Update = "T001112";// 动产信息编辑
	public static final String HomeInfo_ImMovable_Get = "T001166";// 不动产信息查询
	public static final String HomeInfo_ImMovable_Creat = "T001167";// 不动产信息创建
	public static final String HomeInfo_ImMovable_Delete = "T001168";// 不动产信息删除
	public static final String HomeInfo_ImMovable_Update = "T001169";// 不动产信息编辑
	public static final String Contact_Phone_Get = "T001113";// 联系电话查询
	public static final String Contact_Phone_Creat = "T001114";// 联系电话创建
	public static final String Contact_Phone_Delete = "T001115";// 联系电话删除
	public static final String Contact_Phone_Update = "T001116";// 联系电话编辑
	public static final String Contact_Other_Get = "T001117";// 创建其他联系方式查询
	public static final String Contact_Other_Creat = "T001118";// 创建其他联系方式创建
	public static final String Contact_Other_Delete = "T001119";// 创建其他联系方式删除
	public static final String Contact_Other_Update = "T001120";// 创建其他联系方式编辑
	public static final String Contact_Address_Get = "T001175";// 联系地址查询
	public static final String Contact_Address_Creat = "T001176";// 联系地址创建
	public static final String Contact_Address_Delete = "T001177";// 联系地址删除
	public static final String Contact_Address_Update = "T001178";// 联系地址编辑
	public static final String OtherBank_Product_Get = "T001121";// 行外产品签约查询
	public static final String OtherBank_Product_Creat = "T001122";// 行外产品签约创建
	public static final String OtherBank_Product_Delete = "T001123";// 行外产品签约删除
	public static final String OtherBank_Product_Update = "T001124";// 行外产品签约编辑
	public static final String OtherBank_Loan_Get = "T001125";// 行外贷款信息查询
	public static final String OtherBank_Loan_Creat = "T001126";// 行外贷款信息创建
	public static final String OtherBank_Loan_Delete = "T001127";// 行外贷款信息删除
	public static final String OtherBank_Loan_Update = "T001128";// 行外贷款信息编辑
	public static final String OtherBank_Deposit_Get = "T001129";// 外行存款信息查询
	public static final String OtherBank_Deposit_Creat = "T001130";// 外行存款信息创建
	public static final String OtherBank_Deposit_Delete = "T001131";// 外行存款信息删除
	public static final String OtherBank_Deposit_Update = "T001132";// 外行存款信息编辑
	public static final String OtherBank_LiCai_Get = "T001133";// 外行理财信息查询
	public static final String OtherBank_LiCai_Creat = "T001134";// 外行理财信息创建
	public static final String OtherBank_LiCai_Delete = "T001135";// 外行理财信息删除
	public static final String OtherBank_LiCai_Update = "T001136";// 外行理财信息编辑
	public static final String Finance_Deposit_Get = "T001137";// 存款预约查询
	public static final String Finance_Deposit_Creat = "T001138";// 存款预约创建
	public static final String Finance_Deposit_Delete = "T001139";// 存款预约删除
	public static final String Finance_Deposit_Update = "T001140";// 存款预约编辑
	public static final String Finance_Loan_Get = "T001141";// 贷款需求查询
	public static final String Finance_Loan_Creat = "T001142";// 贷款需求创建
	public static final String Finance_Loan_Delete = "T001143";// 贷款需求删除
	public static final String Finance_Loan_Update = "T001144";// 贷款需求编辑
	public static final String Finance_Product_Get = "T001145";// 电子产品需求查询
	public static final String Finance_Product_Creat = "T001146";// 电子产品需求创建
	public static final String Finance_Product_Delete = "T001147";// 电子产品需求删除
	public static final String Finance_Product_Update = "T001148";// 电子产品需求编辑
	public static final String Finance_Other_Get = "T001149";// 其他需求查询
	public static final String Finance_Other_Creat = "T001150";// 其他需求创建
	public static final String Finance_Other_Delete = "T001151";// 其他需求删除
	public static final String Finance_Other_Update = "T001152";// 其他需求编辑
	public static final String Business_Update_Get = "T001174";// 资金结算偏好和、我行业务往来信息、查询更新
	public static final String Customer_Update = "T001156";// 客户评估创建
	public static final String Customer_Get = "T001153";// 客户评估查询
	public static final String Finance_Licai_Get = "T001183";// 理财需求查
	public static final String Finance_Licai_Creat = "T001180";// 理财需求增
	public static final String Finance_Licai_Delete = "T001181";// 理财需求删
	public static final String Finance_Licai_Update = "T001182";// 理财需求改

	/**
	 * 对公客户建档
	 */
	public static final String ActivityBaseInfo_Get = "T011147";// 对公建档关键信息查询
	public static final String BookBuildingBaseInfo_Get = "T011148";// 查询客户基本信息
	public static final String BookBuildingBaseInfo_Creat = "T011104";// 基本信息创建
	public static final String BUSINESSCOMEGO_BHCK_Get = "T011139";// 本行存款
	public static final String BUSINESSCOMEGO_BHCK_Create = "T011140";
	public static final String BUSINESSCOMEGO_BHCK_Update = "T011142";
	public static final String BUSINESSCOMEGO_THCK_Get = "T011135";// 他行存款
	public static final String BUSINESSCOMEGO_THCK_Create = "T011136";
	public static final String BUSINESSCOMEGO_THCK_Delete = "T011137";
	public static final String BUSINESSCOMEGO_THCK_Update = "T011138";
	public static final String BUSINESSCOMEGO_THLCCK_Get = "T011143";// 他行理财存款
	public static final String BUSINESSCOMEGO_THLCCK_Create = "T011144";
	public static final String BUSINESSCOMEGO_THLCCK_Delete = "T011145";
	public static final String BUSINESSCOMEGO_THLCCK_Update = "T011146";
	public static final String BookBuildingUploadPic_Get = "T011165";// 客户评价查询
	public static final String BookBuildingUploadPic_Update = "T011168";// 客户评价编辑
	public static final String FinancialServieces_Update_Get = "T011169";// 金融需求查询更新

    public static final String BUSINESSCOMEGO_SIGN_Create = "T011170";// 行外签约产品信息
    public static final String BUSINESSCOMEGO_SIGN_Delete = "T011171";
    public static final String BUSINESSCOMEGO_SIGN_Update = "T011172";
    public static final String BUSINESSCOMEGO_SIGN_Get = "T011173";

    public static final String BUSINESSCOMEGO_THDK_Create = "T011174";// 行外贷款
    public static final String BUSINESSCOMEGO_THDK_Delete = "T011175";
    public static final String BUSINESSCOMEGO_THDK_Update = "T011176";
    public static final String BUSINESSCOMEGO_THDK_Get = "T011177";



    /** 消息提醒 **/
	public static final String WORKWARN_NUM_Get = "T000067"; // 顶部头消息数量
	public static final String PRODATA_Get = "T000068"; // 消息提醒内容
	public static final String WORKWARN_FD_Person_Get = "T000072"; // 客户金融处理人查询
	public static final String WORKWARN_FD_OPINION = "T000071"; // 客户金融处理意见

	/** 我的业绩 **/
	public static final String MYREPORTTAB01_QH_Get = "T000201"; // 金融资产_全行
	public static final String MYREPORTTAB01_GH_Get = "T000202"; // 金融资产_管户
	public static final String MYREPORTTAB02_Get = "T000204"; // 存款趋势图
	public static final String MYREPORTTAB03_Get = "T000205"; // 渠道签约情况
	public static final String MYREPORTTAB04_Get = "T000203"; // 客户价值评级占比
	public static final String MYREPORTTAB05_Get = "T000209"; // 当年贷款回收情况
	public static final String ORDER_CUST_Get = "T000206"; // 客户排名
	public static final String ORDER_PM_Get = "T000207"; // 客户经理排名
	public static final String ORDER_ORG_Get = "T000208"; // 机构排名

	/** 客户建档 **/
//	public static final String QUERYCUS_FORPERSON = "T000022"; // 客户查询-对个人
//	public static final String QUERYCUS_FORCOMMON = "T000021"; // 客户查询-对公
	public static final String MYSCUS_PERSON_Get = "T001179"; // 我的建档个人查询
	public static final String MYSCUS_COMMON_Get = "T011152"; // 我的建档对公查询
	public static final String RETURNCUS_FORPERSON_QUERY = "T000016"; // 客户回访查询-对个人
	public static final String RETURNCUS_FORCOMMON_QUERY = "T000106"; // 客户回访查询-对公
	public static final String RETURNCUS_FORPERSON_INSERT = "T000015"; // 客户回访新增-对个人
	public static final String RETURNCUS_FORCOMMON_INSERT = "T000105"; // 客户回访新增-对公
	public static final String RETURNCUS_FORPERSON_UPDATE = "T000056"; // 客户回访修改-对个人
	public static final String RETURNCUS_FORCOMMON_UPDATE = "T000107"; // 客户回访修改-对公
	public static final String RETURNCUS_FORPERSON_DEL = "T000057"; // 客户回访删除-对个人
	public static final String RETURNCUS_FORCOMMON_DEL = "T000108"; // 客户回访删除-对公
//	public static final String QUERYCUS_FORPERSON_MYCUS = "T000017"; // 客户查询-对个人-带默认权限
//	public static final String QUERYCUS_FORCOMMON_MYCUS = "T000020"; // 客户查询-对公
 	public static final String QUERYCUS_FORPERSON_MYCUS = "T000022"; // 客户查询-对个人--所有客户
	public static final String QUERYCUS_FORCOMMON_MYCUS = "T000021"; // 客户查询-对公


	/** 厅堂管理 **/
	public static final String HALLFIRST_QUERY = "T000018"; // 客户快速查询
	public static final String HALLFIRST_INFO_QUERY = "T000006"; // 客户基本信息查询
	public static final String HALLCUSTOMER_BUYPOWER = "T000008"; // 客户购买力
	public static final String HALLCUSTOMER_LICAIPRODUCT = "T000009"; // 一句话
	public static final String HALLCUSTOMER_LICAIPRODUCT2 = "T000035"; // 推荐理财产品
	public static final String HALLFIRST_INFO_QUERY_COMMON = "T000010"; // 对公客户基本信息查询
	
	/**
	 * 常用工具
	 */
	public static final String Customer_Map_QueryOneCus = "T000214";// 客户分布（单一客户查询）
	public static final String Customer_Map_QueryCus = "T000211";// 客户分布（周边客户查询）
	public static final String BranchMap_Get = "T000215";// 网点分布查询
	public static final String Promotions_Get = "T000014";// 优惠活动查询
	public static final String Measure_Save = "T000062";// 风险测评保存
	public static final String PassWord_Save="T000002";//修改密码
	public static final String HotProduct_Get="T000060";//热销产品获取
	public static final String HotProductInfo_Get="T000077";//热销产品详情获取
	public static final String SigninRecord_Get="T000070";//签到记录查询
	public static final String SignenRecord_Get_Header="T011150";//签到记录客户经理查询12.07号改建而成
	public static final String Signin_Creat ="T000069";//签到
	/**
	 * 贷记卡查询
	 */
	public static final String DiscoverCard_CardOpen ="T000100";//开卡进度	
	public static final String 	DiscoverCard_CardActivation ="T000101";//激活情况
	public static final String 	DiscoverCard_CardViolations ="T000102";//违约情况

	/**
	 * 金融需求
	 */
//	public static final String FINANCIAL_EBANK_QUERY = "T011169";//电子银行产品-查询
	public static final String FINANCIAL_DEMANDCK_QUERY = "T011181";//存款需求-查询
	public static final String FINANCIAL_DEMANDCK_INSERT = "T011178";//新增
	public static final String FINANCIAL_DEMANDCK_UPDATE = "T011180";//修改
	public static final String FINANCIAL_DEMANDCK_DELETE = "T011179";//删除
	public static final String FINANCIAL_DEMANDDK_QUERY = "T011185";//贷款需求-查询
	public static final String FINANCIAL_DEMANDDK_INSERT = "T011182";//新增
	public static final String FINANCIAL_DEMANDDK_UPDATE = "T011184";//修改
	public static final String FINANCIAL_DEMANDDK_DELETE = "T011183";//删除

	public static final String FINANCIAL_EBANK_INSERT = "T011186";//电子产品中心
	public static final String FINANCIAL_EBANK_DELETE= "T011187";//删除
	public static final String FINANCIAL_EBANK_UPDATE= "T011188";//修改
	public static final String FINANCIAL_EBANK_QUERY= "T011189";//查询

	/**
	 * 申请调配
	 */
	public static final String APPLY_FOR_DEPLOY_QUERY= "T000217";//查询
	public static final String APPLY_FOR_DEPLOY_CREATE= "T000218";//创建
	public static final String APPLY_FOR_DEPLOY_DELETE= "T000219";//删除



}
