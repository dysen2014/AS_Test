package com.pactera.financialmanager.util;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * 角色值
 * @author Administrator
 *
 */
public class QueryRoleUtil {

	private List<Role> roles = null;
	
	/**
	 * 添加岗位信息
	 */
	private void addDefaultValue(){
		roles = new ArrayList<QueryRoleUtil.Role>();
		Role role1 = new Role(3, "ST000100", "系统管理员(联社)");
		Role role2 = new Role(3, "ST000304", "系统管理员(农信)");
		Role role3 = new Role(2, "ST000111", "客户管理岗(联社)");
		Role role4 = new Role(2, "ST000301", "客户管理岗(农信)");
		Role role5 = new Role(2, "ST000701", "客户管理岗(县级)");
		Role role6 = new Role(2, "ST000901", "客户管理岗(一级支行)");
		Role role7 = new Role(2, "ST001101", "客户管理岗(二级支行)");
		Role role8 = new Role(1, "ST001103", "客户经理");
		Role role9 = new Role(3, "ST001105", "大堂经理");
		Role role10 = new Role(2, "ST000501", "客户管理岗(市级)");
		Role role11 = new Role(3, "ST000503", "系统管理员(市级)");
		Role role12 = new Role(3, "ST000704", "系统管理员(县级)");
		roles.add(role1);
		roles.add(role2);
		roles.add(role3);
		roles.add(role4);
		roles.add(role5);
		roles.add(role6);
		roles.add(role7);
		roles.add(role8);
		roles.add(role9);
		roles.add(role10);
		roles.add(role11);
		roles.add(role12);
	}
	
	
	/**
	 * 根据岗位ID返回角色
	 * @param staid
	 * @return
	 */
	public int getRoleType(String staid){
		if(roles == null){
			addDefaultValue();
		}
		Log.i("RoleType", "staid---------->>>"+staid);
		int roleType = 3;
		for(int i=0; i<roles.size(); i++){
			Role tempRole = roles.get(i);
			if(tempRole.getRoleCode().equals(staid)){
				roleType = tempRole.getRoleType();
				Log.i("RoleType", "roleType---------->>>"+roleType);
			}
		}
		return roleType;
	}
	
	
	class Role{
		/**
		 * 角色类别
		 * 1.客户经理  2.管理岗  3.管理员
		 */
		private int roleType;
		
		/** 角色代码 */
		private String roleCode;
		
		/** 角色名称 */
		private String roleName;
		
		public Role() {
			super();
		}

		public Role(int roleType, String roleCode, String roleName) {
			super();
			this.roleType = roleType;
			this.roleCode = roleCode;
			this.roleName = roleName;
		}

		public int getRoleType() {
			return roleType;
		}

		public void setRoleType(int roleType) {
			this.roleType = roleType;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getRoleCode() {
			return roleCode;
		}

		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
	}
}
