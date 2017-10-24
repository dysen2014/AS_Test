package com.pactera.financialmanager.credit.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentFragment;
import com.dysen.common_res.common.utils.ParamUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.NewLoginActivity;

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

    String[] values = {"安全", "关于", "设置"};
    String[] infos = {"1", "2", "3"};
    int[] resIds = {R.drawable.ic_sys_setting, R.drawable.ic_sys_setting, R.drawable.ic_sys_setting};
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {

        mineTextName.setText(LogoActivity.user.getUsername());
        mineTextOrg.setText(LogoActivity.user.getBrName());
        mineTextSystem.setText(ParamUtils.UserId);
//
////        baseItemMine.setBackgroundColor(Color.parseColor("#d1d4d9"));
//
//        baseItemMine.setValueList(ItemUtils.getValueList(values))//value list
//                .setResIdList(ItemUtils.getResIdList(resIds))//icon list
//                .setItemMarginTop(20)//设置所有 item的间距
//                .setItemMarginTop(1, 0)//设置 position 下的 item间距
//                .setIconHeight(24)//icon 高度
//                .setIconWidth(24)//icon 宽度
//                .setItemMode(BaseItemLayout.Mode.IMAGE)//设置显示模式
//                .setArrowResId(R.drawable.img_right)//设置右边箭头
////                .setItemMode(ItemUtils.getValueList(values).size() - 2, BaseItemLayout.Mode.BUTTON)//设置显示模式
////                .setTrunResId(R.drawable.ic_switch_on)//设置被选中img
////                .setUpResId(R.drawable.ic_switch_off)//设置选中img
////                .setItemMode(ItemUtils.getValueList(values).size() - 1, BaseItemLayout.Mode.TXT)//设置显示模式
////                .setItemRightText(ItemUtils.getInfoList(infos))//只有在Mode.TXT 才需要设置改值
//                .create();
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
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_suggest://建议
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_follow://关注
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_memo://备忘录
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_about://关于
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                break;
            case R.id.mine_txt_quit://退出
                startActivity(new Intent(getActivity(), NewLoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
