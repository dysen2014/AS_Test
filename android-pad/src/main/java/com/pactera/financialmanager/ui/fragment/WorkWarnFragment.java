package com.pactera.financialmanager.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.MyHScrollView;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作平台下的工作提醒的fragment
 */

public class WorkWarnFragment extends ParentFragment implements OnClickListener,MyHScrollView.OnScrollListener {
	// 个人&对公 顶部按钮
	private RadioButton rbtnWarnPerson,rbtnWarnCommon;
	// 消息提醒数量控件
	private TextView tvNum[] = new TextView[12];
	// 标签图标布局
	private RelativeLayout rlItem1, rlItem2, rlItem3, rlItem4, rlItem5,
			rlItem6, rlItem7, rlItem8, rlItem9, rlItem10, rlItem11,rlItem12;
	// 标签文字
	private TextView tvTitle1, tvTitle2, tvTitle3, tvTitle4, tvTitle5, tvTitle6,
			tvTitle7, tvTitle8, tvTitle9, tvTitle10, tvTitle11,tvTitle12;

    private HConnection HConPerson;
	private HConnection HConCommon;
	private static final int INDEX_MESSAGE_PERSON_NUMS = 1;
	private static final int INDEX_MESSAGE_COMMON_NUMS = 2;
	private static final String TYPE_PERSON = "01"; // 对个人
	private static final String TYPE_COMMON = "02";	// 对公
	private List<CustEvent> custEventsForPerson = null;
	private List<CustEvent> custEventsForCommon = null;
	
	// 消息提醒
	public static List<CustEvent> tempEventsForPerson = null;
	public static List<CustEvent> tempEventsForCommon = null;
	
	// 是否选择个人提醒
	private boolean isForperson = true;
	private int selectTabIndex = 1;

