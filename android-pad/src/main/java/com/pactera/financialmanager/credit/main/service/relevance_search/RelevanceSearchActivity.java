package com.pactera.financialmanager.credit.main.service.relevance_search;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.utils.LogUtil;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.adapters.CommonAdapter;
import com.pactera.financialmanager.credit.common.bean.CommonBean;
import com.pactera.financialmanager.credit.main.service.search.Arrays;
import com.pactera.financialmanager.credit.main.service.search.QueryList;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.WheelView;
import com.pactera.financialmanager.util.ToolType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RelevanceSearchActivity extends ParentActivity implements View.OnClickListener {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.customerType)
    Spinner customerType;
    @Bind(R.id.customerName)
    EditText customerName;
    @Bind(R.id.iv_ocr)
    ImageView ivOcr;
    @Bind(R.id.cardType)
    Spinner cardType;
    @Bind(R.id.cardNumber)
    EditText cardNumber;
    @Bind(R.id.owned_outlets)
    Spinner ownedOutlets;
    @Bind(R.id.telephoneNumber)
    EditText telephoneNumber;
    @Bind(R.id.level)
    Spinner level;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.btn_owned_outlets)
    Button btnOwnedOutlets;
    @Bind(R.id.ll_owned_outlets)

    LinearLayout llOwnedOutlets;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioGroup BreachRadioGroup;
    PullLoadMoreRecyclerView pullLoadMore;
    TextView tvHideData;
    UberProgressView uberPgsview;

    //创建Dialog 弹窗
    private Dialog dialog;
    private View contentView;
    private TextView selecedName;

    //网点选择器弹窗
    private Dialog branches_dialog;
    private View branches_pop;

    private ToolType toolType;
    private WheelView wheel_line;

    List<CommonBean.BranshesModel> listData;

    private String cusType_name;
    private String cusNamer;
    private String cardNumbers;
    private String cardType_name;
    private String cusType_value;
    private String cardType_value;
    private IDCardResult idCard;
    private String cusType_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevance_search);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        txtTitle.setText(getString(R.string.relevance_search));
        backActivity(this, layBack);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String NameInfo = intent.getStringExtra("NameInfo");
        initTitle(this, Name, true,NameInfo);

        ArrayAdapter<CharSequence> custtype =
                ArrayAdapter.createFromResource(this, R.array.customer_associated_type, android.R.layout
                        .simple_spinner_item);
        custtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerType.setAdapter(custtype);

        ArrayAdapter<CharSequence> cardtype =
                ArrayAdapter.createFromResource(this, R.array.card_type, android.R.layout
                        .simple_spinner_item);
        cardtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardType.setAdapter(cardtype);
    }

    @OnClick({R.id.iv_ocr, R.id.submit, R.id.ll_owned_outlets, R.id.btn_owned_outlets})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ocr:
                openOCR();
//                toast("open ocr");
                break;
            case R.id.submit:
//                toast("chaxun");
                search();
                break;
            case R.id.btn_owned_outlets:
            case R.id.ll_owned_outlets:
                ownedOutLets();
//                selectAddress();
                break;
        }
    }

    private void ownedOutLets() {

        initViewDialog();
        radioButton1.setText("地级市");
        radioButton1.setVisibility(View.VISIBLE);
        radioButton2.setVisibility(View.INVISIBLE);
        radioButton3.setVisibility(View.INVISIBLE);
        Branshes();
    }

    private void initViewDialog() {
        //网点选择器弹窗
        branches_dialog = new Dialog(this, R.style.popupi_dalog);
        branches_pop = LayoutInflater.from(this).inflate(R.layout.branches_pop_layout, null);
        branches_dialog.setContentView(branches_pop);
        ViewGroup.LayoutParams brancheslayoutParams = branches_pop.getLayoutParams();
        brancheslayoutParams.width = getResources().getDisplayMetrics().widthPixels;
        branches_pop.setLayoutParams(brancheslayoutParams);
        branches_dialog.getWindow().setGravity(Gravity.BOTTOM);
        branches_dialog.getWindow().setWindowAnimations(R.style.disappear_dalog);

        //网点选择器选项radioGroup
        BreachRadioGroup = (RadioGroup) branches_pop.findViewById(R.id.Breach_radio_group);
        BreachRadioGroup.setVisibility(View.VISIBLE);
        radioButton1 = (RadioButton) branches_pop.findViewById(R.id.radio_button_01);
        radioButton2 = (RadioButton) branches_pop.findViewById(R.id.radio_button_02);
        radioButton3 = (RadioButton) branches_pop.findViewById(R.id.radio_button_03);
        radioButton4 = (RadioButton) branches_pop.findViewById(R.id.radio_button_04);
        tvHideData = (TextView) branches_pop.findViewById(R.id.tv_hide_data);
        uberPgsview = (UberProgressView) branches_pop.findViewById(R.id.uber_pgsview);
        pullLoadMore = (PullLoadMoreRecyclerView) branches_pop.findViewById(R.id.pull_load_more);
        pullLoadMore.setPullRefreshEnable(false);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setGridLayout(3);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
    }

    private void Branshes() {

        BranshesHttp("", "4");
        branches_dialog.setCanceledOnTouchOutside(true);
        branches_dialog.show();
    }

    private void BranshesHttp(String QueryOrgId, String OrgType) {

//        "{""deviceType"":""Android"",""RequestParams"":{
//        UserId：登陆用户,
//                OrgId：机构ID（首选界面给当前登录机构OrgId），
//        OrgType:选择类型(市：4 县:8 网点：12，默认为 市：4)
//    }}"
        JSONObject jsonObject = ParamUtils.setParams("crmCreditOrg", "crmCreditOrg",
                new Object[]{"system1", "1", QueryOrgId, OrgType}, 4);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, conditionsHandler);
    }

