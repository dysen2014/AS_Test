package com.pactera.financialmanager.ui.model;

/**
 * 版本更新对象
 * @author JackDG
 *
 */
public class UploadInfo {

	// 软件id
	private String softID;
	// 软件名称
	private String softName;
	// 版本号
	private String versionNO;	
	// 下载地址
	private String loadURL;
	// 标识
	private String identifier;

	public String getSoftID() {
		return softID;
	}

	public void setSoftID(String softID) {
		this.softID = softID;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getVersionNO() {
		return versionNO;
	}

	public void setVersionNO(String versionNO) {
		this.versionNO = versionNO;
	}

	public String getLoadURL() {
		return loadURL;
	}

	public void setLoadURL(String loadURL) {
		this.loadURL = loadURL;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
