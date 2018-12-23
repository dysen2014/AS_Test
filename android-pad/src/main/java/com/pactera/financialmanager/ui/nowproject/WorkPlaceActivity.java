package com.pactera.financialmanager.ui.nowproject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.fragment.FragmentBigMoneyChange;
import com.pactera.financialmanager.ui.fragment.FragmentBigUseMoneyLose;
import com.pactera.financialmanager.ui.fragment.FragmentCusUpDown;
import com.pactera.financialmanager.ui.fragment.FragmentCustomerAnnulbasic;
import com.pactera.financialmanager.ui.fragment.FragmentCustomerAnnulgeneral;
import com.pactera.financialmanager.ui.fragment.FragmentCustomerBirthday;
import com.pactera.financialmanager.ui.fragment.FragmentCustomerWorkItem;
import com.pactera.financialmanager.ui.fragment.FragmentFinancialDemand;
import com.pactera.financialmanager.ui.fragment.FragmentMyreport;
import com.pactera.financialmanager.ui.fragment.FragmentOrderLists;
import com.pactera.financialmanager.ui.fragment.Fragmentaccountexception;
import com.pactera.financialmanager.ui.fragment.Fragmentcustomertakecare;
import com.pactera.financialmanager.ui.fragment.PersonItemProDateFragment;
import com.pactera.financialmanager.ui.fragment.PersonItemcustorderFragment;
import com.pactera.financialmanager.ui.fragment.SigninFragment;
import com.pactera.financialmanager.ui.fragment.SigninRecordActivity;
import com.pactera.financialmanager.ui.fragment.WorkWarnFragment;
import com.pactera.financialmanager.ui.myreport.FragmentMyreportTab00;
import com.pactera.financialmanager.ui.myreport.FragmentMyreportTab01;
import com.pactera.financialmanager.ui.myreport.FragmentMyreportTab02;
import com.pactera.financialmanager.ui.myreport.FragmentMyreportTab03;
import com.pactera.financialmanager.ui.myreport.FragmentMyreportTab04;
import com.pactera.financialmanager.ui.myreport.FragmentMyreportTab05;
import com.pactera.financialmanager.util.LimitsUtil;

/**
 * 我的业绩
 * @author Administrator
 *
 */
public class WorkPlaceActivity extends ParentActivity implements OnClickListener,WorkPlaceItemChange{
	private LinearLayout rlItem1; 	// 工作提醒
	private LinearLayout rlItem2;	// 业务概况
	private LinearLayout rlItem3;	// 客户排名
	private LinearLayout rlItem4;	// 客户经理排名
	private LinearLayout rlItem5;	// 机构排名
	private LinearLayout rlItem6;	// 签到
	private LinearLayout rlItem7;	// 签到记录
	private View leftBg1, leftBg2, leftBg3;
	private View leftBg4, leftBg5, leftBg6, leftBg7;
	private ImageView imgIcon1, imgIcon2, imgIcon3;
	private ImageView imgIcon4, imgIcon5, imgIcon6, imgIcon7;
	private TextView tvContent1, tvContent2, tvContent3;
	private TextView tvContent4, tvContent5, tvContent6, tvContent7;
	
	private FragmentManager 		fragmentManager;
	private WorkWarnFragment  		workwarnFragment;		// 消息提醒
	private FragmentMyreport 		myreportPerson;			// 工作业绩 
	private FragmentOrderLists 		custOrder;				// 客户排名
	private FragmentOrderLists 		pmOrder;				// 客户经理排名
	private FragmentOrderLists 		orgOrder;				// 机构排名
	private SigninFragment    		signinFragment;			// 签到
//	private SigninRecordFragment  	signinRecordFragment;	// 签到记录
	
