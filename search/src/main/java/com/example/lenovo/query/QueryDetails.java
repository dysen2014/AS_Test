package com.example.lenovo.query;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/17.
 */

public class QueryDetails extends AppCompatActivity {
    private ViewPager viewPager;
    private Button[] btns;
    private Button keyman;
    private Button loanafter;
    private Button info;
    private Button creditProve;
    private Button entOwner;
    private TextView back;
    private List<String> mTitle = new ArrayList<String>();
    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    private static View oldView;
    private JSONObject jsonObject1;
    private int i;
    static int oldColor ;
    private View title;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_details);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        keyman = (Button) findViewById(R.id.keyman);
        loanafter = (Button) findViewById(R.id.loanafter);
        info = (Button) findViewById(R.id.crmCustomerInfo);
        creditProve = (Button) findViewById(R.id.crmCreditProve);
        entOwner = (Button) findViewById(R.id.crmEntOwner);
        title = (View) findViewById(R.id.title);
        btns = new Button[]{info,keyman,loanafter,creditProve,entOwner};
        setSelectorItemColor(info, R.color.color_red);
        int t = entOwner.getMeasuredHeight();
        Log.i("t", String.valueOf(t));
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
        for(i=0;i<btns.length;i++){
            btns[i].setOnClickListener(new View.OnClickListener() {
                int index = i;
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(index);
                }
            });
        }

        myFragmentPagerAdapter adapter = new myFragmentPagerAdapter(getSupportFragmentManager(),list);

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setSelectorItemColor(info, R.color.color_red);
                        title.scrollTo(0,0);
                        break;
                    case 1:
                        setSelectorItemColor(keyman, R.color.color_red);
                        break;
                    case 2:
                        setSelectorItemColor(loanafter, R.color.color_red);
                        break;
                    case 3:
                        setSelectorItemColor(creditProve, R.color.color_red);
                        break;
                    case 4:
                        setSelectorItemColor(entOwner, R.color.color_red);
                        int offset = title.getMeasuredWidth()- entOwner.getMeasuredWidth();
                        title.scrollTo(offset,0);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initView() {

        Bundle bundle1 = this.getIntent().getExtras();
        Info Info1 = new Info();
        Info1.setArguments(bundle1);
        Keyman keyman1 = new Keyman();
        keyman1.setArguments(bundle1);
        LoanAfter loanAfter1 = new LoanAfter();
        loanAfter1.setArguments(bundle1);
        CreditProve creditProve1 = new CreditProve();
        creditProve1.setArguments(bundle1);
        EntOwner entOwner1 = new EntOwner();
        entOwner1.setArguments(bundle1);
        list.add(Info1);
        list.add(keyman1);
        list.add(loanAfter1);
        list.add(creditProve1);
        list.add(entOwner1);
    }

    /**
     * 设置item 被选中时的效果
     */
    public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId) {

//		LogUtils.d("colorId:"+colorId);
        if (oldView == null){
            //第一次点击
            oldView = view;
//            oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
            oldColor =  R.color.common_yellow ;
            view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16)? R.color.common_yellow : colorId);
        }else {
            //非第一次点击
            //把上一次点击的 变化
            oldView.setBackgroundResource(oldColor);
            view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? R.color.common_yellow : colorId);
            oldView = view;
        }
    }
}
