package com.pactera.financialmanager.ui.customermanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.db.AreaDao;
import com.pactera.financialmanager.entity.CatevalueEntity;
import com.pactera.financialmanager.ui.CitySelectWindow;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.PersonInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pactera.financialmanager.R.id.personInfofragment_companysize;

/**
 * 个人建档下基本信息fragment
 */

public class PersonInfoFragment extends ParentFragment implements
		OnClickListener {
	/*
	 * 基本信息
	 */
	TextView personInfo_custype;// 客户类型
	TextView personInfo_birthday;// 客户生日
	TextView personInfo_OPEN_TIME;//开业日期
	TextView personInfo_NATIVE_PLACE;//籍贯
	TextView personInfo_IDV_NATIONALITY;//民族
	EditText personInfo_phone_no;//联系电话
	EditText personInfo_NATIVEADD;//身份证地址
	TextView personInfo_UNITKIND ;//所属行业
	TextView personInfo_marriage;// 婚姻状况
	TextView personInfo_health;// 健康状况
	TextView personInfo_education;// 教育程度
	EditText personInfo_social;// 社保信息
	TextView personInfo_house;// 住房情况
	TextView personInfo_religion;// 宗教信仰
	ImageView personInfo_employeeflag;// 是否我行员工
	ImageView personInfo_shareholder;// 是否为我行股东

	GridView personInfo_hobby_gv;// 兴趣爱好
	List<HobbyInfo> HobbyInfoList = new ArrayList<HobbyInfo>();
	HobbyAdapter theHobbyAdapter;

	/*
	 * 工作信息
	 */
	EditText personInfo_employer;// 工作单位
	TextView personInfo_industry;// 职业
	TextView personInfo_job;// 职务
	EditText personInfo_jobyear;// 工作年限
	EditText personInfo_income;// 年收入
	TextView personInfo_bank;// 工资代发行
	ImageView personInfo_workers;// 是否务工
	ImageView personInfo_isleaders;// 是否为务工带头人
	EditText personInfo_leadername;// 带头人姓名
	EditText personInfo_leaderphone;// 带头人电话
	EditText personInfo_workplace;// 工作地点

	/*
	 * 经营情况
	 */
	EditText personInfo_businessname;// 商户名称
	EditText personInfo_companysize;// 经营规模
	EditText personInfo_projects;// 经营项目
	ImageView personInfo_isagriculture;// 是否涉农
	ImageView personInfo_isrestrictive;// 是否限制性行业
	ImageView personInfo_ispatent;// 是否有专利
	EditText personInfo_patentinfo;// 专利描述改为开业日期
	EditText personInfo_businessadress;// 经营地址
	EditText personInfo_investment;// 投资资金
	EditText personInfo_annualincome;// 年经营收入

	Button personInfo_nextbtn;// 下一步
	PersonInfo thePersonInfo;// 客户基本信息
	// 标记位
	int isEmployeeflag = isTrue;// 员工标示
	int isLeaders = isTrue;// 是否为务工带头人
	int isShareholder = isTrue;// 是否为我行股东
	int isWorkers = isTrue;// 是否务工
	int isAgriculture = isTrue;// 是否涉农
	int isRestrictive = isTrue;// 是否限制性行业
	int isPatent = isTrue;// 是否有专利

	private HConnection HCon;
	private final int creatReturnIndex = 1;
	private final int getReturnIndex = 0;

	/**
	 * 码值
	 */
	private String custypeID;// 客户类型
	private String marriageID;// 婚姻状况
	private String educationID;// 教育程度
	private String houseID;// 住房情况
	private String postID;// 社会职务
	private String healthID;// 健康状况
	private String bankID;// 工资发放行
	private String religionID;// 宗教信仰
	private String industryID;// 职业
	private String jobID;// 职务
	private String nativeID;//籍贯
	private String nationalityID;//民族
	private String unitkindID;//所属行业
	private String scaleId;//经营规模

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_archiving_presoninfo,
				null);
		setView(view);
		setListener();
		getData();
		return view;
	}

	private void setListener() {
		// TODO Auto-generated method stub
		personInfo_companysize.setOnClickListener(this);
		personInfo_OPEN_TIME.setOnClickListener(this);
		personInfo_birthday.setOnClickListener(this);
		personInfo_NATIVE_PLACE.setOnClickListener(this);
		personInfo_IDV_NATIONALITY.setOnClickListener(this);
		personInfo_UNITKIND.setOnClickListener(this);
		personInfo_employeeflag.setOnClickListener(this);
		personInfo_shareholder.setOnClickListener(this);
		personInfo_workers.setOnClickListener(this);
		personInfo_isleaders.setOnClickListener(this);
		personInfo_isagriculture.setOnClickListener(this);
		personInfo_isrestrictive.setOnClickListener(this);
		personInfo_ispatent.setOnClickListener(this);
		personInfo_nextbtn.setOnClickListener(this);
		personInfo_custype.setOnClickListener(this);
		personInfo_marriage.setOnClickListener(this);
		personInfo_health.setOnClickListener(this);
		personInfo_education.setOnClickListener(this);
		personInfo_house.setOnClickListener(this);
		personInfo_bank.setOnClickListener(this);
		personInfo_religion.setOnClickListener(this);
		personInfo_industry.setOnClickListener(this);
		personInfo_job.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.personInfofragment_custype:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.custClass, personInfo_custype,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							custypeID = spinnerID;
						}
					});
			break;
		case R.id.personInfofragment_marriage:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.Marriage, personInfo_marriage,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							marriageID = spinnerID;
						}
					});
			break;
		case R.id.personInforFragment_UNITKIND://所属行业
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.industy_sec, personInfo_UNITKIND,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								unitkindID = spinnerID;
							}
						});
				break;
			case R.id.personInfofragment_IDV_NATIONALITY://民族
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.nation_code, personInfo_IDV_NATIONALITY,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								nationalityID = spinnerID;
							}
						});
				break;
			case R.id.personInfofragment_NATIVE_PLACE://籍贯
				CityPickerShow(personInfo_NATIVE_PLACE, 2,new CitySelectWindow.CallBackCity() {
					@Override
					public void CallBackInfo(String location, String theLocation) {
						personInfo_NATIVE_PLACE.setText(location);
						nativeID = "ADS"+theLocation;
					}
				});
				/*SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.native_place, personInfo_NATIVE_PLACE,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {

								nativeID = spinnerID;
							}
						});*/
				break;
		case R.id.personInfofragment_health:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.healthStatus, personInfo_health,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							healthID = spinnerID;
						}
					});
			break;
		case R.id.personInfofragment_companysize:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.enterScale, personInfo_companysize, new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							scaleId = spinnerID ;
						}
					});
		case R.id.personInfofragment_education:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.EDUEXPERIENCE, personInfo_education,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							educationID = spinnerID;
						}
					});
			break;
		// 职业
		case R.id.personInfofragment_industry:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.profession, personInfo_industry,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							industryID = spinnerID;
						}
					});
			break;
		case R.id.personInfofragment_job:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.DUTY, personInfo_job,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							jobID = spinnerID;
						}
					});
			break;
		case R.id.personInfofragment_house:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.houseCondition, personInfo_house,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							houseID = spinnerID;
						}
					});
			break;

		case R.id.personInfofragment_bank:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.signBank, personInfo_bank,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							bankID = spinnerID;
						}
					});

			break;
		// 宗教信仰
		case R.id.personInfofragment_religion:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.reglious_fate, personInfo_religion,
					new CallBackItemClickListener() {

						@Override
						public void CallBackItemClick(String spinnerID) {
							// TODO Auto-generated method stub
							religionID = spinnerID;
						}
					});
			break;

		case R.id.personInfofragment_birthday://生日日期
			DatePickerShow(new OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					// TODO Auto-generated method stub
					String dayate = year + "-" + month + "-" + day;
					personInfo_birthday.setText(dayate);
				}
			},personInfo_birthday.getText().toString());
			break;
			case R.id.personInfofragment_OPEN_TIME://开业日期
				DatePickerShow(new OnDateSetListener() {
					@Override
					public void onDateSet(String year, String month, String day) {

						String dayate = year + "-" + month + "-" + day;
						personInfo_OPEN_TIME.setText(dayate);
					}
				},personInfo_OPEN_TIME.getText().toString());

				break;
		case R.id.personInfofragment_employeeflag:
			isEmployeeflag = isEmployeeflag == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_employeeflag, isEmployeeflag);
			break;
		case R.id.personInfofragment_isshareholder:
			isShareholder = isShareholder == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_shareholder, isShareholder);
			break;
		case R.id.personInfofragment_workers:
			isWorkers = isWorkers == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_workers, isWorkers);
			break;
		case R.id.personInfofragment_isleaders:
			isLeaders = isLeaders == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_isleaders, isLeaders);
			break;
		case R.id.personInfofragment_isagriculture:
			isAgriculture = isAgriculture == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_isagriculture, isAgriculture);
			break;
		case R.id.personInfofragment_isrestrictive:
			isRestrictive = isRestrictive == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_isrestrictive, isRestrictive);
			break;
		case R.id.personInfofragment_ispatent:
			isPatent = isPatent == isTrue ? isFalse : isTrue;
			Tool.setSelect(personInfo_ispatent, isPatent);
			break;
		case R.id.personInfofragment_nextbtn:
			sendData();
			break;
		default:
			break;
		}
	}

	/**
	 * 创建信息
	 */
	private void sendData() {

		thePersonInfo = new PersonInfo();
		// 直接获取值的
		thePersonInfo.IDV_BRTH_DT_GL = personInfo_birthday.getText().toString();// 出生日期(公历)
		thePersonInfo.year_earning = Tool.getTextValue(personInfo_annualincome);// 年经营收入
		thePersonInfo.invest_money = Tool.getTextValue(personInfo_investment);// 投入资金

		thePersonInfo.WORKADD = Tool.getTextValue(personInfo_workplace);// 工作地址
        thePersonInfo.NATIVEADD = Tool.getTextValue(personInfo_NATIVEADD);//身份证地址
		thePersonInfo.phone_no = Tool.getTextValue(personInfo_phone_no);//联系电话
		thePersonInfo.OPEN_TIME = personInfo_OPEN_TIME.getText().toString();// 经济来源改为开业日期

		thePersonInfo.busi_project = Tool.getTextValue(personInfo_projects);// 经营项目
		thePersonInfo.MERCHANT_NAME = Tool
				.getTextValue(personInfo_businessname);// 商户名称
		thePersonInfo.WORK_LEADER_NAME = Tool
				.getTextValue(personInfo_leadername);// 务工带头人姓名
		thePersonInfo.WORK_LEADER_PHONE = Tool
				.getTextValue(personInfo_leaderphone);// 务工带头人电话
		thePersonInfo.busi_addr = Tool.getTextValue(personInfo_businessadress);// 经营地址
		thePersonInfo.FAMILY_ANUAL_INCOME = Tool
				.getTextValue(personInfo_income);// 年收入
		thePersonInfo.SERVICE_YEAR = Tool.getTextValue(personInfo_jobyear);// 工作年限
		thePersonInfo.WORKCORP = Tool.getTextValue(personInfo_employer);// 单位名称

	
		thePersonInfo.SECURITY_INFO = Tool.getTextValue(personInfo_social);// 社保信息

		// 下拉框，要获取码值的
		if (!TextUtils.isEmpty(personInfo_marriage.getText().toString())) {
			thePersonInfo.IDV_MAR_ST_TP_ID = marriageID;// 婚姻状况
		}
		if (!TextUtils.isEmpty(personInfo_house.getText().toString())) {
			thePersonInfo.house_info = houseID;// 住房状况
		}
		if(!TextUtils.isEmpty(personInfo_NATIVE_PLACE.getText().toString())){
			thePersonInfo.NATIVE_PLACE = nativeID;   //籍贯
		}
		if(!TextUtils.isEmpty(personInfo_IDV_NATIONALITY.getText().toString())){
			thePersonInfo.IDV_NATIONALITY = nationalityID;//民族
		}
		if (!TextUtils.isEmpty(personInfo_UNITKIND.getText().toString())){
			thePersonInfo.UNITKIND = unitkindID;//所属行业
		}
		if(!TextUtils.isEmpty(personInfo_companysize.getText().toString())){
			thePersonInfo.BUSI_SCALE = scaleId;      //经营规模
		}
		if (!TextUtils.isEmpty(personInfo_custype.getText().toString())) {
			thePersonInfo.CUST_TYPE = custypeID;// 客户类型
		}
		if (!TextUtils.isEmpty(personInfo_health.getText().toString())) {
			thePersonInfo.IDV_HLT_ST_TP_ID = healthID;// 健康状况
		}
		if (!TextUtils.isEmpty(personInfo_education.getText().toString())) {
			thePersonInfo.IDV_EDU_TP_ID = educationID;// 教育程度
		}
		if (!TextUtils.isEmpty(personInfo_bank.getText().toString())) {
			thePersonInfo.SALARY_BANK = bankID;// 工资代发行
		}
		if (!TextUtils.isEmpty(personInfo_religion.getText().toString())) {
			thePersonInfo.RELIGION = religionID;// 宗教信仰
		}
		if (!TextUtils.isEmpty(personInfo_industry.getText().toString())) {
			thePersonInfo.PROFESSION = industryID;// 职业
		}
		if (!TextUtils.isEmpty(personInfo_job.getText().toString())) {
			thePersonInfo.HEADSHIP = jobID;// 职务
		}
		
		
		// 是否的
		thePersonInfo.has_patent = String.valueOf(isPatent);// 是否有专利
		thePersonInfo.is_rel_farm = String.valueOf(isAgriculture);// 是否涉农
		thePersonInfo.IS_WORK = String.valueOf(isWorkers);// 是否务工
		thePersonInfo.IS_WORK_LEADER = String.valueOf(isLeaders);// 是否务工带头人
		thePersonInfo.is_rel_restrict = String.valueOf(isRestrictive);// 是否限制性行业
		thePersonInfo.STOCKHOLDER = String.valueOf(isShareholder);// 是否为我行股东
		thePersonInfo.CUST_TAG = String.valueOf(isEmployeeflag);// 员工标示

		String hobbyInfo = "";
		HobbyInfoList = theHobbyAdapter.getInfolist();
		for (HobbyInfo item : HobbyInfoList) {
			if (item.isCheck) {
				if (hobbyInfo == "") {
					hobbyInfo = hobbyInfo + item.hobbyValue;
				} else {
					hobbyInfo = hobbyInfo + "," + item.hobbyValue;
				}
			}
		}
		thePersonInfo.HOBBY = hobbyInfo;// 兴趣爱好

		// 从上层传进来的参数
		thePersonInfo.custID = PersonArchiving.custID;// 客户ID
		// thePersonInfo.idv_chn_nm = BaseCustomerInfo.custName;// 客户名称

		HCon = RequestInfo(
				PersonInfoFragment.this,
				Constants.requestType.Insert,
				InterfaceInfo.PersonInfo_Creat,
				thePersonInfo.toString() + "&spareOne="
						+ PersonArchiving.toJsonForPerson(), creatReturnIndex);

	}

	/**
	 * 获取信息
	 */
	private void getData() {
		// TODO Auto-generated method stub
		HCon = RequestInfo(PersonInfoFragment.this,
				Constants.requestType.Search, InterfaceInfo.PersonInfo_Get,
				PersonArchiving.custID, getReturnIndex);
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		JSONObject tmpJsonObject;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case creatReturnIndex:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readCreatJson(tmpJsonObject);
			break;
		case getReturnIndex:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readGetDataJson(tmpJsonObject);
			break;
		}

	}

	// 获取数据处理的Json
	private void readGetDataJson(JSONObject tmpJsonObject) {
		// TODO Auto-generated method stub
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			try {
				JSONArray theInfo = tmpJsonObject.getJSONArray("group");
				if (theInfo.length() < 0) {
					return;
				} else {
					JSONObject temps = (JSONObject) theInfo.opt(0);

					Log.i("1111111", "temps:" + temps);
					// 客户类型
					custypeID = Tool.getJsonValue(temps, "CUST_TYPE");
					String theCust = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.custClass, custypeID);

					personInfo_custype.setText(theCust);

					// 客户生日
					personInfo_birthday.setText(Tool.getJsonValue(temps,
							"IDV_BRTH_DT_GL"));
					//开业日期
					personInfo_OPEN_TIME.setText(Tool.getJsonValue(temps,
							"OPEN_TIME"));
					// 婚姻状况
					marriageID = Tool.getJsonValue(temps, "IDV_MAR_ST_TP_ID");
					String theMarriage = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.Marriage, marriageID);
					personInfo_marriage.setText(theMarriage);
					//籍贯
					nativeID = Tool.getJsonValue(temps,"NATIVE_PLACE");
					/*String theNatavive = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.native_place,nativeID);
					personInfo_NATIVE_PLACE.setText(theNatavive);*/
					nativeID = nativeID.replace("ADS","");           //从服务器来的数据进行替换。
					AreaDao theArea = new AreaDao(this.getActivity());
					personInfo_NATIVE_PLACE.setText(theArea.getAreaName(nativeID ));
					theArea.closeDataBase();
					//民族
					nationalityID = Tool.getJsonValue(temps,"IDV_NATIONALITY");
					String theNATONNALITY =  NewCatevalue.getLabel(getActivity(),
							NewCatevalue.nation_code,nationalityID);
					personInfo_IDV_NATIONALITY.setText(theNATONNALITY);
					//所属行业
					unitkindID = Tool.getJsonValue(temps,"UNITKIND");
					String theUNITKIND = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.industy_sec,unitkindID);
					personInfo_UNITKIND.setText(theUNITKIND);
					// 经营规模
					scaleId = Tool.getJsonValue(temps,"BUSI_SCALE");
					String theScale = NewCatevalue.getLabel(getActivity(),NewCatevalue.enterScale, scaleId);
					personInfo_companysize.setText(theScale);
					// 健康状况
					healthID = Tool.getJsonValue(temps, "IDV_HLT_ST_TP_ID");
					String theHealth = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.healthStatus, healthID);
					personInfo_health.setText(theHealth);

					// 教育程度
					educationID = Tool.getJsonValue(temps, "IDV_EDU_TP_ID");
					String theEducation = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.EDUEXPERIENCE, educationID);

					personInfo_education.setText(theEducation);

					// 社保信息
					personInfo_social.setText(Tool.getJsonValue(temps,
							"SECURITY_INFO"));

					// 住房情况
					houseID = Tool.getJsonValue(temps, "house_info");
					String theHouse = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.houseCondition, houseID);

					personInfo_house.setText(theHouse);


					// 宗教信仰
					religionID = Tool.getJsonValue(temps, "RELIGION");

					String theReligion = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.reglious_fate, religionID);

					personInfo_religion.setText(theReligion);

					// 兴趣爱好
					String hobbyInfo = Tool.getJsonValue(temps, "HOBBY");
					// Log.i("1111111", "hobbyInfo:"+hobbyInfo);
					if(HobbyInfoList == null){
						setHobbyData();
					}

					String[] tempHobby = hobbyInfo.split(",");
					String hobbvyInfo="";
					for (int i = 0; i < tempHobby.length; i++) {
						for (HobbyInfo item : HobbyInfoList) {
							// Log.i("1111111",
							// "item.hobbyValue:"+item.hobbyValue+",tempHobby[i]="+tempHobby[i]);

							if (item.hobbyValue.equals(tempHobby[i])) {
								item.isCheck = true;
								if (hobbvyInfo == "") {
									hobbyInfo = hobbyInfo + item.hobbyValue;
								} else {
									hobbyInfo = hobbyInfo + "," + item.hobbyValue;
								}
							}
						}
					}

				//	thePersonInfo.HOBBY = hobbyInfo;// 兴趣爱好 改赋值没有任何意义


					theHobbyAdapter.setInfolist(HobbyInfoList);

					theHobbyAdapter.notifyDataSetChanged();
					// 员工标示
					isEmployeeflag = Tool.IntegerParse(Tool.getJsonValue(temps,
							"CUST_TAG"));
					Tool.setSelect(personInfo_employeeflag, isEmployeeflag);

					// 是否为我行股东
					isShareholder = Tool.IntegerParse(Tool.getJsonValue(temps,
							"STOCKHOLDER"));
					Tool.setSelect(personInfo_shareholder, isShareholder);

					// 工作单位
					personInfo_employer.setText(Tool.getJsonValue(temps,
							"WORKCORP"));

					// 职业
					industryID = Tool.getJsonValue(temps, "PROFESSION");
					String theIndustry = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.profession, industryID);

					personInfo_industry.setText(theIndustry);

					// 职务
					jobID = Tool.getJsonValue(temps, "HEADSHIP");
					String theJob = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.DUTY, jobID);
					personInfo_job.setText(theJob);

					// 工作年限
					personInfo_jobyear.setText(Tool.getJsonValue(temps,
							"SERVICE_YEAR"));

					// 年收入
					personInfo_income.setText(Tool.getJsonValue(temps,
							"FAMILY_ANUAL_INCOME"));

					// 工资代发行
					bankID = Tool.getJsonValue(temps, "SALARY_BANK");
					String theBank = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.signBank, bankID);

					personInfo_bank.setText(theBank);

					// 是否务工
					isWorkers = Tool.IntegerParse(Tool.getJsonValue(temps,
							"IS_WORK"));
					Tool.setSelect(personInfo_workers, isWorkers);

					// 是否为务工带头人
					isLeaders = Tool.IntegerParse(Tool.getJsonValue(temps,
							"IS_WORK_LEADER"));
					Tool.setSelect(personInfo_isleaders, isLeaders);

					// 带头人姓名
					personInfo_leadername.setText(Tool.getJsonValue(temps,
							"WORK_LEADER_NAME"));

					// 带头人电话
					personInfo_leaderphone.setText(Tool.getJsonValue(temps,
							"WORK_LEADER_PHONE"));

					// 工作地点
					personInfo_workplace.setText(Tool.getJsonValue(temps,
							"WORKADD"));

					// 商户名称
					personInfo_businessname.setText(Tool.getJsonValue(temps,
							"MERCHANT_NAME"));


					// 经营项目
					personInfo_projects.setText(Tool.getJsonValue(temps,
							"busi_project"));

					// 是否涉农
					isAgriculture = Tool.IntegerParse(Tool.getJsonValue(temps,
							"is_rel_farm"));
					Tool.setSelect(personInfo_isagriculture,isAgriculture);

					// 是否限制性行业
					isRestrictive = Tool.IntegerParse(Tool.getJsonValue(temps,
							"is_rel_restrict"));
					Tool.setSelect(personInfo_isrestrictive, isRestrictive);

					// 是否有专利
					isPatent = Tool.IntegerParse(Tool.getJsonValue(temps,
							"has_patent"));
					Tool.setSelect(personInfo_ispatent, isPatent);

					//身份证地址
					personInfo_NATIVEADD.setText(Tool.getJsonValue(temps,
							"NATIVEADD"));
					personInfo_phone_no.setText(Tool.getJsonValue(temps,"phone_no"));
					// 经营地址
					personInfo_businessadress.setText(Tool.getJsonValue(temps,
							"busi_addr"));

					// 投资资金
					personInfo_investment.setText(Tool.getJsonValue(temps,
							"invest_money"));

					// 年经营收入
					personInfo_annualincome.setText(Tool.getJsonValue(temps,
							"year_earning"));

				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("1111111", e.toString());
				e.printStackTrace();
			}
		}
	}

	// 创建后处理的Json
	private void readCreatJson(JSONObject tmpJsonObject) {
		// TODO Auto-generated method stub
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			Message msg = ((ParentActivity) getActivity()).handler
					.obtainMessage();
			Bundle data = new Bundle();
			data.putString("value", "" + PersonArchiving.PersonInfoIndex);
			msg.setData(data);
			((ParentActivity) getActivity()).handler.sendMessage(msg);
		} else {
			Toast.makeText(getActivity(), "上传失败！", Toast.LENGTH_SHORT).show();
		}
	}

	private void setView(View view) {
		personInfo_custype = (TextView) view
				.findViewById(R.id.personInfofragment_custype);
		personInfo_birthday = (TextView) view
				.findViewById(R.id.personInfofragment_birthday);
		personInfo_OPEN_TIME = (TextView) view
				.findViewById(R.id.personInfofragment_OPEN_TIME);
		personInfo_NATIVE_PLACE = (TextView) view
				.findViewById(R.id.personInfofragment_NATIVE_PLACE);
		personInfo_IDV_NATIONALITY = (TextView) view
				.findViewById(R.id.personInfofragment_IDV_NATIONALITY);
		personInfo_NATIVEADD = (EditText) view
				.findViewById(R.id.personInfofragment_NATIVEADD);
		personInfo_phone_no = (EditText) view.findViewById(R.id.et_phone_no);//联系电话
		personInfo_UNITKIND = (TextView) view
				.findViewById(R.id.personInforFragment_UNITKIND);
		personInfo_marriage = (TextView) view
				.findViewById(R.id.personInfofragment_marriage);
		personInfo_health = (TextView) view
				.findViewById(R.id.personInfofragment_health);
		personInfo_education = (TextView) view
				.findViewById(R.id.personInfofragment_education);
		personInfo_social = (EditText) view
				.findViewById(R.id.personInfofragment_social);
		personInfo_house = (TextView) view
				.findViewById(R.id.personInfofragment_house);
		personInfo_religion = (TextView) view
				.findViewById(R.id.personInfofragment_religion);
		personInfo_employeeflag = (ImageView) view
				.findViewById(R.id.personInfofragment_employeeflag);
		personInfo_shareholder = (ImageView) view
				.findViewById(R.id.personInfofragment_isshareholder);
		personInfo_employer = (EditText) view
				.findViewById(R.id.personInfofragment_employer);
		personInfo_industry = (TextView) view
				.findViewById(R.id.personInfofragment_industry);
		personInfo_job = (TextView) view
				.findViewById(R.id.personInfofragment_job);
		personInfo_jobyear = (EditText) view
				.findViewById(R.id.personInfofragment_jobyear);
		personInfo_income = (EditText) view
				.findViewById(R.id.personInfofragment_income);
		personInfo_bank = (TextView) view
				.findViewById(R.id.personInfofragment_bank);
		personInfo_workers = (ImageView) view
				.findViewById(R.id.personInfofragment_workers);
		personInfo_isleaders = (ImageView) view
				.findViewById(R.id.personInfofragment_isleaders);
		personInfo_leadername = (EditText) view
				.findViewById(R.id.personInfofragment_leadername);
		personInfo_leaderphone = (EditText) view
				.findViewById(R.id.personInfofragment_leaderphone);
		personInfo_workplace = (EditText) view
				.findViewById(R.id.personInfofragment_workplace);
		personInfo_businessname = (EditText) view
				.findViewById(R.id.personInfofragment_businessname);
		personInfo_companysize = (EditText) view
				.findViewById(R.id.personInfofragment_companysize);
		personInfo_projects = (EditText) view
				.findViewById(R.id.personInfofragment_projects);
		personInfo_isagriculture = (ImageView) view
				.findViewById(R.id.personInfofragment_isagriculture);
		personInfo_isrestrictive = (ImageView) view
				.findViewById(R.id.personInfofragment_isrestrictive);
		personInfo_ispatent = (ImageView) view
				.findViewById(R.id.personInfofragment_ispatent);
		personInfo_businessadress = (EditText) view
				.findViewById(R.id.personInfofragment_businessadress);
		personInfo_investment = (EditText) view
				.findViewById(R.id.personInfofragment_investment);
		personInfo_annualincome = (EditText) view
				.findViewById(R.id.personInfofragment_annualincome);
		personInfo_nextbtn = (Button) view
				.findViewById(R.id.personInfofragment_nextbtn);

		/**
		 * 动态加载兴趣爱好
		 */
		personInfo_hobby_gv = (GridView) view
				.findViewById(R.id.personInfofragment_hobby_gv);
		setHobbyData();
		theHobbyAdapter = new HobbyAdapter(getActivity(),HobbyInfoList);
		theHobbyAdapter.index = 0;
		personInfo_hobby_gv.setAdapter(theHobbyAdapter);


	}

	private void setHobbyData() {
		List<CatevalueEntity> catevalueInfo = new ArrayList<CatevalueEntity>();
		catevalueInfo = NewCatevalue.getCatevalue(getActivity(),
				NewCatevalue.sportType);
		HobbyInfoList.clear();
		for (CatevalueEntity catevalueEntity : catevalueInfo) {
			HobbyInfo tempInfo = new HobbyInfo();
			tempInfo.isCheck = false;
			tempInfo.hobbyValue = catevalueEntity.getValue();
			tempInfo.hobbyLabel = catevalueEntity.getLabel();
			HobbyInfoList.add(tempInfo);
		}
	}

	/**
	 * 兴趣爱好Adapter
	 * 
	 * @author JAY
	 * 
	 */
	class HobbyAdapter extends BaseAdapter {
		private Context context;
		private List<HobbyInfo> data;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		private int index = 0;

		public HobbyAdapter(Context context,List<HobbyInfo> hobbyData) {
			super();
			data = hobbyData;
			this.context = context;
		}

		@Override
		public int getCount() {
			int result = 0 ;
			if (data != null && data.size() > 0) {
				result = data.size();
			}
			return result;

		}
		@Override
		public HobbyInfo getItem(int position) {
			return data.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final  int position, View convertView,
				ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = View.inflate(context, R.layout.item_hobby, null);

			CheckBox theCheck = (CheckBox) vi
					.findViewById(R.id.item_hobby_checkbox);

			System.out.println("position:"+position);
			System.out.println("index:"+position);
			HobbyInfo theInfo = new HobbyInfo();
		//	int index = 	personInfo_hobby_gv.getChildCount();
			theInfo = data.get(position);
			theCheck.setText(theInfo.hobbyLabel);
			theCheck.setChecked(theInfo.isCheck);
         //    index ++;
			theCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					data.get(position).isCheck = isChecked;
				}
			});
			return vi;
		}

		public void setInfolist(List<HobbyInfo> infolist) {
			this.data = infolist;
		}
		public void setHobbyInfo(int position, HobbyInfo hobbyInfo) {
			this.data.set(position, hobbyInfo);
		}

		public List<HobbyInfo> getInfolist() {
			return data;
		}
	}

	/**
	 * 兴趣爱好
	 * 
	 * @author JAY
	 * 
	 */
	class HobbyInfo {
		public boolean isCheck = false;// 是否选中
		public String hobbyValue;// 码值
		public String hobbyLabel;// 码值对应信息
	}

}
