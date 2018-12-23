package com.pactera.financialmanager.credit.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.example.zhouwei.library.CustomPopWindow;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.WarnMainBean;
import com.pactera.financialmanager.credit.common.adapters.WarnMainAdapter;
import com.pactera.financialmanager.credit.main.service.CheckIn;
import com.pactera.financialmanager.credit.main.service.approval.ApprovalActivity;
import com.pactera.financialmanager.credit.main.service.cus_screening.CusScreeningActivity;
import com.pactera.financialmanager.credit.main.service.rate.RateActivity;
import com.pactera.financialmanager.credit.main.service.relevance_search.RelevanceSearchActivity;
import com.pactera.financialmanager.credit.main.service.search.Query;
import com.pactera.financialmanager.credit.main.service.study.LearningBookcaseActivity;
import com.pactera.financialmanager.credit.main.warn.WarnMainFragment;
import com.pactera.financialmanager.ui.DiscoverCardActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.QuerycusActivity;
import com.pactera.financialmanager.ui.ReturnCusActivity;
import com.pactera.financialmanager.ui.commontool.BranchMapActivity;
import com.pactera.financialmanager.ui.commontool.CustomerMapActivity;
import com.pactera.financialmanager.ui.customermanager.CusArchivingStepOne;
import com.pactera.financialmanager.ui.customermanager.MyscusActivity;
import com.pactera.financialmanager.ui.fragment.SigninRecordActivity;
import com.pactera.financialmanager.ui.hallfirst.HallFirstActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.model.CreditFwMenu;
import com.pactera.financialmanager.ui.model.FwMenu;
import com.pactera.financialmanager.ui.model.QtMenu;
import com.pactera.financialmanager.ui.productcenter.HotProductActivity;
import com.pactera.financialmanager.ui.productcenter.PromotionsActivity;
import com.pactera.financialmanager.util.LimitsUtil;
import com.pactera.financialmanager.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 2017/8/16.
 */

public class ServiceFragment extends ParentFragment {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;

    List<List<String>> listNames = new ArrayList<>();
    List<List<String>> listNamesInfo = new ArrayList<>();
    List<List<String>> listNamesPinyin = new ArrayList<>();
    List<List<Integer>> listImages = new ArrayList<>();

    String[] titles = new String[]{"客户关系", "信贷业务", "其他"};
    int[] titleColor = new int[]{R.color.warn_khgx, R.color.warn_xd, R.color.colorAccent};
    public static String[] Names = new String[]{"客户建档", "我的建档", "客户分布", "服务记录", "客户查询", "信用卡查询",
            "优惠活动", "热销产品", "厅堂管理"
            , "利率测算", "多维查询", "学习园地", "业务签批", "黑灰名单", "关联查询"
            , "网点地图", "签到", "签到记录"};
    String[] NamesInfo = new String[]{"采集客户的基本信息、金融信息和照片信息等",
            "查看已建档的客户信息",
            "查看已建档客户的分布情况",
            "记录我服务和营销的客户历史",
            "查询全省范围内的客户信息",
            "查询信用卡客户的开卡进度、激活和逾期情况",
            "查看最新的营销活动",
            "查看法人范围内最近热销的理财产品",
            "查看网点排队信息",
            "根据法人行社制定的利率模型对客户进行简单的利率测算",
            "查询用户管户的借据信息或客户信息",
            "查看各类指导办法或政策性文件",
            "有权用户可审批自己权限内的贷款",
            "黑灰名单：查询全省范围内的黑、灰名单客户",
            "关联查询：查询客户所有关联人近一年在我行贷款情况",
            "查看全省的网点信息",
            "进行工作签到",
            "查看最近7天的签到记录",
    };
    public static int[] imgAs = new int[]{R.mipmap.service_khjd, R.mipmap.service_wdjd, R.mipmap
            .service_khfb, R.mipmap.service_fwjl, R.mipmap.service_khcx, R.mipmap.service_xykcx, R.mipmap
            .service_yhhd, R.mipmap.service_rxcp, R.mipmap.service_ttgl, R.mipmap.service_llcs, R
            .mipmap.service_xdcx, R.mipmap.service_xxyd, R.mipmap.service_ldsp, R.mipmap.service_khsc,
            R.mipmap.service_glcx, R.mipmap.service_wddt, R.mipmap.service_qd, R.mipmap.service_qdjl};

