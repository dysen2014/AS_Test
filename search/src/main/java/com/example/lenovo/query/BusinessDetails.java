package com.example.lenovo.query;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/8/1.
 */

public class BusinessDetails extends AppCompatActivity {
    private ViewPager viewPager;
    private Button crmDuebillInfo;
    private Button crmBusinessCountract;
    private Button crmCustomerInfo;
    private Button[] btns;
    private TextView back;
    private int i;
    private static View oldView;
    static int oldColor ;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_details);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        crmDuebillInfo = (Button) findViewById(R.id.crmDuebillInfo);
        crmBusinessCountract = (Button) findViewById(R.id.crmBusinessCountract);
        crmCustomerInfo = (Button) findViewById(R.id.crmCustomerInfo);
        btns = new Button[]{crmDuebillInfo,crmBusinessCountract,crmCustomerInfo};
        setSelectorItemColor(crmDuebillInfo, R.color.color_red);
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
                        setSelectorItemColor(crmDuebillInfo, R.color.color_red);
                        break;
                    case 1:
                        setSelectorItemColor(crmBusinessCountract, R.color.color_red);
                        break;
                    case 2:
                        setSelectorItemColor(crmCustomerInfo, R.color.color_red);
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
        DuebillInfo duebillInfo1 = new DuebillInfo();
        duebillInfo1.setArguments(bundle1);
        BusinessCountract businessCountract1 = new BusinessCountract();
        businessCountract1.setArguments(bundle1);
        Info Info1 = new Info();
        Info1.setArguments(bundle1);
        list.add(duebillInfo1);
        list.add(businessCountract1);
        list.add(Info1);
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
