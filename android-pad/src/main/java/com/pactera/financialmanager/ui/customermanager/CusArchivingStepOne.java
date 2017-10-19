package com.pactera.financialmanager.ui.customermanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.ocr.OCRAnalysis;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.commontool.DeviceSettingActivity;
import com.pactera.financialmanager.ui.model.BaseCustomerInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.BluetoothTools;
import com.pactera.financialmanager.util.BluetoothUtil;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.IDCReaderSDK;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LimitsUtil;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.RuleUtil;
import com.pactera.financialmanager.util.Tool;
import com.guoguang.util.IdentityCardInfo;
import com.ym.idcard.reg.IDCard;

import org.json.JSONObject;

import java.io.File;

/**
 * 客户建档（第一步）
 *
 * @author JAY
 */
public class CusArchivingStepOne extends ParentActivity implements OnClickListener {
    private static boolean isWorking = false;
    // 各控件前对应文本
    private TextView[] textinfo = new TextView[5];
    private String[] forPersonal = {"客户姓名:", "证件类型:", "客户性别:", "证件号码:", "电话号码:"};
    private String[] forCompany = {"企业名称:", "证件类型:", "联络人姓名:", "证件号码:", "联络人电话:"};

    // 对公对私选择
    private RadioButton rbtnWarnPerson, rbtnWarnCommon;
    private Button btnReadID;// 读取身份证ID
    private Button nextBtn; // 下一步
    private EditText cusName;// 客户姓名（对私）、企业名称（对公）
    private EditText cardNum;// 证件号码
    private EditText cusPhone;// 电话号码（对私）、联络人电话（对公）
    private EditText contactsname;// 联络人姓名（对公）
    private TextView cardType;// 证件类型
    private TextView sexType;// 客户性别类型
    private String cardTypeID, sexTypeID;// 返回的码值

    final static String wltlibDirectory = "/data/data/com.swipecard/wltlib";
    private int ChooseId = 0;// 标记符号（0为对私、1为对公）
    private boolean isPersonRight = false;
    private boolean isCommonRight = false;

    private HConnection HCon, companyHConn;
    private final int returnIndex = 1, companyFlag = 2;
    private MyHandler mHandler;

