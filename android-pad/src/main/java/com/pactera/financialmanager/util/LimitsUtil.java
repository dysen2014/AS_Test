package com.pactera.financialmanager.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * 权限判断工具
 * 
 * @author Administrator
 *
 */
public class LimitsUtil {

	/** 厅堂管理 */
	public static final String T01 = "T01";
	/** 排队信息显示 */
	public static final String T0101 = "T0101";
	/** 客户分流 */
	public static final String T0102 = "T0102";
	
	
	/** 客户快速查询 */
	public static final String T02 = "T02";
	/** 客户基本信息 */
	public static final String T0201 = "T0201";
	/** 客户购买力 */
	public static final String T0202 = "T0202";
	/** 推荐理财产品 */
	public static final String T0203 = "T0203";
	
	
	/** 客户建档 */
	public static final String T03 = "T03";
	/** 我的建档客户 */
	public static final String T0301 = "T0301";
	/** 客户建档 */
	public static final String T0302 = "T0302";
	/** 对私客户 */
	public static final String T030201 = "T030201";
	/** 对公客户 */
	public static final String T030202 = "T030202";
	
	
	/** 客户回访记录 */
	public static final String T04 = "T04";
	/** 创建客户回访记录 */
	public static final String T0401 = "T0401";
	/** 编辑客户回访记录 */
	public static final String T0402 = "T0402";
	/** 查询客户回访记录 */
	public static final String T0403 = "T0403";
	/** 删除客户回访记录 */
	public static final String T0404 = "T0404";
	
		
	/** 工作平台 */
	public static final String T05 = "T05";
	/** 签到 */
	public static final String T0501 = "T0501";
	/** 签到记录 */
	public static final String T0502 = "T0502";
	/** 工作提醒 */
	public static final String T0504 = "T0504";
	/** 业务概览 */
	public static final String T0505 = "T0505";
	/** 客户排名 */
	public static final String T0506 = "T0506";
	/** 客户经理排名 */
	public static final String T0507 = "T0507";
	/** 机构排名 */
	public static final String T0508 = "T0508";
	/** 客户经理-金融资产 */
	public static final String T0509 = "T0509";
	/** 管理岗-金融资产 */
	public static final String T0510 = "T0510";
	/** 当年贷款到期回收情况 */
	public static final String T0511 = "T0511";
	
	/** 常用工具 */
	public static final String T06 = "T06";
	/** 客户分布 */
	public static final String T0601 = "T0601";
	/** 网点地图 */
	public static final String T0602 = "T0602";
	/** 计算器 */
	public static final String T0603 = "T0603";
	/** 风险测评 */
	public static final String T0604 = "T0604";
	
	/** 信用卡查询 */
	public static final String T07 = "T07";
	/** 营销管理 */
	public static final String T08 = "T08";
	/** 优惠活动 */
	public static final String T0801 = "T0801";
	/** 热销产品 */
	public static final String T0802 = "T0802";

	/**
	 * 权限检查
	 * @param context
	 * @param rightStr
	 * @return
	 */
	public static boolean checkUserLimit(Context context, String rightStr) {
//		boolean isRight = checkUserLimit(context, rightStr, true);
		boolean isRight = checkUserLimit(context, rightStr, false);
		return isRight;
	}
	
	
	/**
	 * 判断是否为客户经理
	 * @param staid
	 * @return
	 */
	public static boolean checkIsCustManager(String staid){
		if (TextUtils.isEmpty(staid)) {
			return false;
		}
		
		// 个人客户经理、对公客户经理、客户经理
		String cms[] = new String[]{"ST001102", "ST001104", "ST001103"};
		for (int i = 0; i < cms.length; i++) {
			// 相同返回ture
			if(cms[i].equals(staid)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 提示是否需要对话框
	 * @param context
	 * @param rightStr
	 * @param isAlert
	 * @return
	 */
	public static boolean checkUserLimit(Context context, String rightStr, boolean isAlert) {
		DialogAlert dialog = new DialogAlert(context);
		String[] userLimits = Constants.RIGHTSTR;
		if (null == userLimits) {
			dialog.show();
			dialog.setMsg("接口返回权限有误！！！");
			return false;
		}
		// 循环判断是否有此权限
		for (String limit : userLimits) {
			if (limit.equals(rightStr)) {
				return true;
			}
		}
		
		if(isAlert){
			dialog.show();
		}
		return false;
	}
}
