package com.pactera.financialmanager.ui.model;

/**
 * 客户建档区域信息
 * 
 * @author JAY
 * 
 */
public class CustomerAreaInfo {

	public String custID = "";// 客户ID
	public String mapping_code = "";// 地址层次码
	public String area = "";// 所属区域
	public String areatype = "";// 区域类型
	public String area_name = "";// 区域类型名字
	public String area_id = "";
	@Override
	public String toString() {
		return "{\"custID\":\"" + custID + "\",\"mapping_code\":\""
				+ mapping_code + "\",\"area\":\"" + area +
				"\",\"area_id\":\"" + area_id +    "\",\"area_name\":\"" + area_name +
				"\",\"areatype\":\""+ areatype + "\"}";

	}
}
