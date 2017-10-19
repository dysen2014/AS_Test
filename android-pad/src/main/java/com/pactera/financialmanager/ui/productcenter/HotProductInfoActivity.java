package com.pactera.financialmanager.ui.productcenter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.Constants.requestType;

/**
 * 热销产品详情
 * 
 * @author YPJ
 * 
 */

public class HotProductInfoActivity extends ParentActivity {

	private HConnection HCon;
	private final int returnIndex = 1;

	private TextView PRD_CODE;// 产品编号
	private TextView PRD_NAME;// 产品名称
	private TextView PRD_STATUS;// 产品状态
	private TextView CUST_TYPE;// 客户类型
	private TextView PRD_CLASS;// 产品分类（所属目录）
	private TextView CHANNEL;// 销售渠道
	private TextView ISHOTSALE;// 是否热销
	private TextView ISRECOMMEND;// 是否推荐
	private TextView ONE_WORD;// 一句话营销
	private TextView PRD_DESCRIPTION;// 产品描述
	private TextView DLYH;// 代理银行
	private TextView CPLX;// 产品类型
	private TextView CPZL;// 产品种类
	private TextView CURRENCY;// 币种
	private TextView GRZDRGJE;// 个人最低认购金额
	private TextView GRDZJE;// 个人递增金额
	private TextView DGZDRGJE;// 机构最低认购金额
	private TextView DGDZJE;// 机构递增金额
	private TextView XYZGMJJE;// 协议最高募集金额
	private TextView XYZDMJJE;// 协议最低募集金额
	private TextView ZGNSYL;// 最高年收益率
	private TextView SJNSYL;// 实际年收益率
	private TextView SXFL;// 手续费率
	private TextView SYKHLX;// 适用客户类型
	private TextView CPFXDJ;// 产品风险等级
	private TextView ZJTX;// 资金投向
	private TextView MJQSR;// 募集起始日
	private TextView MJZZR;// 募集终止日
	private TextView CPQXR;// 产品起息日
	private TextView CPDQR;// 产品到期日
	private TextView CPQX;// 产品期限

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotproducts_info);
		initTitle(this, R.drawable.yingxiaoguanli);
		findViews();
		getData();

	}

	/**
	 * 查询详情
	 */
	private void getData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent(); // 获取客户类型
		String PRD_CLASS = intent.getStringExtra("PRD_CLASS");
		String PRD_ID = intent.getStringExtra("PRD_ID");

		String theRequestInfo = "{\"PRD_CLASS\":\"" + PRD_CLASS
				+ "\",\"PRD_ID\":\"" + PRD_ID + "\"}";

		HCon = RequestInfo(this, requestType.JsonData,
				InterfaceInfo.HotProductInfo_Get, theRequestInfo, returnIndex);

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case returnIndex:
			HResponse res = HCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			Log.i("", tmpJsonObject.toString());
			readJson(tmpJsonObject);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject) {
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");
		;

		if (retCode.equals("0000")) {
			PRD_CODE.setText(Tool.getJsonValue(tmpJsonObject, "PRD_CODE"));// 产品编号
			PRD_NAME.setText(Tool.getJsonValue(tmpJsonObject, "PRD_NAME"));// 产品名称

			// 产品状态
			String statusStr = NewCatevalue.getLabel(this,
					NewCatevalue.product_status,
					Tool.getJsonValue(tmpJsonObject, "PRD_STATUS"));
			PRD_STATUS.setText(statusStr);

			// 客户类型
			String custTypeStr = NewCatevalue.getLabel(this,
					NewCatevalue.product_cust_type,
					Tool.getJsonValue(tmpJsonObject, "CUST_TYPE"));
			CUST_TYPE.setText(custTypeStr);

			// 产品分类（所属目录）
			String prdClassStr = NewCatevalue.getLabel(this,
					NewCatevalue.product_class,
					Tool.getJsonValue(tmpJsonObject, "PRD_CLASS"));
			PRD_CLASS.setText(prdClassStr);

			// 是否热销
			if (Tool.getJsonValue(tmpJsonObject, "ISHOTSALE").equals(isFalse)) {
				ISHOTSALE.setText("否");
			} else {
				ISHOTSALE.setText("是");
			}

			// 是否推荐
			if (Tool.getJsonValue(tmpJsonObject, "ISRECOMMEND").equals(isFalse)) {
				ISRECOMMEND.setText("否");
			} else {
				ISRECOMMEND.setText("是");
			}

			ONE_WORD.setText(Tool.getJsonValue(tmpJsonObject, "ONE_WORD"));// 一句话营销
			PRD_DESCRIPTION.setText(Tool.getJsonValue(tmpJsonObject,
					"PRD_DESCRIPTION"));// 产品描述

			// 代理银行
			String dlyhStr = NewCatevalue.getLabel(this,
					NewCatevalue.product_dlyh,
					Tool.getJsonValue(tmpJsonObject, "DLYH"));
			DLYH.setText(dlyhStr);

			// 产品类型
			String cplxStr = NewCatevalue.getLabel(this,
					NewCatevalue.product_cplx,
					Tool.getJsonValue(tmpJsonObject, "CPLX"));
			CPLX.setText(cplxStr);

			// 产品种类
			String cpzlStr = NewCatevalue.getLabel(this,
					NewCatevalue.product_cpzl,
					Tool.getJsonValue(tmpJsonObject, "CPZL"));
			CPZL.setText(cpzlStr);

			// 币种
			String currencyStr = NewCatevalue.getLabel(this,
					NewCatevalue.CRCYCD,
					Tool.getJsonValue(tmpJsonObject, "CURRENCY"));
			CURRENCY.setText(currencyStr);

			GRZDRGJE.setText(Tool.getJsonValue(tmpJsonObject, "GRZDRGJE") + "元");// 个人最低认购金额
			GRDZJE.setText(Tool.getJsonValue(tmpJsonObject, "GRDZJE") + "元");// 个人递增金额
			DGZDRGJE.setText(Tool.getJsonValue(tmpJsonObject, "DGZDRGJE") + "元");// 机构最低认购金额
			DGDZJE.setText(Tool.getJsonValue(tmpJsonObject, "DGDZJE") + "元");// 机构递增金额
			XYZGMJJE.setText(Tool.getJsonValue(tmpJsonObject, "XYZGMJJE") + "元");// 协议最高募集金额
			XYZDMJJE.setText(Tool.getJsonValue(tmpJsonObject, "XYZDMJJE") + "元");// 协议最低募集金额

			// 最高年收益率
			double TheZgnsyl = Tool.DoubleParse(Tool.getJsonValue(
					tmpJsonObject, "ZGNSYL")) * 100;
			ZGNSYL.setText(TheZgnsyl + "%");

			// 实际年收益率
			double TheSjnsyl = Tool.DoubleParse(Tool.getJsonValue(
					tmpJsonObject, "SJNSYL")) * 100;
			SJNSYL.setText(TheSjnsyl + "%");

			// 手续费率
			double TheSxfl = Tool.DoubleParse(Tool.getJsonValue(tmpJsonObject,
					"SXFL")) * 100;
			SXFL.setText(TheSxfl + "%");

			// 产品风险等级
			String theCpfxdj = NewCatevalue.getLabel(this,
					NewCatevalue.product_cpfxdj,
					Tool.getJsonValue(tmpJsonObject, "CPFXDJ"));
			CPFXDJ.setText(theCpfxdj);

			// 资金投向
			String theZjtx = NewCatevalue.getLabel(this,
					NewCatevalue.product_zjtx,
					Tool.getJsonValue(tmpJsonObject, "ZJTX"));
			ZJTX.setText(theZjtx);

			// 募集起始日
			MJQSR.setText(Tool.getJsonValue(tmpJsonObject, "MJQSR"));

			// 募集终止日
			MJZZR.setText(Tool.getJsonValue(tmpJsonObject, "MJZZR"));

			// 产品起息日
			CPQXR.setText(Tool.getJsonValue(tmpJsonObject, "CPQXR"));

			// 产品到期日
			CPDQR.setText(Tool.getJsonValue(tmpJsonObject, "CPDQR"));

			// 产品期限
			CPQX.setText(Tool.getJsonValue(tmpJsonObject, "CPQX") + "天");

			// 销售渠道
			String channelStr = "";
			String channelInfo = Tool.getJsonValue(tmpJsonObject, "CHANNEL");
			String[] channel = channelInfo.split(",");
			for (String itemStr : channel) {
				if (!TextUtils.isEmpty(itemStr)) {
					String theLabel = NewCatevalue.getLabel(this,
							NewCatevalue.sale_way, itemStr);
					Log.i("1111111", "theLabel : channel=" + theLabel);
					if (TextUtils.isEmpty(channelStr)) {
						channelStr = theLabel;
					} else {
						channelStr = channelStr + "，" + theLabel;
					}
				}
			}
			Log.i("1111111", "channelStr=" + channelStr);
			CHANNEL.setText(channelStr);

			// 适用客户类型
			String sykhlxStr = "";
			String sykhlxInfo = Tool.getJsonValue(tmpJsonObject, "SYKHLX");
			String[] sykhlx = sykhlxInfo.split(",");
			for (String itemStr : sykhlx) {
				if (!TextUtils.isEmpty(itemStr)) {
					String theLabel = NewCatevalue.getLabel(this,
							NewCatevalue.product_sykhlx, itemStr);
					Log.i("1111111", "theLabel : sykhlx=" + theLabel);
					if (TextUtils.isEmpty(sykhlxStr)) {
						sykhlxStr = theLabel;
					} else {
						sykhlxStr = sykhlxStr + "," + theLabel;
					}
				}
			}
			Log.i("1111111", "sykhlxStr=" + sykhlxStr);
			SYKHLX.setText(sykhlxStr);

		} else {
			Toast.makeText(HotProductInfoActivity.this, "获取失败!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void findViews() {
		PRD_CODE = (TextView) findViewById(R.id.hotproducts_info_prd_code);// 产品编号
		PRD_NAME = (TextView) findViewById(R.id.hotproducts_info_prd_name);// 产品名称
		PRD_STATUS = (TextView) findViewById(R.id.hotproducts_info_prd_status);// 产品状态
		CUST_TYPE = (TextView) findViewById(R.id.hotproducts_info_cust_type);// 客户类型
		PRD_CLASS = (TextView) findViewById(R.id.hotproducts_info_prd_class);// 产品分类（所属目录）
		CHANNEL = (TextView) findViewById(R.id.hotproducts_info_channel);// 销售渠道
		ISHOTSALE = (TextView) findViewById(R.id.hotproducts_info_ishotsale);// 是否热销
		ISRECOMMEND = (TextView) findViewById(R.id.hotproducts_info_isrecommend);// 是否推荐
		ONE_WORD = (TextView) findViewById(R.id.hotproducts_info_one_word);// 一句话营销
		PRD_DESCRIPTION = (TextView) findViewById(R.id.hotproducts_info_prd_description);// 产品描述
		DLYH = (TextView) findViewById(R.id.hotproducts_info_dlyh);// 代理银行
		CPLX = (TextView) findViewById(R.id.hotproducts_info_cplx);// 产品类型
		CPZL = (TextView) findViewById(R.id.hotproducts_info_cpzl);// 产品种类
		CURRENCY = (TextView) findViewById(R.id.hotproducts_info_currency);// 币种
		GRZDRGJE = (TextView) findViewById(R.id.hotproducts_info_grzdrgje);// 个人最低认购金额
		GRDZJE = (TextView) findViewById(R.id.hotproducts_info_grdzje);// 个人递增金额
		DGZDRGJE = (TextView) findViewById(R.id.hotproducts_info_dgzdrgje);// 机构最低认购金额
		DGDZJE = (TextView) findViewById(R.id.hotproducts_info_dgdzje);// 机构递增金额
		XYZGMJJE = (TextView) findViewById(R.id.hotproducts_info_xyzgmjje);// 协议最高募集金额
		XYZDMJJE = (TextView) findViewById(R.id.hotproducts_info_xyzdmjje);// 协议最低募集金额
		ZGNSYL = (TextView) findViewById(R.id.hotproducts_info_zgnsyl);// 最高年收益率
		SJNSYL = (TextView) findViewById(R.id.hotproducts_info_sjnsyl);// 实际年收益率
		SXFL = (TextView) findViewById(R.id.hotproducts_info_sxfl);// 手续费率
		SYKHLX = (TextView) findViewById(R.id.hotproducts_info_sykhlx);// 适用客户类型
		CPFXDJ = (TextView) findViewById(R.id.hotproducts_info_cpfxdj);// 产品风险等级
		ZJTX = (TextView) findViewById(R.id.hotproducts_info_zjtx);// 资金投向
		MJQSR = (TextView) findViewById(R.id.hotproducts_info_mjqsr);// 募集起始日
		MJZZR = (TextView) findViewById(R.id.hotproducts_info_mjzzr);// 募集终止日
		CPQXR = (TextView) findViewById(R.id.hotproducts_info_cpqxr);// 产品起息日
		CPDQR = (TextView) findViewById(R.id.hotproducts_info_cpdqr);// 产品到期日
		CPQX = (TextView) findViewById(R.id.hotproducts_info_cpqx);// 产品期限
	}

}
