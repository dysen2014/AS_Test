package com.pactera.financialmanager.ui.commontool;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.util.BluetoothUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by xh on 2015/12/1.
 */
public class DeviceSettingActivity extends ParentActivity {

    @ViewInject(R.id.imv_refresh)
    private ImageView imvRefresh;
    @ViewInject(R.id.lv_pulllist)
    private PullableListView lvBTDevices;

    private BluetoothUtil btUitl = null;
    private DevicesBTAdapter btAdapter = null;
    private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1 == 1) {
                BluetoothDevice device = (BluetoothDevice) msg.obj;
                if (device != null) {
                    devices.add(device);
                    btAdapter.setlist(devices);
                    btAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_settings);
        ViewUtils.inject(this);

        initTitle(this, "常用工具");
        btUitl = new BluetoothUtil(this, handler);

        btAdapter = new DevicesBTAdapter(this);
        lvBTDevices.setAdapter(btAdapter);
        refreshDevices();
    }


    /**
     * 刷新
     * @param view
     */
    public void onRefresh(View view){
        btUitl.findDevice();
    }


    /**
     * 刷新重新获取列表
     */
    private void refreshDevices(){
        int openFlag = btUitl.getOpenFlag();
        // 如果关闭则打开蓝牙
        if(openFlag == BluetoothUtil.OPENFLAG_CLOSE){
            btUitl.gotoOpenBluetooth();
        }

        // 如果打开则获取列表
        if(openFlag == BluetoothUtil.OPENFLAG_OPEN){
            // 清空列表
            devices.removeAll(devices);

            // 获取列表
            Set<BluetoothDevice> bts = btUitl.getDeviceLists();
            for(BluetoothDevice btDevice : bts){
                devices.add(btDevice);
            }
            btAdapter.setlist(devices);
            btAdapter.notifyDataSetChanged();
        }
    }



}