    List<WarnMainBean.Content> listCrm = new ArrayList<>();
    List<WarnMainBean.Content> listCredit = new ArrayList<>();
    List<WarnMainBean.Content> listOther = new ArrayList<>();
    List<WarnMainBean> listData = new ArrayList<>();

    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_customerProfile)
    TextView txtCustomerProfile;
    @Bind(R.id.txt_mineProfile)
    TextView txtMineProfile;
    @Bind(R.id.txt_customerDistribution)
    TextView txtCustomerDistribution;
    @Bind(R.id.txt_customerData)
    TextView txtCustomerData;
    @Bind(R.id.txt_customerSearch)
    TextView txtCustomerSearch;
    @Bind(R.id.txt_serviceRecord)
    TextView txtServiceRecord;
    @Bind(R.id.txt_hallManagement)
    TextView txtHallManagement;
    @Bind(R.id.txt_promotions)
    TextView txtPromotions;
    @Bind(R.id.txt_hotProducts)
    TextView txtHotProducts;
    @Bind(R.id.txt_creditIncoming)
    TextView txtCreditIncoming;
    @Bind(R.id.txt_creditSearch)
    TextView txtCreditSearch;
    @Bind(R.id.txt_interestRateCalculation)
    TextView txtInterestRateCalculation;
    @Bind(R.id.txt_study)
    TextView txtStudy;
    @Bind(R.id.txt_approval)
    TextView txtApproval;
    @Bind(R.id.txt_outletsMap)
    TextView txtOutletsMap;
    @Bind(R.id.txt_checkIn)
    TextView txtCheckIn;
    @Bind(R.id.txt_checkInRecord)
    TextView txtCheckInRecord;

    private String certcode;
    private CustomPopWindow mListPopWindow;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (checkObjValid(mListPopWindow))
                mListPopWindow.dissmiss();
