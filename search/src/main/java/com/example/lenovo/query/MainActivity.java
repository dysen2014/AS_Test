package com.example.lenovo.query;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //贷款余额
    private Button loan_balance_btn;
    private TextView loan_balance_text;
    private EditText Loan_balance_after_text;
    //逾期金额
    private Button overdue_amount_btn;
    private TextView overdue_amount_text;
    private EditText overdue_amount_after_text;
    //逾期期数
    private Button overdue_periods_btn;
    private TextView overdue_periods_text;
    private EditText overdue_periods_after_text;
    //发放日期
    private Button issuing_date_btn;
    private TextView issuing_date_text;
    private EditText issuing_date_after_text;
    //到期日期
    private Button due_date_btn;
    private TextView due_date_text;
    private EditText due_date_after_text;

    //创建Dialog 弹窗
    private Dialog dialog;
    private View contentView;

    //菜单部分
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"大于", "小于", "区间"};
    private WheelView wheel_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loan_balance_btn = (Button) findViewById(R.id.loan_balance_btn);
        loan_balance_text = (TextView) findViewById(R.id.loan_balance_text);
        Loan_balance_after_text = (EditText) findViewById(R.id.Loan_balance_after_text);

        overdue_amount_btn = (Button) findViewById(R.id.overdue_amount_btn);
        overdue_amount_text = (TextView) findViewById(R.id.overdue_amount_text);
        overdue_amount_after_text = (EditText) findViewById(R.id.overdue_amount_after_text);

        overdue_periods_btn = (Button) findViewById(R.id.overdue_periods_btn);
        overdue_periods_text = (TextView) findViewById(R.id.overdue_periods_text);
        overdue_periods_after_text = (EditText) findViewById(R.id.overdue_periods_after_text);

        issuing_date_btn = (Button) findViewById(R.id.issuing_date_btn);
        issuing_date_text = (TextView) findViewById(R.id.issuing_date_text);
        issuing_date_after_text = (EditText) findViewById(R.id.issuing_date_after_text);

        due_date_btn = (Button) findViewById(R.id.due_date_btn);
        due_date_text = (TextView) findViewById(R.id.due_date_text);
        due_date_after_text = (EditText) findViewById(R.id.due_date_after_text);

        initView();

        loan_balance_btn.setOnClickListener(this);
        overdue_amount_btn.setOnClickListener(this);
        overdue_periods_btn.setOnClickListener(this);
        issuing_date_btn.setOnClickListener(this);
        due_date_btn.setOnClickListener(this);

    }

    private void initView() {

        dialog = new Dialog(this, R.style.popupi_dalog);
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_dialog_content, null);
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(btnlistener);
        contentView.findViewById(R.id.btn_confirm).setOnClickListener(btnlistener);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.disappear_dalog);
    }

    @Override
    public void onClick(final View view) {
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        wheel_line = (WheelView) contentView.findViewById(R.id.wheel_line);

        wheel_line.setOffset(1);
        wheel_line.setItems(Arrays.asList(PLANETS));
        wheel_line.setSeletion(1);
        wheel_line.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                switch (view.getId()){
                    case R.id.loan_balance_btn:
                        loan_balance_btn.setText(item);
                        encapsulationHide(item,loan_balance_text,Loan_balance_after_text);
                        break;
                    case R.id.overdue_amount_btn:
                        overdue_amount_btn.setText(item);
                        encapsulationHide(item,overdue_amount_text,overdue_amount_after_text);
                        break;
                    case R.id.overdue_periods_btn:
                        overdue_periods_btn.setText(item);
                        encapsulationHide(item,overdue_periods_text,overdue_periods_after_text);
                        break;
                    case R.id.issuing_date_btn:
                        issuing_date_btn.setText(item);
                        encapsulationHide(item,issuing_date_text,issuing_date_after_text);
                        break;
                    case R.id.due_date_btn:
                        due_date_btn.setText(item);
                        encapsulationHide(item,due_date_text,due_date_after_text);
                         break;
                    default:
                        break;
                }
            }
        });
    }

    private void encapsulationHide(String item, TextView textStr, EditText after_text){
        if (!item.equals("区间")){
            textStr.setVisibility(View.GONE);
            after_text.setVisibility(View.GONE);
        }else {
            textStr.setVisibility(View.VISIBLE);
            after_text.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_confirm:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                // 取消
                case R.id.btn_cancel:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}