	private ImageView imgDot0, imgDot1;
    private MyHScrollView hs_scrollview;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			/* 对私客户列表 */
			case INDEX_MESSAGE_PERSON_NUMS:
				custEventsForPerson = (ArrayList<CustEvent>)msg.obj;
				if(custEventsForPerson != null && custEventsForPerson.size() > 0){
					tempEventsForPerson = custEventsForPerson;
					setTopNum(custEventsForPerson);
				}
				break;
			/* 对公客户列表 */
			case INDEX_MESSAGE_COMMON_NUMS:
				custEventsForCommon = (ArrayList<CustEvent>)msg.obj;
				if(custEventsForCommon != null && custEventsForCommon.size() > 0){
					tempEventsForCommon = custEventsForCommon;
					// 对公获取头数据，不能直接赋值，否则会覆盖对个人数据，所以用temp集合保存
					//setTopNum(custEventsForCommon);
				}
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragmentworkwarn, null);
		setupView(view);
		hiddenTabItems();
		setListener();
		if(getActivity() instanceof WorkPlaceItemChange){
			((WorkPlaceItemChange)getActivity()).workPlacestyleChange(1, isForperson);
		}
		
		// 请求头数量信息
		sendRequestForPersonNum();
		sendRequestForCommonNum();
        setDotBg(0);
        return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	
	private void setupView(View view) {
		rbtnWarnPerson = (RadioButton) view.findViewById(R.id.rbtn_warnperson);
		rbtnWarnCommon = (RadioButton) view.findViewById(R.id.rbtn_warncommon);

		rlItem1 = (RelativeLayout) view.findViewById(R.id.rl_personitem1);
		rlItem2 = (RelativeLayout) view.findViewById(R.id.rl_personitem2);
		rlItem3 = (RelativeLayout) view.findViewById(R.id.rl_personitem3);
		rlItem4 = (RelativeLayout) view.findViewById(R.id.rl_personitem4);
		rlItem5 = (RelativeLayout) view.findViewById(R.id.rl_personitem5);
		rlItem6 = (RelativeLayout) view.findViewById(R.id.rl_personitem6);
		rlItem7 = (RelativeLayout) view.findViewById(R.id.rl_personitem77);
		rlItem8 = (RelativeLayout) view.findViewById(R.id.rl_personitem8);
		rlItem9 = (RelativeLayout) view.findViewById(R.id.rl_personitem9);
		rlItem10 = (RelativeLayout) view.findViewById(R.id.rl_personitem10);
		rlItem11 = (RelativeLayout) view.findViewById(R.id.rl_personitem11);
		rlItem12 = (RelativeLayout) view.findViewById(R.id.rl_personitem12);//金融需求
		tvNum[0] = (TextView) view.findViewById(R.id.imgtvtop1);
		tvNum[1] = (TextView) view.findViewById(R.id.imgtvtop2);
		tvNum[2] = (TextView) view.findViewById(R.id.imgtvtop3);
		tvNum[3] = (TextView) view.findViewById(R.id.imgtvtop4);
		tvNum[4] = (TextView) view.findViewById(R.id.imgtvtop5);
		tvNum[5] = (TextView) view.findViewById(R.id.imgtvtop6);
		tvNum[6] = (TextView) view.findViewById(R.id.imgtvtop7);
		tvNum[7] = (TextView) view.findViewById(R.id.imgtvtop8);
		tvNum[8] = (TextView) view.findViewById(R.id.imgtvtop9);
		tvNum[9] = (TextView) view.findViewById(R.id.imgtvtop10);
		tvNum[10] = (TextView) view.findViewById(R.id.imgtvtop11);
		tvNum[11] = (TextView) view.findViewById(R.id.imgtvtop12);
		tvTitle1 = (TextView) view.findViewById(R.id.tv_title01);
		tvTitle2 = (TextView) view.findViewById(R.id.tv_title02);
		tvTitle3 = (TextView) view.findViewById(R.id.tv_title03);
		tvTitle4 = (TextView) view.findViewById(R.id.tv_title04);
		tvTitle5 = (TextView) view.findViewById(R.id.tv_title05);
		tvTitle6 = (TextView) view.findViewById(R.id.tv_title06);
		tvTitle7 = (TextView) view.findViewById(R.id.tv_title07);
		tvTitle8 = (TextView) view.findViewById(R.id.tv_title08);
		tvTitle9 = (TextView) view.findViewById(R.id.tv_title09);
		tvTitle10 = (TextView) view.findViewById(R.id.tv_title10);
		tvTitle11 = (TextView) view.findViewById(R.id.tv_title11);
		tvTitle12 = (TextView) view.findViewById(R.id.tv_title12);
		setSelectTextcolor(tvTitle1);

        hs_scrollview = (MyHScrollView) view.findViewById(R.id.hs_scrollview);
		imgDot0 = (ImageView) view.findViewById(R.id.dot_num_0);
		imgDot1 = (ImageView) view.findViewById(R.id.dot_num_1);
	}
	
	/**
	 * 设置字体颜色
	 * @param tvSelect
	 */
	private void setSelectTextcolor(TextView tvSelect){
		tvTitle1.setTextColor(getResources().getColor(R.color.black));
		tvTitle2.setTextColor(getResources().getColor(R.color.black));
		tvTitle3.setTextColor(getResources().getColor(R.color.black));
		tvTitle4.setTextColor(getResources().getColor(R.color.black));
		tvTitle5.setTextColor(getResources().getColor(R.color.black));
		tvTitle6.setTextColor(getResources().getColor(R.color.black));
		tvTitle7.setTextColor(getResources().getColor(R.color.black));
		tvTitle8.setTextColor(getResources().getColor(R.color.black));
		tvTitle9.setTextColor(getResources().getColor(R.color.black));
		tvTitle10.setTextColor(getResources().getColor(R.color.black));
		tvTitle11.setTextColor(getResources().getColor(R.color.black));
		tvTitle12.setTextColor(getResources().getColor(R.color.black));
		// 选择字体标黄
		tvSelect.setTextColor(getResources().getColor(R.color.separatelightredline));
	}

	private void setListener() {
		rbtnWarnPerson.setOnClickListener(this);
		rbtnWarnCommon.setOnClickListener(this);
		rlItem1.setOnClickListener(this);
		rlItem2.setOnClickListener(this);
		rlItem3.setOnClickListener(this);
		rlItem4.setOnClickListener(this);
		rlItem5.setOnClickListener(this);
		rlItem6.setOnClickListener(this);
		rlItem7.setOnClickListener(this);
		rlItem8.setOnClickListener(this);
		rlItem9.setOnClickListener(this);
		rlItem10.setOnClickListener(this);
		rlItem11.setOnClickListener(this);
		rlItem12.setOnClickListener(this);
        hs_scrollview.setOnScrollListener(this);
    }
	
	/**
	 * 消息请求
	 */
	private void sendRequestForPersonNum() {
		if (Tool.haveNet(getActivity())) {
			String staid = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.WORKWARN_NUM_Get;// "T000067";顶部头消息数量

			String info = "&spareOne=" + TYPE_PERSON + "&spareTwo=" + staid;
			HConPerson = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_MESSAGE_PERSON_NUMS, false);	
		}
	}
	
	private void sendRequestForCommonNum() {
		if (Tool.haveNet(getActivity())) {
			String staid = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.WORKWARN_NUM_Get;// "T000067";顶部头消息数量

			String info = "&spareOne=" + TYPE_COMMON + "&spareTwo=" + staid;
			HConCommon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_MESSAGE_COMMON_NUMS, false);	
		}
	}
	

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR://500-
			Toast.makeText(getActivity(), "获取客户信息失败", Toast.LENGTH_SHORT).show();
			break;
		// 对个人
		case INDEX_MESSAGE_PERSON_NUMS:
			HResponse resP = HConPerson.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (resP == null || resP.dataJsonObject == null) {
				return;
			}
			readJson(resP.dataJsonObject, connectionIndex);
			break;
		// 对公	
		case INDEX_MESSAGE_COMMON_NUMS:
			HResponse resC = HConCommon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (resC == null || resC.dataJsonObject == null) {
				return;
			}
			readJson(resC.dataJsonObject, connectionIndex);
			break;
		}
	}
	
	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = "";
		try {
			if (tmpJsonObject.has("retCode")) { // 返回标志
				retCode = tmpJsonObject.getString("retCode");
			}

			// 获取接口成功
			if (retCode.equals("0000")) {
				Message msg = new Message();
				msg.arg1 = connectionIndex;
				// 获取列表
				if(connectionIndex == INDEX_MESSAGE_PERSON_NUMS
						|| connectionIndex == INDEX_MESSAGE_COMMON_NUMS){
					String group = tmpJsonObject.getString("group");
					List<CustEvent> mycustEvenList = JSON.parseArray(group, CustEvent.class);
					msg.obj = mycustEvenList;
				}
				handler.sendMessage(msg);
			} else {
				Toast.makeText(getActivity(), "操作失败! 错误:"+retCode, Toast.LENGTH_SHORT).show();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 设置按钮状态
	 * @param custEvents
	 */
	private void setTopNum(List<CustEvent> custEvents){
		// 顶部标题提示数据
		int item1Num = 0, item2Num = 0, item3Num = 0;
		int item4Num = 0, item5Num = 0, item6Num = 0;
		int item7Num = 0, item8Num = 0, item9Num = 0;
		int item10Num = 0, item11Num = 0,item12Num = 0;;
		for(int i=0; i<custEvents.size(); i++){
			CustEvent event = custEvents.get(i);
			String eventType = event.getEvent_type();
			String numStr = event.getNum();
			// 判断num是否为空串，如果为""，则赋值为0
			if(TextUtils.isEmpty(numStr)){
				numStr = "0";
			}

			/* 
			 * 【对个人提醒】
			 */
			if(isForperson){
				// 未来十天内定期存款到期 / 未来十天内理财产品到期 / 未来三十天内贷款产品到期
				if(eventType.equals("1") || eventType.equals("2") || eventType.equals("3")){
					item1Num += Integer.parseInt(numStr);
				}
				// 贷款逾期 / 信用卡逾期 / 利息逾期
				if(eventType.equals("4") || eventType.equals("5") || eventType.equals("6")){
					item2Num += Integer.parseInt(numStr);
				}
				// 客户单日累计（入、出）超过10万元 / 客户单日累计（入、出）超过10万元
				if(eventType.equals("7") || eventType.equals("8")){
					item3Num += Integer.parseInt(numStr);
				}
				// 客户账户余额不足
				if(eventType.equals("9")){
					item4Num += Integer.parseInt(numStr);
				}
				// 未来三天内客户生日
				if(eventType.equals("10")){
					item5Num += Integer.parseInt(numStr);
				}
				// 客户升降级
				if(eventType.equals("11")){
					item6Num += Integer.parseInt(numStr);
				}
				// 工作事项
				if(eventType.equals("15")){
					item7Num += Integer.parseInt(numStr);
				}
                // 存款预约 / 理财预约 /贷款需求/电子银行产品需求/其它需求
                if (eventType.equals("12") || eventType.equals("13") || eventType.equals("14") || eventType.equals("60")){
//						|| eventType.equals("61")) {
                    item12Num += Integer.parseInt(numStr);
                }
            }

			/*
			 * 【对公提醒】
			 */
			else{
				// 未来十天内定期存款到期 / 未来十天内理财产品到期 / 未来三十天内贷款产品到期 / 未来三十天内承兑汇票到期
				if(eventType.equals("16") || eventType.equals("17")
						|| eventType.equals("18") || eventType.equals("19")){
					item1Num += Integer.parseInt(numStr);
				}
				// 贷款逾期 / 信用卡逾期 / 利息逾期
				if(eventType.equals("20") || eventType.equals("21") || eventType.equals("22")){
					item2Num += Integer.parseInt(numStr);
				}
				// 客户单笔（入、出）超过50万元 / 客户单日累计（入、出）超过50万元
				if(eventType.equals("23") || eventType.equals("24")){
					item3Num += Integer.parseInt(numStr);
				}
				// 客户账户余额不足
				if(eventType.equals("25")){
					item4Num += Integer.parseInt(numStr);
				}
				// 工作事项
				if(eventType.equals("32")){
					item7Num += Integer.parseInt(numStr);
				}
				// 客户账户异常提醒
				if(eventType.equals("26")){
					item8Num += Integer.parseInt(numStr);
				}
				// 客户关怀类
				if(eventType.equals("27")){
					item9Num += Integer.parseInt(numStr);
				}
				// 客户升降级
				if(eventType.equals("28")){
					item6Num += Integer.parseInt(numStr);
				}
				// 基本户销户提醒
				if(eventType.equals("33")){
					item10Num += Integer.parseInt(numStr);
				}
				// 一般户销户提醒
				if(eventType.equals("34")){
					item11Num += Integer.parseInt(numStr);
				}
                // 存款预约 / 理财需求 /贷款需求/电子产品需求/其它需求
                if (eventType.equals("29") || eventType.equals("30")){
//						|| eventType.equals("31")) {
                    item12Num += Integer.parseInt(numStr);
                }
			}
		}
		
		// 设置按钮状态
		for(int i=0; i<tvNum.length; i++){
			tvNum[i].setVisibility(View.GONE);
		}
		
		String maxValue = "99+";
		if(item1Num != 0){
			tvNum[0].setVisibility(View.VISIBLE);
			tvNum[0].setText(""+item1Num);
			// 数量大于99，显示99+
			if(item1Num > 99){
				tvNum[0].setText(maxValue);
			}
		}
		if(item2Num != 0){
			tvNum[1].setVisibility(View.VISIBLE);
			tvNum[1].setText(""+item2Num);
			if(item2Num > 99){
				tvNum[1].setText(maxValue);
			}
		}
		if(item3Num != 0){
			tvNum[2].setVisibility(View.VISIBLE);
			tvNum[2].setText(""+item3Num);
			if(item3Num > 99){
				tvNum[2].setText(maxValue);
			}
		}
		if(item4Num != 0){
			tvNum[3].setVisibility(View.VISIBLE);
			tvNum[3].setText(""+item4Num);
			if(item4Num > 99){
				tvNum[3].setText(maxValue);
			}
		}
		if(item5Num != 0){
			tvNum[4].setVisibility(View.VISIBLE);
			tvNum[4].setText(""+item5Num);
			if(item5Num > 99){
				tvNum[4].setText(maxValue);
			}
		}
		if(item6Num != 0){
			tvNum[5].setVisibility(View.VISIBLE);
			tvNum[5].setText(""+item6Num);
			if(item6Num > 99){
				tvNum[5].setText(maxValue);
			}
		}
		if(item7Num != 0){
			tvNum[6].setVisibility(View.VISIBLE);
			tvNum[6].setText(""+item7Num);
			if(item7Num > 99){
				tvNum[6].setText(maxValue);
			}
		}
		if(item8Num != 0){
			tvNum[7].setVisibility(View.VISIBLE);
			tvNum[7].setText(""+item8Num);
			if(item8Num > 99){
				tvNum[7].setText(maxValue);
			}
		}
		if(item9Num != 0){
			tvNum[8].setVisibility(View.VISIBLE);
			tvNum[8].setText(""+item9Num);
			if(item9Num > 99){
				tvNum[8].setText(maxValue);
			}
		}
		if(item10Num != 0){
			tvNum[9].setVisibility(View.VISIBLE);
			tvNum[9].setText(""+item10Num);
			if(item10Num > 99){
				tvNum[9].setText(maxValue);
			}
		}
		if(item11Num != 0){
			tvNum[10].setVisibility(View.VISIBLE);
			tvNum[10].setText(""+item11Num);
			if(item11Num > 99){
				tvNum[10].setText(maxValue);
			}
		}
        if(item12Num != 0){
			tvNum[11].setVisibility(View.VISIBLE);
			tvNum[11].setText(""+item12Num);
			if(item12Num > 99){
				tvNum[11].setText(maxValue);
			}
		}
	}
	
	/**
	 * 根据对公对私来显示不同头选项卡
	 */
	private void hiddenTabItems(){
		if(isForperson){
			rlItem5.setVisibility(View.VISIBLE);
			rlItem8.setVisibility(View.GONE);
			rlItem9.setVisibility(View.GONE);
			rlItem10.setVisibility(View.GONE);
			rlItem11.setVisibility(View.GONE);
		}else{
			rlItem5.setVisibility(View.GONE);
			rlItem8.setVisibility(View.VISIBLE);
			rlItem9.setVisibility(View.VISIBLE);
			rlItem10.setVisibility(View.VISIBLE);
			rlItem11.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置轮播图片中的小白点
	 */
	private void setDotBg(int index) {
		imgDot0.setImageResource(R.drawable.main_ad_dot_normal);
		imgDot1.setImageResource(R.drawable.main_ad_dot_normal);
		if (index == 0) {
			imgDot0.setImageResource(R.drawable.main_ad_dot_select);
		} else if (index == 1) {
			imgDot1.setImageResource(R.drawable.main_ad_dot_select);
		}
	}
	
	/**
	 * 对公对私-切换选择不同的fragment
	 * @param index
	 */
	private void resetFragment(int index){
		switch (index) {
		case 1:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(1, isForperson);
			}
			selectTabIndex = 1;
			setSelectTextcolor(tvTitle1);
			break;
		case 2:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(2, isForperson);
			}
			selectTabIndex = 2;
			setSelectTextcolor(tvTitle2);
			break;
		case 3:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(3, isForperson);
			}
			selectTabIndex = 3;
			setSelectTextcolor(tvTitle3);
			break;
		case 4:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(4, isForperson);
			}
			selectTabIndex = 4;
			setSelectTextcolor(tvTitle4);
			break;
		case 5:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(5, isForperson);
			}
			selectTabIndex = 5;
			setSelectTextcolor(tvTitle5);
			break;
		case 6:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(6, isForperson);
			}
			selectTabIndex = 6;
			setSelectTextcolor(tvTitle6);
			break;
		case 7:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(7, isForperson);
			}
			selectTabIndex = 7;
			setSelectTextcolor(tvTitle7);
			break;
		case 8:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(8, isForperson);
			}
			selectTabIndex = 8;
			setSelectTextcolor(tvTitle8);
			break;
		case 9:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(9, isForperson);
			}
			selectTabIndex = 9;
			setSelectTextcolor(tvTitle9);
			break;
		case 10:
			selectTabIndex = 10;
			setSelectTextcolor(tvTitle10);
			break;
		case 11:
			selectTabIndex = 11;
			setSelectTextcolor(tvTitle11);
			break;
		case 12:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(12, isForperson);
			}
			selectTabIndex = 12;
			setSelectTextcolor(tvTitle12);
			break;
		default:
			break;
		}
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 个人提醒
		case R.id.rbtn_warnperson:
			isForperson = true;
			hiddenTabItems();
			if(custEventsForPerson != null && custEventsForPerson.size() > 0){
				setTopNum(custEventsForPerson);
			}else{
				sendRequestForPersonNum();
			}
			// 点击对公切换到对私，选择下标大于6，默认显示第一项
            if (selectTabIndex > 7 && selectTabIndex != 12) {
                if(getActivity() instanceof WorkPlaceItemChange){
					((WorkPlaceItemChange)getActivity()).workPlacestyleChange(1, isForperson);
				}
				
				selectTabIndex = 1;
				setSelectTextcolor(tvTitle1);
			}
			resetFragment(selectTabIndex);
			break;
		// 对公提醒
		case R.id.rbtn_warncommon:
			isForperson = false;
			hiddenTabItems();
			if(custEventsForCommon != null && custEventsForCommon.size() > 0){
				setTopNum(custEventsForCommon);
			}else{
				sendRequestForCommonNum();
			}
			
			// 点击对个人切换到对公，选择下标等于[客户生日]，默认显示第一项
			if(selectTabIndex == 5){
				if(getActivity() instanceof WorkPlaceItemChange){
					((WorkPlaceItemChange)getActivity()).workPlacestyleChange(1, isForperson);
				}
				
				selectTabIndex = 1;
				setSelectTextcolor(tvTitle1);
			}
			resetFragment(selectTabIndex);
			break;
		
		/* ============  产品到期   ============ */ 
		case R.id.rl_personitem1:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(1, isForperson);
			}
			selectTabIndex = 1;
			setSelectTextcolor(tvTitle1);
			
			// 对公对私状态
