package com.pactera.financialmanager.ui.credit;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.bean.IDCheckResult;
import com.pactera.financialmanager.db.CreditCardApplyDao;
import com.pactera.financialmanager.entity.CreditCardApplyEntity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xh on 2015/12/8.
 */
public class CreditCardApplyListActivity extends ParentActivity implements View.OnClickListener {

    private ListView listView;
    private Button btnApply;
    private Button btnUpload;

    private CreditCardApplyDao dao;

    private CheckBox allPickCheckBox;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ProgressDialog pd;
    private String certcode;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_apply_list);
        dao = new CreditCardApplyDao(getApplicationContext());
        listView = (ListView) findViewById(R.id.lv_list);

        btnApply = (Button) findViewById(R.id.btn_apply);
        btnUpload = (Button) findViewById(R.id.btn_upload);
       Intent intent = getIntent();
        certcode = intent.getStringExtra("certcode");

        allPickCheckBox = (CheckBox) findViewById(R.id.idx);
        btnApply.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        allPickCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i = 0; i < listView.getChildCount(); i++){
                    View view = listView.getChildAt(i);
                    Holder h = (Holder)view.getTag();
                    if (h != null) {
                        if (h.idx.isEnabled()) {
                            h.idx.setChecked(isChecked);
                        }
                    }
                }
            }
        });
        super.initTitle(this, "");
        ParamHelper.updateParam(this, handler, LogoActivity.user.getClearingCenterBrCode());
    }

    private void refreshList() {
        List<CreditCardApplyEntity> dataList = dao.loadList(LogoActivity.user.getUserCode());
        ApplyListAdapter adapter = new ApplyListAdapter(dataList);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        //点击创建申请按钮进入的操作
        if (view.getId() == R.id.btn_apply) {
            mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBlueToothAdapter!= null && !mBlueToothAdapter.isEnabled()){
                mBlueToothAdapter.enable();
                toast("opne blue success!");
            }
            SystemClock.sleep(3000);
            if (mBlueToothAdapter.isEnabled()) {

//                startActivityForResult(new Intent(this, IDCheck.class), 1);

                startActivityForResult(new Intent(this, CardEntryActivity.class), 1);

            }else {

                toast("请先打开蓝牙！再操作。");
            }
//            final IDCheckDialog dialog = new IDCheckDialog();
//            dialog.needApplyType(true);
//            dialog.setFinishCallbak(new IDCheckDialog.FinishCallback() {
//                @Override
//                public void onFinish(IDCheckDialog.CheckResult checkResult) {
//                    // id check passed
//                    if (checkResult != null && checkResult.pass) {
//                        Intent intent = new Intent(CreditCardApplyListActivity.this, CreditCardApplyActivity.class);
//                        intent.putExtra("apply", checkResult);
//                        intent.putExtra("certcode",LogoActivity.user.getCERTCODE());
//                        intent.putExtra("loginUserCode",LogoActivity.user.getUserCode());
//                        LogUtils.d("join to *********************");
//                        startActivityForResult(intent, 0);///点击创建申请按钮进入的操作
//                    }
//                }
//            });
//
//            dialog.show(getFragmentManager(), "single");
        } else if (view.getId() == R.id.btn_upload) {  //点击上传的操作
            List<CreditCardApplyEntity> listForUpload = new ArrayList<CreditCardApplyEntity>();
            for (int i = 0; i < listView.getChildCount(); i++) {
                Holder h = (Holder)listView.getChildAt(i).getTag();
                if (h.idx.isChecked()) {
                    listForUpload.add((CreditCardApplyEntity)listView.getAdapter().getItem(i));
                }
            }

            if (listForUpload.size() > 0) {
                pd = new ProgressDialog(CreditCardApplyListActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setCanceledOnTouchOutside(false);
                pd.setTitle("请等待");
                pd.setMessage("xxx");
                pd.setProgress(0);
//            pd.setIcon();
                pd.setIndeterminate(false);
                pd.setCancelable(true);
//数据上传的操作
                UploadHelper uploader = new UploadHelper(CreditCardApplyListActivity.this, handler, listForUpload, pd);
                uploader.start();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtils.d(requestCode+"=============="+resultCode);
        if (requestCode == 0) {
            // save success
            if (resultCode == 0) {
                refreshList();
            }
        }else if (requestCode == 1){
            if (resultCode == 1){
                Bundle bundle = data.getExtras();
                IDCheckResult checkResult = (IDCheckResult) bundle.getSerializable("check");
                if (checkResult != null && checkResult.pass) {
                    Intent intent = new Intent(CreditCardApplyListActivity.this, CreditCardApplyActivity.class);
                    intent.putExtra("apply", checkResult);
                    intent.putExtra("certcode",LogoActivity.user.getCERTCODE());
                    intent.putExtra("loginUserCode",LogoActivity.user.getUserCode());
                    LogUtils.d("join to *********************");
                    startActivityForResult(intent, 0);///点击创建申请按钮进入的操作
                }
            }else {//取消

            }
        }
    }


    private static class Holder {
        private CheckBox idx;
        private TextView txtName;
        private TextView txtApplyType;
        private TextView txtProductType;
        private TextView txtIdCode;
        private TextView txtLastUpdateTime;
        private TextView txtFinished;

        private View lines[];
        private Button btnEdit;
        private Button btnDel;
        private View txtOperate;

        private void change() {
            for (View v : lines) {
                v.setVisibility(View.INVISIBLE);
            }

            btnEdit.setVisibility(View.VISIBLE);
            btnDel.setVisibility(View.VISIBLE);
            txtOperate.setVisibility(View.GONE);
        }
    }

    private static int[] LIST_BG_COLORS = new int[]{
            R.color.lvtopbg,
            R.color.white
    };

    private class ApplyListAdapter extends BaseAdapter {

        private List<CreditCardApplyEntity> list;

        ApplyListAdapter(List<CreditCardApplyEntity> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(CreditCardApplyListActivity.this, R.layout.item_credit_apply, null);
                final Holder holder = initHolderViews(convertView);
                convertView.setTag(holder);
             //点击编辑按钮后进入的操作
                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreditCardApplyListActivity.this, CreditCardApplyActivity.class);
                        intent.putExtra("applyId", list.get(position).getId());
                        intent.putExtra("certcode",certcode);
                        intent.putExtra("loginUserCode",LogoActivity.user.getUserCode());
                        startActivityForResult(intent, 0);//点击编辑按钮后进入的操作   0表示requestCode
                    }
                });

                holder.btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final CreditCardApplyEntity entity = list.get(position);
                        String showInfo = "[" + holder.txtName.getText() + "][" + holder.txtApplyType.getText() + ":"
                                + holder.txtProductType.getText() + "]";
                        new AlertDialog.Builder(v.getContext())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("删除确认")
                                .setMessage("是否确定要删除申请" + showInfo + "吗？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dao.delById(entity.getId());
                                        refreshList();
                                    }
                                })
                                .setNegativeButton("否", null)
                                .show();
                    }
                });
            }

            CreditCardApplyEntity entity = list.get(position);

