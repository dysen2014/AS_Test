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
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.bean.IDCheckResult;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardEntryActivity extends ParentActivity {


    @Bind(R.id.queue_code)
    TextView queueCode;
    @Bind(R.id.txt_close)
    TextView txtClose;
    @Bind(R.id.btn_blue_conn)
    Button btnBlueConn;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.edit_id)
    EditText editId;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.radio_master)
    RadioButton radioMaster;
    @Bind(R.id.radio_supplementary)
    RadioButton radioSupplementary;
    @Bind(R.id.radio_both)
    RadioButton radioBoth;
    @Bind(R.id.rgroup_apply_type)
    RadioGroup rgroupApplyType;
    @Bind(R.id.btn_get_id)
    Button btnGetId;
    @Bind(R.id.btn_check)
    Button btnCheck;

    private HashMap<String, String> hashMap = new HashMap<String, String>();
    private HashMap<String, Bitmap> bitMap = new HashMap<String, Bitmap>();
    private int deviceId = 1;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_entry);
        ButterKnife.bind(this);

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

    }


    List<String> list = new ArrayList<>();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub


            list.add("不知道");
            mBlueToothAdapter.cancelDiscovery();


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
                BlueUtils.ConnectDevice(deviceId, this, handler);
                break;
            case R.id.btn_get_id:
                BlueUtils.IdCardInfoGet(deviceId, this, handler, hashMap, bitMap);
                break;
        }
    }

    /**
     * @param i -1   关闭当前页 不关蓝牙
     *          -2   关闭当前页 关蓝牙
     *          1    读卡成功 返回
     */
    private void mySetResult(int i) {
        Intent intent = new Intent();
        if (i == 1) {
            if (applyType >= 0) {
                Bundle bundle = new Bundle();
                checkResult = setData(editName, editId);
                bundle.putSerializable("check", checkResult);
                intent.putExtras(bundle);

                setResult(i, intent);
                finish();
            } else
                toast("请选择申请类型");
        } else if (i == -2) {

            setResult(i, intent);
            finish();
        } else {

            setResult(i, intent);
            finish();
        }
    }

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

    private int applyType = -1;
    private IDCheckResult checkResult;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


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

        }


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

    };
}