	private PersonItemProDateFragment 		prodate;				// 产品到期
	private PersonItemcustorderFragment  	itemcustorder;			// 客户违约
	private FragmentBigMoneyChange 			bigmoneychange;			// 大额资金变动
	private FragmentBigUseMoneyLose 		usemoneylose;			// 账户余额不足
	private FragmentCustomerBirthday 		customerbir;			// 客户生日
	private FragmentCusUpDown 				customerupdown;			// 客户升降级
	private FragmentCustomerWorkItem  		customerworkitem;		// 工作事项
	private Fragmentaccountexception  		accoutexception;		// 账户异常
	private Fragmentcustomertakecare  		customertakecare;		// 客户关怀
	private FragmentCustomerAnnulbasic 		customerAnnulbasic;		// 基本户销户
	private FragmentCustomerAnnulgeneral 	customerAnnulGeneral;	// 一般户销户
	private FragmentFinancialDemand financialDemand;		// 金融需求


	private FragmentMyreportTab00 myreportTab00;	// 管理岗-金融资产
	private FragmentMyreportTab01 myreportTab01;	// 客户经理-金融资产
	private FragmentMyreportTab02 myreportTab02;	// 存款趋势图
	private FragmentMyreportTab03 myreportTab03;	// 渠道签约情况
	private FragmentMyreportTab04 myreportTab04;	// 客户价值评比
	private FragmentMyreportTab05 myreportTab05;	// 大年贷款到期回收情况
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nowworkplaceactivity);
		initTitle(this, "工作平台");
		findViews();
		setListener();
		
		fragmentManager = getSupportFragmentManager();
		hiddentLeftTab();
		initLeftItem(leftBg1, imgIcon1, tvContent1);
		setFragmentWorkWarn();
		
		//地图闪屏问题的解决方法
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		//地图服务注册
		SDKInitializer.initialize(getApplicationContext()); //网络提供的是此方法放在setContentView()方法前面
	}

	
	private void findViews() {
		rlItem1 = (LinearLayout) findViewById(R.id.rl_workplaceitem1);
		rlItem2 = (LinearLayout) findViewById(R.id.rl_workplaceitem2);
		rlItem3 = (LinearLayout) findViewById(R.id.rl_workplaceitem3);
		rlItem4 = (LinearLayout) findViewById(R.id.rl_workplaceitem4);
		rlItem5 = (LinearLayout) findViewById(R.id.rl_workplaceitem5);
		rlItem6 = (LinearLayout) findViewById(R.id.rl_workplaceitem6);
		rlItem7 = (LinearLayout) findViewById(R.id.rl_workplaceitem7);
		leftBg1 = (View) findViewById(R.id.leftbgitem1);
		leftBg2 = (View) findViewById(R.id.leftbgitem2);
		leftBg3 = (View) findViewById(R.id.leftbgitem3);
		leftBg4 = (View) findViewById(R.id.leftbgitem4);
		leftBg5 = (View) findViewById(R.id.leftbgitem5);
		leftBg6 = (View) findViewById(R.id.leftbgitem6);
		leftBg7 = (View) findViewById(R.id.leftbgitem7);
		imgIcon1 = (ImageView) findViewById(R.id.img_workplaceitem1);
		imgIcon2 = (ImageView) findViewById(R.id.img_workplaceitem2);
		imgIcon3 = (ImageView) findViewById(R.id.img_workplaceitem3);
		imgIcon4 = (ImageView) findViewById(R.id.img_workplaceitem4);
		imgIcon5 = (ImageView) findViewById(R.id.img_workplaceitem5);
		imgIcon6 = (ImageView) findViewById(R.id.img_workplaceitem6);
		imgIcon7 = (ImageView) findViewById(R.id.img_workplaceitem7);
		tvContent1 = (TextView) findViewById(R.id.tv_workplaceitem1);
		tvContent2 = (TextView) findViewById(R.id.tv_workplaceitem2);
		tvContent3 = (TextView) findViewById(R.id.tv_workplaceitem3);
		tvContent4 = (TextView) findViewById(R.id.tv_workplaceitem4);
		tvContent5 = (TextView) findViewById(R.id.tv_workplaceitem5);
		tvContent6 = (TextView) findViewById(R.id.tv_workplaceitem6);
		tvContent7 = (TextView) findViewById(R.id.tv_workplaceitem7);
	}
	
	private void setListener() {
		rlItem1.setOnClickListener(this);
		rlItem2.setOnClickListener(this);
		rlItem3.setOnClickListener(this);
		rlItem4.setOnClickListener(this);
		rlItem5.setOnClickListener(this);
		rlItem6.setOnClickListener(this);
		rlItem7.setOnClickListener(this);
	}
	
	
	/**
	 * 根据权限隐藏左侧图标
	 */
	private void hiddentLeftTab(){
		rlItem1.setVisibility(View.VISIBLE);
		rlItem2.setVisibility(View.VISIBLE);
		rlItem3.setVisibility(View.VISIBLE);
		rlItem4.setVisibility(View.VISIBLE);
		rlItem5.setVisibility(View.VISIBLE);
		rlItem6.setVisibility(View.VISIBLE);
		rlItem7.setVisibility(View.VISIBLE);
		
		boolean isRight = false;
		// 工作提醒
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0504, false);
		if(!isRight){
			rlItem1.setVisibility(View.GONE);
		}
		// 业务概览
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0505, false);
		if(!isRight){
			rlItem2.setVisibility(View.GONE);
		}
		// 客户排名
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0506, false);
		if(!isRight){
			rlItem5.setVisibility(View.GONE);
		}
		// 客户经理排名
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0507, false);
		if(!isRight){
			rlItem6.setVisibility(View.GONE);
		}
		// 机构排名
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0508, false);
		if(!isRight){
			rlItem7.setVisibility(View.GONE);
		}
		// 签到
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0501, false);
		/*取消权限 展示签到模块*/
