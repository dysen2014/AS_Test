package com.pactera.financialmanager.ui.credit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.examples.outputjar.BlueUtils;
import com.examples.outputjar.ClsUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.bean.IDCheckResult;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dysen on 2017/7/24.
 */

public class IDCheck extends ParentActivity {

    @Bind(R.id.edit_id)
    EditText editId;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.rgroup_apply_type)
    RadioGroup rgroupApplyType;
    @Bind(R.id.radio_master)
    RadioButton radioMaster;
    @Bind(R.id.radio_supplementary)
    RadioButton radioSupplementary;
    @Bind(R.id.radio_both)
    RadioButton radioBoth;
    @Bind(R.id.queue_code)
    TextView queueCode;
    @Bind(R.id.txt_close)
    TextView txtClose;
    @Bind(R.id.btn_check)
    Button btnCheck;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_blue_conn)
    Button btnBlueConn;
    @Bind(R.id.btn_get_id)
    Button btnGetId;

    private int searchGuoguang, searchShensi, searchWoqi;
    private int bondedGuoguang, bondedShensi, bondedWoqi;
    private int neededConGuoguang, neededConShensi, neededConWoqi;
    private int deviceId;

    private final int DEVICE_GUOGUANG_ID = 1;
    private final int DEVICE_SHENSI_ID = 2;
    private final int DEVICE_WOQI_ID = 3;
    private HashMap<String, String> hashMap = new HashMap<String, String>();
    private HashMap<String, Bitmap> bitMap = new HashMap<String, Bitmap>();
    private String deviceName;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_id);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        receiverRegister();
//        getBondedDevices();
        searchDevices();
    }

    private void receiverRegister() {
        // TODO Auto-generated method stub
        //注册用以接收到已搜索到的设备设备的receiver
        IntentFilter mFilter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);//搜索发现设备
        registerReceiver(mReceiver, mFilter1);
        //注册搜索完成时的receiver
        mFilter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter1);

        IntentFilter mFilter2 = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
