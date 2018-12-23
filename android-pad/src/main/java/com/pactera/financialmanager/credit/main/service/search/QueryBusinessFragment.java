package com.pactera.financialmanager.credit.main.service.search;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.ocr_new.IDCardActivity;
import com.pactera.financialmanager.credit.common.ocr_new.IDCardInfoReaderActivity;
import com.pactera.financialmanager.credit.main.service.rate.GeneralListTableActivity;
import com.pactera.financialmanager.ui.ParentFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 2017/7/27.
 */

public class QueryBusinessFragment extends ParentFragment implements View.OnClickListener {
    @Bind(R.id.customerType)
    Spinner customerType;
    @Bind(R.id.customerName)
    EditText customerName;
    @Bind(R.id.cardType)
    Spinner cardType;
    @Bind(R.id.cardNumber)
    EditText cardNumber;
    @Bind(R.id.telephoneNumber)
    EditText telephoneNumber;
    @Bind(R.id.ll_businessType)
    LinearLayout llBusinessType;
    @Bind(R.id.tabr_business)
    TableRow tabrBusiness;
    @Bind(R.id.btn_businessType)
    Button btnBusinessType;
    @Bind(R.id.ll_btn)
    LinearLayout llBtn;
    @Bind(R.id.tabr_btn)
    TableRow tabrBtn;
    @Bind(R.id.level)
    Spinner level;
    @Bind(R.id.loanAfter_left)
    Button loanAfterLeft;
    @Bind(R.id.loanAfter_before_text)
    EditText loanAfterBeforeText;
    @Bind(R.id.loanAfter_text)
    TextView loanAfterText;
    @Bind(R.id.loanAfter_after_text)
    EditText loanAfterAfterText;
    @Bind(R.id.overdueAmount_left)
    Button overdueAmountLeft;
    @Bind(R.id.overdueAmount_before_text)
    EditText overdueAmountBeforeText;
    @Bind(R.id.overdueAmount_text)
    TextView overdueAmountText;
    @Bind(R.id.overdueAmount_after_text)
    EditText overdueAmountAfterText;
    @Bind(R.id.overduePeriods_left)
    Button overduePeriodsLeft;
    @Bind(R.id.overduePeriods_before_text)
    EditText overduePeriodsBeforeText;
    @Bind(R.id.overduePeriods_text)
    TextView overduePeriodsText;
    @Bind(R.id.overduePeriods_after_text)
    EditText overduePeriodsAfterText;
    @Bind(R.id.issuingDate_left)
    Button issuingDateLeft;
    @Bind(R.id.issuingDate_before_text)
    EditText issuingDateBeforeText;
    @Bind(R.id.issuingDate_text)
    TextView issuingDateText;
    @Bind(R.id.issuingDate_after_text)
    EditText issuingDateAfterText;
    @Bind(R.id.dueDate_left)
    Button dueDateLeft;
    @Bind(R.id.dueDate_before_text)
    EditText dueDateBeforeText;
    @Bind(R.id.dueDate_text)
    TextView dueDateText;
    @Bind(R.id.dueDate_after_text)
    EditText dueDateAfterText;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.et_custType)
    Button etCustType;
    @Bind(R.id.ll_custType)
    LinearLayout llCustType;
    @Bind(R.id.iv_ocr)
    ImageView ivOcr;
    @Bind(R.id.businessType)
    Spinner businessType;
    private Context mContext;
    //贷款余额
    private Button loanAfter_left;
    private TextView loanAfter_text;
    private EditText loanAfter_before_text;
    private EditText loanAfter_after_text;
    //逾期金额
    private Button overdueAmount_left;
    private TextView overdueAmount_text;
    private EditText overdueAmount_before_text;
    private EditText overdueAmount_after_text;
    //逾期期数
    private Button overduePeriods_left;
    private TextView overduePeriods_text;
    private EditText overduePeriods_before_text;
    private EditText overduePeriods_after_text;
    //发放日期
    private Button issuingDate_left;
    private TextView issuingDate_text;
    private EditText issuingDate_before_text;
    private EditText issuingDate_after_text;
    //到期日期
    private Button dueDate_left;
    private TextView dueDate_text;
    private EditText dueDate_before_text;
    private EditText dueDate_after_text;

    private Button[] btns;
    private Spinner Level;
    //创建Dialog 弹窗
    private Dialog dialog;
    private View contentView;
    //菜单部分
    private static final String[] PLANETS = new String[]{"大于", "小于", "区间"};
    private WheelView wheel_line;

    private ArrayList<String> arry;
    private ArrayList<String> TypeNo;
    private String[] arryString;
    private String[] TypeNoString;
    //intent
    private String customerType_name;
    private String customerType_value;
    private String customerNamer;
    private String cardType_name;
    private String cardType_value;
    private String TypeNo_name;
    private String TypeNo_value = "";
    private String classifyResult_name;
    private String classifyResult_value;
    private String cardNumber_value;
    private String telephoneNumber_value;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TypeNo = new ArrayList<String>();
            arry = new ArrayList<String>();
            if (msg.obj != null) {
                List<ListQueryBusiness> list = null;
                try {
                    list = HttpThread.parseList(HttpThread.parseJSONWithGson((String) msg.obj),
                            ListQueryBusiness.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < list.size(); i++) {
                    Log.d("list当前的：", list.get(i).getValue() + "list当前的TypeNo:" + list.get(i).getTypeNo());
                    TypeNo.add(list.get(i).getTypeNo());
                    arry.add(list.get(i).getValue());
                }
                Log.i("arry", String.valueOf(arry));
                arryString = (String[]) arry.toArray(new String[0]);
                TypeNoString = (String[]) TypeNo.toArray(new String[0]);
            }

        }
    };

    //    private String[] formList;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.query_business, container, false);
        ButterKnife.bind(this, v);
        mContext = getActivity();

        findView(v);

        //
        btns = new Button[]{loanAfter_left, overdueAmount_left, overduePeriods_left, issuingDate_left, dueDate_left};
        initView();

        //设置弹窗的默认值
        wheel_line = (WheelView) contentView.findViewById(R.id.wheel_line);
        wheel_line.setOffset(1);
        wheel_line.setItems(java.util.Arrays.asList(PLANETS));
        wheel_line.setSeletion(1);
        for (int i = 0; i < btns.length; i++) {
            btns[i].setOnClickListener(this);
        }