//            ApprovalsLogon:审批密码验证(Y或者N)\

            if (msg.what == -1) {
                toast(getResources().getString(R.string.request_timeout));
            } else {
                if (msg.obj != null) {
                    try {
                        String loginState = HttpThread.parseJSON(msg.obj.toString()).getString("ApprovalsLogon");
                        if (loginState.equals("Y"))
                            startActivity(new Intent(getActivity(), ApprovalActivity.class));
                        else if (loginState.equals("N"))
                            ShowDialog(getActivity(), "登录失败!\n" + loginState);
                        toast("登录失败!");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initData() {

        List<FwMenu> fwMenus = LogoActivity.user.getFw_menu();
        if (fwMenus != null) {
            for (int i = 0; i < fwMenus.size() + 1; i++) {

                WarnMainBean.Content warnMainBean;
                if (i == fwMenus.size()) {
                    warnMainBean = new WarnMainBean.Content(R.drawable.hall_management, "厅堂管理",
                            "厅堂管理", "ttgl", "1");
                } else {
                    warnMainBean = new WarnMainBean.Content(imgAs[i], fwMenus.get(i)
                            .getMENU_NAME(),
                            fwMenus.get(i).getMENU_MESSAGE(), fwMenus.get(i)
                            .getMENU_PICTURE_ID(), "1");
                }
                listCrm.add(warnMainBean);
            }
        }
        List<CreditFwMenu> creditFwMenus = WarnMainFragment.fwMenuList;
        for (int i = 0; i < creditFwMenus.size(); i++) {
            WarnMainBean.Content warnMainBean = new WarnMainBean.Content(imgAs[i], creditFwMenus
                    .get(i).getMENU_NAME(),
                    creditFwMenus.get(i).getMENU_MESSAGE(), creditFwMenus.get(i).getMENU_PICTURE_ID(), "1");
            listCredit.add(warnMainBean);
        }

        List<QtMenu> qtMenus = LogoActivity.user.getQt_menu();
        if (qtMenus != null){
            for (int i = 0; i < qtMenus.size(); i++) {
                WarnMainBean.Content warnMainBean = new WarnMainBean.Content(imgAs[i], qtMenus.get(i)
                        .getMENU_NAME(),
                        qtMenus.get(i).getMENU_MESSAGE(), qtMenus.get(i).getMENU_PICTURE_ID(), "1");
                listOther.add(warnMainBean);
            }
        }

        listData.add(new WarnMainBean(titles[0], titleColor[0], listCrm));
        if (listCredit.size() > 0) {
            listData.add(new WarnMainBean(titles[1], titleColor[1], listCredit));
        }
        listData.add(new WarnMainBean(titles[2], titleColor[2], listOther));


        pullLoadMore.setAdapter(new WarnMainAdapter.MyServiceAdapter(getActivity(), listData, new
                OnItemClickCallback<Integer>() {
                    @Override
                    public void onClick(View view, Integer info) {

                    }

                    @Override
                    public void onLongClick(View view, Integer info) {

                        LogUtils.d("asdasdasd");

                    }

                    @Override
                    public void onClick(View view, int position, int index) {
                        LogUtils.d("position" + position);
                        LogUtils.d("index" + index);


                        boolean isRight = false;
                        Intent intent = new Intent();
                        String picture = listData.get(position).list.get(index).getName_picture();
                        String Name = listData.get(position).list.get(index).getName();
                        String NameInfo = listData.get(position).list.get(index).getNameInfo();

                        intent.putExtra("Name", Name);
                        intent.putExtra("NameInfo", NameInfo);
                        switch (picture) {

                            case "khjd"://客户建档
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0302);
                                if (isRight) {
                                    intent.setClass(getContext(), CusArchivingStepOne.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "wdjd"://我的建档
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0301);
                                if (isRight) {
                                    intent.setClass(getContext(), MyscusActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "khfb"://客户分布
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0601);
                                if (isRight) {
                                    intent.setClass(getContext(), CustomerMapActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "fwjl"://服务记录
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T04);
                                if (isRight) {
                                    intent.setClass(getContext(), ReturnCusActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "khcx"://客户查询
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T02);
                                if (isRight) {
                                    intent.setClass(getContext(), QuerycusActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "xykcx"://信用卡查询
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T07);
                                if (isRight) {
                                    intent.setClass(getContext(), DiscoverCardActivity.class);
                                    intent.putExtra("certcode", LogoActivity.user.getCERTCODE());
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "yhhd"://优惠活动
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0801);
                                if (isRight) {
                                    intent.setClass(getContext(), PromotionsActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "rxcp"://热销产品
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0802);
                                if (isRight) {
                                    intent.setClass(getContext(), HotProductActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "ttgl"://厅堂管理
//                                        isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T01);
//                                        if(isRight){
                                intent.setClass(getContext(), HallFirstActivity.class);
                                startActivity(intent);
//                                        }else
//                                            toast("您,暂无厅堂管理权限!");
                                break;
                            case "llcs"://利率测算
                                intent.setClass(getContext(), RateActivity.class);
                                startActivity(intent);
                                break;
                            case "dwcx"://多维查询
                                intent.setClass(getContext(), Query.class);
                                startActivity(intent);
                                break;
                            case "xxyd"://学习园地
                                intent.setClass(getContext(), LearningBookcaseActivity.class);
                                startActivity(intent);
                                break;
                            case "ywqp"://业务签批
                                LogUtils.i("approvals:" + ParamUtils.approvals);
                                if (ParamUtils.approvals.equals("Y")) {
                                    showPopListView(view);
                                } else if (ParamUtils.approvals.equals("N")) {
                                    toast("您,暂无审批权限!");
//                                            ShowDialog(getContext(), "您,暂无审批权限!");
                                }
                                break;
                            case "hhcx"://黑灰名单
                                intent.setClass(getContext(), CusScreeningActivity.class);
                                startActivity(intent);
                                break;
                            case "glcs"://关联查询
                                intent.setClass(getContext(), RelevanceSearchActivity.class);
                                startActivity(intent);
                                break;

                            case "wddt"://网点查询
                                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0602);
                                if (isRight) {
                                    intent.setClass(getContext(), BranchMapActivity.class);
                                    startActivity(intent);
                                } else
                                    toast("您,暂无权限!");
                                break;
                            case "qd"://签到
//                                        isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0501, true);
                                intent.setClass(getContext(), CheckIn.class);
                                startActivity(intent);
                                break;
                            case "qdjl"://签到记录
//                                        isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0502, false);
//                                        if (!isRight) {
                                intent.setClass(getContext(), SigninRecordActivity.class);
                                startActivity(intent);
//                                        }else
//                                            toast("您,暂无权限!");
                                break;
                        }


                    }
                }));
    }

    private void initView() {

        txtTitle.setText("服务");
        layBack.setVisibility(View.INVISIBLE);
        uberPgsview.setVisibility(View.INVISIBLE);

        pullLoadMore.setGridLayout(1);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setPullRefreshEnable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getContext());
    }

    private void showPopListView(View view) {

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_login, null);

        TextView approvalTitleName = (TextView) contentView.findViewById(R.id.approval_title_name);
        final TextView approvalName = (TextView) contentView.findViewById(R.id.approval_name);
        final EditText approvalPwd = (EditText) contentView.findViewById(R.id.approval_pwd);
        Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
        Button btnOk = (Button) contentView.findViewById(R.id.btn_ok);

//        approvalName.setText("B129103");
        ParamUtils.UserIdApproval = ParamUtils.UserId;
        approvalName.setText(ParamUtils.UserIdApproval);
        final String name = ViewUtils.getText(approvalName);

        //创建并显示popWindowz
        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.6f)
                .create()
                .showAtLocation(view, Gravity.CENTER, 200, 200);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopWindow.dissmiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pwd = ViewUtils.getText(approvalPwd);
                approvalName.setText("帐号：\t" + "B129103");
                if (!name.equals("") && !pwd.equals("")) {
                    sendRequest(name, pwd);
                    toast("审批登录请求中！");
                } else {
                    toast("帐号，密码不能为空");
                }
            }
        });
    }

    private void sendRequest(String name, String pwd) {
        LogUtils.v(name + "-------name----------pwd-------" + pwd);
        //{"deviceType":"Android","RequestParams":{UserID:登陆用户, PassWord:登陆密码}}
        JSONObject jsonObject = ParamUtils.setParams("login", "crmApprovalsLogon", new Object[]{name, pwd}, 2);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, mHandler);
    }
}
