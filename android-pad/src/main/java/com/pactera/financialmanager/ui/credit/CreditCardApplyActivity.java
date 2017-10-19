package com.pactera.financialmanager.ui.credit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.bean.IDCheckResult;
import com.pactera.financialmanager.db.CreditCardApplyDao;
import com.pactera.financialmanager.entity.CreditCardApplyEntity;
import com.pactera.financialmanager.entity.CreditCardApplyEntity.PicEntity;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.fragmentbookbuilding.WindowTakePictures;
import com.pactera.financialmanager.util.BitmapUtils;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.IDNumberAnalyser;
import com.pactera.financialmanager.util.Tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xh on 2015/12/14.
 */
public class CreditCardApplyActivity extends ParentActivity implements View.OnClickListener {

    /**
     * views start
     **/
    private Spinner spinCaseCode;
    private TextView txtApplyBranCode;
    private Button btnAddSupplementary;
    private Spinner spinProductCode;
    private Spinner spinBrandCode;
    private Spinner spinBillDate;
    private Spinner spinUnitCode;

    private EditText editMCertNoId;
    private EditText editMPinyin;
    private EditText editMphone;
    private EditText editMemail;
    private EditText editMhomeAddr;
    private EditText editMhomeZipcode;
    private EditText editMworkAddr;
    private EditText editMworkZipcode;
//    private EditText editMcensusAddr;
//    private EditText editMcensusZipcode;
    private Spinner spinMmailAddr;
    private EditText editMdireLinkmanName;
    private Spinner spinMdireLinkmanRela;
    private EditText editMdireLinkmanMobileTel;
    private EditText editMdireLinkmanAddr;
    private EditText editMothLinkmanName;
    private Spinner spinMothLinkmanRela;
    private EditText editMothLinkmanMobileTel;
    private EditText editMothLinkmanAddr;
    private MaritalRadioGroup rgroupMmaritalStatus;
    private CheckBox chkMhasChildren;
    private EditText editMyearIncome;
    private EditText editMworkYears;
    private Spinner spinMcorpAttr;
    private Spinner spinMindustryCode;
    private Spinner spinMspecLevel;
    private Spinner spinMdutyCode;
    private Spinner spinMhouseStatus;
    private Spinner spinMdegreeCode;
    private EditText editMhousingPrice;
    private EditText editMcorpName;
    private EditText editMcorpTel;
//    private EditText editMCarPrice;

    private EditText editPrimaryCustName;
    private EditText editPriCardNo;
    private Spinner spinPcRela;
    private EditText editSpyName;
    private EditText editSmobileTel;
    private EditText editShomeAddr;
    private EditText editShomeZip;
    private EditText editSCertNoId;
    private ListView listPics;
    private Button btnAddPic;

    private EditText editRecordClerkNo;
    private PopupWindow bigImageWindow;
    /**
     * views end
     **/

    private boolean isUpdate;

    private CreditCardApplyDao dao;

    private CreditCardApplyEntity applyEntity;

    private IDNumberAnalyser analyser;

    private int lastChoosePic = -1;

    private WatcherResultManager wrm = new WatcherResultManager();

    private static final SimpleDateFormat BIRTHDAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final SpinnerItem<String> DEFAULT_SPINNER_ITEM = new SpinnerItem<String>("", "--请选择--");
//findViewById()
    private void bindViews() {

        btnAddSupplementary = (Button) findViewById(R.id.btn_add_supplementary);//同时申请附卡
        btnAddPic = (Button) findViewById(R.id.btn_add_pic);//添加资料图片
        {
            listPics = (ListView) findViewById(R.id.list_pic);
            refreshListPics();
        }

        btnAddSupplementary.setOnClickListener(this);//同时申请附卡
        findViewById(R.id.btn_save).setOnClickListener(this);//提交
        findViewById(R.id.btn_save_draft).setOnClickListener(this);//保存草稿
        btnAddPic.setOnClickListener(this);//添加资料图片


        // spinners
        {
            {
                spinProductCode = (Spinner) findViewById(R.id.spin_product_code);//产品代码
                if (param != null) {
                    SpinnerItem<String>[] s = initSpinnerItemsWithDefault(param.products.size());
                    for (int i = 1; i < s.length; i++) {
                        ParamHelper.Product p = param.products.get(i - 1);
                        s[i] = new SpinnerItem<String>(p.code, p.desc);
                    }
                    initSpinner(spinProductCode, s);
                } else {
                    initSpinner(spinProductCode, new SpinnerItem[]{DEFAULT_SPINNER_ITEM});
                }

                spinProductCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SpinnerItem<String> item = ((ArrayAdapter<SpinnerItem<String>>) parent.getAdapter()).getItem(position);
                        initProductLinkage(position, item);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            // brand code
            {
                spinBrandCode = (Spinner) findViewById(R.id.spin_brand_code);//品牌代码
                initSpinner(spinBrandCode, new SpinnerItem[]{DEFAULT_SPINNER_ITEM});
            }

            // bill date  账单日期
            {
                spinBillDate = (Spinner) findViewById(R.id.spin_bill_date);
                initSpinner(spinBillDate, new SpinnerItem[]{DEFAULT_SPINNER_ITEM});

                //unit code  所属单位
                {
                    spinUnitCode = (Spinner) findViewById(R.id.spin_unit_code);
                    if (param != null) {
                        initSpinner(spinUnitCode, initSpinnerItems(param.units));
                    } else {
                        initSpinner(spinUnitCode, new SpinnerItem[]{DEFAULT_SPINNER_ITEM});
                    }
                }


                {
                    spinCaseCode = (Spinner) findViewById(R.id.spin_case_code);
                    if (param != null) {
                        initSpinner(spinCaseCode, initSpinnerItems(param.projects));
                    } else {
                        initSpinner(spinCaseCode, new SpinnerItem[]{DEFAULT_SPINNER_ITEM});
                    }
                }

                //mail address, from home address or work address 邮寄地址
                {
                    spinMmailAddr = (Spinner) findViewById(R.id.spin_m_mail_addr);
                    initSpinner(spinMmailAddr, new SpinnerItem[]{DEFAULT_SPINNER_ITEM});
                    spinMmailAddr.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_UP:
                                    String oldCode = ((SpinnerItem<String>) spinMmailAddr.getSelectedItem()).code;
                                    initSpinMailAddr();
                                    spinMmailAddr.setSelection(SpinnerItem.getSelectedPosition(spinMmailAddr, oldCode));
                                    break;
                            }
                            return false;
                        }
                    });
//单位性质
                    {
                        spinMcorpAttr = (Spinner) findViewById(R.id.spin_m_corp_attr);
                        initSpinner(spinMcorpAttr, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("1", "政府机关/事业单位"),
                                new SpinnerItem("2", "国营企业"),
                                new SpinnerItem("3", "三资企业"),
                                new SpinnerItem("4", "股份制企业"),
                                new SpinnerItem("5", "民营企业"),
                                new SpinnerItem("6", "私营企业"),
                                new SpinnerItem("7", "军队"),
                                new SpinnerItem("8", "其他"),
                        });
                    }