//    private CommonAdapter.BranshesAdpater adapter;
    /**
     * 解析数据
     */
    private Handler conditionsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (uberPgsview != null)
                uberPgsview.setVisibility(View.INVISIBLE);
            if (msg.what ==-1){
                if (tvHideData != null) {
                    tvHideData.setVisibility(View.VISIBLE);
                    tvHideData.setText(getString(R.string.unNetwork));
                }
            }else if (msg.what == -100){
                if (tvHideData != null)
                    tvHideData.setVisibility(View.VISIBLE);
            }
            if (msg.obj != null) {
                try {
                    String json = HttpThread.parseJSONWithGson(String.valueOf(msg.obj));
                    listData = HttpThread.parseList(json, CommonBean
                            .BranshesModel.class);
                    pullLoadMore.setAdapter(new CommonAdapter.BranshesAdpater2(aty, listData,
                            new OnItemClickCallback<Integer>() {
                                @Override
                                public void onClick(View view, Integer position) {
                                    String OrgName = listData.get(position).getOrgName();
                                    BranshesHttp(listData.get(position).getQueryOrgId(), listData.get(position).getOrgType());


                                    switch (listData.get(position).getOrgType()) {
                                        case "4":
                                            radioButton1.setText("地级市");
                                            radioButton1.setVisibility(View.VISIBLE);
                                            radioButton1.setChecked(true);
                                            break;
                                        case "8":
                                            radioButton2.setText("县区");
                                            radioButton2.setVisibility(View.VISIBLE);
                                            radioButton2.setChecked(true);
                                            break;
                                        case "12":
                                            radioButton3.setText("网点");
                                            radioButton3.setVisibility(View.VISIBLE);
                                            radioButton3.setChecked(true);
                                            break;
                                        case "00":
                                            btnOwnedOutlets.setText(OrgName);
                                            branches_dialog.hide();
                                            break;
                                    }
                                }

                                @Override
                                public void onLongClick(View view, Integer info) {

                                }

                                @Override
                                public void onClick(View view, int position, int index) {

                                }
                            }));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void search() {
        cusType_name = (String) customerType.getSelectedItem();

        if (customerType.getSelectedItemId() == 1){
            cusType_Id = "030";
            cardType_value = "0";

        }else {
            cusType_Id = "010";
            cardType_value = "020";
        }

        cusNamer = customerName.getText().toString();
        cardNumbers = cardNumber.getText().toString();
        Log.i("cardNumbers", cardNumbers);
        Arrays arrays = new Arrays();
        cardType_name = (String) cardType.getSelectedItem();
        String[] customer_type = getResources().getStringArray(R.array.customer_type);
        cusType_value = arrays.customerType(cusType_name, customer_type);
        String[] card_type = getResources().getStringArray(R.array.card_type);
//        cardType_value = arrays.cardType(cardType_name, card_type);

        LogUtils.d("CustomerType=" + cusType_value + "\tCustomerName=" + cusNamer + "\tCertTypeName=" +
                        cardType_value + "\tCertID=",
                cardNumbers);
        if (TextUtils.isEmpty(cusType_Id)){
            toast("请选择客户类型");

        }else if (TextUtils.isEmpty(cusNamer)){
            toast("请输入用户名");

        }else if (TextUtils.isEmpty(cardNumbers)){
            toast("请输入证件号");

        }else {
            Intent intent = new Intent(this, QueryList.class);
            Bundle bundle = new Bundle();
            bundle.putString("transType", ViewUtils.getText(txtTitle));
            bundle.putString("CustomerType", cusType_Id);
            bundle.putString("CustomerName", cusNamer);
            bundle.putString("CertTypeName", cardType_value);
            bundle.putString("CertID", cardNumbers);
            intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        onAtyResult(requestCode, resultCode, data, customerName, cardNumber);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //滚动选择器确认
            case R.id.btn_confirm:

//                setData(wheel_line.getSeletedItem(),viewId);
//
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            // 滚动选择器取消
            case R.id.btn_cancel:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            //网点radiu
            case R.id.radio_button_01:
                BranshesHttp("", "4");
                radioButton2.setVisibility(View.INVISIBLE);
                radioButton3.setVisibility(View.INVISIBLE);
                break;
            case R.id.radio_button_02:
                BranshesHttp("", "8");
                radioButton3.setVisibility(View.INVISIBLE);
                break;
            case R.id.radio_button_03:
                break;
        }
    }
}
