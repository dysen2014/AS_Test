package com.pactera.financialmanager.ui.service;

import com.pactera.financialmanager.BuildConfig;
import com.pactera.financialmanager.ui.ParentActivity;

public class HRequest {
//	public static final String BMC_URL_DOMAIN = "http://192.168.2.114:9080/";				//开发环境
//	public static final String BMC_URL_DOMAIN = "http://192.168.2.111:7001/BMC/";		//UAT地址
//	public static final String BMC_URL_DOMAIN = "http://27.17.37.104:9995/";			//生产出地址
	public static final String BMC_URL_DOMAIN = BuildConfig.BMC_URL_DOMAIN;

	public static final String URL_REQUEST = BMC_URL_DOMAIN + "services/"; 	

	public static final int TIME_OUT = 8000;

	// 后缀
	public String ext;
	// 方法
	private String method;
	private int tmpType;
	private String host;

	public HRequest(String ext, String method) {
		host = URL_REQUEST;
		this.ext = ext;
		this.method = method;
	}

	// http://10.80.80.172/upic/m/te/st/test_347700362.gif
	public HRequest(String ext, String method, int type) {
		host = URL_REQUEST;
		tmpType = type;
		switch (tmpType) {
		case ParentActivity.HREQUEST_TYPE_HOST00:
			this.ext = ext;
			this.method = method;
			break;
		case ParentActivity.HREQUEST_TYPE_HOST01:
			this.ext = ext;
			this.method = method;
			break;
		case ParentActivity.HREQUEST_TYPE_HOSTPHOTO:
			this.ext = ext;
			this.method = method;
			break;
		case ParentActivity.HREQUEST_TYPE_FILEHOST:
			this.host = ext;
			this.method = method;
			break;
		}
	}

	public String getURL() {
		if (tmpType == ParentActivity.HREQUEST_TYPE_FILEHOST)
			return host;
		else
			return host + ext;
	}

	public String getDomain() {
		return host;
	}

	public String getMethod() {
		return method;
	}
}
