package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 财富锦囊，新闻
 * @author Administrator
 *
 */
public class WKPdf implements Serializable{
	private static final long serialVersionUID = 5347799053800996432L;
	public String pdfID; // id
	public String pdfName; //名称
	public String pdfTime; //时间
	public String pdfType; //标题
	public String iconUrl; //图片连接地址
	public String pdfUser; //作者
	public String pdfUrl; //pdf连接地址
	
}
