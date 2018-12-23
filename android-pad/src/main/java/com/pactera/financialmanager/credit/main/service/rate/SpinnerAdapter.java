package com.pactera.financialmanager.credit.main.service.rate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;


/**
 * @author: xiaolijuan
 * @description: Spinner适配器
 * @projectName: SpinnerProject
 * @date: 2015-10-18
 * @time: 00:19
 */
public class SpinnerAdapter extends BaseAdapter {
    private final static String TAG = "SpinnerAdapter";
    private Context context;
    private String[] array;
    private int layoutId;

    /**
     * 构造方法
     * @param context 上下文对象
     * @param array  数组
     * @param layoutId 布局Id
     */
    public SpinnerAdapter(Context context, String[] array, int layoutId) {
        this.context = context;
        this.array = array;
        this.layoutId = layoutId;
    }

    /**
     * 获取Item总数
     * @return
     */
    @Override
    public int getCount() {
        return array.length;
    }

    /**
     * 获取一个Item对象
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return array[position];
    }

    /**
     * 获取指定item的ID
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 绘制的内容均在此实现
     * @param position position就是位置从0开始
     * @param convertView convertView是Spinner中每一项要显示的view
     * @param parent parent就是父窗体了，也就是Spinner
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView != null ? convertView : View.inflate(context, layoutId, null);
        TextView txt_name  = (TextView) item.findViewById(R.id.txt_name);
//        Log.d(TAG, "array[position]: "+array[position]);
//        Log.d(TAG, "txt_name： "+txt_name);
        txt_name.setText(array[position]);
        return item;
    }
}