                    {
                        spinMindustryCode = (Spinner) findViewById(R.id.spin_m_industry_code);
                        initSpinner(spinMindustryCode, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("A", "农、林、牧、渔业"),
                                new SpinnerItem("B", "采矿业"),
                                new SpinnerItem("C", "制造业"),
                                new SpinnerItem("D", "电力、燃气及水的生产和供应业"),
                                new SpinnerItem("E", "建筑业"),
                                new SpinnerItem("F", "交通运输、仓库和邮政业"),
                                new SpinnerItem("G", "信息传输、计算机服务和软件业"),
                                new SpinnerItem("H", "批发和零售业"),
                                new SpinnerItem("I", "住宿和餐饮业"),
                                new SpinnerItem("J", "银行业"),
                                new SpinnerItem("K", "房地产业"),
                                new SpinnerItem("L", "租赁和商务服务业"),
                                new SpinnerItem("M", "科学研究、技术服务和地质勘查业"),
                                new SpinnerItem("N", "水利、环境和公共设施管理业"),
                                new SpinnerItem("O", "居民服务和其他服务业"),
                                new SpinnerItem("P", "教育业"),
                                new SpinnerItem("Q", "卫生、社会保障和社会福利业"),
                                new SpinnerItem("R", "文化、体育和娱乐业"),
                                new SpinnerItem("S", "公共管理和社会组织"),
                                new SpinnerItem("T", "国际组织"),
                        });
                    }

                    {
                        spinMspecLevel = (Spinner) findViewById(R.id.spin_m_spec_level);
                        initSpinner(spinMspecLevel, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("0", "无  "),
                                new SpinnerItem("1", "高级"),
                                new SpinnerItem("2", "中级"),
                                new SpinnerItem("3", "初级"),
                                new SpinnerItem("9", "未知"),
                        });
                    }

                    {
                        spinMdutyCode = (Spinner) findViewById(R.id.spin_m_duty_code);
                        initSpinner(spinMdutyCode, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("1", "事业单位 - (副)厅、局级及以上"),
                                new SpinnerItem("2", "企业单位/龙头企业 - 总经理/股东"),
                                new SpinnerItem("3", "事业单位 - (副)处级"),
                                new SpinnerItem("4", "企业单位/规模农业 - 企业高级经理/股东"),
                                new SpinnerItem("5", "事业单位 - 科级"),
                                new SpinnerItem("6", "企业单位/涉农个体工商户 - 经理/个体户"),
                                new SpinnerItem("7", "事业单位 - 科员"),
                                new SpinnerItem("8", "企业单位/一般农户 - 一般员工/一般农户"),
                        });
                    }

                    //contacts relation
                    {
                        spinMdireLinkmanRela = (Spinner) findViewById(R.id.spin_m_dire_linkman_rela);
                        initSpinner(spinMdireLinkmanRela, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("0", "配偶"),
                                new SpinnerItem("1", "父母"),
                                new SpinnerItem("2", "子女"),
                                new SpinnerItem("9", "其他")
                        });
                    }

                    {
                        spinMothLinkmanRela = (Spinner) findViewById(R.id.spin_m_o_linkman_rela);
                        initSpinner(spinMothLinkmanRela, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("0", "同事"),
                                new SpinnerItem("1", "同学"),
                                new SpinnerItem("2", "朋友")
                        });
                    }

                    {
                        spinMhouseStatus = (Spinner) findViewById(R.id.spin_m_house_status);
                        initSpinner(spinMhouseStatus, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("0", "自购无贷款"),
                                new SpinnerItem("1", "自购有贷款"),
                                new SpinnerItem("2", "无房"),
                                new SpinnerItem("9", "其他"),
                        });
                    }

                    {
                        spinPcRela = (Spinner) findViewById(R.id.spin_pc_rela);
                        initSpinner(spinPcRela, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("0", "配偶"),
                                new SpinnerItem("1", "父母"),
                                new SpinnerItem("2", "子女"),
                                new SpinnerItem("9", "其他")
                        });
                    }

                    {
                        spinMdegreeCode = (Spinner) findViewById(R.id.spin_m_degree_code);
                        initSpinner(spinMdegreeCode, new SpinnerItem[]{
                                DEFAULT_SPINNER_ITEM,
                                new SpinnerItem("10", "研究生及以上"),
                                new SpinnerItem("20", "大学本科"),
                                new SpinnerItem("30", "大专"),
                                new SpinnerItem("60", "高中及以下"),
                                new SpinnerItem("99", "其他"),
                        });
                    }
                }
            }
        }


        editRecordClerkNo = (EditText) findViewById(R.id.edit_record_clerk_no);
        txtApplyBranCode = (TextView) findViewById(R.id.txt_apply_bran_code);
        editMCertNoId = (EditText) findViewById(R.id.edit_m_cert_no_id);
        editMPinyin = (EditText) findViewById(R.id.edit_m_pinyin);//姓名拼音
        editMphone = (EditText) findViewById(R.id.edit_m_phone);//移动电话
        editMemail = (EditText) findViewById(R.id.edit_m_email);//E-mail
        editMhomeAddr = (EditText) findViewById(R.id.edit_m_home_addr);
        editMhomeZipcode = (EditText) findViewById(R.id.edit_m_home_zipcode);
        editMworkAddr = (EditText) findViewById(R.id.edit_m_work_addr);
        editMworkZipcode = (EditText) findViewById(R.id.edit_m_work_zipcode);