//			if(isForperson){
//				tempMap = mapPersonNumStatus;
//			}else{
//				tempMap = mapCommonNumStatus;
//			}
//			boolean onClickStatus = tempMap.get(selectTabIndex);
//			if(onClickStatus){
//				rlItem1.setVisibility(View.INVISIBLE);
//			}
			break;
		/* ============  客户违约   ============ */ 
		case R.id.rl_personitem2:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(2, isForperson);
			}
			selectTabIndex = 2;
			setSelectTextcolor(tvTitle2);
			break;
		/* ============  大额资金变动  ============ */ 
		case R.id.rl_personitem3:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(3, isForperson);
			}
			selectTabIndex = 3;
			setSelectTextcolor(tvTitle3);
			break;
		/* ============  账户余额不足   ============ */ 
		case R.id.rl_personitem4:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(4, isForperson);
			}
			selectTabIndex = 4;
			setSelectTextcolor(tvTitle4);
			break;
		/* ============  客户生日   ============ */ 
		case R.id.rl_personitem5:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(5, isForperson);
			}
			selectTabIndex = 5;
			setSelectTextcolor(tvTitle5);
			break;
		/* ============  客户升降级   ============ */ 
		case R.id.rl_personitem6:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(6, isForperson);
			}
			selectTabIndex = 6;
			setSelectTextcolor(tvTitle6);
			break; 
		/* ============  用户工作事项   ============ */
		case R.id.rl_personitem77:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(7, isForperson);
			}
			selectTabIndex = 7;
			setSelectTextcolor(tvTitle7);
			break;
		/* ============  账户异常提醒   ============ */
		case R.id.rl_personitem8:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(8, isForperson);
			}
			selectTabIndex = 8;
			setSelectTextcolor(tvTitle8);
			break;
		/* ============  客户关怀提醒   ============ */
		case R.id.rl_personitem9:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(9, isForperson);
			}
			selectTabIndex = 9;
			setSelectTextcolor(tvTitle9);
			break;
		/* ============  基本户销户     ============ */
		case R.id.rl_personitem10:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(10, isForperson);
			}
			selectTabIndex = 10;
			setSelectTextcolor(tvTitle10);
			break;
		/* ============  一般户销户     ============ */
		case R.id.rl_personitem11:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(11, isForperson);
			}
			selectTabIndex = 11;
			setSelectTextcolor(tvTitle11);
			break;
		/* ============  金融需求     ============ */
		case R.id.rl_personitem12:
			if (getActivity() instanceof WorkPlaceItemChange) {
					((WorkPlaceItemChange) getActivity()).workPlacestyleChange(12, isForperson);
			}
			selectTabIndex = 12;
			setSelectTextcolor(tvTitle12);
			break;
		default:
			break;
		}
	}


    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {
        if(x>200){
            setDotBg(1);
        }else
            setDotBg(0);
    }
}