//        sendRequest();
        etCustType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("ll approval_business type ");
                tabrBusiness.setVisibility(View.VISIBLE);
                tabrBtn.setVisibility(View.GONE);
                startActivityForResult(new Intent(getActivity(), GeneralListTableActivity.class), 0);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mySubmit();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        LogUtils.d("返回之后执行------");

        if (requestCode == IDCardInfoReaderActivity.REQUEST_CODE_CAMERA)
            onAtyResult(requestCode, resultCode, data, customerName, cardNumber);
        switch (resultCode) {

            //业务品种返回的数据
            case RESULT_OK:
                TypeNo_name = data.getExtras().getString("typeName");
                TypeNo_value = data.getExtras().getString("typeNo");
//                TypeNo_value = data.getExtras().getString("typeProductML");//1为目录，2为产品，
                etCustType.setText(TypeNo_name);
                break;
            default:
                break;
        }

    }

    private void mySubmit() {
        customerNamer = customerName.getText().toString();
        Arrays arrays = new Arrays();
        cardType_name = (String) cardType.getSelectedItem();
        customerType_name = (String) customerType.getSelectedItem();
        String[] customer_type = getResources().getStringArray(R.array.customer_associated_type);
        customerType_value = arrays.customerType(customerType_name, customer_type);
        String[] card_type = getResources().getStringArray(R.array.card_type);
        cardType_value = arrays.cardType(cardType_name, card_type);
        if (cardType_value.equals("0")){
            cardType_value = "";
        }
//        Log.i("tag1", customerType_value);
//        LogUtils.d(TypeNo_name + "===" + arryString + "===" + TypeNoString);

        classifyResult_name = (String) Level.getSelectedItem();
        String[] classify_result = getResources().getStringArray(R.array.classify_result);
        classifyResult_value = arrays.getClassifyResult_value(classifyResult_name, classify_result);
        cardNumber_value = cardNumber.getText().toString();
        telephoneNumber_value = telephoneNumber.getText().toString();

        Intent intent = new Intent(mContext, BusinessList.class);
        Bundle bundle = new Bundle();
        bundle.putString("CustomerType", customerType_value);
        bundle.putString("CustomerName", customerNamer);
        bundle.putString("CertType", cardType_value);
        bundle.putString("CertID", cardNumber_value);
        bundle.putString("MobileTelephone", telephoneNumber_value);
//        bundle.putString("BusinessTypeBg", TypeNo_value);
        bundle.putString("SortNo", TypeNo_value);
        putinString(bundle);
        bundle.putString("ClassifyResult", classifyResult_value);
        bundle.putString("BusinessSum", "");
        bundle.putString("OverDueDay", "");
        bundle.putString("UserID", ParamUtils.UserId);
        intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
        startActivity(intent);
    }

    private void findView(View v) {
        loanAfter_left = (Button) v.findViewById(R.id.loanAfter_left);
        loanAfter_text = (TextView) v.findViewById(R.id.loanAfter_text);
        loanAfter_before_text = (EditText) v.findViewById(R.id.loanAfter_before_text);
        loanAfter_after_text = (EditText) v.findViewById(R.id.loanAfter_after_text);
        //
        overdueAmount_left = (Button) v.findViewById(R.id.overdueAmount_left);
        overdueAmount_text = (TextView) v.findViewById(R.id.overdueAmount_text);
        overdueAmount_before_text = (EditText) v.findViewById(R.id.overdueAmount_before_text);
        overdueAmount_after_text = (EditText) v.findViewById(R.id.overdueAmount_after_text);
        //
        overduePeriods_left = (Button) v.findViewById(R.id.overduePeriods_left);
        overduePeriods_text = (TextView) v.findViewById(R.id.overduePeriods_text);
        overduePeriods_before_text = (EditText) v.findViewById(R.id.overduePeriods_before_text);
        overduePeriods_after_text = (EditText) v.findViewById(R.id.overduePeriods_after_text);
        //
        issuingDate_left = (Button) v.findViewById(R.id.issuingDate_left);
        issuingDate_text = (TextView) v.findViewById(R.id.issuingDate_text);
        issuingDate_before_text = (EditText) v.findViewById(R.id.issuingDate_before_text);
        issuingDate_after_text = (EditText) v.findViewById(R.id.issuingDate_after_text);
        //
        dueDate_left = (Button) v.findViewById(R.id.dueDate_left);
        dueDate_text = (TextView) v.findViewById(R.id.dueDate_text);
        dueDate_before_text = (EditText) v.findViewById(R.id.dueDate_before_text);
        dueDate_after_text = (EditText) v.findViewById(R.id.dueDate_after_text);

        Level = (Spinner) v.findViewById(R.id.level);
        llBusinessType = (LinearLayout) v.findViewById(R.id.ll_businessType);
        llBtn = (LinearLayout) v.findViewById(R.id.ll_btn);
    }

    private void sendRequest() {

        JSONObject jsonObject = ParamUtils.setParams("search", "businessType", new Object[]{ParamUtils.UserId, "", "Y"}, 3);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    public void putinString(Bundle bundle) {
        String loanAfter_left_text = loanAfter_left.getText().toString();
        String loanAfter_before_text_text = loanAfter_before_text.getText().toString();
        String loanAfter_after_text_text = loanAfter_after_text.getText().toString();
        String overdueAmount_left_text = overdueAmount_left.getText().toString();
        String overdueAmount_before_text_text = overdueAmount_before_text.getText().toString();
        String overdueAmount_after_text_text = overdueAmount_after_text.getText().toString();
        String overduePeriods_left_text = overduePeriods_left.getText().toString();
        String overduePeriods_before_text_text = overduePeriods_before_text.getText().toString();
        String overduePeriods_after_text_text = overduePeriods_after_text.getText().toString();
        String dueDate_left_text = dueDate_left.getText().toString();
        String dueDate_before_text_text = dueDate_before_text.getText().toString();
        String dueDate_after_text_text = dueDate_after_text.getText().toString();
        String issuingDate_left_text = issuingDate_left.getText().toString();
        String issuingDate_before_text_text = issuingDate_before_text.getText().toString();
        String issuingDate_after_text_text = issuingDate_after_text.getText().toString();
        String[] arraylist = new String[]{loanAfter_left_text, overdueAmount_left_text, overduePeriods_left_text, issuingDate_left_text, dueDate_left_text};
        String[] arraybefore = new String[]{loanAfter_before_text_text, overdueAmount_before_text_text, overduePeriods_before_text_text, dueDate_before_text_text, issuingDate_before_text_text};
        String[] arrayafter = new String[]{loanAfter_after_text_text, overdueAmount_after_text_text, overduePeriods_after_text_text, dueDate_after_text_text, issuingDate_after_text_text};
        String[] arraylist1 = new String[]{"Balance", "OverDueBalance", "LcaTimes", "ActualPutOutDate", "ActualMaturity"};
        for (int t = 0; t < arraylist.length; t++) {
            Log.i("arraylist:", arraylist + "arraybefore:" + arraybefore);
            String currentStr = arraylist1[t];
            if (arraylist[t].equals("区间")) {
                if ((TextUtils.isEmpty(arraybefore[t]) || arraybefore[t] == null) || (TextUtils.isEmpty(arrayafter[t]) || arrayafter[t] == null)) {
                    bundle.putString(currentStr, "");
                } else {
                    bundle.putString(currentStr, arraybefore[t] + "@" + arrayafter[t] + "@c");
                }

            } else if (arraylist[t].equals("小于")) {
                if (TextUtils.isEmpty(arraybefore[t]) || arraybefore[t] == null) {
                    bundle.putString(currentStr, "");
                } else {
                    bundle.putString(currentStr, arraybefore[t] + "@b");
                }

            } else {
                if (TextUtils.isEmpty(arraybefore[t]) || arraybefore[t] == null) {
                    bundle.putString(currentStr, "");
                } else {
                    bundle.putString(currentStr, arraybefore[t] + "@a");
                }
            }
        }
    }

    @Override
    public void onClick(final View view) {
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        switch (view.getId()) {
            case R.id.loanAfter_left:
                setinitView(loanAfter_left);
//                contentView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (dialog != null) {
//                            dialog.dismiss();
//                            Log.i("SeletedItem",wheel_line.getSeletedItem());
//                        }
//                    }
//                });
                break;
            case R.id.overdueAmount_left:
                setinitView(overdueAmount_left);
                break;
            case R.id.overduePeriods_left:
                setinitView(overduePeriods_left);
                break;
            case R.id.issuingDate_left:
                setinitView(issuingDate_left);
                break;
            case R.id.dueDate_left:
                setinitView(dueDate_left);
                break;

        }
        wheel_line.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d("dysen", "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                switch (view.getId()) {
                    case R.id.loanAfter_left:
                        loanAfter_left.setText(item);
//                        wheel_line.setSeletion(selectedIndex);
                        encapsulationHide(item, loanAfter_text, loanAfter_after_text);
                        break;
                    case R.id.overdueAmount_left:
                        overdueAmount_left.setText(item);
                        encapsulationHide(item, overdueAmount_text, overdueAmount_after_text);
                        break;
                    case R.id.overduePeriods_left:
                        overduePeriods_left.setText(item);
                        encapsulationHide(item, overduePeriods_text, overduePeriods_after_text);
                        break;
                    case R.id.issuingDate_left:
                        issuingDate_left.setText(item);
                        encapsulationHide(item, issuingDate_text, issuingDate_after_text);
                        break;
                    case R.id.dueDate_left:
                        dueDate_left.setText(item);
                        encapsulationHide(item, dueDate_text, dueDate_after_text);
                        break;
                    default:
                        break;
                }
            }
        });
        Log.i("itemValue", (String) loanAfter_left.getText());
    }

    //设置弹窗
    private void initView() {

        ArrayAdapter<CharSequence> custtype =
                ArrayAdapter.createFromResource(mContext, R.array.customer_associated_type, android.R.layout.simple_spinner_item);
        custtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerType.setAdapter(custtype);
        ArrayAdapter<CharSequence> cardtype =
                ArrayAdapter.createFromResource(mContext, R.array.card_type, android.R.layout.simple_spinner_item);
        cardtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardType.setAdapter(cardtype);
        ArrayAdapter<CharSequence> level =
                ArrayAdapter.createFromResource(mContext, R.array.classify_result, android.R.layout.simple_spinner_item);
        level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Level.setAdapter(level);

        dialog = new Dialog(mContext, R.style.popupi_dalog);
        contentView = LayoutInflater.from(mContext).inflate(R.layout.activity_dialog_content, null);
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(btnlistener);
        contentView.findViewById(R.id.btn_confirm).setOnClickListener(btnlistener);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.disappear_dalog);

    }

    //
    private void setinitView(Button button) {
        CharSequence Text = button.getText();
        if (!TextUtils.isEmpty(Text)) {
            for (int i = 0; i < PLANETS.length; i++) {
                if (Text.toString().equals(PLANETS[i])) {
                    wheel_line.setSeletion(i);
                }
            }
        }
    }

    private void encapsulationHide(String item, TextView textStr, EditText after_text) {
        if (!item.equals("区间")) {
            textStr.setVisibility(View.GONE);
            after_text.setVisibility(View.GONE);
        } else {
            textStr.setVisibility(View.VISIBLE);
            after_text.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_confirm:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                // 取消
                case R.id.btn_cancel:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.iv_ocr)
    public void onViewClicked() {
        openOCR();
    }
}


