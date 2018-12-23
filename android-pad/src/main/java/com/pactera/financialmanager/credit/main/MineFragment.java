package com.pactera.financialmanager.credit.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.mine.AboutActivity;
import com.pactera.financialmanager.credit.main.mine.ProposalActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.login.NewLoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dysen on 2017/8/16.
 */

public class MineFragment extends ParentFragment {

    @Bind(R.id.mine_text_name)
    TextView mineTextName;
    @Bind(R.id.mine_text_org)
    TextView mineTextOrg;
    @Bind(R.id.mine_text_system)
    TextView mineTextSystem;
    @Bind(R.id.mine_txt_security)
    LinearLayout mineTxtSecurity;
    @Bind(R.id.mine_txt_suggest)
    LinearLayout mineTxtSuggest;
    @Bind(R.id.mine_txt_follow)
    LinearLayout mineTxtFollow;
    @Bind(R.id.mine_txt_memo)
    LinearLayout mineTxtMemo;
    @Bind(R.id.mine_txt_about)
    LinearLayout mineTxtAbout;
    @Bind(R.id.mine_txt_quit)
    LinearLayout mineTxtQuit;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.txt_security_img)
    TextView txtSecurityImg;
    @Bind(R.id.txt_suggest_img)
    TextView txtSuggestImg;
    @Bind(R.id.txt_follow_img)
    TextView txtFollowImg;
    @Bind(R.id.txt_memo_img)
    TextView txtMemoImg;
    @Bind(R.id.txt_about_img)
    TextView txtAboutImg;
    @Bind(R.id.tx_quit_img)
    TextView txQuitImg;

    int[] imgId = new int[]{R.mipmap.mine_grey_security, R.mipmap
            .mine_grey_follow, R.mipmap.mine_grey_memo};
    @Bind(R.id.CRM_text_name)
    TextView CRMTextName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {

        txtTitle.setText("我的");
        layBack.setVisibility(View.INVISIBLE);
        if (ParamUtils.returnCredit.equals("Y")) {
            ParamUtils.orgName = "信贷系统ID:" + ParamUtils.orgName;
            mineTextSystem.setText(ParamUtils.UserId);

        }
//        else {
//            ParamUtils.orgName = "暂无关联信贷账户";
//            mineTextSystem.setText("暂无信贷机构");
//        }
        mineTextName.setText(ParamUtils.userName + "(累计登录" + ParamUtils.userLogNum + "天)");
        CRMTextName.setText(LogoActivity.user.getBrName());
        //创建 SpannableString 对象
        SpannableString mStyledText = new SpannableString(ViewUtils.getText(mineTextName));
        //对字符串
        mStyledText.setSpan(new RelativeSizeSpan(0.8f), ViewUtils.getText(mineTextName)
                .indexOf("("), ViewUtils.getText
                (mineTextName).length(), Spannable
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        mStyledText.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.gray)),
                ViewUtils
                        .getText
                                (mineTextName)
                        .indexOf("("), ViewUtils.getText
                        (mineTextName).length(), Spannable
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
        mineTextName.setText(mStyledText);

//        ViewUtils.getText(mineTextName).substring(ViewUtils.getText(mineTextName).indexOf("("));
        mineTextOrg.setText(ParamUtils.orgName);
        LogUtils.v(ParamUtils.orgName + "\tmine" + ParamUtils.UserId + "\t orgId=" + ParamUtils.orgId);

        TextView[] textViews = new TextView[]{txtSecurityImg, txtFollowImg, txtMemoImg};
        LinearLayout[] layouts = new LinearLayout[]{mineTxtSecurity,
                mineTxtFollow, mineTxtMemo};
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setBackgroundResource(imgId[i]);
            layouts[i].setEnabled(false);
//            textViews[i].setClickable(false);
        }
//        txtSecurityImg.setBackgroundResource(R.mipmap.mine_grey_security);
//        txtSuggestImg.setBackgroundResource(R.mipmap.mine_grey_suggest);
//        txtFollowImg.setBackgroundResource(R.mipmap.mine_grey_follow);
//        txtMemoImg.setBackgroundResource(R.mipmap.mine_grey_memo);
//        txt.setBackgroundResource(R.mipmap.mine_grey_);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.mine_txt_security, R.id.mine_txt_suggest, R.id.mine_txt_follow, R.id.mine_txt_memo, R.id.mine_txt_about, R.id.mine_txt_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_txt_security://安全中心
//                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_suggest://建议
                startActivity(new Intent(getActivity(), ProposalActivity.class));
                break;
            case R.id.mine_txt_follow://关注
//                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_memo://备忘录
//                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_about://关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.mine_txt_quit://退出
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