//        editMcensusAddr = (EditText) findViewById(R.id.edit_m_census_addr);
//        editMcensusZipcode = (EditText) findViewById(R.id.edit_m_census_zipcode);
        editMhousingPrice = (EditText) findViewById(R.id.edit_m_housing_price);
        editMdireLinkmanName = (EditText) findViewById(R.id.edit_m_contacts);
        editMdireLinkmanMobileTel = (EditText) findViewById(R.id.edit_m_dire_linkman_mobile_tel);
        editMdireLinkmanAddr = (EditText) findViewById(R.id.edit_m_dire_linkman_addr);
        editMothLinkmanName = (EditText) findViewById(R.id.edit_m_o_contacts);
        editMothLinkmanMobileTel = (EditText) findViewById(R.id.edit_m_o_linkman_mobile_tel);
        editMothLinkmanAddr = (EditText) findViewById(R.id.edit_m_o_linkman_addr);

        editMyearIncome = (EditText) findViewById(R.id.edit_m_year_income);
        editMworkYears = (EditText) findViewById(R.id.edit_m_work_years);
        rgroupMmaritalStatus = (MaritalRadioGroup) findViewById(R.id.rgroup_m_marital_status);
        rgroupMmaritalStatus.initText();
        chkMhasChildren = (CheckBox) findViewById(R.id.chk_m_has_children);

        editMcorpName = (EditText) findViewById(R.id.edit_m_corp_name);
        editMcorpTel = (EditText) findViewById(R.id.edit_m_corp_tel);
//        editMCarPrice = (EditText) findViewById(R.id.edit_m_car_price);

        editPriCardNo = (EditText) findViewById(R.id.edit_pri_card_no);
        editPrimaryCustName = (EditText) findViewById(R.id.edit_primary_cust_name);
        editSpyName = (EditText) findViewById(R.id.edit_s_pinyin);
        editSmobileTel = (EditText) findViewById(R.id.edit_s_phone);
        editShomeAddr = (EditText) findViewById(R.id.edit_s_home_addr);
        editShomeZip = (EditText) findViewById(R.id.edit_s_home_zip_code);

        editSCertNoId = (EditText) findViewById(R.id.edit_s_cert_no_id);

        editMPinyin.addTextChangedListener(new PinYinEditWatcher());//拼音校验
        editSpyName.addTextChangedListener(new PinYinEditWatcher());
        editMemail.addTextChangedListener(new SimpleInputEditWatcher(editMemail, SimpleInputEditWatcher.EMAIL, wrm));
        editMphone.addTextChangedListener(new SimpleInputEditWatcher(editMphone, SimpleInputEditWatcher.MOBILE_PHONE, wrm));
        editSmobileTel.addTextChangedListener(new SimpleInputEditWatcher(editSmobileTel, SimpleInputEditWatcher.MOBILE_PHONE, wrm));

