package com.pactera.financialmanager.ui.model;

/**
 * 资产实体类
 * @author Administrator
 *
 */
public class Property {
	
	/** 客户数量 */ 
	private String khsl;
	
	/** 金融资产 */ 
	private String jrzc;
	
	/** 活期存款 */ 
	private String hqck;
	
	/** 定期存款 */ 
	private String dqck;
	
	/** 存款总额 */ 
	private String ckze;
	
	/** 理财 */ 
	private String lc;
	
	/** 贷款 */ 
	private String dk;
	
	/** 理财余额 */
	private String lcye;
	
	/** 存款余额净值(比年初) */
	private String ckyejz;
	
	/** 贷款余额 */
	private String dkye;
	
	/** 贷款余额净值(比年初) */
	private String dkyejz;
	
	/** 客户名 */
	private String cusName;
	
	/** 存款余额 */
	private String property;
	
	/** 比年初 */
	private String pacent;
	
	/** 当年到期贷款总金额 */
	private String dndqdkzje;
	
	/** 当年到期贷款收回金额 */
	private String dndqdkshje;
	
	/** 当年到期贷款未收回金额 */
	private String dndqdkwshje;

	public String getKhsl() {
		return khsl;
	}

	public void setKhsl(String khsl) {
		this.khsl = khsl;
	}

	public String getJrzc() {
		return jrzc;
	}

	public String getCkyejz() {
		return ckyejz;
	}

	public void setCkyejz(String ckyejz) {
		this.ckyejz = ckyejz;
	}

	public String getDkye() {
		return dkye;
	}

	public void setDkye(String dkye) {
		this.dkye = dkye;
	}

	public String getDkyejz() {
		return dkyejz;
	}

	public void setDkyejz(String dkyejz) {
		this.dkyejz = dkyejz;
	}

	public void setJrzc(String jrzc) {
		this.jrzc = jrzc;
	}

	public String getHqck() {
		return hqck;
	}

	public void setHqck(String hqck) {
		this.hqck = hqck;
	}

	public String getDndqdkzje() {
		return dndqdkzje;
	}

	public void setDndqdkzje(String dndqdkzje) {
		this.dndqdkzje = dndqdkzje;
	}

	public String getDndqdkshje() {
		return dndqdkshje;
	}

	public void setDndqdkshje(String dndqdkshje) {
		this.dndqdkshje = dndqdkshje;
	}

	public String getDndqdkwshje() {
		return dndqdkwshje;
	}

	public void setDndqdkwshje(String dndqdkwshje) {
		this.dndqdkwshje = dndqdkwshje;
	}

	public String getDqck() {
		return dqck;
	}

	public void setDqck(String dqck) {
		this.dqck = dqck;
	}

	public String getLcye() {
		return lcye;
	}

	public void setLcye(String lcye) {
		this.lcye = lcye;
	}

	public String getCkze() {
		return ckze;
	}

	public void setCkze(String ckze) {
		this.ckze = ckze;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getDk() {
		return dk;
	}

	public void setDk(String dk) {
		this.dk = dk;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getPacent() {
		return pacent;
	}

	public void setPacent(String pacent) {
		this.pacent = pacent;
	}
	
}
