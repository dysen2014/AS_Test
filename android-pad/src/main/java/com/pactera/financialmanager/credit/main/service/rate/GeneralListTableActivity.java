package com.pactera.financialmanager.credit.main.service.rate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.rate.BussType;
import com.pactera.financialmanager.ui.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 11/3/2017.
 */

public class GeneralListTableActivity extends ParentActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
//    @Bind(R.id.txt_)
//    TextView txt;
    @Bind(R.id.buss_item_00)
    RadioButton bussItem00;
    @Bind(R.id.buss_item_01)
    RadioButton bussItem01;
    @Bind(R.id.buss_item_02)
    RadioButton bussItem02;
    @Bind(R.id.buss_item_03)
    RadioButton bussItem03;
    @Bind(R.id.buss_item_04)
    RadioButton bussItem04;
    @Bind(R.id.buss_item_05)
    RadioButton bussItem05;
    @Bind(R.id.buss_item_06)
    RadioButton bussItem06;
    @Bind(R.id.group_radio_buss)
    RadioGroup groupRadioBuss;
    @Bind(R.id.general_list_table)
    ListView generalListTable;

    private String SortNo = "";
    private Intent in;

    private List<BussType> headingsbussTypeList = new ArrayList<>();
    private List<BussType> bussTypeList = new ArrayList<>();

    //当前选中列表的下标
    private int indexNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_list_table);
        ButterKnife.bind(this);
        initTitle(this, "", false,"");

        txtTitle.setText("业务品种");
        initTitle(this, "", false,"");

        in = new Intent();

        generalListTable.setOnItemClickListener(this);

        radioInitData();
        if (layBack != null) {
            layBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
    }


    private void radioInitData() {
//        JSONObject jsonObject = ParamUtils.setParams("search", "businessType",
//                new Object[]{ParamUtils.UserId, SortNo, "Y"}, 3);
        //UserId:登陆用户,SortNo:业务品种类
        JSONObject jsonObject = ParamUtils.setParams("crmBusinessType", "crmBusinessType",
                new Object[]{ParamUtils.UserId, SortNo}, 2);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, conditionsHandler);
    }

//    private void radioVarietiesData() {
//        JSONObject jsonObject = ParamUtils.setParams("crmBusinessType", "crmBusinessType",
//                new Object[]{ParamUtils.UserId, SortNo}, 2);
//        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, conditionsHandler);
//    }
    /**
     * 解析业务数据
     */
    private Handler conditionsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {
                LogUtils.d("msg.obj=====" + msg.obj);

                String radioGson = null;
                try {
                    radioGson = HttpThread.parseJSONWithGson(msg.obj.toString());
                    headingsbussTypeList = HttpThread.parseList(radioGson, BussType.class);
                    findRadioButtonViewIds();
                    if (headingsbussTypeList.size() > 0){
                        businesNextInitData(headingsbussTypeList.get(0).getSortNo());
                    }else {
                        in.putExtra("typeName", bussTypeList.get(indexNumber).getTypeName());
                        in.putExtra("typeNo", bussTypeList.get(indexNumber).getTypeNo());
//                        in.putExtra("typeProductML", bussTypeList.get(indexNumber).get());
                        setResult(RESULT_OK, in);
                        //没有数据的时候
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (bussTypeList != null) {
                        in.putExtra("typeName", bussTypeList.get(indexNumber).getTypeName());
                        in.putExtra("typeNo", bussTypeList.get(indexNumber).getTypeNo());
//                        in.putExtra("typeProductML", bussTypeList.get(indexNumber).get());
                        setResult(RESULT_OK, in);
                        //没有数据的时候
                        finish();
                    }
                }
            }
        }
    };

    /**
     * 初始化标题的RadioButton的item引用
     */
    private void findRadioButtonViewIds() {

        for (int i = 0; i <= headingsbussTypeList.size(); i++) {
            try {
                Field field = R.id.class.getField("buss_item_0" + i);
                int key = field.getInt(new R.id());
                RadioButton radioButton = (RadioButton) findViewById(key);
                radioButton.setTag(String.valueOf(10000 + i));
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int num = Integer.parseInt(String.valueOf(view.getTag())) - 10000;
                        if (num <= headingsbussTypeList.size()) {
                            SortNo = headingsbussTypeList.get(num).getSortNo();
//                            LogUtils.d("headingsbussTypeList.get(num).getSortNo()----" + headingsbussTypeList.get(num).getTypeNo());
                            businesNextInitData(SortNo);
                        }
                    }
                });
                radioButton.setText(headingsbussTypeList.get(i).getTypeName());
                radioButton.setVisibility(View.VISIBLE);
                //隐藏多余的RadioButton
                for (int j = 0; j < 7; j++) {
                    if (j >= headingsbussTypeList.size()) {
                        Field field1 = R.id.class.getField("tv_table_title_" + j);
                        int key1 = field1.getInt(new R.id());
                        RadioButton radioButton1 = (RadioButton) findViewById(key1);
                        radioButton1.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 业务下一级菜单
     */
    private void businesNextInitData(String SortNo) {
//        JSONObject jsonObject = ParamUtils.setParams("search", "businessType",
//                new Object[]{ParamUtils.UserId, SortNo, "Y"}, 3);
        //UserId:登陆用户,SortNo:业务品种类
        JSONObject jsonObject = ParamUtils.setParams("crmBusinessType", "crmBusinessType",
                new Object[]{ParamUtils.UserId, SortNo}, 2);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, nextHandler);
    }

    private Handler nextHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {

                String radioGson = null;
                try {
                    radioGson = HttpThread.parseJSONWithGson(String.valueOf(msg.obj));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bussTypeList = HttpThread.parseList(radioGson, BussType.class);
                generalListTable.setAdapter(new GeneralListAdapert(GeneralListTableActivity.this, bussTypeList));
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        indexNumber = i;
        SortNo = bussTypeList.get(i).getSortNo();
        if (bussTypeList.get(i).getTypeProductML().equals("2")){
            in.putExtra("typeName", bussTypeList.get(indexNumber).getTypeName());
            in.putExtra("typeNo", bussTypeList.get(indexNumber).getTypeNo());
//                        in.putExtra("typeProductML", bussTypeList.get(indexNumber).get());
            setResult(RESULT_OK, in);
            //没有数据的时候
            finish();
        }else {
            LogUtils.d("SortNo===" + SortNo);
            radioInitData();
        }


    }

    class GeneralListAdapert extends BaseAdapter {

        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
        private TextView textItem;


        private List<BussType> listData = new ArrayList<>();

        /*构造函数*/
        public GeneralListAdapert(Context context, List<BussType> bussTypeList) {
            this.mInflater = LayoutInflater.from(context);
            this.listData = bussTypeList;
        }

        @Override
        public int getCount() {

            return listData.size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.general_item_list, null);
                textItem = (TextView) convertView.findViewById(R.id.text_ganer_list);
            }

            textItem.setText(listData.get(position).getTypeName());

            return convertView;
        }

    }
}
