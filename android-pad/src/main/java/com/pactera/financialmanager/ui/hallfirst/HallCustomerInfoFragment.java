package com.pactera.financialmanager.ui.hallfirst;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.CMMarketingActivity2CustomerInfo;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 客户信息的fragment
 */
public class HallCustomerInfoFragment extends ParentFragment {
	
	private TextView tvTitle11, tvTitle12, tvTitle13, tvTitle14, tvTitle15,
			tvTitle16, tvTitle17, tvTitle18, tvTitle19, tvTitle20, tvTitle21,
			tvTitle38, tvTitle39, tvTitle23, tvTitle24, tvTitle25, tvTitle26, tvTitle27,
			tvTitle28, tvTitle29, tvTitle30, tvTitle31, tvTitle32, tvTitle33,
			tvTitle34, tvTitle35, tvTitle36, tvTitle37;
	private ImageView tvInfo11, tvInfo12, tvInfo13, tvInfo14, tvInfo15,
			tvInfo16, tvInfo17, tvInfo18, tvInfo19, tvInfo20, tvInfo21,
			tvInfo38, tvInfo39, tvInfo23, tvInfo24, tvInfo25, tvInfo26, tvInfo27,
			tvInfo28, tvInfo29, tvInfo30, tvInfo31, tvInfo32, tvInfo33,
			tvInfo34, tvInfo35, tvInfo36, tvInfo37;
	
	private ImageView imvRefresh;
	private LinearLayout layLoading;
	private LinearLayout layNodate;
	private TextView tvResult;
	private WebView chartshow_wb;
	private String pieDateArray;
	private String pieTitleArray;