//        editMcensusZipcode.addTextChangedListener(new SimpleInputEditWatcher(editMcensusZipcode, SimpleInputEditWatcher.ZIP_CODE, wrm));
        editMhomeZipcode.addTextChangedListener(new SimpleInputEditWatcher(editMhomeZipcode, SimpleInputEditWatcher.ZIP_CODE, wrm));//住宅邮编校验
        editMworkZipcode.addTextChangedListener(new SimpleInputEditWatcher(editMworkZipcode, SimpleInputEditWatcher.ZIP_CODE, wrm));

        editMdireLinkmanMobileTel.addTextChangedListener(new SimpleInputEditWatcher(editMdireLinkmanMobileTel, SimpleInputEditWatcher.MOBILE_PHONE, wrm));//电话号码校验
        editMothLinkmanMobileTel.addTextChangedListener(new SimpleInputEditWatcher(editMothLinkmanMobileTel, SimpleInputEditWatcher.MOBILE_PHONE, wrm));
        editMcorpTel.addTextChangedListener(new SimpleInputEditWatcher(editMcorpTel, SimpleInputEditWatcher.HOME_PHONE, wrm));
    }

    private ParamHelper.Param param;

    // record last choose for product
    private String lastProductCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_apply);
        dao = new CreditCardApplyDao(getApplicationContext());

        param = ParamHelper.getParam();//获取son解析切割好的正则匹配字符串

        initApplyEntity();//通过实体类中的set方法传值出去
        bindViews();//findviewbyId()
        initViewsData();//值的回显
        super.initTitle(this, 0);
    }
    //通过实体类中的set方法传值出去
    private void initApplyEntity() {
        long id = getIntent().getLongExtra("applyId", -1); //CreditCardApplyListActivity中点击编辑按钮后来的数据
        // update
        if (id != -1) {
            isUpdate = true;
            applyEntity = dao.loadById(id);
        } else {// create
            IDCheckResult cr = (IDCheckResult) getIntent().getSerializableExtra("apply");//CreditCardApplyListActivity中点击创建申请按钮来的数据
            analyser = new IDNumberAnalyser(cr.id);   //校验身份证

            applyEntity = new CreditCardApplyEntity();
            applyEntity.setApplyBranCode(LogoActivity.user.getBrCode());//   营销机构的  getBrCode()机构号
            applyEntity.setRecordBranCode(LogoActivity.user.getBrCode()); //录入机构 机构号
            applyEntity.setUserId(LogoActivity.user.getUserID());
            applyEntity.setApplyType(cr.applyType);
            // getApplyType() 0: 主卡  1：附卡  2：主卡+附卡
            if (applyEntity.getApplyType() == 0 || applyEntity.getApplyType() == 2) {
                applyEntity.setMaCertNo(cr.id);
                applyEntity.setMaCustName(cr.name);
                applyEntity.setMaSex(analyser.gender() == 1 ? "M" : "F");
                applyEntity.setMaBirthday(BIRTHDAY_FORMAT.format(analyser.birthday()));
                applyEntity.setMaExternCustNo(cr.custNo);

            } else if (applyEntity.getApplyType() == 1) {
                applyEntity.setSuCertNo(cr.id);
                applyEntity.setSuCustName(cr.name);
                applyEntity.setSuSex(analyser.gender() == 1 ? "M" : "F");
                applyEntity.setSuBirthday(BIRTHDAY_FORMAT.format(analyser.birthday()));
                applyEntity.setSuExternCustNo(cr.custNo);
            }
        }

        if (applyEntity.getAttachedPics() == null) {
            applyEntity.setAttachedPics(new ArrayList<PicEntity>());
        }

        applyEntity.setUserId(LogoActivity.user.getUserCode());
    }


    private void initViewsData() {
        showBasicInfo();//基本信息的展示
        syncFromEntity();//存储在实体类里边的信息进行展示
    }
    //基本信息的展示
    private void showBasicInfo() {
        // getApplyType() 0: 主卡  1：附卡  2：主卡+附卡
        if (applyEntity.getApplyType() == 0) {  // master
            findViewById(R.id.area_master_card).setVisibility(View.VISIBLE);
            findViewById(R.id.area_supplementary_card).setVisibility(View.GONE);
            btnAddSupplementary.setVisibility(View.GONE);

        } else if (applyEntity.getApplyType() == 1) {   // supplementary
            findViewById(R.id.area_master_card).setVisibility(View.GONE);
            findViewById(R.id.area_supplementary_card).setVisibility(View.VISIBLE);
            btnAddSupplementary.setVisibility(View.GONE);

        } else if (applyEntity.getApplyType() == 2) {   // both
            editPrimaryCustName.setEnabled(false);
            editPriCardNo.setEnabled(false);

            findViewById(R.id.area_master_card).setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(applyEntity.getSuCustName())) {
                findViewById(R.id.area_supplementary_card).setVisibility(View.GONE);
                btnAddSupplementary.setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.area_supplementary_card).setVisibility(View.VISIBLE);
                btnAddSupplementary.setVisibility(View.GONE);
            }
        }

        ((TextView) findViewById(R.id.txt_m_name)).setText(nullToEmpty(applyEntity.getMaCustName()));//姓名
        ((ImageView) findViewById(R.id.img_m_gender)).setImageResource(showGender(applyEntity.getMaSex()));//性别
        ((TextView) findViewById(R.id.txt_m_id)).setText(nullToEmpty(applyEntity.getMaCertNo()));//客户身份证的显示
        ((TextView) findViewById(R.id.txt_m_birthday)).setText(nullToEmpty(applyEntity.getMaBirthday()));

        ((TextView) findViewById(R.id.txt_s_cust_name)).setText(nullToEmpty(applyEntity.getSuCustName()));
        ((ImageView) findViewById(R.id.img_s_sex)).setImageResource(showGender(applyEntity.getSuSex()));
        ((TextView) findViewById(R.id.txt_s_cert_no)).setText(nullToEmpty(applyEntity.getSuCertNo()));
        ((TextView) findViewById(R.id.txt_s_birthday)).setText(nullToEmpty(applyEntity.getSuBirthday()));
        txtApplyBranCode.setText(applyEntity.getApplyBranCode());//"营销机构号
    }

    private SpinnerItem<String>[] initSpinnerItems(List<ParamHelper.Entry> entries) {
        SpinnerItem<String>[] r = new SpinnerItem[entries.size() + 1];
        r[0] = DEFAULT_SPINNER_ITEM;
        for (int i = 0; i < entries.size(); i++) {
            r[i + 1] = new SpinnerItem(entries.get(i).key, entries.get(i).value);
        }
        return r;
    }

    private SpinnerItem<String>[] initSpinnerItemsWithDefault(int length) {
        SpinnerItem<String>[] r = new SpinnerItem[length + 1];
        r[0] = DEFAULT_SPINNER_ITEM;
        return r;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
      //保存草稿
            case R.id.btn_save_draft: {
                if (!checkPicList()) {
                    break;
                }
                syncFromView();//获取输入框的值进行上值
                applyEntity.setFinished(0);
                if (saveDraft()) {
                    setResult(0); //把数据返回给调用startActivityForResult()方法的类，并在此类的onActivityResult()方法中进行接收数据后的refreshList()处理
                    finish();
                }
                break;
            }
    //提交
            case R.id.btn_save: {
                if (!checkPicList()) {
                    break;
                }
                syncFromView();   //获取输入框的值进行上值
                if (checkAndSave()) {
                    setResult(0);//把数据返回给调用startActivityForResult()方法的类，并在此类的onActivityResult()方法中进行接收数据后的refreshList()处理
                    finish();
                } else {

                }
                break;
            }
  //同时申请附卡
            case R.id.btn_add_supplementary: {
                final IDCheckDialog dialog = new IDCheckDialog();
                dialog.setFinishCallbak(new IDCheckDialog.FinishCallback() {
                    @Override
                    public void onFinish(IDCheckDialog.CheckResult checkResult) {
                        // id check passed
                        if (checkResult != null && checkResult.pass) {
                            applyEntity.setSuCertNo(checkResult.id);
                            applyEntity.setSuCustName(checkResult.name);
                            applyEntity.setSuExternCustNo(checkResult.custNo);
                            applyEntity.setPrimaryCustName(applyEntity.getMaCustName());
                            IDNumberAnalyser analyser = new IDNumberAnalyser(checkResult.id);
                            applyEntity.setSuSex(analyser.gender() == 1 ? "M" : "F");
                            applyEntity.setSuBirthday(BIRTHDAY_FORMAT.format(analyser.birthday()));
                            showBasicInfo();

                            editPrimaryCustName.setText(applyEntity.getPrimaryCustName());
                            btnAddSupplementary.setVisibility(View.GONE);
                        } else {
                            // do nothing
                        }
                    }
                });

                dialog.show(getFragmentManager(), "single");
                break;
            }
//添加图片资料
            case R.id.btn_add_pic: {
                // check, ensure all editing item submitted
                boolean err =  false;
                for (PicEntity e : applyEntity.getAttachedPics()) {
                    if (!e.checked) {
                        Toast.makeText(CreditCardApplyActivity.this, "存在未确认的资料项", Toast.LENGTH_SHORT).show();
                        err = true;
                        break;
                    }
                }

                if (err) break;
                PicEntity e = new PicEntity();
                e.checked = false;
                applyEntity.getAttachedPics().add(e);

                refreshListPics();
                break;
            }
        }
    }