//            convertView.setBackgroundColor(getResources().getColor(LIST_BG_COLORS[position % LIST_BG_COLORS.length]));
            Holder holder = (Holder) convertView.getTag();
            holder.change();
            if (entity.getApplyType() == 0 || entity.getApplyType() == 2) {
                holder.txtName.setText(entity.getMaCustName());
                holder.txtIdCode.setText(entity.getMaCertNo());
            } else {
                holder.txtName.setText(entity.getSuCustName());
                holder.txtIdCode.setText(entity.getSuCertNo());
            }

            holder.txtApplyType.setText(CreditCardApplyEntity.applyTypeDesc(entity.getApplyType()));

            ParamHelper.Param param = ParamHelper.getParam();
            if (param != null) {
                holder.txtProductType.setText(param.productDesc(entity.getProductCode()));
            } else {
                holder.txtProductType.setText("");
            }

            holder.txtLastUpdateTime.setText(DATE_FORMAT.format(entity.getLastUpdateTime()));

            if (entity.getFinished() == 0) {
                holder.txtFinished.setText("未完善");
                holder.idx.setEnabled(false);
            } else {
                holder.txtFinished.setText("可提交");
                holder.idx.setEnabled(true);
            }

            return convertView;
        }

        private Holder initHolderViews(View convertView) {
            Holder holder = new Holder();

            holder.idx = (CheckBox)convertView.findViewById(R.id.idx);
            holder.txtIdCode = (TextView) convertView.findViewById(R.id.txt_id_code);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtApplyType = (TextView) convertView.findViewById(R.id.txt_apply_type);
            holder.txtProductType = (TextView) convertView.findViewById(R.id.txt_product_type);
            holder.txtLastUpdateTime = (TextView) convertView.findViewById(R.id.txt_last_update_time);
            holder.txtFinished = (TextView) convertView.findViewById(R.id.txt_finished);

            holder.txtOperate = convertView.findViewById(R.id.txt_operate);
            holder.btnEdit = (Button) convertView.findViewById(R.id.btn_edit);
            holder.btnDel = (Button) convertView.findViewById(R.id.btn_del);
            holder.lines = new View[]{
                    convertView.findViewById(R.id.view1),
                    convertView.findViewById(R.id.view2),
                    convertView.findViewById(R.id.view3),
                    convertView.findViewById(R.id.view4),
                    convertView.findViewById(R.id.view5),
                    convertView.findViewById(R.id.view6),
                    convertView.findViewById(R.id.view7),
            };

            return holder;
        }
    }

    private String getShowingName(CreditCardApplyEntity entity) {
        if (entity.getApplyType() == 0 || entity.getApplyType() == 2) {
            return (entity.getMaCustName());
        } else {
            return (entity.getSuCustName());
        }
    }

    private String getShowingId(CreditCardApplyEntity entity) {
        if (entity.getApplyType() == 0 || entity.getApplyType() == 2) {
            return (entity.getMaCertNo());
        } else {
            return (entity.getSuCertNo());
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                pd.setMessage((String)msg.obj);
            } else if (msg.what == 2) {
                // 参数更新后的消息，先加载参数信息到内存，再刷新list
                ParamHelper.Param param = ParamHelper.reloadParam(CreditCardApplyListActivity.this);
                if (param != null) {
                    refreshList();
                }
                Toast.makeText(CreditCardApplyListActivity.this, (String)msg.obj, Toast.LENGTH_LONG).show();
            } else if (msg.what == 9) {
                // 上传成功后本地删除
                dao.delById(msg.getData().getLong("id"));
                refreshList();
                Toast.makeText(CreditCardApplyListActivity.this, (String)msg.obj, Toast.LENGTH_LONG).show();
            }
        }
    };
}
