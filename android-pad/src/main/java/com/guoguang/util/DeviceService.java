package com.guoguang.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.pactera.financialmanager.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tongy 20140320 设备管理类
 */
public class DeviceService extends BluetoothService implements Serializable {

    private static final String IOERROR = "连接异常，请检查后重新连接！";
    private static final long serialVersionUID = 1L;
    private Context context = null;
    private String DIRPATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    public static boolean isConnDevice = false;
    /* 外设相关 */
    public BluetoothChatService mChatService = null;
//    private Fingerprint fingerprint = null;
    private ParseSFZ parseIDCard = null;
//    private DrvMagCard drvMagCard = null;
//    private DrvPrinter drvPrinter = null;
//    private DrvPinPad drvPinPad = null;
//    private DrvICCard drvICCard = null;
//    private DrvUpdate drvUpdate = null;
    private ParseSFZ.People peopleMsg = null;
    private boolean flagPrinter = false;
    public String macAddr;
    public String deviceName;
    private Handler initBTHandler;
    static boolean usedFlag = false;

    public DeviceService() {
        // 使用set的方式注入参数
        isConnDevice = false;
    }

    /**
     * 监测外设交互线程 超过一定时间被监视线程不执行完 断开蓝牙socket连接 防止外设出问题 read阻塞
     * 调用外设方法一定要新建线程 远程服务默认执行线程 是个线程池 方法返回线程不会退出
     *
     * @author hujin
     */
    private class TimeoutTask extends TimerTask {
        private Thread timeoutThread = null;
        private Timer timer = new Timer();

        public TimeoutTask(Thread timeoutThread) {
            this.timeoutThread = timeoutThread;
            Log.d("currentThread_id", "currentThread_id:" + timeoutThread.getId());
        }

        @Override
        public void run() {
            /**
             * 测试结果 远程方法调用返回后线程一直没有死
             * 所以aidl实现应该有维护一个内部的线程池 根据测试结果 线程数量为3个
             * 所以这个超市异常不能正常实现 read阻塞 一定时间后中断 远程方法调用线程
             *
             * 所以要在线程池的线程中开新线程执行外设调用任务 监视那个线程
             */
            if (timeoutThread.isAlive()) {
                disConnect();
            }
        }

        public void start(int timemillion) {
            timer.schedule(this, timemillion);
        }
    }

    @Override
    public void setArgs(Context context, String macAddr, Handler initBTHandler) {
        this.context = context;
        this.macAddr = macAddr;
        this.initBTHandler = initBTHandler;
        initHandler();
    }