//        registerReceiver(pReceiver, mFilter2);
    }

    private void setButtonEnable() {
        LogUtils.d("searchShensi:" + searchShensi + "\tbondedShensi:" + bondedShensi);
        if (searchGuoguang == 1 && bondedGuoguang == 1) {
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = 2;
//            mHandler.sendMessage(msg);
        }

        if (searchWoqi == 1 && bondedWoqi == 1) {
            Message msg = new Message();
            msg.what = 3;
            mHandler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = 4;
//            mHandler.sendMessage(msg);
        }

        if (searchShensi == 1 && bondedShensi == 1) {
            Message msg = new Message();
            msg.what = 5;
            mHandler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = 6;
//            mHandler.sendMessage(msg);
        }
    }

    private void searchDevices() {

        if (mBlueToothAdapter.isDiscovering()) {
            toast("正在扫描设备！");
            mBlueToothAdapter.cancelDiscovery();
        }
        mBlueToothAdapter.startDiscovery();
    }

    int temp;
    private Handler handlerState = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                toast("背夹设备已断开失败！");
            } else if (msg.what == 0) {
                toast("背夹设备已断开！");
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 1) {
                deviceId = DEVICE_GUOGUANG_ID;
            } else if (msg.what == 2) {
                temp = 0;
            } else if (msg.what == 3) {
                deviceId = DEVICE_WOQI_ID;
            } else if (msg.what == 4) {
                temp = 0;
            } else if (msg.what == 5) {
                deviceId = DEVICE_SHENSI_ID;
            } else if (msg.what == 6) {
                temp = 0;
            }

            LogUtils.d(msg.what + "\tdeviceId:" + deviceId);
            if (deviceId > 0) {
                SystemClock.sleep(1000);

                BlueUtils.ConnectDevice(deviceId, IDCheck.this, handler);
//                BlueUtils.IdCardInfoGet(deviceId, IDCheck.this, handler, hashMap, bitMap);
            } else {
                LogUtils.d("deviceId error");
            }
        }
    };

    boolean nxFlag;
    private String tempPin;
    private String tempAddress;
                List<String> list = new ArrayList<>();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();

                boolean nxBlueFlag1 = false, nxBlueFlag2 = false;
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String temp = device.getName();
                LogUtils.d(temp + "\tdevice===" + device);
                if (temp == null) {
                    temp = "";
                }
                if (temp.contains("P3520") || temp.contains("GEIT") || temp.contains("SS-")) {
                    deviceName = temp;
                    list.add(deviceName);
                    mBlueToothAdapter.cancelDiscovery();
                    nxBlueFlag1 = true;
                    //搜索到的不是已经配对的设备设备
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

                        nxBlueFlag2 = true;
                    } else {

                        nxBlueFlag2 = false;
                        String address = device.getAddress();
                        tempAddress = address;

                        try {
                            if (temp.contains("P3520")) {
                                searchWoqi = 1;
                                tempPin = "1235";
                                if (neededConWoqi == 1) {
                                    toast("握奇设备已配对！");
                                } else {
//                                getDevices(address);
                                }
                            } else if (temp.contains("GEIT")) {
                                searchGuoguang = 1;
                                tempPin = "1234";
                                if (neededConGuoguang == 1) {
                                    toast("国光设备已配对！");
                                } else {
//                                getDevices(address);
                                }
                            } else if (temp.contains("SS-")) {
                                searchShensi = 1;
                                if (neededConShensi == 1) {
                                    toast("神思设备已配对！");
                                } else {
//                                getDevices(address);
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        LogUtils.i("---广播---" + temp + "\nneededConWoqi:" + neededConWoqi +
                                "\tneededConGuoguang:" + neededConGuoguang + "\tneededConShensi:" + neededConShensi);
//                setButtonEnable();
                        getBondedDevices();
                    }
                    if (nxBlueFlag2 && nxBlueFlag1) {
                        final DialogAlert dialog = ShowDialog(context, "设备未配对，请去系统里配对设备！");
                        Button btn = (Button) dialog.getWindow().findViewById(R.id.btn_ok);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                                dialog.close();
                                mySetResult(-1);
                            }
                        });
                    }
                }else {
                    LogUtils.d("other devices");
                }

            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {

                setProgressBarIndeterminate(false);
                LogUtils.d(nxBlueFlag1+"scan finish"+list.size());
                if (list.size()== 0) {
                    LogUtils.d("is devices");
                if (!nxBlueFlag1){//非背夹设备
                        nxBlueFlag1 = false;
                        if (!nxBlueFlag1){
                            final DialogAlert dialog = ShowDialog(context, "未能找到背夹设备！");
                            Button btn = (Button) dialog.getWindow().findViewById(R.id.btn_ok);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.close();
                                    mySetResult(-1);
                                }
                            });
                        }
                    }
                }
            } else {
                LogUtils.d("Other");
            }
        }
    };

    @OnClick({R.id.txt_close, R.id.btn_check, R.id.btn_cancel, R.id.btn_blue_conn, R.id.btn_get_id})
    public void onViewClicked(View view) {
        LogUtils.d("deviceId----:" + deviceId);
        switch (view.getId()) {
            case R.id.txt_close:
                mySetResult(-2);
                break;
            case R.id.btn_check:
                mySetResult(1);
                break;
            case R.id.btn_cancel:
                mySetResult(-2);
                break;
            case R.id.btn_blue_conn:
                BlueUtils.ConnectDevice(deviceId, IDCheck.this, handler);
                break;
            case R.id.btn_get_id:
                BlueUtils.IdCardInfoGet(deviceId, IDCheck.this, handler, hashMap, bitMap);
                break;
        }
    }

    /**
     *
     * @param i
     * -1   关闭当前页 不关蓝牙
     * -2   关闭当前页 关蓝牙
     * 1    读卡成功 返回
     */
    private void mySetResult(int i) {
//        disconn(IDCheck.this, handlerState);
        Intent intent = new Intent();
        if (i == 1) {
            if (applyType >= 0) {
                checkResult = setData(editName, editId);
                Bundle bundle = new Bundle();
                bundle.putSerializable("check", checkResult);
                intent.putExtras(bundle);
                if (mBlueToothAdapter.enable()) {
                    mBlueToothAdapter.disable();//关闭设备连接
                }
                setResult(i, intent);
                finish();
            } else
                toast("请选择申请类型");
        } else if(i == -2){
            if (mBlueToothAdapter.enable()) {
                mBlueToothAdapter.disable();//关闭设备连接
            }
            setResult(i, intent);
            finish();
        }else {

            setResult(i, intent);
            finish();
        }
    }

    private BroadcastReceiver pReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            BluetoothDevice btDevice = mBlueToothAdapter.getRemoteDevice(tempAddress);
            try {
                ClsUtils.setPin(btDevice.getClass(), btDevice, tempPin);

                //取消用户输入
//                ClsUtils.cancelPairingUserInput(btDevice.getClass(), btDevice);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取设备适配器中已配对的设备
     */
    private void getBondedDevices() {

        Set<BluetoothDevice> devices = mBlueToothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {

                String temp = bluetoothDevice.getName();

                if (temp.equals(deviceName)){
                    LogUtils.i("---广播2---" + temp + "\nsearchWoqi:" + searchWoqi +
                            "\tsearchGuoguang:" + searchGuoguang + "\tsearchShensi:" + searchShensi);
                    if (temp.contains("P3520") && searchWoqi == 1) {
                        bondedWoqi = 1;
                        //已配对，不需要重新连接
                        neededConWoqi = 1;
                        toast("握奇设备已配对2！");
                        setButtonEnable();
                    } else if (temp.contains("GEIT") && searchGuoguang == 1) {
                        bondedGuoguang = 1;
                        //已配对，不需要重新连接
                        neededConGuoguang = 1;
                        toast("国光设备已配对2！");
                        setButtonEnable();
                    } else if (temp.contains("SS-") && searchShensi == 1) {
                        bondedShensi = 1;
                        //已配对，不需要重新连接
                        neededConShensi = 1;
                        toast("神思设备已配对2！");
                        setButtonEnable();
                    }
                }
            }
        }
    }

    private int applyType = -1;
    private IDCheckResult checkResult;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            LogUtils.d("---msg.what:" + msg.what);
            if (msg.what == 0) {
                toast(deviceName + "设备连接成功");
                SystemClock.sleep(500);
                btnGetId.setEnabled(true);
                btnGetId.setBackgroundResource(R.drawable.qy_btn_red_check);
//                BlueUtils.IdCardInfoGet(deviceId, IDCheck.this, handler, hashMap, bitMap);
            } else if (msg.what == 1) {
//                toast("设备连接失败");

                btnGetId.setEnabled(false);
                btnGetId.setBackgroundResource(R.color.transparent);
                final DialogAlert dialog = ShowDialog(IDCheck.this, deviceName+"设备连接失败！");

                Button btn = (Button) dialog.getWindow().findViewById(R.id.btn_ok);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.close();
                        mySetResult(-2);
                    }
                });
            } else if (msg.what == 2) {
                toast("证件信息获取成功");
//                .setImageBitmap(bitMap.get("icon"));
                editName.setText(hashMap.get("name"));
                editId.setText(hashMap.get("idNo"));
                btnCheck.setEnabled(true);
                btnCheck.setBackgroundResource(R.drawable.qy_btn_red_check);
                LogUtils.d("name:" + hashMap.get("name") + "\tidNo:" + hashMap.get("idNo") + "\taddr:" + hashMap.get("idAddr"));

                rgroupApplyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                        int chooseId = rgroupApplyType.getCheckedRadioButtonId();

                        switch (chooseId) {
                            case R.id.radio_master:
                                applyType = 0;
                                break;
                            case R.id.radio_supplementary:
                                applyType = 1;
                                break;
                            case R.id.radio_both:
                                applyType = 2;
                                break;
                            default:
                                toast("请选择申请类型");
                                applyType = -1;
                                break;
                        }

                    }
                });