//退出提示Dialog
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("退出确认")
                .setMessage("确定未保存直接退出？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreditCardApplyActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("否", null)
                .show();
    }
//处理图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //通过相机获取图片
                case Constants.TAKE_PICTURE_FROM_CAMERA: {
                    BitmapUtils.startPhotoZoom(this, BitmapUtils.getLastGenerateUri());
                    break;
                }
                //从相册获取图片
                case Constants.TAKE_PICTURE_FROM_PHOTO_ALBUM: {
                    BitmapUtils.startPhotoZoom(this, data.getData());
                    break;
                }
                //裁剪图片
                case Constants.PHOTO_ZOOM: {

                    Uri uri;
                    Bitmap image;

                    if(data.getData() != null){
                        uri = data.getData();
                    }else{
                        uri = BitmapUtils.CROP_TMP_FILE_URI;
//                        uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),image,null,null));
                    }
                        try {
                            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }

                    // compress to 200kb
                    byte[] imageContent = BitmapUtils.compressImage(image, 200 * 1024);
                    applyEntity.getAttachedPics().get(lastChoosePic).setContent(imageContent);
                    ((Holder) (listPics.getChildAt(lastChoosePic)).getTag()).imgPic.setImageBitmap(
                            BitmapFactory.decodeByteArray(imageContent, 0, imageContent.length));
//					((Holder) (listPics.getChildAt(lastChoosePic)).getTag()).imgPic.setImageBitmap(BitmapUtils.zoomBitmap(image, image.getWidth() / 3, image.getHeight() / 3));
                }
                case Constants.PHOTO_DELETE:    {

                    break;
            }
        }
    }
    }


    private boolean checkPicList() {
        // check pic list
        for (PicEntity e : applyEntity.getAttachedPics()) {
            if (!e.checked) {
                Toast.makeText(CreditCardApplyActivity.this, "图片资料未完整", Toast.LENGTH_SHORT).show();
                applyEntity.setFinished(0);
                return false;
            }
        }
        return true;
    }
