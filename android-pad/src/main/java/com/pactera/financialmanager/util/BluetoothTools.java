package com.pactera.financialmanager.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.guoguang.util.DeviceService;
import com.guoguang.util.IdentityCardInfo;

/**
 * 蓝牙使用功能
 * Created by DengGuang on 2016/12/22.
 */
public class BluetoothTools {

    public Context context;
    public DeviceService service = null;

    private String devName;     // 蓝牙名称
    private String devMacAddress;   // 蓝牙地址

    public BluetoothTools(Context context) {
        this.context = context;
    }


    /**
     * 设置设备信息
     * @param devName
     * @param devMacAddress
     */
    public void setDeviceInfo(String devName, String devMacAddress) {
        this.devName = devName;
        this.devMacAddress = devMacAddress;
    }

    /**
     * 链接蓝牙设备
     */
    public void connectBluetooth(final Handler mHandler) {
        service = new DeviceService();
        service.setArgs(context, devMacAddress, initHandler);
        new AlertDialog.Builder(context).
                setTitle("连接设备").
                setMessage("蓝牙背夹").
                setPositiveButton("连接", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog btProgressDialog = ProgressDialog.show(context, "请等待...", "连接外设中。。。。。。", true, false);
                        LogUtils.d("开始连接外设...");
                        new Thread() {
                            public void run() {
                                if (service.mBluetoothAdapter.isDiscovering()) {
                                    service.mBluetoothAdapter.cancelDiscovery();
                                }
                                if (service != null && service.isConnDevice) {
                                    service.disConnect();
                                }

                                boolean flag = service.connBlueTooth();
                                Message msg = new Message();
                                msg.what = 2;
                                if (flag) {
                                    msg.arg1 = 1;
                                } else {
                                    msg.arg1 = 0;
                                }
                                mHandler.sendMessage(msg);

                                if (btProgressDialog != null) {
                                    btProgressDialog.dismiss();
                                }
                            }
                        }.start();

                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }



    Handler initHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                int what = msg.what;
                LogUtils.d("MainActivity:initHandler what=" + what);
                if (what == 0xFF) {
                    new AlertDialog.Builder(context).
                            setTitle("驱动回调信息").
                            setMessage("what=0XFF").
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    LogUtils.d("MainActivity:initHandler what=0xFF");
                } else if (what == 0xFE) {
                    new AlertDialog.Builder(context).
                            setTitle("驱动回调信息").
                            setMessage("what=0XFE").
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    tvStatus.setTextColor(Color.RED);
//                                    tvStatus.setText("中断");
                                }
                            }).show();
                    LogUtils.d("MainActivity:initHandler what=0xFE");
                } else if (what == 0xEF) {
                    new AlertDialog.Builder(context).
                            setTitle("驱动回调信息").
                            setMessage("巡检发现蓝牙连接已断开").
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    tvStatus.setTextColor(Color.RED);
//                                    tvStatus.setText("中断");
                                }
                            }).show();
                    LogUtils.d("MainActivity:initHandler what=0xEF");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    /** ---------------------------------------------------------------------- */


    public void readIDCardinfo(Handler mHandler){
        MyIDThread myIDThread = new MyIDThread(mHandler);
        myIDThread.start();
    }


    class MyIDThread extends Thread {
        private Handler mHandler;
        public MyIDThread(Handler mHandler){
            this.mHandler = mHandler;
        }

        public void run() {
            IdentityCardInfo idinfo = null;
            if (service != null && service.isConnDevice) {
                idinfo = service.readIDCard();
            }

            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = 1;
            msg.obj = idinfo;
            mHandler.sendMessage(msg);
        }
    };

}