	private boolean isForperson = false;
	private CMMarketingActivity2CustomerInfo tempCus;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_customerinfo, null);
		setupView(view);
		return view;
	}
	
	private void setupView(View view) {
		tvInfo11=(ImageView)view.findViewById(R.id.imv_v11);// 借记卡 ------
		tvInfo12=(ImageView)view.findViewById(R.id.imv_v12);// 信用卡 ------
		tvInfo13=(ImageView)view.findViewById(R.id.imv_v13);// 个人手机银行 ------
		tvInfo14=(ImageView)view.findViewById(R.id.imv_v14);// 商务手机银行 ------
		tvInfo15=(ImageView)view.findViewById(R.id.imv_v15);// 电话银行 ------
		tvInfo19=(ImageView)view.findViewById(R.id.imv_v19);// 微信银行 ------
		tvInfo20=(ImageView)view.findViewById(R.id.imv_v20);// 支付宝 ------
		tvInfo21=(ImageView)view.findViewById(R.id.imv_v21);// 银联在线 ------
		tvInfo23=(ImageView)view.findViewById(R.id.imv_v23);// 京东签约 ------
		tvInfo24=(ImageView)view.findViewById(R.id.imv_v24);// 财付通签约 ------
		tvInfo25=(ImageView)view.findViewById(R.id.imv_v25);// 百付宝签约 ------
		tvInfo16=(ImageView)view.findViewById(R.id.imv_v16);// 短信银行 *******
		tvInfo17=(ImageView)view.findViewById(R.id.imv_v17);// pos机 *******
		tvInfo18=(ImageView)view.findViewById(R.id.imv_v18);// 卡乐付 *******
		tvInfo38=(ImageView)view.findViewById(R.id.imv_v38);// 个人网银 *******
		tvInfo39=(ImageView)view.findViewById(R.id.imv_v39);// 个人网银iPad版 *******
		tvInfo26=(ImageView)view.findViewById(R.id.imv_v26);// 代缴电费  *******
		tvInfo27=(ImageView)view.findViewById(R.id.imv_v27);// 代缴水费  *******
		tvInfo28=(ImageView)view.findViewById(R.id.imv_v28);// 省财税库行签约 ====
		tvInfo29=(ImageView)view.findViewById(R.id.imv_v29);// 代发工资====
		tvInfo30=(ImageView)view.findViewById(R.id.imv_v30);// 单位结算卡持有 ====
		tvInfo31=(ImageView)view.findViewById(R.id.imv_v31);// 活期存款持有 ====
		tvInfo32=(ImageView)view.findViewById(R.id.imv_v32);// 定期存款持有 ====
		tvInfo33=(ImageView)view.findViewById(R.id.imv_v33);// 贷款持有 ====
		tvInfo34=(ImageView)view.findViewById(R.id.imv_v34);// 贴现持有 ====
		tvInfo35=(ImageView)view.findViewById(R.id.imv_v35);// 承兑汇票持有 ====
		tvInfo36=(ImageView)view.findViewById(R.id.imv_v36);// 行内理财持有 ====
		tvInfo37=(ImageView)view.findViewById(R.id.imv_v37);// 兴业银行理财持有 ====
		
		tvTitle11=(TextView)view.findViewById(R.id.tv_t11);// 借记卡 ------
		tvTitle12=(TextView)view.findViewById(R.id.tv_t12);// 信用卡 ------
		tvTitle13=(TextView)view.findViewById(R.id.tv_t13);// 个人手机银行 ------
		tvTitle14=(TextView)view.findViewById(R.id.tv_t14);// 商务手机银行 ------
		tvTitle15=(TextView)view.findViewById(R.id.tv_t15);// 电话银行 ------
		tvTitle19=(TextView)view.findViewById(R.id.tv_t19);// 微信银行 ------
		tvTitle20=(TextView)view.findViewById(R.id.tv_t20);// 支付宝 ------
		tvTitle21=(TextView)view.findViewById(R.id.tv_t21);// 银联在线 ------
		tvTitle23=(TextView)view.findViewById(R.id.tv_t23);// 京东签约 ------
		tvTitle24=(TextView)view.findViewById(R.id.tv_t24);// 财付通签约 ------
		tvTitle25=(TextView)view.findViewById(R.id.tv_t25);// 百付宝签约 ------
		tvTitle16=(TextView)view.findViewById(R.id.tv_t16);// 短信银行 *******
		tvTitle17=(TextView)view.findViewById(R.id.tv_t17);// pos机 *******
		tvTitle18=(TextView)view.findViewById(R.id.tv_t18);// 卡乐付 *******
		tvTitle38=(TextView)view.findViewById(R.id.tv_t38);// 个人网银 *******
		tvTitle39=(TextView)view.findViewById(R.id.tv_t39);// 个人网银iPad版 *******
		tvTitle26=(TextView)view.findViewById(R.id.tv_t26);// 代缴电费  *******
		tvTitle27=(TextView)view.findViewById(R.id.tv_t27);// 代缴水费  *******
		tvTitle28=(TextView)view.findViewById(R.id.tv_t28);// 省财税库行签约 ====
		tvTitle29=(TextView)view.findViewById(R.id.tv_t29);// 代发工资====
		tvTitle30=(TextView)view.findViewById(R.id.tv_t30);// 单位结算卡持有 ====
		tvTitle31=(TextView)view.findViewById(R.id.tv_t31);// 活期存款持有 ====
		tvTitle32=(TextView)view.findViewById(R.id.tv_t32);// 定期存款持有 ====
		tvTitle33=(TextView)view.findViewById(R.id.tv_t33);// 贷款持有 ====
		tvTitle34=(TextView)view.findViewById(R.id.tv_t34);// 贴现持有 ====
		tvTitle35=(TextView)view.findViewById(R.id.tv_t35);// 承兑汇票持有 ====
		tvTitle36=(TextView)view.findViewById(R.id.tv_t36);// 行内理财持有 ====
		tvTitle37=(TextView)view.findViewById(R.id.tv_t37);// 兴业银行理财持有 ====
		
		imvRefresh = (ImageView) view.findViewById(R.id.imv_refresh);
		// 初始化本地控件
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodate = (LinearLayout) view.findViewById(R.id.lay_nodata);
		tvResult = (TextView) view.findViewById(R.id.tv_result);
		chartshow_wb = (WebView) view.findViewById(R.id.chartshow_wb);
		// 开启本地文件读取（默认为true，不设置也可以）
		chartshow_wb.getSettings().setAllowFileAccess(true);
		chartshow_wb.getSettings().setJavaScriptEnabled(true);// 开启脚本支持
		chartshow_wb.loadUrl("file:///android_asset/echart/echart_cuspapers.html");
		
		// 刷新饼图
		imvRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setCutomerInfo(isForperson, tempCus);
			}
		});
	}
	
	
	/**
	 * 字符转义HUTSGQ
	 * @param value
	 * @return
	 */
	private int getValue(String value){
		if ("1".equals(value)) {
			return R.drawable.cb_yes;
		}
		return R.drawable.cb_no;
	}
	
	
	/**
	 * 设置签约服务值
	 * @param customerinfo
	 */
	public void setCutomerInfo(boolean isForPerson, CMMarketingActivity2CustomerInfo customerinfo) {
		isForperson = isForPerson;
		tempCus = customerinfo;
		setHiddentItem(isForPerson);
		
		tvInfo11.setImageResource(getValue(customerinfo.getDEBIT_CARD_HOLD()));
		tvInfo12.setImageResource(getValue(customerinfo.getCRDT_CARD_HOLD()));
		tvInfo13.setImageResource(getValue(customerinfo.getMOBL_BANK_SGN()));
		tvInfo14.setImageResource(getValue(customerinfo.getB_MOBL_BANK_SGN()));
		tvInfo15.setImageResource(getValue(customerinfo.getTEL_BANK_SGN()));
		tvInfo16.setImageResource(getValue(customerinfo.getSMS_BANK_SGN()));
		tvInfo17.setImageResource(getValue(customerinfo.getPOS_SGN()));
		tvInfo18.setImageResource(getValue(customerinfo.getKLF_SGN()));
		tvInfo19.setImageResource(getValue(customerinfo.getWXBANK_SGN()));
		tvInfo20.setImageResource(getValue(customerinfo.getALIPAY_SGN()));
		tvInfo21.setImageResource(getValue(customerinfo.getUNPAY_SGN()));
		
		tvInfo38.setImageResource(getValue(customerinfo.getEBANK_SGN()));
		tvInfo39.setImageResource(getValue(customerinfo.getEBANK_PAD_SGN()));
		tvInfo23.setImageResource(getValue(customerinfo.getJDPAY_SGN()));
		tvInfo24.setImageResource(getValue(customerinfo.getTENPAY_SGN()));
		tvInfo25.setImageResource(getValue(customerinfo.getBDPAY_SGN()));
		tvInfo26.setImageResource(getValue(customerinfo.getPWR_SGN()));
		tvInfo27.setImageResource(getValue(customerinfo.getWRT_SGN()));
		
		tvInfo28.setImageResource(getValue(customerinfo.getTPS_SGN()));
		tvInfo29.setImageResource(getValue(customerinfo.getPAYROLL_SNG()));
		tvInfo30.setImageResource(getValue(customerinfo.getUNIT_CARD_SNG()));
		tvInfo31.setImageResource(getValue(customerinfo.getCURR_DPST_HOLD()));
		tvInfo32.setImageResource(getValue(customerinfo.getTM_DPST_HOLD()));
		tvInfo33.setImageResource(getValue(customerinfo.getLOAN_HOLD()));
		tvInfo34.setImageResource(getValue(customerinfo.getDIS_HOLD()));
		tvInfo35.setImageResource(getValue(customerinfo.getBGD_HOLD()));
		tvInfo36.setImageResource(getValue(customerinfo.getFNC_HOLD()));
		tvInfo37.setImageResource(getValue(customerinfo.getXYB_FNC_HOLD()));
		
		String pro = customerinfo.getPRO_PRO_AMT();	// 客户总资产
		String deposit = customerinfo.getDEPOSIT_PRO_AMT_ALL();// 存款余额
		String fund = customerinfo.getFUND_AMT();// 基金余额
		String mmm = customerinfo.getMMM_PRO_AMT();// 理财余额
		String mamt = customerinfo.getNEW_LC_MAMT();// 个贷余额
		if(!isForperson){
			mmm = customerinfo.getCUR_YR_LOAN_DAVG();
			mamt = customerinfo.getNEW_TIME_YAMT();
		}
		if(TextUtils.isEmpty(pro)){
			pro = "0";
		}
		if(TextUtils.isEmpty(deposit)){
			deposit = "0";
		}
		if(TextUtils.isEmpty(fund)){
			fund = "0";
		}
		if(TextUtils.isEmpty(mmm)){
			mmm = "0";
		}
		if(TextUtils.isEmpty(mamt)){
			mamt = "0";
		}
		loadEChars(mamt, deposit, fund, mmm);
	}
	
	/**
	 * 隐藏左侧对公、对私签约产品选项
	 * @param isForPerson
	 */
	private void setHiddentItem(boolean isForPerson){
		int personVisible = View.VISIBLE;
		int commonVisible = View.GONE;
		if(!isForPerson){
			personVisible = View.GONE;
			commonVisible = View.VISIBLE;
		}
		tvInfo11.setVisibility(personVisible);// 借记卡 ------
		tvInfo12.setVisibility(personVisible);// 信用卡 ------
		tvInfo13.setVisibility(personVisible);// 个人手机银行 ------
		tvInfo14.setVisibility(personVisible);// 商务手机银行 ------
		tvInfo15.setVisibility(personVisible);// 电话银行 ------
		tvInfo19.setVisibility(personVisible);// 微信银行 ------
		tvInfo20.setVisibility(personVisible);// 支付宝 ------
		tvInfo21.setVisibility(personVisible);// 银联在线 ------
		tvInfo23.setVisibility(personVisible);// 京东签约 ------
		tvInfo24.setVisibility(personVisible);// 财付通签约 ------
		tvInfo25.setVisibility(personVisible);// 百付宝签约 ------
		tvInfo28.setVisibility(View.VISIBLE);// 省财税库行签约 ====
		tvInfo16.setVisibility(View.VISIBLE);// 短信银行 *******
		tvInfo17.setVisibility(View.VISIBLE);// pos机 *******
		tvInfo18.setVisibility(View.VISIBLE);// 卡乐付 *******
		tvInfo38.setVisibility(View.VISIBLE);// 个人网银 *******
		tvInfo39.setVisibility(View.VISIBLE);// 个人网银iPad版 *******
		tvInfo26.setVisibility(View.VISIBLE);// 代缴电费  *******
		tvInfo27.setVisibility(View.VISIBLE);// 代缴水费  *******
		tvInfo29.setVisibility(commonVisible);// 代发工资====
		tvInfo30.setVisibility(commonVisible);// 单位结算卡持有 ====
		tvInfo31.setVisibility(commonVisible);// 活期存款持有 ====
		tvInfo32.setVisibility(commonVisible);// 定期存款持有 ====
		tvInfo33.setVisibility(commonVisible);// 贷款持有 ====
		tvInfo34.setVisibility(commonVisible);// 贴现持有 ====
		tvInfo35.setVisibility(commonVisible);// 承兑汇票持有 ====
		tvInfo36.setVisibility(commonVisible);// 行内理财持有 ====
		tvInfo37.setVisibility(commonVisible);// 兴业银行理财持有 ====
		
		tvTitle11.setVisibility(personVisible);// 借记卡 ------
		tvTitle12.setVisibility(personVisible);// 信用卡 ------
		tvTitle13.setVisibility(personVisible);// 个人手机银行 ------
		tvTitle14.setVisibility(personVisible);// 商务手机银行 ------
		tvTitle15.setVisibility(personVisible);// 电话银行 ------
		tvTitle19.setVisibility(personVisible);// 微信银行 ------
		tvTitle20.setVisibility(personVisible);// 支付宝 ------
		tvTitle21.setVisibility(personVisible);// 银联在线 ------
		tvTitle23.setVisibility(personVisible);// 京东签约 ------
		tvTitle24.setVisibility(personVisible);// 财付通签约 ------
		tvTitle25.setVisibility(personVisible);// 百付宝签约 ------
		tvTitle16.setVisibility(View.VISIBLE);// 短信银行 *******
		tvTitle17.setVisibility(View.VISIBLE);// pos机 *******
		tvTitle18.setVisibility(View.VISIBLE);// 卡乐付 *******
		tvTitle38.setVisibility(View.VISIBLE);// 网银签约 *******
		tvTitle39.setVisibility(View.VISIBLE);// 网银签约 *******
		tvTitle26.setVisibility(View.VISIBLE);// 代缴电费  *******
		tvTitle27.setVisibility(View.VISIBLE);// 代缴水费  *******
		tvTitle28.setVisibility(View.VISIBLE);// 省财税库行签约 ====
		tvTitle29.setVisibility(commonVisible);// 代发工资====
		tvTitle30.setVisibility(commonVisible);// 单位结算卡持有 ====
		tvTitle31.setVisibility(commonVisible);// 活期存款持有 ====
		tvTitle32.setVisibility(commonVisible);// 定期存款持有 ====
		tvTitle33.setVisibility(commonVisible);// 贷款持有 ====
		tvTitle34.setVisibility(commonVisible);// 贴现持有 ====
		tvTitle35.setVisibility(commonVisible);// 承兑汇票持有 ====
		tvTitle36.setVisibility(commonVisible);// 行内理财持有 ====
		tvTitle37.setVisibility(commonVisible);// 兴业银行理财持有 ====
	}
	
	/**
	 * 加载饼状图
	 * @param pro 客户总资产
	 * @param deposit 存款余额
	 * @param fund 基金余额
	 * @param mmm 理财余额
	 */
	private void loadEChars(String mamt, String deposit, String fund, String mmm){
		// 无值时，显示空
		if(mamt.equals("0") && deposit.equals("0")
				&& fund.equals("0") && mmm.equals("0")){
			setNodateStatus("");
		}else{
			layLoading.setVisibility(View.GONE);
			layNodate.setVisibility(View.GONE);
			chartshow_wb.setVisibility(View.VISIBLE);
			
			pieTitleArray = "['存款余额','基金余额','理财余额','贷款余额']";
			pieDateArray =  "[{value:"+deposit+", name:'存款余额'},"
							+"{value:"+fund+", name:'基金余额'},"
							+"{value:"+mmm+", name:'理财余额'},"
							+"{value:"+mamt+", name:'贷款余额'}]";

			// 延迟加载
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					chartshow_wb.loadUrl("javascript:createChart('pie', "+pieTitleArray+", "+pieDateArray+");");
				}
			}, 1000);
		}
	}
	
	
	/**
	 * 设置没有查询到数据
	 * @param code
	 */
	public void setNodateStatus(String code){
		layLoading.setVisibility(View.GONE);
		layNodate.setVisibility(View.VISIBLE);
		chartshow_wb.setVisibility(View.GONE);
		
		String error = tvResult.getText() + code;
		tvResult.setVisibility(View.VISIBLE);
		tvResult.setText(error);
	}

}
