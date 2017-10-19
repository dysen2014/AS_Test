package com.pactera.financialmanager.ui.newcommonadapter;

import java.util.List;
import com.pactera.financialmanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;  
    protected Context mContext;  
    protected List<T> mDatas;  
    protected final int mItemLayoutId;  
    private int intposition;
  
    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId)  
    {  
        this.mContext = context;  
        this.mInflater = LayoutInflater.from(mContext);  
        this.mDatas = mDatas;  
        this.mItemLayoutId = itemLayoutId;  
    }  
  
    @Override  
    public int getCount()  
    {  
        return mDatas.size();  
    }  
  
    @Override  
    public T getItem(int position)  
    {  
        return mDatas.get(position);  
    }  
  
    @Override  
    public long getItemId(int position)  
    {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent)  
    {  
        final CommonViewHolder CommonViewHolder = getViewHolder(position, convertView,  
                parent);
        if(position%2!=0){
        	CommonViewHolder.getConvertView().setBackgroundColor(mContext.getResources().getColor(R.color.lvtopbg1));
        }else{
        	CommonViewHolder.getConvertView().setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        convert(CommonViewHolder, getItem(position)); 
        intposition=position;
        return CommonViewHolder.getConvertView();  
  
    }  
  
    public abstract void convert(CommonViewHolder helper, T item);  
  
    private CommonViewHolder getViewHolder(int position, View convertView,  
            ViewGroup parent)  
    {  
        return CommonViewHolder.get(mContext, convertView, parent, mItemLayoutId,  
                position);  
    }  
    
    public int getNowPosition(){
    	return intposition;
    }
    
}
