package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by dysen on 2017/8/28.
 */

public class KeyPersonBean {

	public class Customer{

		public String CertName;//证件类型
		public String CertID;//证件号码
		public String CustomerName;//名称
		public String RelationName;//与该客户关系
		public String UserName;//登记人
		public String OrgName;//登记机构
		public String InputDate;//输入日期
		public String UpdateDate;//更新日

		public String getCertName() {
			return CertName;
		}

		public Customer setCertName(String certName) {
			CertName = certName;
			return this;
		}

		public String getCertID() {
			return CertID;
		}

		public Customer setCertID(String certID) {
			CertID = certID;
			return this;
		}

		public String getCustomerName() {
			return CustomerName;
		}

		public Customer setCustomerName(String customerName) {
			CustomerName = customerName;
			return this;
		}

		public String getRelationName() {
			return RelationName;
		}

		public Customer setRelationName(String relationName) {
			RelationName = relationName;
			return this;
		}

		public String getUserName() {
			return UserName;
		}

		public Customer setUserName(String userName) {
			UserName = userName;
			return this;
		}

		public String getOrgName() {
			return OrgName;
		}

		public Customer setOrgName(String orgName) {
			OrgName = orgName;
			return this;
		}

		public String getInputDate() {
			return InputDate;
		}

		public Customer setInputDate(String inputDate) {
			InputDate = inputDate;
			return this;
		}

		public String getUpdateDate() {
			return UpdateDate;
		}

		public Customer setUpdateDate(String updateDate) {
			UpdateDate = updateDate;
			return this;
		}
	}

	public class Business{

		public String CertName;//证件类型
		public String CertID;//证件号码
		public String CustomerName;//关键人名称
		public String RelationName;//担任职务
		public String Telephone;//联系电话
		public String OfficeTime;//任职时间
		public String PractitionerTime;//从业时间
		public String OrgName;//登记机构
		public String UserName;//登记人

		public String getCertName() {
			return CertName;
		}

		public Business setCertName(String certName) {
			CertName = certName;
			return this;
		}

		public String getCertID() {
			return CertID;
		}

		public Business setCertID(String certID) {
			CertID = certID;
			return this;
		}

		public String getCustomerName() {
			return CustomerName;
		}

		public Business setCustomerName(String customerName) {
			CustomerName = customerName;
			return this;
		}

		public String getRelationName() {
			return RelationName;
		}

		public Business setRelationName(String relationName) {
			RelationName = relationName;
			return this;
		}

		public String getTelephone() {
			return Telephone;
		}

		public Business setTelephone(String telephone) {
			Telephone = telephone;
			return this;
		}

		public String getOfficeTime() {
			return OfficeTime;
		}

		public Business setOfficeTime(String officeTime) {
			OfficeTime = officeTime;
			return this;
		}

		public String getPractitionerTime() {
			return PractitionerTime;
		}

		public Business setPractitionerTime(String practitionerTime) {
			PractitionerTime = practitionerTime;
			return this;
		}

		public String getOrgName() {
			return OrgName;
		}

		public Business setOrgName(String orgName) {
			OrgName = orgName;
			return this;
		}

		public String getUserName() {
			return UserName;
		}

		public Business setUserName(String userName) {
			UserName = userName;
			return this;
		}
	}
}