    /**
     * 连接蓝牙外设，连接前需要给macAddr赋值，其值为蓝牙外设的mac地址
     */
    @Override
    public boolean connBlueTooth() {
        try {
            LogUtils.d("",
                    "DeviceService:--connBlueTooth()--macAddr:" + macAddr);

            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddr);
            // mChatService = new BluetoothChatService(mHandler);
            mChatService = new BluetoothChatService(null);
            long stime = System.currentTimeMillis();
            long etime = 0L;
            while (true) {
                if (isConnDevice)
                    break;
                etime = System.currentTimeMillis();
                if ((etime - stime) / 1000 > 10)
                    break;
                try {
                    mChatService.connect(device);
                    isConnDevice = true;
                } catch (IOException e) {
                    LogUtils.d("",
                            "DeviceService:--connect--IOException:"
                                    + e.getMessage());
                    e.printStackTrace();
                    isConnDevice = false;
                }
                Thread.sleep(1000);
            }
            LogUtils.d("",
                    "DeviceService:--connBlueTooth()--connect end :"
                            + isConnDevice);
        } catch (Exception e) {
            LogUtils.d("",
                    "DeviceService:--connBlueTooth()--Exception:"
                            + e.getMessage());
            e.printStackTrace();
            isConnDevice = false;
            return false;
        }
        return isConnDevice;
    }

    /**
     * 断开蓝牙连接
     */
    public void disConnect() {
        try {
//            this.parseIDCard = null;
//            this.fingerprint = null;
//            this.drvMagCard = null;
//            this.drvPinPad = null;
//            this.drvICCard = null;
//            this.drvPrinter = null;
            mChatService.disconnect();
            LogUtils.d("",
                    "DeviceService:--disConnect()--disconnect end");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isConnDevice = false;
    }

    // ---------------------------------------------------------------------------------------------------------------------

    /**
     * 读磁条卡
     *
     * @return
     */
//    public MagCardInfo readMAGCard() {
//        //25秒后当前线程没返回 断开蓝牙连接
//        //new TimeoutTask(Thread.currentThread()).start(25000);
//        MagCardInfo magcardInfo = new MagCardInfo();
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvMagCard == null) {
//                drvMagCard = new DrvMagCard(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvMagCard"
//                    + drvMagCard);
//            magcardInfo = drvMagCard.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            magcardInfo.setResult(false);
//            magcardInfo.setResultMsg(IOERROR);
//            return magcardInfo;
//        } finally {
//            usedFlag = false;
//        }
//        Log4jUtil.debug(this.getClass(),
//                "DeviceService:--readMAGCard()--magcardInfo:" + magcardInfo);
//        return magcardInfo;
//    }

    // ------------------------------------------------------------------------------------------------------------------

    /**
     * 读ic卡
     *
     * @param type 1 接触 2 非接
     * @return
     */
//    public ICCardInfo readICCard(int type) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(25000);
//        ICCardInfo iccardInfo = new ICCardInfo();
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvICCard == null) {
//                drvICCard = new DrvICCard(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvICCard"
//                    + drvICCard);
//            iccardInfo = drvICCard.read(type);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            iccardInfo.setResult(false);
//            iccardInfo.setResultMsg(IOERROR);
//            return iccardInfo;
//        } finally {
//            usedFlag = false;
//        }
//        Log4jUtil.debug(this.getClass(),
//                "DeviceService:--readICCard()--iccardInfo:" + iccardInfo);
//        return iccardInfo;
//    }

    /**
     * 读取ic卡arqc
     *
     * @param type    1 接触 2 非接
     * @param AIDList 读卡参数说明 详见 ic卡.doc 测试时可使用"A0000003330101";
     * @param tagList 读卡参数说明 详见 ic卡.doc 测试时可使用"AJL";
     * @param txData  读卡参数说明 详见 ic卡.doc 测试时可使用"P012000000000001Q012000000000000R0040156S006141009T002U006112406W004GZNX";
     * @return 最新测试结果 连续读3次arqc底层c就会报错导致程序崩溃，应该改底座程序有关系，当前底座支持新的底座读arqc的代码
     */
//    public ICCardInfo readICCardArqc(int type, String AIDList, String tagList,
//                                     String txData) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(25000);
//        ICCardInfo iccardInfo = new ICCardInfo();
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvICCard == null) {
//                drvICCard = new DrvICCard(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvICCard"
//                    + drvICCard);
//            String result = drvICCard.readICCardArqc(type, AIDList, tagList, txData);
//            //ajk 返回分别为 卡号 序列号 二磁道
//            if (result.indexOf("success") > -1) {
//                String[] resultArray = result.split(";");
//                for (int i = 0; i < tagList.length(); i++) {
//                    char c = tagList.charAt(i);
//                    String rs = resultArray[i + 1];
//                    if ('level_a' == c) {
//                        iccardInfo.setId(rs);
//                    } else if ('J' == c) {
//                        iccardInfo.setSerial(rs);
//                    } else if ('L' == c) {
//                        iccardInfo.setTrack2(rs);
//                    }
//                }
//                iccardInfo.setArqc(resultArray[resultArray.length - 1]);
//                iccardInfo.setResult(true);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            iccardInfo.setResult(false);
//            iccardInfo.setResultMsg(IOERROR);
//            return iccardInfo;
//        } finally {
//            usedFlag = false;
//        }
//        Log4jUtil.debug(this.getClass(),
//                "DeviceService:--readICCard()--iccardInfo:" + iccardInfo);
//        return iccardInfo;
//    }

    /**
     * 读PSAM卡
     *
     * @return
     */
//    public String readPSAMCard() {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(25000);
//        String result = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvICCard == null) {
//                drvICCard = new DrvICCard(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvICCard"
//                    + drvICCard);
//            result = drvICCard.readPSAM();
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return "上电失败";
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    //ic卡新接口

    /**
     * @param readType 0 接触 1非接
     * @param aidList  "A000000333010101"
     * @param txData   "P012000000000100Q012000000000100R003156S00820150706T00299U006111728W006taobao"
     * @return
     */
//    public String getArqc(int readType, String aidList, String txData) {
//        String result = null;
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvICCard == null) {
//                drvICCard = new DrvICCard(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvICCard"
//                    + drvICCard);
//            result = drvICCard.getArqc(readType, aidList, txData);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * @param readType 0 接触 1非接
     * @param aidList  "A000000333010101"
     * @param tagList  "ABCDEFGHIJ";
     * @return
     */
//    public ICCardInfo getIcCardInfo(int readType, String aidList, String tagList) {
//        ICCardInfo icCardInfo = new ICCardInfo();
//        String result = null;
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvICCard == null) {
//                drvICCard = new DrvICCard(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvICCard"
//                    + drvICCard);
//            result = drvICCard.getIcCardInfo(readType, aidList, tagList);
//            if (result.startsWith(ResultUtil.SUCCESS)) {
//                icCardInfo.setResult(true);
//                String userInfo = result.split(ResultUtil.SPLIT)[1];
//                icCardInfo.setUserInfo(userInfo);
//                Map<String, String> userInfoMap;
//                userInfoMap = MessageUtils.parseIcUserInfo2(CHexConver.hexStringToBytes(userInfo.substring(2)));
//                icCardInfo.setTrack2(userInfoMap.get("E"));
//                icCardInfo.setId(userInfoMap.get("level_a"));
//                icCardInfo.setSerial(userInfoMap.get("J"));
//            } else {
//                icCardInfo.setResult(false);
//                icCardInfo.setResultMsg("读卡失败！");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//            icCardInfo.setResult(false);
//            icCardInfo.setResultMsg(IOERROR);
//        } finally {
//            usedFlag = false;
//        }
//        return icCardInfo;
//    }


    // ------------------------------------------------------------------------------------------------------------------
//    public boolean clearPinpad() throws IOException {
//        if (drvPinPad == null) {
//            drvPinPad = new DrvPinPad(mChatService);
//        }
//        return drvPinPad.clear();
//    }

    /**
     * 初始化密码键盘
     *
     * @param primaryKey 主密钥
     * @param workKey    工作密钥
     * @return
     */
//    public String initEpinpad(String primaryKey, String workKey) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        //new TimeoutTask(Thread.currentThread()).start(25000);
//        String result = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return "";
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPinPad"
//                    + drvPinPad);
//            result = drvPinPad.initEpinpad(primaryKey, workKey);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 获取mackey
     *
     * @param workKey 工作密钥
     * @return mackey
     */
//    public String getMackey(String workKey) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(25000);
//        String result = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return "";
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPinPad"
//                    + drvPinPad);
//            result = drvPinPad.getEPinpadMackey(workKey);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 获取pinkey
     *
     * @param workKey
     * @return pinkey
     */
//    public String getPinkey(String workKey) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(25000);
//        String result = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return "";
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPinPad"
//                    + drvPinPad);
//            result = drvPinPad.getEPinpadPinkey(workKey);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 读取密码键盘
     *
     * @param type 加密类型：1、des加密，2、rsa加密
     * @return
     */
//    public String getPin(int type) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        //new TimeoutTask(Thread.currentThread()).start(25000);
//        String oripwd = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return "";
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPinPad"
//                    + drvPinPad);
//            if (type == 1) {
//                oripwd = drvPinPad.getPin();
//            } else {
//                oripwd = drvPinPad.getPinRSA();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return oripwd;
//    }

//    public String getPinBlock(String keyname, String cryptdata) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        //new TimeoutTask(Thread.currentThread()).start(25000);
//        String oripwd = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return "";
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPinPad"
//                    + drvPinPad);
//            oripwd = drvPinPad.getPinBlock(keyname, cryptdata);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return oripwd;
//    }

//    public String getPinBlockDouble(String keyname, String cryptdata) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(25000);
//        String oripwd = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return "";
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPinPad"
//                    + drvPinPad);
//            oripwd = drvPinPad.getPinBlockDouble(keyname, cryptdata);
//        } catch (Exception e) {
//            e.printStackTrace();
//            disConnect();
//            return IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return oripwd;
//    }

//    public boolean setPublicKey(String publicKey) {
//        //25秒后当前线程没返回 断开蓝牙连接
//        boolean result = false;
//        try {
//            if (usedFlag) {
//                return false;
//            }
//            usedFlag = true;
//            if (drvPinPad == null) {
//                drvPinPad = new DrvPinPad(mChatService);
//            }
//            result = drvPinPad.setPublicKey(publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//            disConnect();
//            return false;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    // -------------------------------------------------------------------------------------------------------------------

    /**
     * 蓝牙外设自带打印机打印
     *
     * @param data 需要打印的数据
     * @return
     */
//    public boolean printerData(String data) {
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return false;
//            }
//            usedFlag = true;
//            if (drvPrinter == null) {
//                drvPrinter = new DrvPrinter(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPrinter"
//                    + drvPrinter);
//            flagPrinter = drvPrinter.printData(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return false;
//        } finally {
//            usedFlag = false;
//        }
//        Log4jUtil.debug(this.getClass(),
//                "DeviceService:--printerData()--flagPrinter:" + flagPrinter);
//        return flagPrinter;
//    }
//
//    public boolean printerRows(int number) {
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return false;
//            }
//            usedFlag = true;
//            if (drvPrinter == null) {
//                drvPrinter = new DrvPrinter(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--drvPrinter"
//                    + drvPrinter);
//            flagPrinter = drvPrinter.printRows(number);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return false;
//        } finally {
//            usedFlag = false;
//        }
//        Log4jUtil.debug(this.getClass(),
//                "DeviceService:--printerRows()--flagPrinter:" + flagPrinter);
//        return flagPrinter;
//    }

    // --------------------------------------------------------------------------------------------------------------------

    /**
     * 读二代证
     */
    public IdentityCardInfo readIDCard() {
        //25秒后当前线程没返回 断开蓝牙连接
        //new TimeoutTask(Thread.currentThread()).start(25000);
        try {
            if (usedFlag) {
                LogUtils.d("",  "DeviceService:多击返回.");
                return null;
            }
            usedFlag = true;
            if (parseIDCard == null) {
                parseIDCard = new ParseSFZ(mChatService);
            }
            LogUtils.d("",
                    "DeviceService:--readIDCard()--parseIDCard:" + parseIDCard);
            peopleMsg = parseIDCard.read();
        } catch (IOException e) {
            e.printStackTrace();
            disConnect();
            IdentityCardInfo identityCardInfo = new IdentityCardInfo();
            identityCardInfo.setResult(false);
            identityCardInfo.setResultMsg(IOERROR);
            return identityCardInfo;
        } finally {
            usedFlag = false;
        }
        LogUtils.d("",
                "DeviceService:--readIDCard()--peopleMsg:" + peopleMsg);
        return getIdentityCardInfo(peopleMsg);
    }

    /**
     * 解析二代证数据
     *
     * @param peopleMsg
     * @return
     */
    public IdentityCardInfo getIdentityCardInfo(ParseSFZ.People peopleMsg) {
        IdentityCardInfo idCardInfo = new IdentityCardInfo();
        if (!"success".equals(peopleMsg.getResultMsg())) {
            idCardInfo.setResult(false);
            idCardInfo.setResultMsg(peopleMsg.getResultMsg());
            return idCardInfo;
        }

        String name = peopleMsg.getPeopleName(); // 姓名
        String sex = peopleMsg.getPeopleSex(); // 性别
        String nation = peopleMsg.getPeopleNation(); // 民族
        String birthday = peopleMsg.getPeopleBirthday();
        String address = peopleMsg.getPeopleAddress(); // 地址
        String number = peopleMsg.getPeopleIDCode();// 身份证号码
        String department = peopleMsg.getDepartment();
        String startDate = peopleMsg.getStartDate();
        String endDate = peopleMsg.getEndDate();
        byte[] photo = peopleMsg.getPhoto();

        LogUtils.d("",
                "DeviceService:--getIdentityCardInfo()--name:" + name);
        LogUtils.d("",
                "DeviceService:--getIdentityCardInfo()--birthday:" + birthday);
        LogUtils.d("",
                "DeviceService:--getIdentityCardInfo()--startDate:"
                                + startDate);
        LogUtils.d("",
                "DeviceService:--getIdentityCardInfo()--endDate:" + endDate);
        LogUtils.d("",
                "DeviceService:--getIdentityCardInfo()--photo:" + photo);

        idCardInfo.setResult(true);
        idCardInfo.setName(name);
        idCardInfo.setSex(sex);
        idCardInfo.setNation(nation);
        idCardInfo.setAddress(address);
        idCardInfo.setIssueDepartment(department);
        idCardInfo.setNumber(number);
        idCardInfo.setPhoto(photo);
        idCardInfo.setStartDate(startDate);
        idCardInfo.setEndDate(endDate);
        try {
            if (birthday != null && birthday.length() == 8) {
                idCardInfo.setBirth(DateFormat1(birthday));
            } else {
                return null;
            }
            if (endDate != null && endDate.length() == 10) {// 可能是长期
                idCardInfo.setUsefulLifeEnd(DateFormat(endDate));
            } else {// 长期处理
                endDate = "2099:01:01";
                idCardInfo.setUsefulLifeEnd(DateFormat(endDate));
            }
            if (startDate != null && startDate.length() == 10) {
                idCardInfo.setUsefulLifeStart(DateFormat(startDate));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return idCardInfo;
    }

    // --------------------------------------------------------------------------------------------------------------------

    /**
     * cj1000b上的指纹还未实现该方法 暂时不能调用 有需求再实现
     */
    @Override
    public Bitmap readFingerPrint() {
//        TimerTask task = new TimerTask() {
//            public void run() {
//                Message message = new Message();
//                message.what = 2;
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, 20000, 1000000);
//        if (usedFlag) {
//            Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//            return null;
//        }
//        usedFlag = true;
//        byte[] b;
//        try {
//            if (fingerprint == null) {
//                fingerprint = new Fingerprint(mChatService);
//            }
//            b = fingerprint.PSUpImage();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//            disConnect();
//            usedFlag = false;
//            return null;
//        }
//        String pathDir = DIRPATH + File.separator + "pingan";
//        Log4jUtil.debug(getClass(), "内置存储空间目录：" + DIRPATH + ";驱动资源目录："
//                + pathDir);
//        File myFile = new File(pathDir);
//        if (!myFile.exists()) {
//            Log4jUtil.debug(getClass(), "创建pingan文件夹");
//            boolean flag = myFile.mkdirs();
//            Log4jUtil.debug(getClass(), "创建pingan文件夹结果：" + flag);
//        }
//        String fileName = DIRPATH + File.separator + "pingan" + File.separator
//                + "fingerprint.bmp";
//        File fileTmp = new File(fileName);
//        if (fileTmp.exists())
//            fileTmp.delete();
//
//        if (b == null) {
//            Log4jUtil.debug(getClass(), "step ERR:指纹仪读取指纹数据失败！");
//            usedFlag = false;
//            return null;
//        } else {
//            try {
//                byte[] file = fileName.getBytes();
//                byte[] file1 = new byte[200];
//                System.arraycopy(file, 0, file1, 0, file.length);
//                JniCallTest.writeBMP(file1, b, 152, 200);
//                FileInputStream fin = null;
//                fin = new FileInputStream(fileName);
//                int length = fin.available();
//                byte[] buffer = new byte[length];
//                fin.read(buffer);
//                fin.close();
//                usedFlag = false;
//                return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log4jUtil.debug(getClass(), "step ERR:指纹数据生成指纹图片失败！");
//                usedFlag = false;
                return null;
//            }
//        }
    }

    /**
     * 获取指纹仪内核信息
     *
     * @return 返回指纹仪内核信息
     */
//    public String getFingerInfo() {
//        //25秒后当前线程没返回 断开蓝牙连接
//        new TimeoutTask(Thread.currentThread()).start(35000);
//        if (usedFlag) {
//            Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//            return "";
//        }
//        usedFlag = true;
//        String result = "";
//        try {
//            if (fingerprint == null) {
//                fingerprint = new Fingerprint(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--fingerprint"
//                    + fingerprint);
//            result = fingerprint.getInfo();
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 注册指纹 需要连续按3次
     *
     * @return 返回hexString指纹特征值
     */
//    public String registFinger() {
//        //25秒后当前线程没返回 断开蓝牙连接
//        //new TimeoutTask(Thread.currentThread()).start(25000);
//        if (usedFlag) {
//            Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//            return "";
//        }
//        usedFlag = true;
//        String result = "";
//        try {
//            if (fingerprint == null) {
//                fingerprint = new Fingerprint(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--fingerprint"
//                    + fingerprint);
//            result = fingerprint.regist();
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 读取指纹
     *
     * @return hexString形式指纹数据
     */
//    public String readFinger() {
//        //25秒后当前线程没返回 断开蓝牙连接
//        //new TimeoutTask(Thread.currentThread()).start(25000);
//        if (usedFlag) {
//            Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//            return "";
//        }
//        usedFlag = true;
//        String result = "";
//        try {
//            if (fingerprint == null) {
//                fingerprint = new Fingerprint(mChatService);
//            }
//            Log4jUtil.debug(this.getClass(), "DeviceService:--fingerprint"
//                    + fingerprint);
//            result = fingerprint.fingerPrinter();
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

//    public String readFingerGDNH() {
//        //25秒后当前线程没返回 断开蓝牙连接
//		//new TimeoutTask(Thread.currentThread()).start(25000);
//        if (usedFlag) {
//            return "";
//        }
//        usedFlag = true;
//        String result = "";
//        try {
//            if (fingerprint == null) {
//                fingerprint = new Fingerprint(mChatService);
//            }
//            result = fingerprint.fingerPrinterGDNH();
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            result = IOERROR;
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 网络测试线程
     *
     * @author hanjj
     */
    public String pingTest() {
        String result = "";
        String str = "";
        String address = "202.108.22.5";//百度IP地址
        try {
            Process p = Runtime.getRuntime().exec(
                    "ping -c 4 -i 1 -w 1000 " + address);
            int status = p.waitFor();
            if (status == 0) {
                BufferedReader buf = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                while ((str = buf.readLine()) != null) {
                    result += str + "\r\n";
                }
            } else {
                result = "网络连接失败！";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 升级底座程序
     *
     * @author hanjj
     */
//    public String updataDrv(String dirPath, String fileName, int type) {
//        String result = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvUpdate == null) {
//                drvUpdate = new DrvUpdate(mChatService);
//            }
//            result = drvUpdate.updateDrv(dirPath, fileName, type);
//        } catch (IOException e) {
//            e.printStackTrace();
//            disConnect();
//            return "升级失败";
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    /**
     * 查询底座程序版本号
     *
     * @author hanjj
     */
//    public String readVersionNum() {
//        String result = "";
//        try {
//            if (usedFlag) {
//                Log4jUtil.debug(this.getClass(), "DeviceService:多击返回.");
//                return null;
//            }
//            usedFlag = true;
//            if (drvUpdate == null) {
//                drvUpdate = new DrvUpdate(mChatService);
//            }
//            result = drvUpdate.readVersionNumEncry();
//        } catch (Exception e) {
//            e.printStackTrace();
//            disConnect();
//            return "查询失败";
//        } finally {
//            usedFlag = false;
//        }
//        return result;
//    }

    // -------------------------------------------------------------------------------------------------------------

    // 日期
    private Date DateFormat(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 生日
    private Date DateFormat1(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Handler mHandler = null;

    public void initHandler() {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                mHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case BluetoothChatService.CONNECTION_SUCCESS:
                                // cancleProgressDialog();
                                // Toast.makeText(context, "连接成功",
                                // Toast.LENGTH_SHORT).show();
                                LogUtils.d("",
                                        "DeviceService:--initHandler()--连接成功");
                                sendInitMsgToHandler(0xFF);
                                isConnDevice = true;
                                break;
                            case BluetoothChatService.CONNECTION_FAIL:
                                // cancleProgressDialog();
                                // Toast.makeText(context, "连接失败",
                                // Toast.LENGTH_SHORT).show();
                                LogUtils.d("",
                                        "DeviceService:--initHandler()--连接失败");
                                sendInitMsgToHandler(0xFE);
                                isConnDevice = false;
                                break;
                            case BluetoothChatService.CONNECTION_INTERUPT:
                                // cancleProgressDialog();
                                // Toast.makeText(context, "连接意外中断",
                                // Toast.LENGTH_SHORT).show();
                                sendInitMsgToHandler(0xEF);
                                isConnDevice = false;
                                break;
                            default:
                                break;
                        }
                    }

                };

            }
        });
    }

    /**
     * 给主线程发消息
     *
     * @param what
     * @param data
     */
    private void sendMsgToHandler(int what, Bundle data) {
        Message msg = new Message();
        msg.setData(data);
        msg.setTarget(mHandler);
        msg.what = what;
        msg.sendToTarget();
    }

    private void sendInitMsgToHandler(int what) {
        Message msg = new Message();
        msg.setTarget(initBTHandler);
        msg.what = what;
        msg.sendToTarget();
    }

    @Override
    public boolean openPower() {
        return true;
    }

    @Override
    public boolean closePower() {
        return true;
    }

    /**
     * 根据传入的蓝牙设备名称获取已绑定设备的地址
     *
     * @param name
     * @return
     */
    public String getAddrByName(String name) {
        String addr = null;
        Set<BluetoothDevice> set = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice ds : set) {
            if (name.equals(ds.getName())) {
                addr = ds.getAddress();
            }
        }
        return addr;
    }

    /**
     * 读取密码键盘信息
     */
//    public String readPwd() {
//        // TODO Auto-generated method stub
//        JSONObject jsonObject = new JSONObject();
//        String password = "";
//        try {
//            if (this.isConnDevice) {
//                String result = this.getPin(2);
//                if (result.contains("success")) {
//                    String[] val = result.split(";");
//                    password = val[1];
//                    jsonObject.put("requestCode", "0");
//                    jsonObject.put("password", password);
//                } else {
//                    jsonObject.put("requestCode", "1");
//                    jsonObject.put("requestResult", result);
//                }
//            } else {
//                jsonObject.put("requestCode", "1");
//                jsonObject.put("requestResult", "请先连接设备");
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }

    /**
     * 获取绑定设备的mac地址 要求唯一绑定一个蓝牙设备
     *
     * @return
     */
    public String getBoundAddr() {
        String addr = null;
        Set<BluetoothDevice> set = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice ds : set) {
            addr = ds.getAddress();
        }
        return addr;
    }
}