//		if(!isRight){
//			rlItem3.setVisibility(View.GONE);
//		}
		// 签到记录
		isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0502, false);
		if(!isRight){
			rlItem4.setVisibility(View.GONE);
		}
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}


	private void initLeftItem(View leftbg,ImageView imgIcon,TextView tvContent){
		leftBg1.setVisibility(View.INVISIBLE);
		leftBg2.setVisibility(View.INVISIBLE);
		leftBg3.setVisibility(View.INVISIBLE);
		leftBg4.setVisibility(View.INVISIBLE);
		leftBg5.setVisibility(View.INVISIBLE);
		leftBg6.setVisibility(View.INVISIBLE);
		leftBg7.setVisibility(View.INVISIBLE);
		imgIcon1.setEnabled(true);
		imgIcon2.setEnabled(true);
		imgIcon3.setEnabled(true);
		imgIcon4.setEnabled(true);
		imgIcon5.setEnabled(true);
		imgIcon6.setEnabled(true);
		imgIcon7.setEnabled(true);
		tvContent1.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		tvContent2.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		tvContent3.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		tvContent4.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		tvContent5.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		tvContent6.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		tvContent7.setTextColor(getResources().getColor(R.color.leftwrodcolor));
		
		leftbg.setVisibility(View.VISIBLE);
		imgIcon.setEnabled(false);
		tvContent.setTextColor(getResources().getColor(R.color.nowworkplacewordbg));
	}
	
	
	/******************************************************************************/
	/*******************************【      工作平台      】*********************************/
	/******************************************************************************/
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 工作提醒
		case R.id.rl_workplaceitem1:
			initLeftItem(leftBg1, imgIcon1, tvContent1);
			setFragmentWorkWarn();
			break;
		// 业务概况(我的业绩)
		case R.id.rl_workplaceitem2:
			initLeftItem(leftBg2, imgIcon2, tvContent2);
			setFragmentMyReport(); 
			break;
		// 客户排名
		case R.id.rl_workplaceitem5:
			initLeftItem(leftBg5, imgIcon5, tvContent5);
			setCustOrder();
			break;
		// 客户经理排名
		case R.id.rl_workplaceitem6:
			initLeftItem(leftBg6, imgIcon6, tvContent6);
			setPMOrder();
			break;
		// 机构排名
		case R.id.rl_workplaceitem7:
			initLeftItem(leftBg7, imgIcon7, tvContent7);
			setOrgOrder();
			break;
		// 签到
		case R.id.rl_workplaceitem3:
			// 签到权限提示
			boolean isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0501, true);
