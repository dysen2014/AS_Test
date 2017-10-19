package com.pactera.financialmanager.entity;

import java.io.Serializable;

public class PercentInfo implements Serializable{

	private static final long serialVersionUID = 1880067270929439241L;
	
	/**
	 * 客户资产结构 360视图下方三个条目 的信息
	 */
//	private static final long serialVersionUID = -8264189042790240748L;
	public int IV_BACKGROUDCOLOR; //小方块颜色
	public String TV_TYPE;  //资产类型：理财 定期存款 活期存款
	public String TV_MONEY;    //该类型资产数目
	public String TV_PERCENT;  //该类型资产所占比例

}
