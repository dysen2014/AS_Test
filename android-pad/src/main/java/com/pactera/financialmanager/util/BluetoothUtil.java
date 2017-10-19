package com.pactera.financialmanager.util;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.pactera.financialmanager.db.Settings;

import java.util.Set;

/**
 * Created by DengGuang on 2016/12/21.
 */
public class BluetoothUtil {

    public static final int OPENFLAG_OPEN = 1;//打开
    public static final int OPENFLAG_CLOSE = 0;//关闭
    public static final int OPENFLAG_NULL = -1;//不支持

    public static BluetoothTools btTools = null;

    private Context context;
    private Handler handler;

    public BluetoothUtil(Context context){
        this.context = context;
        btTools = new BluetoothTools(context);
    }

    public BluetoothUtil(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        btTools = new BluetoothTools(context);
    }

    /**
     * 检查是否有蓝牙模块
     * 如果blueadapter为null，表示不支持蓝牙
     *
     * @return
     */
    private BluetoothAdapter getBluetoothAdapter() {
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        return blueadapter;
    }


    public void disableBluetooth(){
        getBluetoothAdapter().disable();
    }

    /**
     * 判断蓝牙是否打开
     *
     * @return
     */
    public int getOpenFlag() {
        int openFlag = OPENFLAG_NULL;
        BluetoothAdapter blueadapter = getBluetoothAdapter();
        if (blueadapter != null) {
            boolean isOpen = blueadapter.isEnabled();
            if (isOpen) {
                openFlag = OPENFLAG_OPEN;
            } else {
                openFlag = OPENFLAG_CLOSE;
            }
        } else {
            Toast.makeText(context, "抱歉，您的设备无蓝牙功能.", Toast.LENGTH_SHORT).show();
        }
        return openFlag;
    }


    /**
     * 打开蓝牙模块
     */
    public void gotoOpenBluetooth() {
        // 启动配置蓝牙可见模式，即进入可配对模式
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(intent);
    }


    /**
     * 获取蓝牙适配器中已经配对的设备
     *
     * @return
     */
    public Set<BluetoothDevice> getDeviceLists() {
        BluetoothAdapter blueadapter = getBluetoothAdapter();
        if (blueadapter != null) {
            Set<BluetoothDevice> devices = blueadapter.getBondedDevices();
            return devices;
        }
        return null;
    }


    /**
     * 查找周围的蓝牙设备
     */
    public void findDevice() {
        BluetoothAdapter blueadapter = getBluetoothAdapter();
        if (blueadapter != null) {
            // 设置广播信息过滤
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
            intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            // 注册广播接收器，接收并处理搜索结果
            context.registerReceiver(receiver, intentFilter);
            // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
            blueadapter.startDiscovery();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Message msg = new Message();
                msg.arg1 = 1;
                msg.obj = device;
                handler.sendMessage(msg);
            }
        }
    };

}
