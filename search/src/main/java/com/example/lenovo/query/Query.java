package com.example.lenovo.query;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Query extends AppCompatActivity {
    private Context mContext;
    private ViewPager viewPager;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    private Button customer;
    private Button bussiness;
    private static View oldView;
    static int oldColor ;
    private Button[] btns;
    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        mContext = this;
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        customer = (Button) findViewById(R.id.rbtn_0);
        bussiness = (Button) findViewById(R.id.rbtn_1);
        customer.setText(getString(R.string.s_customer));
        bussiness.setText(getString(R.string.s_bussiness));
        btns = new Button[]{customer,bussiness};
        setSelectorItemColor(customer, R.color.color_red);
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
                        setSelectorItemColor(customer, R.color.color_red);
                        break;
                    case 1:
                        setSelectorItemColor(bussiness, R.color.color_red);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void initView() {
        QueryCustomer queryCustomer = new QueryCustomer();
        QueryBusiness queryBusiness = new QueryBusiness();
        list.add(queryCustomer);
        list.add(queryBusiness);
    }
    /**
     * 设置item 被选中时的效果
     */
    public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId) {

        if (oldView == null){
            //第一次点击
            oldView = view;
//            oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
            oldColor =  R.color.gray ;
            view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16)? R.color.gray : colorId);
        }else {
            //非第一次点击
            //把上一次点击的 变化
            oldView.setBackgroundResource(oldColor);
            view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? R.color.gray : colorId);
            oldView = view;
        }
    }

}
