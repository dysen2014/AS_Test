package com.example.lenovo.query;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/7/27.
 */

public class QueryBusiness extends Fragment implements View.OnClickListener{
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
    private Button submit;
    private Spinner customerType;
    private EditText customerName;
    private Spinner cardType;
    private EditText cardNumber;
    private EditText telephoneNumber;
    private Spinner Level;
    //创建Dialog 弹窗
    private Dialog dialog;
    private View contentView;
    //菜单部分
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"大于", "小于", "区间"};
    private WheelView wheel_line;
    //接口变量设置
    private JSONObject jsonObject;
    private JSONObject jsonObject1;
    private Spinner businessType;
    private Handler handler;
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
    private String TypeNo_value;
    private String classifyResult_name;
    private String classifyResult_value;
    private String cardNumber_value;
    private String telephoneNumber_value;
//    private String[] formList;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.query_business, container, false);
        mContext = getActivity();

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
        //设置下拉框值
        customerType = (Spinner) v.findViewById(R.id.customerType);
        customerName = (EditText) v.findViewById(R.id.customerName);
        cardType = (Spinner) v.findViewById(R.id.cardType);
        Level =(Spinner) v.findViewById(R.id.level);
        //获取所有节点
        cardNumber = (EditText) v.findViewById(R.id.cardNumber);
        telephoneNumber = (EditText) v.findViewById(R.id.telephoneNumber);
        Level = (Spinner) v.findViewById(R.id.level);
        submit = (Button) v.findViewById(R.id.submit);
        ArrayAdapter<CharSequence> custtype =
                ArrayAdapter.createFromResource(mContext, R.array.customer_type, android.R.layout.simple_spinner_item);
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
//        //调用业务品种接口设置下拉框值
        businessType = (Spinner) v.findViewById(R.id.businessType);
        jsonObject = new JSONObject();
        jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserID","B129409");
            jsonObject1.put("SortNo","");
            jsonObject.put("deviceType","Android");
            jsonObject.put("RequestParams",jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new QueryBusiness.HttpThread().sendRequestWithOkHttp("http://192.168.1.100:9080/ALS7M/JSONService?method=businessType",jsonObject);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                TypeNo = new ArrayList<String>();
                arry = new ArrayList<String>();
                if (msg.obj != null ){
                    final List<ListQueryBusiness> list = (List<ListQueryBusiness>) msg.obj;
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("list当前的：",list.get(i).getValue()+"list当前的TypeNo:"+list.get(i).getTypeNo());
                        TypeNo.add(list.get(i).getTypeNo());
                        arry.add(list.get(i).getValue());
                    }
                    Log.i("arry", String.valueOf(arry));
                    arryString = (String[]) arry.toArray(new String[0]);
                    TypeNoString = (String[]) TypeNo.toArray(new String[0]);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, arry);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    businessType.setAdapter(adapter);
                }

            }
        };

        //
        btns = new Button[]{loanAfter_left,overdueAmount_left,overduePeriods_left,issuingDate_left,dueDate_left};
        initView();
        //设置弹窗的默认值
        wheel_line = (WheelView) contentView.findViewById(R.id.wheel_line);
        wheel_line.setOffset(1);
        wheel_line.setItems(java.util.Arrays.asList(PLANETS));
        wheel_line.setSeletion(1);
        for (int i = 0;i<btns.length;i++){
            btns[i].setOnClickListener(this);
        }
        submit = (Button) v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerType_name = (String) customerType.getSelectedItem();
                customerNamer = customerName.getText().toString();
                Arrays arrays = new Arrays();
                cardType_name = (String) cardType.getSelectedItem();
                String[] customer_type = getResources().getStringArray(R.array.customer_type);
                customerType_value = arrays.customerType(customerType_name,customer_type);
                String[] card_type = getResources().getStringArray(R.array.card_type);
                cardType_value = arrays.cardType(cardType_name,card_type);
                Log.i("tag1",customerType_value);
                TypeNo_name = (String) businessType.getSelectedItem();
                TypeNo_value = arrays.getTypeNo_value(TypeNo_name,arryString,TypeNoString);
                classifyResult_name = (String) Level.getSelectedItem();
                String[] classify_result = getResources().getStringArray(R.array.classify_result);
                classifyResult_value = arrays.getClassifyResult_value(classifyResult_name,classify_result);
                cardNumber_value = cardNumber.getText().toString();
                telephoneNumber_value = telephoneNumber.getText().toString();
                Log.i("TypeNo_value:",TypeNo_value);
                Log.i("classifyResult_value:",classifyResult_value);

                Intent intent = new Intent(mContext,BusinessList.class);
                Bundle bundle = new Bundle();
                bundle.putString("CustomerType",customerType_value);
                bundle.putString("CustomerName",customerNamer);
                bundle.putString("CertType",cardType_value);
                bundle.putString("CertID",cardNumber_value);
                bundle.putString("MobileTelephone",telephoneNumber_value);
                bundle.putString("BusinessTypeBg",TypeNo_value);
                putinString(bundle);