//			if(isRight){
				initLeftItem(leftBg3, imgIcon3, tvContent3);
				setFragmentSignin();
//			}
			break;
		// 签到记录
		case R.id.rl_workplaceitem4:
			// initLeftItem(srLeftBg,imgSrIcon,tvSrContent);
			// setFragmentSigninRecord();
			Intent intent = new Intent(this, SigninRecordActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	// 工作提醒
	private void setFragmentWorkWarn(){
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if(workwarnFragment==null){
			workwarnFragment=new WorkWarnFragment();
			transaction.add(R.id.fl_workplace_fragment, workwarnFragment);
		}else{
			transaction.show(workwarnFragment);
		}
		transaction.commitAllowingStateLoss();
		setFragmentProDate(true);//产品到期类(对私)
	}
	
	// 我的业绩
	private void setFragmentMyReport(){
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if(myreportPerson==null){
			myreportPerson=new FragmentMyreport();
			transaction.add(R.id.fl_workplace_fragment, myreportPerson);
		}else{
			transaction.show(myreportPerson);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 客户排名
	private void setCustOrder() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (custOrder == null) {
			custOrder = new FragmentOrderLists();
			custOrder.setOrderType(FragmentOrderLists.TYPE_CUST);
			transaction.add(R.id.fl_workplace_fragment, custOrder);
		} else {
			custOrder.setOrderType(FragmentOrderLists.TYPE_CUST);
			transaction.show(custOrder);
		}
		transaction.commitAllowingStateLoss();
	}

	// 客户经理排名
	private void setPMOrder() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if(pmOrder==null){
			pmOrder=new FragmentOrderLists();
			pmOrder.setOrderType(FragmentOrderLists.TYPE_PM);
			transaction.add(R.id.fl_workplace_fragment, pmOrder);
		}else{
			pmOrder.setOrderType(FragmentOrderLists.TYPE_PM);
			transaction.show(pmOrder);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 机构排名
	private void setOrgOrder() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if(orgOrder==null){
			orgOrder=new FragmentOrderLists();
			orgOrder.setOrderType(FragmentOrderLists.TYPE_ORG);
			transaction.add(R.id.fl_workplace_fragment, orgOrder);
		}else{
			orgOrder.setOrderType(FragmentOrderLists.TYPE_ORG);
			transaction.show(orgOrder);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 签到
	private void setFragmentSignin(){
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if(signinFragment==null){
			signinFragment=new SigninFragment();
			transaction.add(R.id.fl_workplace_fragment, signinFragment);
		}else{
			transaction.show(signinFragment);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 签到记录
//	private void setFragmentSigninRecord(){
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		hideFragments(transaction);
//		if(signinRecordFragment==null){
//			signinRecordFragment=new SigninRecordFragment();
//			transaction.add(R.id.fl_workplace_fragment, signinRecordFragment);
//		}else{
//			transaction.show(signinRecordFragment);
//		}
//		transaction.commitAllowingStateLoss();
//	}

	//先隐藏其他所有的fragment
	private void hideFragments(FragmentTransaction transaction) {
		if (workwarnFragment != null) {
			transaction.hide(workwarnFragment);
		}
		if (myreportPerson != null) {
			transaction.hide(myreportPerson);
		}
		if (custOrder != null){
			transaction.hide(custOrder);
		}
		if (pmOrder != null) {
			transaction.hide(pmOrder);
		}
		if (orgOrder != null){
			transaction.hide(orgOrder);
		}
		if (signinFragment != null) {
			transaction.hide(signinFragment);
		}
//		if (signinRecordFragment != null) {
//			transaction.hide(signinRecordFragment);
//		}
	}
	
	

	
	/******************************************************************************/
	/********************************【      工作提醒      】********************************/
	/******************************************************************************/
	
	
	@Override
	public void workPlacestyleChange(int tag) {
		
	}
	

	@Override
	public void workPlacestyleChange(int tag, boolean isForperson) {
		switch (tag) {
		case 1:
			setFragmentProDate(isForperson);         //产品到期类
			break;
		case 2:
			setFragmentCusOrder(isForperson);        //违约提醒
			break;
		case 3:
			setFragmentBigMoneyChange(isForperson);  //大额资金变动
			break;
		case 4:
			setFragmentUsedMoneyLose(isForperson);   //账户余额不足
			break;
		case 5:
			setFragmentCusDir();                     //客户生日        -->(仅对私)
			break;
		case 6:
			setFragmentCusUpDown(isForperson);       //客户升降级
			break;
		case 7:
			setFragmentCusWorkItems(isForperson);    //用户工作事项 
			break;
		case 8:
			setFragmentCusException();               //账户异常提醒  -->(仅对公)
			break;
		case 9:
			setFragmentCusTackCare();                //客户关怀提醒   -->(仅对公)
			break;
		case 10:
			setFragmentCustomerAnnulbasic();         //基本户销户       -->(仅对公)
			break;
		case 11:
			setFragmentCustomerAnnulgeneral();       //一般户销户       -->(仅对公)
			break;
		case 12:
			setFragmentFinancialDemand(isForperson);  //金融需求
			break;
		default:
			break;
		}
	}
	
	// 一般客户销户
	private void setFragmentCustomerAnnulgeneral() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(customerAnnulGeneral==null){
			customerAnnulGeneral=new FragmentCustomerAnnulgeneral();
			transaction.add(R.id.fl_workplacewrokwarn, customerAnnulGeneral);
		}else{
			transaction.show(customerAnnulGeneral);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 基本客户销户
	private void setFragmentCustomerAnnulbasic() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(customerAnnulbasic==null){
			customerAnnulbasic=new FragmentCustomerAnnulbasic();
			transaction.add(R.id.fl_workplacewrokwarn, customerAnnulbasic);
		}else{
			transaction.show(customerAnnulbasic);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 客户关怀提醒
	private void setFragmentCusTackCare() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(customertakecare==null){
			customertakecare=new Fragmentcustomertakecare();
			transaction.add(R.id.fl_workplacewrokwarn, customertakecare);
		}else{
			transaction.show(customertakecare);
		}
		transaction.commitAllowingStateLoss();
	}

	// 客户资金异常
	private void setFragmentCusException() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(accoutexception==null){
			accoutexception=new Fragmentaccountexception();
			transaction.add(R.id.fl_workplacewrokwarn, accoutexception);
		}else{
			transaction.show(accoutexception);
		}
		transaction.commitAllowingStateLoss();
	}

	// 工作事项
	private void setFragmentCusWorkItems(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(customerworkitem==null){
			customerworkitem=new FragmentCustomerWorkItem();
			customerworkitem.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacewrokwarn, customerworkitem);
		}else{
			customerworkitem.setIsForperson(isForperson);
			transaction.show(customerworkitem);
		}
		transaction.commitAllowingStateLoss();
	}

	// 客户升降级
	private void setFragmentCusUpDown(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(customerupdown==null){
			customerupdown=new FragmentCusUpDown();
			customerupdown.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacewrokwarn, customerupdown);
		}else{
			customerupdown.setIsForperson(isForperson);
			transaction.show(customerupdown);
		}
		transaction.commitAllowingStateLoss();
	}


	// 金融需求
	private void setFragmentFinancialDemand(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(financialDemand==null){
			financialDemand=new FragmentFinancialDemand();
			transaction.add(R.id.fl_workplacewrokwarn, financialDemand);
		}else{
			transaction.show(financialDemand);
		}
		financialDemand.setIsForperson(isForperson);
		transaction.commitAllowingStateLoss();

	}

	// 客户关怀
	private void setFragmentCusDir() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(customerbir==null){
			customerbir=new FragmentCustomerBirthday();
			transaction.add(R.id.fl_workplacewrokwarn, customerbir);
		}else{
			transaction.show(customerbir);
		}
		transaction.commitAllowingStateLoss();
		
	}

	//账户余额不足
	private void setFragmentUsedMoneyLose(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(usemoneylose==null){
			usemoneylose=new FragmentBigUseMoneyLose();
			usemoneylose.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacewrokwarn, usemoneylose);
		}else{
			usemoneylose.setIsForperson(isForperson);
			transaction.show(usemoneylose);
		}
		transaction.commitAllowingStateLoss();
	}

	//客户大额资金变动
	private void setFragmentBigMoneyChange(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideSonFragments(transaction);
		if(bigmoneychange==null){
			bigmoneychange=new FragmentBigMoneyChange();
			bigmoneychange.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacewrokwarn, bigmoneychange);
		}else{
			bigmoneychange.setIsForperson(isForperson);
			transaction.show(bigmoneychange);
		}
		transaction.commitAllowingStateLoss();
	}

	//客户违约提醒
	private void setFragmentCusOrder(boolean isForperson) {
		FragmentTransaction transaction1 = fragmentManager.beginTransaction();
		hideSonFragments(transaction1);
		if(itemcustorder==null){
			itemcustorder=new PersonItemcustorderFragment();
			itemcustorder.setIsForperson(isForperson);
			transaction1.add(R.id.fl_workplacewrokwarn, itemcustorder);
		}else{
			itemcustorder.setIsForperson(isForperson);
			transaction1.show(itemcustorder);
		}
		transaction1.commitAllowingStateLoss();
	}

	//产品到期类
	private void setFragmentProDate(boolean isForperson) {
		FragmentTransaction transaction1 = fragmentManager.beginTransaction();
		hideSonFragments(transaction1);
		if(prodate==null){
			prodate=new PersonItemProDateFragment();
			prodate.setIsForperson(isForperson);
			transaction1.add(R.id.fl_workplacewrokwarn, prodate);
		}else{
			prodate.setIsForperson(isForperson);
			transaction1.show(prodate);
		}
		transaction1.commitAllowingStateLoss();
	}

	private void hideSonFragments(FragmentTransaction transaction) {
		if(itemcustorder!=null){
			transaction.hide(itemcustorder);
		}
		if(prodate!=null){
			transaction.hide(prodate);
		}
		if(bigmoneychange!=null){
			transaction.hide(bigmoneychange);
		}
		if(usemoneylose!=null){
			transaction.hide(usemoneylose);
		}
		if(customerbir!=null){
			transaction.hide(customerbir);
		}
		if(customerupdown!=null){
			transaction.hide(customerupdown);
		}
		if(customerworkitem!=null){
			transaction.hide(customerworkitem);
		}
		if(accoutexception!=null){
			transaction.hide(accoutexception);
		}
		if(customertakecare!=null){
			transaction.hide(customertakecare);
		}
		if(customerAnnulbasic!=null){
			transaction.hide(customerAnnulbasic);
		}
		if(customerAnnulGeneral!=null){
			transaction.hide(customerAnnulGeneral);
		}
        if(financialDemand!=null){
			transaction.hide(financialDemand);
		}
	}
	
	
	
	/******************************************************************************/
	/********************************【    对个人选项    】********************************/
	/******************************************************************************/
	

	@Override
	public void workMyreportPersonChange(int tag, boolean isForperson) {
		switch (tag) {
		case 0:
			setMyReportTab00(isForperson); 			// 管理岗-全行金融资产
			break;

		case 1:
			setMyReportTab01(isForperson); 			// 客户经理-全行金融资产
			break;
			
		case 2:
			setMyReportTab02(isForperson);			// 存款趋势图
			break;
			
		case 3:
			setMyReportTab03(isForperson);			// 渠道签约情况
			break;
			
		case 4:
			setMyReportTab04(isForperson);			// 客户价值评级
			break;
			
		case 5:											
			setMyReportTab05(isForperson);			// 当年到期贷款收回	
			break;

		default:
			break;
		}
	}

	// 管理岗-全行金融资产
	private void setMyReportTab00(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideMyreportPersonFragments(transaction);
		if(myreportTab00==null){
			myreportTab00=new FragmentMyreportTab00();
			myreportTab00.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacemyreportperson, myreportTab00);
		}else{
			myreportTab00.setIsForperson(isForperson);
			transaction.show(myreportTab00);
		}
		transaction.commitAllowingStateLoss();
	}


	// 客户经理全行金融资产
	private void setMyReportTab01(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideMyreportPersonFragments(transaction);
		if(myreportTab01==null){
			myreportTab01=new FragmentMyreportTab01();
			myreportTab01.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacemyreportperson, myreportTab01);
		}else{
			myreportTab01.setIsForperson(isForperson);
			transaction.show(myreportTab01);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 存款趋势图
	private void setMyReportTab02(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideMyreportPersonFragments(transaction);
		if(myreportTab02==null){
			myreportTab02=new FragmentMyreportTab02();
			myreportTab02.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacemyreportperson, myreportTab02);
		}else{
			myreportTab02.setIsForperson(isForperson);
			transaction.show(myreportTab02);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 渠道签约情况
	private void setMyReportTab03(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideMyreportPersonFragments(transaction);
		if(myreportTab03==null){
			myreportTab03=new FragmentMyreportTab03();
			myreportTab03.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacemyreportperson, myreportTab03);
		}else{
			myreportTab03.setIsForperson(isForperson);
			transaction.show(myreportTab03);
		}
		transaction.commitAllowingStateLoss();
	}
	
	// 客户价值评级
	private void setMyReportTab04(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideMyreportPersonFragments(transaction);
		if(myreportTab04==null){
			myreportTab04=new FragmentMyreportTab04();
			myreportTab04.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacemyreportperson, myreportTab04);
		}else{
			myreportTab04.setIsForperson(isForperson);
			transaction.show(myreportTab04);
		}
		transaction.commitAllowingStateLoss();
	}

	// 当年到期贷款收回	
	private void setMyReportTab05(boolean isForperson) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideMyreportPersonFragments(transaction);
		if(myreportTab05==null){
			myreportTab05=new FragmentMyreportTab05();
			myreportTab05.setIsForperson(isForperson);
			transaction.add(R.id.fl_workplacemyreportperson, myreportTab05);
		}else{
			myreportTab05.setIsForperson(isForperson);
			transaction.show(myreportTab05);
		}
		transaction.commitAllowingStateLoss();
	}

	// 隐藏我的业绩-fragment
	private void hideMyreportPersonFragments(FragmentTransaction transaction){
		if(myreportTab00 != null){
			transaction.hide(myreportTab00);
		}
		if(myreportTab01 != null){
			transaction.hide(myreportTab01);
		}
		if(myreportTab02 != null){
			transaction.hide(myreportTab02);
		}
		if(myreportTab03 != null){
			transaction.hide(myreportTab03);
		}
		if(myreportTab04 != null){
			transaction.hide(myreportTab04);
		}
		if(myreportTab05 != null){
			transaction.hide(myreportTab05);
		}
	}
}
