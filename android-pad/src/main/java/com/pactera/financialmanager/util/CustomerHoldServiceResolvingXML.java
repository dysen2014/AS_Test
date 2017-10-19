package com.pactera.financialmanager.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.pactera.financialmanager.ui.model.CustomerServices;

public class CustomerHoldServiceResolvingXML {
	public List<CustomerServices> getCustomerHoldServiceXML(InputStream inputXml)
			throws Exception {

		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputXml, "utf-8");
		// CustomerInfo info = null;
		int eventCode = xmlpull.getEventType();

		String[] holdServices = {"电话银行对外转账 " ,"短信通 " ,"三方存管 ", " 加息宝",
				"定存宝", "阳光卡质押贷款 ", "实物黄金 ", "活期宝 ", " 电子国债", "银期通 ", "网上支付 ",
				" 基金", " 汇市通", "跨行通存通兑 ", "自助设备转账 ", "信用卡自动还款 ", "个人支票业务 ", "定活关联 ", " 汽车道路救援附属信息",
				" 手机银行", "网银（王） ", "令牌 ", "个人结算账户 ", "协议支付 "};
		
		
		List<CustomerServices> customerServices = null;
		CustomerServices customerHoldService = null;
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT: // 开始文档 new数组
				customerServices = new ArrayList<CustomerServices>();
				break;

			case XmlPullParser.START_TAG:
				if (customerServices != null) {
					if ("SIGN00001".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[0]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
						
					} else if ("SIGN00002".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[1]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
						// Log.i("SIGN00002",
						// customerHoldService.getServiceName());
					} else if ("SIGN00003".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[2]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
						// Log.i("SIGN00003",
						// customerHoldService.getServiceName());
					} else if ("SIGN00004".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[3]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00005".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[4]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00006".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[5]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00007".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setServiceName(holdServices[6]);
						customerHoldService.setStatus(xmlpull.nextText());
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00008".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[7]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00009".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[8]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00010".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[9]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00011".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[10]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00012".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[11]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00013".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[12]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00014".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[13]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00015".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[14]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00016".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[15]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00017".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[16]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00018".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[17]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00019".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[18]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00020".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[19]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00021".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[20]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00022".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[21]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					} else if ("SIGN00023".equals(xmlpull.getName())) {
						customerHoldService = new CustomerServices();
						customerHoldService.setStatus(xmlpull.nextText());
						customerHoldService.setServiceName(holdServices[22]);
						customerServices.add(customerHoldService);
						customerHoldService = null;						
					}
				}

				break;
				
			case XmlPullParser.END_TAG:
				
//				customerServices.add(customerHoldService);
//				customerHoldService = null;
				
				break;
			}

			eventCode = xmlpull.next();// 没有结束xml文件就推到下个进行解析
		}
		return customerServices;

	}

	

}