//                bundle.putString("Balance","");
//                bundle.putString("OverDueBalance","");
//                bundle.putString("LcaTimes","");
//                bundle.putString("ActualPutOutDate","");
//                bundle.putString("ActualMaturity","");
                bundle.putString("ClassifyResult",classifyResult_value);
                bundle.putString("BusinessSum","");
                bundle.putString("OverDueDay","");
                bundle.putString("UserID","B129106");
                intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                startActivity(intent);

            }
        });
        return v;
    }
    public void putinString(Bundle bundle){
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
        String[] arraylist = new String[]{loanAfter_left_text,overdueAmount_left_text,overduePeriods_left_text,issuingDate_left_text,dueDate_left_text};
        String[] arraybefore = new String[]{loanAfter_before_text_text,overdueAmount_before_text_text,overduePeriods_before_text_text,dueDate_before_text_text,issuingDate_before_text_text};
        String[] arrayafter = new String[]{loanAfter_after_text_text,overdueAmount_after_text_text,overduePeriods_after_text_text,dueDate_after_text_text,issuingDate_after_text_text};
        String[] arraylist1 = new String[]{"Balance","OverDueBalance","LcaTimes","ActualPutOutDate","ActualMaturity"};
        for(int t=0;t<arraylist.length;t++){
            Log.i("arraylist:",arraylist+"arraybefore:"+arraybefore);
            String currentStr = arraylist1[t];
            if(arraylist[t].equals("区间")){
                if((TextUtils.isEmpty(arraybefore[t])||arraybefore[t]==null)||(TextUtils.isEmpty(arrayafter[t])||arrayafter[t]==null)){
                    bundle.putString(currentStr,"");
                }else{
                    bundle.putString(currentStr,arraybefore[t]+"@"+arrayafter[t]+"@c");
                }

            }else if(arraylist[t].equals("小于")){
                if(TextUtils.isEmpty(arraybefore[t])||arraybefore[t]==null){
                    bundle.putString(currentStr,"");
                }else{
                    bundle.putString(currentStr,arraybefore[t]+"@b");
                }

            }else{
                if(TextUtils.isEmpty(arraybefore[t])||arraybefore[t]==null){
                    bundle.putString(currentStr,"");
                }else {
                    bundle.putString(currentStr,arraybefore[t]+"@a");
                }
            }
        }
    }
    @Override
    public void onClick(final View view) {
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        switch (view.getId()){
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
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                switch (view.getId()){
                    case R.id.loanAfter_left:
                        loanAfter_left.setText(item);
//                        wheel_line.setSeletion(selectedIndex);
                        encapsulationHide(item,loanAfter_text,loanAfter_after_text);
                        break;
                    case R.id.overdueAmount_left:
                        overdueAmount_left.setText(item);
                        encapsulationHide(item,overdueAmount_text,overdueAmount_after_text);
                        break;
                    case R.id.overduePeriods_left:
                        overduePeriods_left.setText(item);
                        encapsulationHide(item,overduePeriods_text,overduePeriods_after_text);
                        break;
                    case R.id.issuingDate_left:
                        issuingDate_left.setText(item);
                        encapsulationHide(item,issuingDate_text,issuingDate_after_text);
                        break;
                    case R.id.dueDate_left:
                        dueDate_left.setText(item);
                        encapsulationHide(item,dueDate_text,dueDate_after_text);
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
    private void setinitView(Button button){
        CharSequence Text = button.getText();
        if(!TextUtils.isEmpty(Text)){
            for(int i = 0;i<PLANETS.length;i++){
                if(Text.toString().equals(PLANETS[i])){
                    wheel_line.setSeletion(i);
                }
            }
        }
    }
    private void encapsulationHide(String item, TextView textStr, EditText after_text){
        if (!item.equals("区间")){
            textStr.setVisibility(View.GONE);
            after_text.setVisibility(View.GONE);
        }else {
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
    class HttpThread extends Thread {
        private final MediaType MEDIA_TYPE_MARKDOWN  = MediaType.parse("application/json; charset=utf-8");
        protected void sendRequestWithOkHttp(final String url, final JSONObject jsonObject) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Log.i("jsonObject:", String.valueOf(jsonObject));
                        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,  jsonObject.toString());
                        Request request = new Request.Builder().url(url).post(body).build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        Log.i("responseData",responseData);
                        parseJSONWithGson(responseData);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private Object parseJSONWithGson(String jsonData) throws JSONException {
            if (!TextUtils.isEmpty(jsonData) || jsonData != null){
                JSONObject jsonObject = new JSONObject(jsonData);
                String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
                Log.d("tag10", "jsonObject: " + jsonArray);
//            StringBuffer jsonBuffer = new StringBuffer(jsonArray);
//            jsonBuffer.deleteCharAt(0);
//            jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
//            jsonArray = jsonBuffer.toString();
                //Log.d("tag19", jsonArray);
                Gson gson = new Gson();
                List<ListQueryBusiness> customerList = gson.fromJson(jsonArray, new TypeToken<List<ListQueryBusiness>>(){}.getType());
                Log.i("tag11", "customerList: " + customerList);
                Message msg = new Message();
                msg.obj = customerList;
                handler.sendMessage(msg);
                return customerList;
            }else
                return false;
        }
    }
}