//保存草稿 先更新再插入
    private boolean saveDraft() {
        applyEntity.setLastUpdateTime(new Date());
        CreditCardApplyDao dao = new CreditCardApplyDao(getApplicationContext());
        if (isUpdate) {
            dao.update(applyEntity);
        } else {
            dao.insert(applyEntity);
        }
        return true;
    }

    private boolean checkAndSave() {
        applyEntity.setFinished(1);  //whether all blanks filled, if finished, upload operation could be done检验填充
   //校验输入栏内容
        if (!wrm.isAllOk()) {
            Toast.makeText(CreditCardApplyActivity.this, "存在未正确填写的输入栏", Toast.LENGTH_SHORT).show();
            return false;
        }

        String checkResult = new CreditInputChecker(applyEntity).check();
        if (checkResult != null) {
            applyEntity.setFinished(0);
            Toast.makeText(CreditCardApplyActivity.this, checkResult, Toast.LENGTH_SHORT).show();
            return false;
        }

        return saveDraft();
    }

    static class SpinnerItem<T> {
        final public T code;

        final public String show;

        public SpinnerItem(T code, String show) {
            this.code = code;
            this.show = show;
        }

        public static <T> int getSelectedPosition(Spinner s, T code) {
            if (code == null) return -1;
            SpinnerAdapter sa = s.getAdapter();
            for (int i = 0; i < sa.getCount(); i++) {
                SpinnerItem it = (SpinnerItem) sa.getItem(i);
                if (it.code.equals(code)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public String toString() {
            return show;
        }
    }
//图片适配器
    private class PicListAdapter extends BaseAdapter {

        private List<PicEntity> list;

        public PicListAdapter(List<PicEntity> list) {
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
                convertView = View.inflate(CreditCardApplyActivity.this, R.layout.item_credit_pic, null);
                Holder holder = new Holder(convertView);
                convertView.setTag(holder);
            }

            final Holder holder = (Holder) convertView.getTag();
            final PicEntity entity = list.get(position);

            // image view init
            setPicStatus(holder, entity, position);

            // spinner init
            holder.spinType.setSelection(SpinnerItem.getSelectedPosition(holder.spinType, nullToEmpty(entity.getType())));

            holder.txtDesc.setText(entity.getDesc());

            // delete this line
            holder.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("删除确认")
                            .setMessage("是否确定要删除？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    list.remove(position);
                                    refreshListPics();
                                }
                            })
                            .setNegativeButton("否", null)
                            .show();
                }
            });

            // sync data to data model
            holder.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = ((SpinnerItem<String>) holder.spinType.getSelectedItem()).code;

                    if (entity.getContent() == null) {
                        return;
                    }

                    if (type.isEmpty()) {
                        return;
                    }

                    entity.setType(type);
                    entity.setDesc(holder.txtDesc.getText().toString());
                    entity.checked = true;
                    holder.changeChecked();
                    setPicStatus(holder, entity, position);
                }
            });

            if (entity.checked) {
                holder.changeChecked();
            }

            return convertView;
        }

        private void setPicStatus(final Holder holder, final PicEntity entity, final int position) {
            if (entity.getContent() != null) {
                holder.imgPic.setImageBitmap(BitmapFactory.decodeByteArray(entity.getContent(), 0, entity.getContent().length));
                holder.imgPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView img;
                        if (bigImageWindow == null) {
                            View popupView = LayoutInflater.from(CreditCardApplyActivity.this).inflate(R.layout.popwindow_promotions, null);
                            bigImageWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            bigImageWindow.setFocusable(true);
                            img = (ImageView) popupView.findViewById(R.id.popwindow_pro_img);
                            img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bigImageWindow.dismiss();
                                }
                            });
                        } else {
                            img = (ImageView) bigImageWindow.getContentView().findViewById(R.id.popwindow_pro_img);
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(entity.getContent(), 0, entity.getContent().length);
                        img.setImageBitmap(bitmap);
                        bigImageWindow.showAtLocation(findViewById(R.id.scroll), Gravity.CENTER, 0, 0);
                    }
                });
            } else {
                holder.imgPic.setImageResource(R.drawable.archiving_photo);
                holder.imgPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = new WindowTakePictures(CreditCardApplyActivity.this).getWindowView();
                        int viewHeight = Tool.getScreenHeight(CreditCardApplyActivity.this) * 312 / 1230;
                        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, viewHeight);
                        popupWindow.setFocusable(true);
                        popupWindow.showAtLocation(findViewById(R.id.scroll), Gravity.BOTTOM, 0, 0);

                        lastChoosePic = position;
                    }
                });
            }
        }
    }

    private class Holder {
        ImageView imgPic;
        Spinner spinType;
        TextView txtDesc;
        Button btnDel;
        Button btnOk;

        Holder(View convertView) {
            imgPic = (ImageView) convertView.findViewById(R.id.img_photo);
            spinType = (Spinner) convertView.findViewById(R.id.spin_pic_type);
            txtDesc = (TextView) convertView.findViewById(R.id.edit_desc);
            btnDel = (Button) convertView.findViewById(R.id.btn_del);
            btnOk = (Button) convertView.findViewById(R.id.btn_ok);

            initSpinner(spinType, new SpinnerItem[]{
                    DEFAULT_SPINNER_ITEM,
                    new SpinnerItem("01", "身份证件"),
                    new SpinnerItem("02", "学历证明"),
                    new SpinnerItem("03", "房产证明"),
                    new SpinnerItem("04", "车辆证明"),
                    new SpinnerItem("05", "营业执照"),
                    new SpinnerItem("06", "税务证明"),
                    new SpinnerItem("07", "收入证明"),
                    new SpinnerItem("08", "存款证明"),
                    new SpinnerItem("09", "其他证明"),
            });
        }

        void changeChecked() {
            btnOk.setVisibility(View.GONE);
            spinType.setEnabled(false);
            txtDesc.setEnabled(false);
            txtDesc.clearFocus();
        }
    }
//显示性别头像
    private int showGender(String gender) {
        if ("M".equals(gender)) return R.drawable.fm_male;
        if ("F".equals(gender)) return R.drawable.fm_female;
        return 0;
    }
//判断非空
    private String nullToEmpty(Object o) {
        if (o == null) return "";
        return o.toString();
    }
//转int类型
    private Integer toInteger(String s) {
        if (s == null || s.length() == 0) return null;
        return Integer.valueOf(s);
    }
