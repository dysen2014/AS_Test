package com.pactera.financialmanager.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.model.ReturnCustomer;

/**
 * ListView对应的adapter
 * @author Administrator
 */
public class ReturnCustomerAdapter extends BaseAdapter {
	
	private Context context;
	private List<ReturnCustomer> infolist;
	private String serveModelArray[], serveTypeArray[], csstatusArray[];

    private int mRightWidth = 0;
    private boolean isDel = false;
    
	public ReturnCustomerAdapter(Context context, int rightWidth) {
		super();
		this.context = context;
		mRightWidth = rightWidth;

		serveModelArray = context.getResources().getStringArray(R.array.serveModelArray);
		serveTypeArray = context.getResources().getStringArray(R.array.serveTypeArray);
		csstatusArray = context.getResources().getStringArray(R.array.cusstatus);
	}

	@Override
	public int getCount() {
		if (infolist != null && infolist.size() > 0) {
			return infolist.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return infolist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		HolderView holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.returncus_item, null);
			holder = new HolderView();
			holder.item_left = (LinearLayout)convertView.findViewById(R.id.item_left);
            holder.item_right = (RelativeLayout)convertView.findViewById(R.id.item_right);
			holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
			holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
			holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
			holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
			holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
			holder.view1 = (View) convertView.findViewById(R.id.view1);
			holder.view2 = (View) convertView.findViewById(R.id.view2);
			holder.view3 = (View) convertView.findViewById(R.id.view3);
			holder.view4 = (View) convertView.findViewById(R.id.view4);
			convertView.setTag(holder);
		} else {
			holder = (HolderView) convertView.getTag();
		}
		if (position % 2 != 0) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.lvtopbg));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		// 是否隐藏删除按钮
		if(!isDel){
			holder.item_right.setVisibility(View.GONE);
		}else{
			holder.item_right.setVisibility(View.VISIBLE);
		}
		
		// 填充Item数据
		if(infolist != null && infolist.size() > 0){
			// 将获取到的数据进行展示
			String cusID = infolist.get(position).getCUSTID();
			if(cusID == null){
				cusID = "";
			}
			String cusName = infolist.get(position).getCUSTNAME();
			if(cusName == null){
				cusName = "";
			}
			// 联络去掉转义成文字
			String servemode = infolist.get(position).getSERVEMODE();
			if(!TextUtils.isEmpty(servemode)){
				int servemodeIndex = Integer.parseInt(servemode);
				if(servemodeIndex <= serveModelArray.length){
					servemode = serveModelArray[servemodeIndex];
				}
			}
			// 服务类型
			String servetype = infolist.get(position).getSERVETYPE();
			if(!TextUtils.isEmpty(servetype)){
				int servetypeIndex = Integer.parseInt(servetype);
				if(servetypeIndex <= serveTypeArray.length){
					servetype = serveTypeArray[servetypeIndex];
				}
			}
			// 执行状态
			String csstatus = infolist.get(position).getCSSTATUS();
			if(!TextUtils.isEmpty(csstatus)){
				int csstatusIndex = Integer.parseInt(csstatus);
				// 如果下标等于0，则取数据的第二项（已执行状态）；否者取其它项
				csstatusIndex = csstatusIndex == 0 ? 2 : csstatusIndex;
				if(csstatusIndex <= csstatusArray.length){
					csstatus = csstatusArray[csstatusIndex];
				}
			}else{
				csstatus = "--";
			}
			String serveTitle = infolist.get(position).getSERVETITLE();
			holder.lvItemInfo1.setText(!cusName.equals("") ? cusName : "--");
			holder.lvItemInfo2.setText(!servemode.equals("") ? servemode : "--");
			holder.lvItemInfo3.setText(!servetype.equals("") ? servetype : "--");
			holder.lvItemInfo4.setText(!serveTitle.equals("") ? serveTitle : "--");
			holder.lvItemInfo5.setText(csstatus);
			holder.view1.setVisibility(View.GONE);
			holder.view2.setVisibility(View.GONE);
			holder.view3.setVisibility(View.GONE);
			holder.view4.setVisibility(View.GONE);
			
	        LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
	        holder.item_left.setLayoutParams(lp1);
	        holder.item_right.setLayoutParams(lp2);
	        holder.item_right.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                if (mListener != null) {
	                    mListener.onRightItemClick(v, position);
	                }
	            }
	        });
		}
		
		return convertView;
	}

	public void setInfolist(List<ReturnCustomer> infolist) {
		this.infolist = infolist;
	}
	
	public void setHiddentDel(boolean isDel){
		this.isDel = isDel;
	}

	private class HolderView {
    	private LinearLayout item_left;
    	private RelativeLayout item_right;
		private TextView lvItemInfo1;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextView lvItemInfo5;
		private View view1;
		private View view2;
		private View view3;
		private View view4;
	}
	
	/**
     * 单击事件监听器
     */
    private OnRightItemClickListener mListener = null;
    
    public void setOnRightItemClickListener(OnRightItemClickListener listener){
    	mListener = listener;
    }

    public interface OnRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}