//                text2.setText(hashMap.get("sex"));
//                text3.setText(hashMap.get("nation"));
//                text4.setText(hashMap.get("birthday"));
//                text5.setText(hashMap.get("idAddr"));
//                text7.setText(hashMap.get("idOrg"));
//                text8.setText(hashMap.get("beginDate"));
//                text9.setText(hashMap.get("endDate"));
            } else if (msg.what == 3) {
                toast("证件信息获取失败");
            } else if (msg.what == 4) {
                toast("IC卡信息获取成功");
//                IDCardNo.setText(hashMap.get("cardNo"));
            } else if (msg.what == 5) {
                toast("IC卡信息获取失败");
            } else if (msg.what == 6) {
                toast("磁条卡信息获取成功");
//                IDCardNo.setText(hashMap.get("cardNo"));
            } else if (msg.what == 7) {
                toast("磁条卡信息获取失败");
            } else if (msg.what == 8) {
                toast("密码信息获取成功");
//                passwordText.setText(hashMap.get("password"));
            } else if (msg.what == 9) {
                toast("密码信息获取失败");
            } else if (msg.what == 10) {
                toast("指纹信息获取成功");
                toast(hashMap.get("fingerKey"));
            } else if (msg.what == 11) {
                toast("指纹信息获取失败");
            }
        }
    };

    private IDCheckResult setData(EditText editName, EditText editId) {

        String id = editId.getText().toString();
        String name = editName.getText().toString();
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name)) {
            toast("身份证 姓名都不能为空");
            return null;
        }
        IDCheckResult checkResult = new IDCheckResult(true, id, name,
                "PN00C7BH", applyType);

        return checkResult;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mReceiver);
//        unregisterReceiver(pReceiver);
//        if (mBlueToothAdapter.enable()) {
//            mBlueToothAdapter.disable();//关闭设备连接
//        }
    }

//    iMateInfo imf;
//    public void disconn(Context context, final Handler handler){
//        imf = new iMateInfo(context, iMateInfo.Operate.DISCON_BLUETOOTH, new iMateInfo.iMateCallback() {
//            @Override
//            public void onSucceed(Bundle bundle) {
//                String state = bundle.getString(iMateInfo.MESSAGE);
//                toast("" + state);
//                Message msg1 = new Message();
//                msg1.what = 0;
//                handler.sendMessage(msg1);
//            }
//
//            @Override
//            public void onError(Bundle bundle) {
//                toast("error");
//                Message msg1 = new Message();
//                msg1.what = 1;
//                handler.sendMessage(msg1);
//            }
//        });
//        imf.startImate();
//    }
}