//刷新
    private void refreshListPics() {
        listPics.setAdapter(new PicListAdapter(applyEntity.getAttachedPics()));
        Tool.setListViewHeightByChildren(listPics);

    }

    private void initSpinner(Spinner spinner, SpinnerItem<String>[] items) {
        ArrayAdapter<SpinnerItem<String>> arrayAdapter = new ArrayAdapter<SpinnerItem<String>>(CreditCardApplyActivity.this, android.R.layout.simple_spinner_item, items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
    }
    //展示存储在实体类里边的信息
    private void syncFromEntity() {
        editRecordClerkNo.setText(getIntent().getStringExtra("certcode")); //需要改动的地方,获取身份证或者工号getIntent().getStringExtra("loginUserCode").replace("UR","")applyEntity.getRecordClerkNo()
        editRecordClerkNo.setEnabled(false);//不可编辑
        int type = applyEntity.getApplyType();
        // 主卡
        if (type == 0 || type == 2) {
            spinCaseCode.setSelection(SpinnerItem.getSelectedPosition(spinCaseCode, nullToEmpty(applyEntity.getProjectCode())));//专案代码

            int pSel = SpinnerItem.getSelectedPosition(spinProductCode, nullToEmpty(applyEntity.getProductCode()));//产品代码
            spinProductCode.setSelection(pSel);
            initProductLinkage(pSel, (SpinnerItem<String>)spinProductCode.getAdapter().getItem(pSel));

            spinBrandCode.setSelection(SpinnerItem.getSelectedPosition(spinBrandCode, nullToEmpty(applyEntity.getBrandCode())));// bill date  账单日期
            spinBillDate.setSelection(SpinnerItem.getSelectedPosition(spinBillDate, nullToEmpty(applyEntity.getBillDate())));// bill date  账单日期
            spinUnitCode.setSelection(SpinnerItem.getSelectedPosition(spinUnitCode, nullToEmpty(applyEntity.getUnitCode())));  //unit code  所属单位
            editMPinyin.setText(applyEntity.getMaPyName());//姓名拼音
            editMphone.setText(applyEntity.getMaMobileTel());//移动电话
            editMemail.setText(applyEntity.getMaEmail());//E-mail
            editMhomeAddr.setText(applyEntity.getMaHomeAddr());//住宅地址
            editMhomeZipcode.setText(applyEntity.getMaHomeZip());//住宅邮编
            editMworkAddr.setText(applyEntity.getMaCorpAddr());//工作地址
            editMworkZipcode.setText(applyEntity.getMaCorpZipCode());//工作地邮编
//            editMcensusAddr.setText(applyEntity.getMaCensusAddr());
//            editMcensusZipcode.setText(applyEntity.getMaCensusZipCode());
            {
                initSpinMailAddr();
                spinMmailAddr.setSelection(SpinnerItem.getSelectedPosition(spinMmailAddr, nullToEmpty(applyEntity.getMaStatementAddr())));
            }
            editMdireLinkmanName.setText(applyEntity.getMaDireLinkmanName());//联系人
            spinMdireLinkmanRela.setSelection(SpinnerItem.getSelectedPosition(spinMdireLinkmanRela, nullToEmpty(applyEntity.getMaDireLinkmanRela())));
            editMdireLinkmanMobileTel.setText(applyEntity.getMaDireLinkmanMobileTel());//联系方式
            editMdireLinkmanAddr.setText(applyEntity.getMaDireLinkmanAddr());
            editMothLinkmanName.setText(applyEntity.getMaOthLinkmanName());
            spinMothLinkmanRela.setSelection(SpinnerItem.getSelectedPosition(spinMdireLinkmanRela, nullToEmpty(applyEntity.getMaOthLinkmanRela())));
            editMothLinkmanMobileTel.setText(applyEntity.getMaOthLinkmanMobileTel());
            editMothLinkmanAddr.setText(applyEntity.getMaOthLinkmanAddr());

            editMyearIncome.setText(applyEntity.getMaYearIncome());     //年收入
            rgroupMmaritalStatus.setByCode(applyEntity.getMaMaritalStatus());
            chkMhasChildren.setChecked("1".equals(applyEntity.getMaHasChildren()));

            spinMdegreeCode.setSelection(SpinnerItem.getSelectedPosition(spinMdegreeCode, nullToEmpty(applyEntity.getMaDegreeCode())));
            editMcorpName.setText(nullToEmpty(applyEntity.getMaCorpName()));
            editMcorpTel.setText(nullToEmpty(applyEntity.getMaCorpTel()));

            spinMcorpAttr.setSelection(SpinnerItem.getSelectedPosition(spinMcorpAttr, nullToEmpty(applyEntity.getMaCorpAttr())));
            spinMindustryCode.setSelection(SpinnerItem.getSelectedPosition(spinMindustryCode, nullToEmpty(applyEntity.getMaIndustryCode())));//行业类别
            spinMspecLevel.setSelection(SpinnerItem.getSelectedPosition(spinMspecLevel, nullToEmpty(applyEntity.getMaSpecLevel())));
            spinMdutyCode.setSelection(SpinnerItem.getSelectedPosition(spinMdutyCode, nullToEmpty(applyEntity.getMaDutyCode())));
            editMworkYears.setText(nullToEmpty(applyEntity.getMaWorkYears()));
            spinMhouseStatus.setSelection(SpinnerItem.getSelectedPosition(spinMhouseStatus, nullToEmpty(applyEntity.getMaHouseStatus())));
            editMhousingPrice.setText(nullToEmpty(applyEntity.getMaHouseValue()));
//            editMCarPrice.setText(nullToEmpty(applyEntity.getMaCarValue()));
            editMCertNoId.setText(nullToEmpty(applyEntity.getMaCertNoId()));
        }
        // 附卡
        if (type == 1 || type == 2) {
            editPrimaryCustName.setText(applyEntity.getPrimaryCustName());
            editPriCardNo.setText(applyEntity.getPriCardNo());
            spinPcRela.setSelection(SpinnerItem.getSelectedPosition(spinPcRela, nullToEmpty(applyEntity.getPcRela())));
            editSpyName.setText(applyEntity.getSuPyName());
            editSmobileTel.setText(applyEntity.getSuMobileTel());
            editShomeAddr.setText(applyEntity.getSuHomeAddr());
            editShomeZip.setText(applyEntity.getSuHomeZip());
            editSCertNoId.setText(applyEntity.getSuCertNoId());
        }
    }
//获取输入框的值暴露对外的写入权限
    private void syncFromView() {
        applyEntity.setRecordClerkNo(getIntent().getStringExtra("certcode"));//核心柜员号的值获取到之后传递
        applyEntity.setCustManger(getIntent().getStringExtra("certcode"));//11/30号客户经理号改为跟跟录入柜员号一般applyEntity.getRecordClerkNo()
        // 主卡
        applyEntity.setProjectCode(((SpinnerItem<String>) spinCaseCode.getSelectedItem()).code);
        applyEntity.setProductCode(((SpinnerItem<String>) spinProductCode.getSelectedItem()).code);
        applyEntity.setBrandCode(((SpinnerItem<String>) spinBrandCode.getSelectedItem()).code);
        applyEntity.setBillDate(((SpinnerItem<String>) spinBillDate.getSelectedItem()).code);
        applyEntity.setMaPyName(editMPinyin.getText().toString());
        applyEntity.setUnitCode(((SpinnerItem<String>) spinUnitCode.getSelectedItem()).code);
        applyEntity.setMaMobileTel(editMphone.getText().toString());
        applyEntity.setMaEmail(editMemail.getText().toString());
        applyEntity.setMaHomeAddr(editMhomeAddr.getText().toString());
        applyEntity.setMaHomeZip(editMhomeZipcode.getText().toString());
        applyEntity.setMaCorpAddr(editMworkAddr.getText().toString());
        applyEntity.setMaCorpZipCode(editMworkZipcode.getText().toString());
//        applyEntity.setMaCensusAddr(editMcensusAddr.getText().toString());
//        applyEntity.setMaCensusZipCode(editMcensusZipcode.getText().toString());
        applyEntity.setMaCertNoId(editMCertNoId.getText().toString());

        applyEntity.setMaStatementAddr(((SpinnerItem<String>) spinMmailAddr.getSelectedItem()).code);
        applyEntity.setMaDireLinkmanName(editMdireLinkmanName.getText().toString());
        applyEntity.setMaDireLinkmanRela(((SpinnerItem<String>) spinMdireLinkmanRela.getSelectedItem()).code);
        applyEntity.setMaDireLinkmanMobileTel(editMdireLinkmanMobileTel.getText().toString());
        applyEntity.setMaDireLinkmanAddr(editMdireLinkmanAddr.getText().toString());
        applyEntity.setMaOthLinkmanName(editMothLinkmanName.getText().toString());
        applyEntity.setMaOthLinkmanRela(((SpinnerItem<String>) spinMothLinkmanRela.getSelectedItem()).code);
        applyEntity.setMaOthLinkmanMobileTel(editMothLinkmanMobileTel.getText().toString());
        applyEntity.setMaOthLinkmanAddr(editMothLinkmanAddr.getText().toString());

        applyEntity.setMaYearIncome(editMyearIncome.getText().toString());
        applyEntity.setMaMaritalStatus(rgroupMmaritalStatus.getCode());
        applyEntity.setMaHasChildren(chkMhasChildren.isChecked() ? "1" : "0");
        applyEntity.setMaCorpAttr(((SpinnerItem<String>) spinMcorpAttr.getSelectedItem()).code);
        applyEntity.setMaIndustryCode(((SpinnerItem<String>) spinMindustryCode.getSelectedItem()).code);//行业类别
        applyEntity.setMaSpecLevel(((SpinnerItem<String>) spinMspecLevel.getSelectedItem()).code);
        applyEntity.setMaDutyCode(((SpinnerItem<String>) spinMdutyCode.getSelectedItem()).code);
        applyEntity.setMaWorkYears(toInteger(editMworkYears.getText().toString()));
        applyEntity.setMaHouseStatus(((SpinnerItem<String>) spinMhouseStatus.getSelectedItem()).code);
        applyEntity.setMaHouseValue(toInteger(editMhousingPrice.getText().toString()));
        applyEntity.setMaCorpName(editMcorpName.getText().toString());
        applyEntity.setMaCorpTel(editMcorpTel.getText().toString());
        applyEntity.setMaDegreeCode(((SpinnerItem<String>) spinMdegreeCode.getSelectedItem()).code);
//        applyEntity.setMaCarValue(toInteger(editMCarPrice.getText().toString()));

        // 附卡相关
        applyEntity.setPrimaryCustName(editPrimaryCustName.getText().toString());
        applyEntity.setPriCardNo(editPriCardNo.getText().toString());
        applyEntity.setPcRela(((SpinnerItem<String>) spinPcRela.getSelectedItem()).code);
        applyEntity.setSuPyName(editSpyName.getText().toString());
        applyEntity.setSuMobileTel(editSmobileTel.getText().toString());
        applyEntity.setSuHomeAddr(editShomeAddr.getText().toString());
        applyEntity.setSuHomeZip(editShomeZip.getText().toString());
        applyEntity.setSuCertNoId(editSCertNoId.getText().toString());
    }
//邮寄地址的选择
    private void initSpinMailAddr() {
        List<SpinnerItem<String>> list = new ArrayList<SpinnerItem<String>>(3);
        list.add(DEFAULT_SPINNER_ITEM);
        if (!TextUtils.isEmpty(editMhomeAddr.getText())) {
            list.add(new SpinnerItem<String>("1", "住宅地址"));
        }
        if (!TextUtils.isEmpty(editMworkAddr.getText())) {
            list.add(new SpinnerItem<String>("2", "单位地址"));
        }
        initSpinner(spinMmailAddr, list.toArray(new SpinnerItem[list.size()]));
    }

    private void initProductLinkage(int position, SpinnerItem<String> item) {
        if (item.code.equals(lastProductCode)) {
            return;
        }

        if ("0003".equals(item.code)) { // 公务卡才可以选择所属单位
            spinUnitCode.setEnabled(true);
        } else {
            spinUnitCode.setSelection(SpinnerItem.getSelectedPosition(spinUnitCode, ""));
            spinUnitCode.setEnabled(false);
        }

        for (ParamHelper.Product p : param.products) {
            // init statementDate, brand spinners
            if (p.code.equals(item.code)) {
                initSpinner(spinBrandCode, initSpinnerItems(p.brands));
                SpinnerItem<String>[] s = initSpinnerItemsWithDefault(p.statementDates.size());
                for (int i = 1; i < s.length; i++) {
                    String d = p.statementDates.get(i - 1);
                    s[i] = new SpinnerItem<String>(d, d);
                }
                initSpinner(spinBillDate, s);
                break;
            }
        }

        lastProductCode = item.code;
    }
}