    // 读身份证信息
    private ProgressDialog progressDialogIDCard = null;
    BluetoothTools btt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusarchiving_step_1);
        // 初始化
        findViews();
        OCRAnalysis.idCard = null;
        // 绑定监听器
        bindOnClickListener();
        initTitle(this, R.drawable.customermanagercon);
        mHandler = new MyHandler();
        isPersonRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T030201, false);
        isCommonRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T030202, false);
        // 个人建档无权限，则显示对公状态
        if (!isPersonRight) {
            rbtnWarnCommon.setChecked(true);
            ChooseId = 1;
            setRbtnStatus();
        }
        btt = new BluetoothTools(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IDCard idCard = OCRAnalysis.idCard;
        if(idCard != null){
            cusName.setText(idCard.getName());
            cardNum.setText(idCard.getCardNo());
        }
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        // 子类必须重写此方法，接受数据
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // 此处可以更新UI
            Bundle b = msg.getData();
            String Data = b.getString("CardData");
            byte[] PictureData = b.getByteArray("PictureData");
            int retCode = b.getInt("retCode");
            String cusNam = b.getString("cusName");
            String cardN = b.getString("cardNum");
            cusName.setText(cusNam);
            cardNum.setText(cardN);
            //text_view.append(Data);
            //text_view.setText(Data);
            Log.v("MyHandler", "IDCReaderSDK 1= " + retCode);
            if (retCode == 0) {
                retCode = IDCReaderSDK.decodingPictureData(wltlibDirectory, PictureData);
                switch (retCode) {
                    case 0:
                        //message2 = "\n照片解码成功";
                        Bitmap bm = BitmapFactory.decodeFile(wltlibDirectory + File.separator + "zp.bmp");
                        //image_view.setImageBitmap(bm);
                        break;
                    case 1:
                        //message2 = "\n照片解码初始化失败，需要检查传入的wltlibDirectory以及base.dat文件";
                        break;
                    case 2:
                        //message2 = "\n授权文件license.lic错误";
                        break;
                    default:
                        //message2 = "\n照片解码失败，其它错误";
                        break;
                }
                //text_view.append(message2);
            }
        }
    }


    private void findViews() {
        rbtnWarnPerson = (RadioButton) findViewById(R.id.rbtn_warnperson);
        rbtnWarnCommon = (RadioButton) findViewById(R.id.rbtn_warncommon);
        textinfo[0] = (TextView) findViewById(R.id.cusarchiving_step1_text1);
        textinfo[1] = (TextView) findViewById(R.id.cusarchiving_step1_text2);
        textinfo[2] = (TextView) findViewById(R.id.cusarchiving_step1_text3);
        textinfo[3] = (TextView) findViewById(R.id.cusarchiving_step1_text4);
        textinfo[4] = (TextView) findViewById(R.id.cusarchiving_step1_text5);
        btnReadID = (Button) findViewById(R.id.cusarchiving_step1_readID);
        nextBtn = (Button) findViewById(R.id.cusarchiving_step1_nextbtn);
        cusName = (EditText) findViewById(R.id.cusarchiving_step1_cusname);
        cardNum = (EditText) findViewById(R.id.cusarchiving_step1_cardnum);
        cusPhone = (EditText) findViewById(R.id.cusarchiving_step1_phonenum);
        contactsname = (EditText) findViewById(R.id.cusarchiving_step1_contactsname);
        cardType = (TextView) findViewById(R.id.cusarchiving_step1_cardtype);
        sexType = (TextView) findViewById(R.id.cusarchiving_step1_sextype);

        cardTypeID = "01";
        cardType.setText("居民身份证");
        cardType.setBackgroundColor(Color.TRANSPARENT);
    }

    private void bindOnClickListener() {
        rbtnWarnPerson.setOnClickListener(this);
        rbtnWarnCommon.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        cardType.setOnClickListener(this);
        sexType.setOnClickListener(this);
        sexType.setOnClickListener(this);
        btnReadID.setOnClickListener(this);
    }

    /**
     * 下一步提交数据
     *
     * @param index
     */
    private void creatData(int index) {
        if (index == 0) {
            String custNameStr = cusName.getText().toString();//客户姓名
            String sexTypeStr = sexType.getText().toString();//客户性别
            String cardNumberStr = cardNum.getText().toString();//身份证号码
            if (checkInputStr(custNameStr)) {
                Toast.makeText(getApplicationContext(), "客户姓名不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            //12.20号要求性别取消
           /* if (checkInputStr(sexTypeStr)) {
                Toast.makeText(getApplicationContext(), "请选择客户性别！", Toast.LENGTH_SHORT).show();
                return;
            }*/
            if (!RuleUtil.checkIDNumber(this, cardNumberStr)) {
                return;
            }

            BaseCustomerInfo theNewCustomer = new BaseCustomerInfo();
            theNewCustomer.idv_gnd_id = sexTypeID;
            theNewCustomer.custName = custNameStr;
            theNewCustomer.cust_psn_card_number = cardNumberStr;
            theNewCustomer.cust_psn_card_type = cardTypeID;
            setData(theNewCustomer.toJsonForPerson());
        } else if (index == 1) {
            String cusNameStr = cusName.getText().toString().trim();// 姓名
            String cardTypeStr = cardType.getText().toString();// 证件类型
            String contactsNameStr = contactsname.getText().toString().trim();// 联络人姓名
            String cardNumStr = cardNum.getText().toString().trim();// 证件号码
            String cusPhoneNumStr = cusPhone.getText().toString().trim();// 联络人电话
            if (checkInputStr(cusNameStr)) {
                Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkInputStr(cardTypeStr)) {
                Toast.makeText(getApplicationContext(), "请选择证件类型", Toast.LENGTH_SHORT).show();
                return;
            }
           /* 2017/2/13注销  if (checkInputStr(contactsNameStr)) {
                Toast.makeText(getApplicationContext(), "请输入联络人姓名", Toast.LENGTH_SHORT).show();
                return;
            }*/
            if (checkInputStr(cardNumStr)) {
                Toast.makeText(getApplicationContext(), "请输入证件号码", Toast.LENGTH_SHORT).show();
                return;
            }
            if ("33".equals(cardTypeID)) {
                if (!Tool.cheakOrgCode(cardNumStr)) {
                    Toast.makeText(getApplicationContext(), "你的组织机构代码非法", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //电话号码非必输项
//            if (!RuleUtil.checkPhoneNum(this, cusPhoneNumStr)) {
//                return;
//            }

            BaseCustomerInfo theNewCustomer = new BaseCustomerInfo();
            theNewCustomer.custName = cusNameStr;// 姓名
            theNewCustomer.cust_psn_card_type = cardTypeID;// 证件类型
            theNewCustomer.cust_psn_card_number = cardNumStr;// 证件号码
            theNewCustomer.CONTACTNAME = contactsNameStr;// 联系人姓名
            theNewCustomer.phone_no = cusPhoneNumStr;// 手机号码
            sendRequestForInfo(theNewCustomer.toJsonForCompany());
        }

    }

    // 传输数据（对公）
    private void sendRequestForInfo(String info) {

        companyHConn = RequestInfo(this, requestType.Insert, InterfaceInfo.CusArchivingStepOne_forCom, info,
                companyFlag);

    }

    private Boolean checkInputStr(String inputStr) {
        if (TextUtils.isEmpty(inputStr)) {
            return true;
        }
        return false;
    }

    // 传输数据（对私关键信息创建）
    private void setData(String data) {

        HCon = RequestInfo(this, requestType.JsonData, InterfaceInfo.CusArchivingStepOne_forPer, data, returnIndex);

    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        JSONObject tmpJsonObject;
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:

                break;
            case returnIndex:
                HResponse res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = res.dataJsonObject;
                readJson(tmpJsonObject);
                break;
            case companyFlag:
                HResponse companyHr = companyHConn.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyHr == null || companyHr.dataJsonObject == null) {
                    Toast.makeText(getApplicationContext(), "返回数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject retJsonObject = companyHr.dataJsonObject;
                // String jsonString = tmpJsonObject.toString();
                Log.i("1111", "....company....:" + retJsonObject.toString());

                readJson(retJsonObject);
        }

    }

    private void readJson(JSONObject tmpJsonObject) {

        String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");
        String custstate = "";
        String custInfo = "";

        if (retCode.equals("0000")) {

            String custID = Tool.getJsonValue(tmpJsonObject, "custID");

            custstate = Tool.getJsonValue(tmpJsonObject, "state");// 状态
            /**
             * 0、 首次建档 （可建档）
             * 1、建档未完成(可修改) ® 2、建档完成 （可修改）
             * 3、潜在客户在本机构已经存在 （不能建档）
             * 4、创建潜在客户 （可建档）
             * 5、无权限操作（不能建档）
             *
             * 修改后: 3 潜在客户在法人机构已经存在（不能建档） 5该客户不在管辖机构（不能建档）
             */
            if (custstate.endsWith("1") || custstate.endsWith("2") || custstate.endsWith("0")
                    || custstate.endsWith("4")) {
                Intent intent = new Intent(CusArchivingStepOne.this, CusArchivingStepTwo.class);
                intent.putExtra("ChooseId", ChooseId);
                intent.putExtra("custID", custID);
                // 也可以使用Bundle来传参数
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                if (custstate.endsWith("3")) {
                    custInfo = "潜在客户在法人机构已经存在,不能建档！";
                } else if (custstate.endsWith("5")) {
                    custInfo = "该客户不在管辖机构,不能建档！";
                }

                Toast.makeText(this, custInfo, Toast.LENGTH_SHORT).show();
                // BaseCustomerInfo.isEdit = false;
            }

        } else {
            Toast.makeText(this, "提交失败!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ChangeStatus(String[] changeStr) {
        for (int i = 0; i < changeStr.length; i++) {
            textinfo[i].setText(changeStr[i]);
        }
    }

    /**
     * 设置顶部单选按钮状态
     */
    private void setRbtnStatus() {
        if (ChooseId == 0) {
            ChangeStatus(forPersonal);
            contactsname.setVisibility(View.GONE);
            sexType.setVisibility(View.VISIBLE);
            cardType.setText("");
            cusName.setText("");
            cardNum.setText("");
            cusPhone.setText("");
            contactsname.setText("");
            btnReadID.setVisibility(View.VISIBLE);
        } else {
            ChangeStatus(forCompany);
            contactsname.setVisibility(View.VISIBLE);
            sexType.setVisibility(View.GONE);
            cardType.setText("");
            cusName.setText("");
            cardNum.setText("");
            cusPhone.setText("");
            contactsname.setText("");
            btnReadID.setVisibility(View.INVISIBLE);
        }
    }


//    Handler btHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                int what = msg.what;
//                Log4jUtil.debug(this.getClass(), "btHandler what=" + what);
//                if (what == 1) {
//                    tvStatus.setTextColor(Color.GREEN);
//                    tvStatus.setText("已连接");
//
//                    Settings.saveIdCardDeviceMac(context, devMacAddress);
//                } else if (what == 2) {
//                    tvStatus.setTextColor(Color.RED);
//                    tvStatus.setText("中断");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };

    private boolean isConnected = false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (progressDialogIDCard != null) {
                        progressDialogIDCard.dismiss();
                        progressDialogIDCard = null;
                    }

                    if (msg.arg1 == 1) {
                        IdentityCardInfo idinfo = (IdentityCardInfo) msg.obj;
                        if (idinfo != null) {
                            Log.i("ID_TAG", "--->" + idinfo.getName());
                            cusName.setText(idinfo.getName());
                            cardNum.setText(idinfo.getNumber());
                        }
                    } else {
                        Toast.makeText(CusArchivingStepOne.this, "抱歉，身份证信息读取失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 2:
                    if (msg.arg1 == 1) {
                        isConnected = true;
                        Toast.makeText(CusArchivingStepOne.this, "蓝牙连接成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        isConnected = false;
                        Toast.makeText(CusArchivingStepOne.this, "蓝牙连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 个人建档
            case R.id.rbtn_warnperson:
                isPersonRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T030201, true);
                if (isPersonRight) {
                    ChooseId = 0;
                    setRbtnStatus();
                } else {
                    rbtnWarnCommon.setChecked(true);
                }
                cardTypeID = "01";
                cardType.setText("居民身份证");
                cardType.setBackgroundColor(Color.TRANSPARENT);
                break;

            // 对公建档
            case R.id.rbtn_warncommon:
                isCommonRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T030202, false);
                if (isCommonRight) {
                    ChooseId = 1;
                    setRbtnStatus();
                } else {
                    rbtnWarnPerson.setChecked(true);
                }
                cardTypeID = "";
                cardType.setText("");
                cardType.setBackgroundResource(R.drawable.cusarchiving_step_spinner_long);
                break;

            // 下一步
            case R.id.cusarchiving_step1_nextbtn:
                creatData(ChooseId);
                break;

            // 证件类型
            case R.id.cusarchiving_step1_cardtype:
                choseCardType();
                break;

            // 选择性别
            case R.id.cusarchiving_step1_sextype:
                SpinnerAdapter.showSelectDialog(this, NewCatevalue.CONTACT_SEX, sexType, new CallBackItemClickListener() {

                    @Override
                    public void CallBackItemClick(String spinnerID) {
                        sexTypeID = spinnerID;
                    }
                });
                break;

            // 读取身份证id
            case R.id.cusarchiving_step1_readID:
               /* BluetoothUtil btUtil = new BluetoothUtil(this);
                int btFlag = btUtil.getOpenFlag();
                //Toast.makeText(this, "---->>"+btFlag, Toast.LENGTH_SHORT).show();

                // 如果关闭则打开蓝牙
                if (btFlag == BluetoothUtil.OPENFLAG_CLOSE) {
                    Intent intent = new Intent(this, DeviceSettingActivity.class);
                    startActivity(intent);
                } else if (btFlag == BluetoothUtil.OPENFLAG_OPEN) {
                    if (isConnected) {
                        progressDialogIDCard = ProgressDialog.show(this, "请等待...", "读取证件中，请将二代证放在设备上。", true, false);
                        btt.readIDCardinfo(handler);
                    } else {
                        btt.setDeviceInfo("蓝牙链接", "00:15:83:1F:FE:42");
                        btt.connectBluetooth(handler);
                    }
                }*/
                OCRAnalysis analysis = new OCRAnalysis(this);
                analysis.takePhoto("IDCardinfoReaderActivity");
                break;
                default:
                    break;
        }
    }



    /**
     * 选择证件类型
     */
    private void choseCardType() {
        if (ChooseId == 0) {
        } else {
            SpinnerAdapter.showSelectDialog(this, NewCatevalue.C_CERTTP, cardType, new CallBackItemClickListener() {
                @Override
                public void CallBackItemClick(String spinnerID) {
                    cardTypeID = spinnerID;
                }
            });
        }
    }

}
