package com.pactera.financialmanager.credit.common.utils;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by dysen on 2017/10/9.
 */

public class InitAdapter {

    private LinearLayoutManager mLayoutManager;

    protected RecyclerView setRecyclerView( FragmentActivity activity, RecyclerView recyclerView, int orientation) {

        //设置固定大小
        recyclerView.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(activity);
        //垂直方向 OrientationHelper.HORIZONTAL 0 OrientationHelper.VERTICAL 1
        mLayoutManager.setOrientation(orientation);
        //添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);

        return recyclerView;
    }

}
