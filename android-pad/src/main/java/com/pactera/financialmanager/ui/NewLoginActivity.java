package com.pactera.financialmanager.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.CreditActivity;
import com.pactera.financialmanager.ui.model.UploadInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.ApkUpdater;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.GetDeviceSerialNumberUtil;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.VersionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户登录首页
 *
 * @author YPJ
 */
public class NewLoginActivity extends ParentActivity implements
        View.OnClickListener {

    private EditText login_edit_user, login_edit_psd;
    private Button login_btn;
    private TextView tvVersion;
    private TextView tvSeriNo;

    private HConnection loginCon;
    private final int loginIndex = 1;
    private final int checkversion = 2;

    private int screenWidth;// 屏幕宽度 　
    private int screenHeight;// 屏幕高度
    @SuppressWarnings("unused")
    private String softID, softName, versionNO, loadURL, fileName, brName;
    private SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        // 获取屏幕宽和高
        findDisplayScreen();
        LogoActivity.user.getStaId();//ST001103
        findViews();
        bindOnClickListener();

        //****************方便测试 临时添加的************
//        login_edit_user.setText("A001");
//        login_edit_psd.setText("111111");

        userInfo = getSharedPreferences("user_info", 0);
        if (userInfo != null) {
            login_edit_user.setText(userInfo.getString("userid", ""));
            login_edit_psd.setText(userInfo.getString("password", ""));
        }

        checkNewVersion();
    }

    /**
     * 检查新版本
     */
    private void checkNewVersion() {
        if (Tool.haveNet(this)) {
            String requestType = InterfaceInfo.CHECK_VERSION;

            HRequest request = null;
            request = new HRequest(requestType + "?method=getJSON"
                    + "&transCode=" + requestType
                    + "&userCode=" + ""
                    + "&password=" + ""
                    + "&spareOne=" + "01"
                    + "&spareTwo=" + VersionUtils.getVersionNo(this)
                    + "&seriNo=" + LogoActivity.user.getImei()
                    + "&chnlCode=" + Constants.CHANNEL_CODE, "GET");
            try {
                HConnection.isShowLoadingProcess = false; // 显示loading
                loginCon = new HConnection(NewLoginActivity.this, request,LogoActivity.user,HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                loginCon.setIndex(checkversion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void getData() {
        if (Tool.haveNet(this)) {
            String loginStr = login_edit_user.getText().toString().trim();
            String pwdStr = login_edit_psd.getText().toString().trim();
            pwdStr = Tool.ecodeByMD5(pwdStr); // 加密
            String requestType = InterfaceInfo.NEW_LOGIN;

            HRequest request = null;
            request = new HRequest(requestType + "?method=getJSON"
                    + "&transCode=" + requestType
                    + "&userCode=" + loginStr
                    + "&password=" + pwdStr
                    + "&seriNo=" + LogoActivity.user.getImei()
                    + "&chnlCode=" + Constants.CHANNEL_CODE, "GET");
            try {
                HConnection.isShowLoadingProcess = true; // 显示loading
                loginCon = new HConnection(NewLoginActivity.this, request,
                        LogoActivity.user,
                        HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                loginCon.setIndex(loginIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void findDisplayScreen() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        Constants.SCREEN_WIDTH = screenWidth;
        Constants.SCREEN_HEIGHT = screenHeight;
    }

    private void findViews() {
        login_btn = (Button) findViewById(R.id.login_btn);
        login_edit_user = (EditText) findViewById(R.id.login_edit_user);
        login_edit_psd = (EditText) findViewById(R.id.login_edit_psd);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvSeriNo = (TextView) findViewById(R.id.tv_seriNo);

        // 显示当前版本号
        String versioName = VersionUtils.getVersionName(this);
        String seriNo = LogoActivity.user.getImei();

            GetDeviceSerialNumberUtil.displayDevice(this);

        tvVersion.setText("当前版本：" + versioName);
        tvSeriNo.setText("序列号:" + seriNo);
    }

    private void bindOnClickListener() {
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (Tool.haveNet(NewLoginActivity.this)) {
                    String user_code = login_edit_user.getText().toString().trim();
                    if (user_code == null || "".equals(user_code)) {
                        Toast.makeText(NewLoginActivity.this, R.string.no_user_code, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String user_pad = login_edit_psd.getText().toString().trim();
                    if (user_pad == null || "".equals(user_pad)) {
                        Toast.makeText(NewLoginActivity.this, R.string.no_user_psd, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getData();
                } else {
                    Toast.makeText(NewLoginActivity.this, R.string.use_offline_login, Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        HResponse res = null;

        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR://做哪些处理比较好？
                Toast.makeText(this,"系统请求错误",Toast.LENGTH_SHORT).show();//2016/11/29添加错误吐司
                break;
            // 登录返回信息
            case loginIndex:
                res = loginCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                LogUtils.d("login respons:"+res.dataJsonObject.toString());
                readJson(res.dataJsonObject);
                break;

            // 版本更新
            case checkversion:
                res = loginCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                // 单个对象解析方法
                String searchInfo = res.dataJsonObject.toString();
                final UploadInfo uploadInfo = JSON.parseObject(searchInfo, UploadInfo.class);
                // 判断第一次并且版本号不为null
                if (uploadInfo != null && !TextUtils.isEmpty(uploadInfo.getVersionNO())) {
                    boolean isUpload = VersionUtils.isUploadNewVersion(this, uploadInfo.getVersionNO());
                    if (isUpload) {

                        final ProgressDialog pd = new ProgressDialog(this);
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pd.setMessage("有新的版本 " + uploadInfo.getVersionNO() + "，正在下载更新");
                        pd.setCancelable(false);
                        pd.show();

                        ApkUpdater apkUpdater = new ApkUpdater(pd, uploadInfo, NewLoginActivity.this);
                        apkUpdater.start();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void readJson(JSONObject tmpJsonObject) {
        if (tmpJsonObject.has("USERVALIDSTATUS")) //
            try {
                String stat = tmpJsonObject.getString("USERVALIDSTATUS");
                if (!"01".equals(stat)){
                    Toast.makeText(NewLoginActivity.this, "用户未在CRM系统启用", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if(tmpJsonObject.has("CERTCODE")) {
            try {
                String certcode = tmpJsonObject.getString("CERTCODE");
                LogoActivity.user.setCERTCODE(tmpJsonObject.getString("CERTCODE"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (tmpJsonObject.has("ENABLEFLAG")) //
            try {
                String stat = tmpJsonObject.getString("ENABLEFLAG");
                if (!"01".equals(stat)){
                    Toast.makeText(NewLoginActivity.this, "用户未在CRM系统激活", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("brID")) // 客户所在银行ID，机构号
            try {
                String brid = tmpJsonObject.getString("brID");
                LogoActivity.user.setBrID(brid);
                Constants.BRID = brid;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("brName")) // 客户所在银行名字，机构名称
            try {
                LogoActivity.user.setBrName(tmpJsonObject.getString("brName"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("tyBrID")) // 体验银行机构号
            try {
                LogoActivity.user.setTyBrID(tmpJsonObject.getString("tyBrID"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("rightStr")) // 权限
            try {
                String tmpRightStr = tmpJsonObject.getString("rightStr");
                LogoActivity.user.setRightStr(tmpRightStr);

                String[] tmpSplit = tmpRightStr.split(",");
                Constants.RIGHTSTR = tmpSplit;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("userCode")) // 用户号
            try {
                LogoActivity.user.setUserCode(tmpJsonObject
                        .getString("userCode"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("userName")) // 用户名
            try {
                LogoActivity.user.setUsername(tmpJsonObject
                        .getString("userName"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        if (tmpJsonObject.has("eixtTime")) // 无操作退出时间
            try {
                LogoActivity.user.setEixtTime(tmpJsonObject.getString("eixtTime"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            if (tmpJsonObject.has("CERTCODE")){

                try {
                    LogoActivity.user.setCERTCODE(tmpJsonObject.getString("CERTCODE"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        if (tmpJsonObject.has("brCode")) // 机构号
            try {
                LogoActivity.user.setBrCode(tmpJsonObject.getString("brCode"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        try {
            LogoActivity.user.setClearingCenterBrCode(tmpJsonObject.getString("clearingCenterBrCode").trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject joStation = null;
        try {
            if (tmpJsonObject.has("station")) {
                JSONArray stationArray = tmpJsonObject.getJSONArray("station");
                joStation = (JSONObject) stationArray.get(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (joStation != null) {
            try {
                String staId = joStation.getString("staId"); // 岗位ID
                String roleName = joStation.getString("staName");
                //String roleCode = joStation.getString("roleCode");
                String roleDesc = joStation.getString("staDesc");
                LogoActivity.user.setStaId(staId);
                LogoActivity.user.setRoleName(roleName);
                //LogoActivity.user.setRoleCode(roleCode);
                LogoActivity.user.setRoleDesc(roleDesc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Integer retCode = -1;
        try {
            retCode = Integer.parseInt(tmpJsonObject.getString("retCode"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (retCode) {
            case -1:
                Toast.makeText(NewLoginActivity.this, "登录请求超时", Toast.LENGTH_SHORT)
                        .show();
                break;
//            case 1101:
//                Toast.makeText(NewLoginActivity.this, "用户未在CRM系统启用", Toast.LENGTH_SHORT).show();
//                break;
//            case 1102:
//                Toast.makeText(NewLoginActivity.this, "用户未在CRM系统激活", Toast.LENGTH_SHORT).show();
//                break;

            case 0000:
                // 这是登录成功之后 ，保存了当前的登录帐号和密码
                LogoActivity.user.setUserid1(login_edit_user.getText().toString().trim());
                LogoActivity.user.setUserid(LogoActivity.user.getUserCode());
                LogoActivity.user.setPassword(login_edit_psd.getText().toString().trim());
                // 这是登录成功之后，将当前登录的帐号、密码、机构号、客户所在银行机构名称都保存在了SharedPreferences中。
                if (userInfo != null) {
                    userInfo.edit().putString("userid", login_edit_user.getText().toString().trim())
                            .commit();
                    userInfo.edit()
                            .putString("password",
                                    login_edit_psd.getText().toString().trim())
                            .commit();
                    userInfo.edit().putString("brID", LogoActivity.user.getBrID())
                            .commit();
                    userInfo.edit()
                            .putString("brName", LogoActivity.user.getBrName())
                            .commit();
                }

                Intent intent = new Intent(NewLoginActivity.this, CreditActivity.class);
                IS_OUTlINE = false;
                startActivity(intent);
                finish();
                break;
            default:

                userInfo = getSharedPreferences("errorCode", 0);
                if (userInfo != null) {
                    Toast.makeText(NewLoginActivity.this, "" + userInfo.getString(retCode + "", ""), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}