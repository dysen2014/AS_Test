package com.pactera.financialmanager.ui.customermanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.db.AreaDao;
import com.pactera.financialmanager.ui.CitySelect;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.CustomerAreaInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户建档（第二步）
 *
 * @author JAY
 */
public class CusArchivingStepTwo extends ParentActivity implements
        OnClickListener {

    // 建档类型
    private String[] cusTypeStr = {"个人客户", "对公客户"};
    private TextView cusType;// 客户类型
    private Button nextBtn; // 下一步

    private TextView step2_city;// 城市选择
    private TextView cityTypeSp;// 区域类型
    private TextView cityBelongSp;// 区域所属
    private int ChooseId = 0;// 标记符号（0为对私、1为对公）
    private String custID = "";// 标记符号（0为对私、1为对公）
    private boolean isEdit = false;

    private String TypeID = "", BelongID = "", theLocationID = "";

    private static List<String> cityBelongName = new ArrayList<String>();
    private static List<String> cityBelongID = new ArrayList<String>();
    private HConnection HCon, companyAreaHConn;
    private final int returnIndex = 1;
    private final int returnCityIndex = 2;
    private final int getInfoIndex = 3;// 获取信息

    private final int companyAreaFlag = 97;
    private CustomerAreaInfo theAreaInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusarchiving_step_2);
        // 初始化
        findViews();
        // 绑定监听器
        bindOnClickListener();
        getInfo();
        initTitle(this, "客户建档");
    }

    // 获取地址等信息
    private void getInfo() {
        if (ChooseId == 0) {
            HCon = RequestInfo(this, requestType.Search,
                    InterfaceInfo.CusArchivingStepTwo_Get_forPer, custID,
                    getInfoIndex);
        } else {
            HCon = RequestInfo(this, requestType.Search,
                    InterfaceInfo.CusArchivingStepTwo_Get_forCom, custID,
                    getInfoIndex);
        }

    }

    private void bindOnClickListener() {
        nextBtn.setOnClickListener(this);
        step2_city.setOnClickListener(this);
        cityTypeSp.setOnClickListener(this);
        cityBelongSp.setOnClickListener(this);
    }

    private void findViews() {

        nextBtn = (Button) findViewById(R.id.cusarchiving_step2_nextbtn);
        cusType = (TextView) findViewById(R.id.cusarchiving_step2_custype);

        step2_city = (TextView) findViewById(R.id.cusarchiving_step2_city);

        cityTypeSp = (TextView) findViewById(R.id.cusarchiving_step2_citytype);
        cityBelongSp = (TextView) findViewById(R.id.cusarchiving_step2_citybelong);
        initTitle(this, "工作平台");

        Intent intent = getIntent(); // 获取客户类型
        ChooseId = intent.getIntExtra("ChooseId", 0);
        if (ChooseId == 0) {
            cusType.setText(cusTypeStr[0]);
        } else {
            cusType.setText(cusTypeStr[1]);
        }

        custID = intent.getStringExtra("custID");
        theAreaInfo = new CustomerAreaInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cusarchiving_step2_nextbtn:
                setData();
                break;
            case R.id.cusarchiving_step2_city:

                CityPicker(step2_city,new CitySelect.CallBackCitySelect() {
                    @Override
                    public void CallBackInfo(String location, String theLocation) {
                        step2_city.setText(location);
                        theLocationID = theLocation;
                        isEdit = true;
                        getCitybelong();
                    }
                });
                break;
            case R.id.cusarchiving_step2_citytype:
                if (isEdit) {
                    SpinnerAdapter.showSelectDialog(this, NewCatevalue.areaType,
                            cityTypeSp, new CallBackItemClickListener() {

                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    TypeID = spinnerID;
                                    BelongID = "";
                                    cityBelongSp.setText("");
                                    getCitybelong();
                                }
                            });
                } else {
                    Toast.makeText(this, "请先选择地址！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cusarchiving_step2_citybelong:
                if (cityBelongName.size() > 0 && cityBelongID.size() > 0) {
                    SpinnerAdapter.showSelectDialog(this,
                            Tool.List2String(cityBelongName),
                            Tool.List2String(cityBelongID), cityBelongSp,
                            new CallBackItemClickListener() {

                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    BelongID = spinnerID;
                                }
                            });
                } else {
                    Toast.makeText(this, "数据为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setData() {
        theAreaInfo.custID = custID;
        theAreaInfo.mapping_code = "ADS"+theLocationID;
        theAreaInfo.areatype = TypeID;
        theAreaInfo.area_id = BelongID;

        if (TextUtils.isEmpty(theLocationID) || TextUtils.isEmpty(TypeID)) {
            Toast.makeText(this, "请选择信息！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(step2_city.getText().toString().trim())) {
            Toast.makeText(this, "请选择所属地区！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(cityTypeSp.getText().toString().trim())) {
            Toast.makeText(this, "请选择所属区域类型", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ChooseId == 1) {
            HCon = RequestInfo(this, requestType.JsonData,
                    InterfaceInfo.CusArchivingStepTwo_forCom,
                    theAreaInfo.toString(), returnIndex);
        } else if (ChooseId == 0) {
            HCon = RequestInfo(this, requestType.JsonData,
                    InterfaceInfo.CusArchivingStepTwo_forPer,
                    theAreaInfo.toString(), returnIndex);
        }

    }

    // 查询区域所属
    private void getCitybelong() {

        String info = "&spareOne=" + TypeID + "&spareTwo=" + theLocationID;
        HCon = RequestInfo(this, requestType.Other,
                InterfaceInfo.CusArchivingStepTwo_City_Get, info,
                returnCityIndex);

    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        JSONObject tmpJsonObject;
        HResponse res = null;
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:

                break;
            case returnIndex:
                res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = res.dataJsonObject;
                readJson(tmpJsonObject, connectionIndex);
                break;
            case returnCityIndex:
                HResponse res1 = HCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res1 == null || res1.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = res1.dataJsonObject;
                readCityJson(tmpJsonObject);
                break;
            case companyAreaFlag:
                HResponse companyAreaRes = companyAreaHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyAreaRes == null || companyAreaRes.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = companyAreaRes.dataJsonObject;
                Log.i("1111", "....companyarea....:" + tmpJsonObject.toString());
                readCityJson(tmpJsonObject);
                break;
            case getInfoIndex:
                res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = res.dataJsonObject;
                readJson(tmpJsonObject, connectionIndex);
                break;
        }
    }

    private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
        String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

        if (retCode.equals("0000")) {
            // 上传
            if (connectionIndex == returnIndex) {
                Intent intent = new Intent();
                // cusID传下一层
                intent.putExtra("custID", custID);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                if (ChooseId == 0) {
                    intent.setClass(CusArchivingStepTwo.this,
                            PersonArchiving.class);
                    startActivity(intent);
                } else if (ChooseId == 1) {
                    intent.setClass(CusArchivingStepTwo.this,
                            ActivityBaseInfo.class);
                    startActivity(intent);
                }
                finish();

                // 获取信息
            } else if (connectionIndex == getInfoIndex) {
                JSONArray theInfo;
                try {
                 /*   theInfo = tmpJsonObject.getJSONArray("group");
                    if (theInfo.length() < 0) {
                        return;
                    } else {
                        JSONObject temps = (JSONObject) theInfo.opt(0);

                        theAreaInfo.mapping_code = Tool.getJsonValue(temps,
                                "mapping_code");// 地址层次码
                        if(theAreaInfo.mapping_code != null){
                            theAreaInfo.mapping_code = theAreaInfo.mapping_code.replace("ADS","");
                        }
                        theAreaInfo.area = Tool.getJsonValue(temps, "area");// 所属区域
                        theAreaInfo.areatype = Tool.getJsonValue(temps,
                                "areatype");// 区域类型
*/
                    theAreaInfo.mapping_code = tmpJsonObject.getString("mapping_code");
                    if(theAreaInfo.mapping_code != null){
                        theAreaInfo.mapping_code = theAreaInfo.mapping_code.replace("ADS","");
                    }
                    theAreaInfo.area_id = tmpJsonObject.getString("area_id");// 所属区域
                    theAreaInfo.areatype = tmpJsonObject.getString("areatype");// 区域类型
                    theAreaInfo.area_name = tmpJsonObject.getString("area_name");
                        setView();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{

            if (!(connectionIndex == getInfoIndex && retCode.equals("2001"))) {
                Toast.makeText(this, "数据上传失败，请重新再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setView() {
        theLocationID = theAreaInfo.mapping_code;
        TypeID = theAreaInfo.areatype;
        BelongID = theAreaInfo.area_id;

        // 获取城市名字

        if (!TextUtils.isEmpty(theLocationID)) {
            AreaDao theArea = new AreaDao(this);
            step2_city.setText(theArea.getAreaName(theLocationID));
            theArea.closeDataBase();
            isEdit = true;
        }
        if (!TextUtils.isEmpty(TypeID)) {
            // 设置所属区域
            cityTypeSp.setText(NewCatevalue.getLabel(this,
                    NewCatevalue.areaType, TypeID));
            // 查询区域所属
            getCitybelong();
        }


    }

    private void readCityJson(JSONObject tmpJsonObject) {
        String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

        if (retCode.equals("0000")) {
            cityBelongName.clear();
            cityBelongID.clear();
            try {
                JSONArray theInfo = tmpJsonObject.getJSONArray("group");
                for (int i = 0; i < theInfo.length(); i++) {
                    JSONObject temps = (JSONObject) theInfo.opt(i);
                    if (temps.has("area_name") && temps.has("area_id")) {
                        cityBelongName.add(temps.getString("area_name"));
                        cityBelongID.add(temps.getString("area_id"));
                    }
                }

                // 当获取值不为空时，设置显示
                if (BelongID != "" && cityBelongName.size() > 0&& cityBelongID.size() > 0) {
                    cityBelongSp.setText(NewCatevalue.getValue(BelongID,Tool.List2String(cityBelongID),Tool.List2String(cityBelongName)));
                    Toast.makeText(getApplicationContext(),"所属区域数据返回成功",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
