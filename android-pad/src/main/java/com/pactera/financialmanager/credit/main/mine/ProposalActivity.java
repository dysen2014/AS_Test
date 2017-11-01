package com.pactera.financialmanager.credit.main.mine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProposalActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_proposal)
    EditText edtProposal;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        txtBack.setText(R.string.tab_mine);
        txtTitle.setText("建议");
        btnSubmit.setText("提交建议");
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {

        String text = ViewUtils.getText(edtProposal);
        if (!txtBack.equals("")){
            toast("您的建议提交成功！");
        }else
            toast("请输入建议信息！");
        finish();
    }
}
