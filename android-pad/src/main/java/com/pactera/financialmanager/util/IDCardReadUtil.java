package com.pactera.financialmanager.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import ccb.ygqd.centerm.device.ResultCode;
import ccb.ygqd.centerm.device.TransControl;
import ccb.ygqd.centerm.idcard.Idcard;

/**
 * Created by xh on 2015/11/30.
 */
public class IDCardReadUtil {

    public static final int ID_CARD_FIN_MSG = 108921;


    public static void read(final Activity context, final String macAddress, final int timeout, final Handler handler) {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "本机没有找到蓝牙硬件或驱动！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("正在连接，请稍候...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


        MyBtReceiver myBtReceiver = new MyBtReceiver(handler, timeout, pDialog);
        IntentFilter mBtFilter = new IntentFilter("android.ccb.ygqd.centerm.btconnectBroadcast");
        context.registerReceiver(myBtReceiver, mBtFilter);

        int ret = TransControl.getInstance().openDevice(2, context, macAddress);
        if (ret == ResultCode.DEVICE_PARAM_ERROR) {
            Toast.makeText(context, "传入参数错误", Toast.LENGTH_SHORT).show();
        } else if (ret == ResultCode.BLUETOOTH_ADAPTER_NOT_FIND) {
            Toast.makeText(context, "没找到本机蓝牙设配器", Toast.LENGTH_SHORT).show();
        } else if (ret == ResultCode.BLUETOOTH_MAC_ERROR) {
            Toast.makeText(context, "蓝牙MAC地址格式错误", Toast.LENGTH_SHORT).show();
        } else if (ret == ResultCode.BLUETOOTH_OPEN_FAIL) {
            Toast.makeText(context, "开启蓝牙失败或蓝牙已经开启", Toast.LENGTH_SHORT).show();
        } else if (ret == ResultCode.BLUETOOTH_DEVICE_NOT_FIND) {
            Toast.makeText(context, "没找到相应蓝牙设备", Toast.LENGTH_SHORT).show();
        } else if (ret != 0) {
            Toast.makeText(context, "蓝牙通讯建立失败", Toast.LENGTH_SHORT).show();
        }

        if (ret != ResultCode.DEVICE_SUCCESS) {
            context.unregisterReceiver(myBtReceiver);
            return;
        }
    }

    static class MyBtReceiver extends BroadcastReceiver {
        private Handler handler;

        private int timeout;

        private ProgressDialog dialog;

        public MyBtReceiver(Handler handler, int timeout, ProgressDialog dialog) {
            this.handler = handler;
            this.timeout = timeout;
            this.dialog = dialog;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("msg").equals("1")) {
                new Thread() {
                    public void run() {
                        String[] r = new Idcard().getIDCardInfo(String.valueOf(timeout));
                        Message msg = new Message();
                        msg.what = ID_CARD_FIN_MSG;
                        if ("0".equals(r[0])) {
                            IDInfo info = new IDInfo(
                                    r[1],
                                    r[2],
                                    r[3],
                                    r[4],
                                    r[5],
                                    r[6],
                                    r[7],
                                    r[8]
                            );
                            msg.obj = info;
                        } else {
                            msg.obj = r;
                        }
                        handler.sendMessage(msg);
                        TransControl.getInstance().closeDevice();
                        dialog.dismiss();
                    }
                }.start();

                context.unregisterReceiver(this);
            } else {
                dialog.dismiss();
                TransControl.getInstance().closeDevice();
                Toast.makeText(context, "蓝牙连接失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    static class BlutoothConnReceiver extends BroadcastReceiver {

        private Handler handler;

        private int timeout;

        public BlutoothConnReceiver(Handler handler, int timeout) {
            this.handler = handler;
            this.timeout = timeout;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            int state = 0;

            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            } else if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                new Thread() {
                    public void run() {
                        String[] r = new Idcard().getIDCardInfo(String.valueOf(timeout));

                        Message msg = new Message();
                        if ("0".equals(r[0])) {
                            IDInfo info = new IDInfo(
                                    r[1],
                                    r[2],
                                    r[3],
                                    r[4],
                                    r[5],
                                    r[6],
                                    r[7],
                                    r[8]
                            );

                            msg.what = ID_CARD_FIN_MSG;
                            msg.obj = info;

                        } else {
                            msg.obj = r;
                        }
                        handler.sendMessage(msg);
                    }
                }.start();

                context.unregisterReceiver(this);
            }
        }
    }

    public static class IDInfo {
        final public String name;
        final public String sex;
        final public String nation;
        final public String birthday;
        final public String address;
        final public String num;
        final public String government;
        final public String effectiveDay;


        public IDInfo(String name, String sex, String nation, String birthday, String address, String num, String government, String effectiveDay) {
            this.name = name;
            this.sex = sex;
            this.nation = nation;
            this.birthday = birthday;
            this.address = address;
            this.num = num;
            this.government = government;
            this.effectiveDay = effectiveDay;
        }
    }
}