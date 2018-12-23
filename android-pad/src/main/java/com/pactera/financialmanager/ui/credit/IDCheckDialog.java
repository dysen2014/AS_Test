package com.pactera.financialmanager.ui.credit;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;

import java.io.Serializable;

/**
 * Created by xh on 2015/12/14.
 */
public class IDCheckDialog extends DialogFragment implements View.OnClickListener {

    private TextView txtErrMsg;

    private EditText editID;

    private EditText editName;

    private FinishCallback f;

    private RadioGroup rgroupApplyType;

    private boolean needApplyType;

    private ProgressDialog pDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (pDialog != null) {
                pDialog.dismiss();
            }
            if (msg.what == 1) {    // success
                f.onFinish((CheckResult) msg.obj);
                dismiss();
            } else if (msg.what == 2) {     // search result empty
//                Toast.makeText(getActivity(), "查询不到该客户", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {     // response empty
//                Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void needApplyType(boolean needApplyType) {
        this.needApplyType = needApplyType;
    }

    public void setFinishCallbak(FinishCallback f) {
        this.f = f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_id_check, null);

        Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);

        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        int w = display.getWidth() * 2 / 3;
        int h = display.getHeight() * 2 / 3;
        ;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        dialog.addContentView(view, params);

        txtErrMsg = (TextView) view.findViewById(R.id.txt_errmsg);
        editID = (EditText) view.findViewById(R.id.edit_id);
        editName = (EditText) view.findViewById(R.id.edit_name);
        rgroupApplyType = (RadioGroup) view.findViewById(R.id.rgroup_apply_type);

        if (needApplyType) {
            rgroupApplyType.setVisibility(View.VISIBLE);
        } else {
            rgroupApplyType.setVisibility(View.INVISIBLE);
        }

        view.findViewById(R.id.btn_check).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check: {
                final Integer applyType;
                if (needApplyType) {
                    int chooseId = rgroupApplyType.getCheckedRadioButtonId();
                    if (chooseId == R.id.radio_both) {
                        applyType = 2;
                    } else if (chooseId == R.id.radio_master) {
                        applyType = 0;
                    } else if (chooseId == R.id.radio_supplementary) {
                        applyType = 1;
                    } else {
                        Toast.makeText(getActivity(), "请选择申请类型", Toast.LENGTH_SHORT).show();
                        break;
                    }
                } else {
                    applyType = null;
                }

                final String id = editID.getText().toString();
                final String name = editName.getText().toString();
                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "身份证 姓名都不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                //跳过核验 临时
                { CheckResult checkResult = new CheckResult(true, id , name,
                        "PN00C7BH", applyType);

                    f.onFinish(checkResult);
                    dismiss();
                }

//                pDialog = new ProgressDialog(getActivity());
//                pDialog.setMessage("正在查询，请稍候...");
//                pDialog.setCanceledOnTouchOutside(false);
//                pDialog.show();
//
//                final String requestUrl;
//                try {
//                    String jsonDate = URLEncoder.encode(String.format("{\"CUSTID\":\"\",\"CUSTNAME\":\"\",\"USERNAME\":\"\",\"PHONE_NO\":\"\",\"BRNAME\":\"\",\"CARDNUMBER\":\"%s\"}", id), "utf-8");
//                    requestUrl = String.format(HRequest.URL_REQUEST + "T000022?method=getJSON&chnlCode=02&transCode=T000022&userCode=%s&brCode=%s&seriNo=%s&jsonData=%s",
//                             LogoActivity.user.getUserCode(), LogoActivity.user.getBrCode(), LogoActivity.user.getImei(), jsonDate);
//                } catch (UnsupportedEncodingException e) {
//                    break;
//                }
//
//                Log.i("1111", "ID check dialog:" + requestUrl);
//                Thread t = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpClient hc = new DefaultHttpClient();
//
//                        hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000);
//                        hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
//
//                        String respJson = null;
//                        try {
//                            HttpGet get = new HttpGet(requestUrl);
//                            LogUtils.d("requestUrl:"+requestUrl);
//
//                            HttpResponse response = hc.execute(get);
//                            if (response.getStatusLine().getStatusCode() == 200) {
//                                String resp = EntityUtils.toString(response.getEntity(), "utf-8");
//                                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                                Document doc = builder.parse(new InputSource(new StringReader(resp)));
//
//                                Element e = doc.getDocumentElement();
//                                e = (Element)e.getElementsByTagName("soapenv:Body").item(0);
//                                e = (Element)e.getElementsByTagName("getJSONResponse").item(0);
//                                e = (Element)e.getElementsByTagName("getJSONReturn").item(0);
//                                respJson = e.getTextContent();
//                                LogUtils.d("respJson:"+respJson);
//                            }
//
//
//                        } catch (Exception e) {
//                            respJson = null;
//                            e.printStackTrace();
//                        }
//
//                        Message msg = Message.obtain();
//
//                        do {
//                            if (respJson == null) {
//                                msg.what = 3;
//                                break;
//                            }
//                            JSONObject respObj = JSON.parseObject(respJson);
//                            if (!"0000".equals(respObj.getString("retCode"))) {
//                                msg.what = 3;
//                                break;
//                            }
//
//                            JSONArray group = respObj.getJSONArray("group");
//                            if (group.size() < 1) {
//                                msg.what = 2;
//                                break;
//                            }
//
//                            JSONObject o = group.getJSONObject(0);
//                            if (!name.equals(o.getString("CUSTNAME"))) {
//                                msg.what = 2;
//                                break;
//                            }
//
////                            CheckResult checkResult = new CheckResult(true,
////                                    o.getString("CUST_PSN_CARD_NUMBER"), name,
////                                    o.getString("CUSTID"), applyType);
//
//	                        //跳过核验 临时
//	                        CheckResult checkResult = new CheckResult(true, id , name,
//			                        "PN00C7BH", applyType);
//
//                            LogUtils.d("checkResult:"+checkResult);
//                            msg.what = 1;
//                            msg.obj = checkResult;
//
//                        } while (false);
//
//                        handler.sendMessage(msg);
//                    }
//                });
//                t.start();
//                CheckResult checkResult = new CheckResult(true, id, name, "custID", applyType);
//                f.onFinish(checkResult);
//                pDialog.dismiss();
//                dismiss();
                break;
            }


            case R.id.btn_cancel: {
                dismiss();
                break;
            }
        }
    }

    public interface FinishCallback {
        void onFinish(CheckResult checkResult);
    }

    public static class CheckResult implements Serializable {
        public final boolean pass;

        public final String id;

        public final String name;

        public final String custNo;

        public final Integer applyType;

        private CheckResult(boolean pass, String id, String name, String custNo, Integer applyType) {
            this.pass = pass;
            this.id = id;
            this.name = name;
            this.custNo = custNo;
            this.applyType = applyType;
        }
    }
}
