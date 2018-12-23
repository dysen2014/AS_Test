package com.pactera.financialmanager.ui;

import java.util.ArrayList;
import java.util.List;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.entity.AreaEntity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 选择所在地
 * @author JAY
 *
 */
public class LocationSelectWindow extends View{
	private View view;
	private Context context;
	
	private OnLocationSelectListener onLocationSelectListener;
	
	private WheelView wva1, wva2, wva3,wva4,wva5;
	private LinearLayout  ll1, ll2, ll3,ll4,ll5;
	private Button wheel_button;

	
	private CallBackInterface callBack = null;  
	public LocationSelectWindow(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public LocationSelectWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public LocationSelectWindow(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	public void setProvince(List<AreaEntity> list,int indexHuBei){
		if(list==null){
			list=new ArrayList<AreaEntity>();
		}
		wva1.setItems(list);
		wva1.setSeletion(indexHuBei);
		if (list.size()<=0) {
			ll1.setVisibility(View.GONE);
		}else{
			ll1.setVisibility(View.VISIBLE);
		}
	}
	
	public void setCity(List<AreaEntity> list){
		if(list==null){
			list=new ArrayList<AreaEntity>();
		}
		wva2.setItems(list);
		if (list.size()<=0) {
			ll2.setVisibility(View.GONE);
		}else{
			ll2.setVisibility(View.VISIBLE);
		}
	}
	
	public void setArea(List<AreaEntity> list){
		if(list==null){
			list=new ArrayList<AreaEntity>();
		}
		wva3.setItems(list);
		if (list.size()<=0) {
			ll3.setVisibility(View.GONE);
		}else{
			ll3.setVisibility(View.VISIBLE);
		}
	}
	
	public void setCounty(List<AreaEntity> list){
		if(list==null){
			list=new ArrayList<AreaEntity>();
		}
		wva4.setItems(list);
		if (list.size()<=0) {
			ll4.setVisibility(View.GONE);
		}else{
			ll4.setVisibility(View.VISIBLE);
		}
	}
	
	public void setStreet(List<AreaEntity> list){
		if(list==null){
			list=new ArrayList<AreaEntity>();
		}
		wva5.setItems(list);
		if (list.size()<=0) {
			ll5.setVisibility(View.GONE);
		}else{
			ll5.setVisibility(View.VISIBLE);
		}
	}
	
	public View getWindowView() {
		view = View.inflate(context, R.layout.window_location_select, null);
		setupView(view);
        wva1.setOffset(1);
        wva1.setItems(new ArrayList<AreaEntity>());
        wva2.setOffset(1);
        wva2.setItems(new ArrayList<AreaEntity>());
        wva3.setOffset(1);
        wva3.setItems(new ArrayList<AreaEntity>());    
        wva4.setOffset(1);
        wva4.setItems(new ArrayList<AreaEntity>());  
        wva5.setOffset(1);
        wva5.setItems(new ArrayList<AreaEntity>());  
		addListener();
		return view;
	}

	private void addListener() {
        wva1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(String selectedIndex, String item) {
               
                if(onLocationSelectListener!=null){
                	onLocationSelectListener.setOnLocationSelectListener1(selectedIndex, item);
                	wva2.setSeletion(0);
                	 wva3.setSeletion(0);
                	 wva4.setSeletion(0);
                	 wva5.setSeletion(0);
                }
            }
        });
        wva2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(String selectedIndex, String item) {
               
                if(onLocationSelectListener!=null){
                	onLocationSelectListener.setOnLocationSelectListener2(selectedIndex, item);
                	 wva3.setSeletion(0);
                	 wva4.setSeletion(0);
                	 wva5.setSeletion(0);
                }
            }
        });
        wva3.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(String selectedIndex, String item) {
        
                if(onLocationSelectListener!=null){
                	onLocationSelectListener.setOnLocationSelectListener3(selectedIndex, item);
                	 wva4.setSeletion(0);
                	 wva5.setSeletion(0);
                }
            }
        });       
        wva4.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(String selectedIndex, String item) {
        
                if(onLocationSelectListener!=null){
                	onLocationSelectListener.setOnLocationSelectListener4(selectedIndex, item);
               	 wva5.setSeletion(0);
                }
            }
        }); 
        
        wva5.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(String selectedIndex, String item) {
        
                if(onLocationSelectListener!=null){
                	onLocationSelectListener.setOnLocationSelectListener5(selectedIndex, item);
                }
            }
        }); 
        
        
        wheel_button.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
			   if(callBack!=null)
	                callBack.execute("button");  
			}
		});  
	}

	private void setupView(View view) {
		wva1=(WheelView)view.findViewById(R.id.wheel_view_wv1);
		wva2=(WheelView)view.findViewById(R.id.wheel_view_wv2);
		wva3=(WheelView)view.findViewById(R.id.wheel_view_wv3);
		wva4=(WheelView)view.findViewById(R.id.wheel_view_wv4);
		wva5=(WheelView)view.findViewById(R.id.wheel_view_wv5);
		ll1=(LinearLayout) view.findViewById(R.id.wheel_view_ll1);
		ll2=(LinearLayout) view.findViewById(R.id.wheel_view_ll2);
		ll3=(LinearLayout) view.findViewById(R.id.wheel_view_ll3);
		ll4=(LinearLayout) view.findViewById(R.id.wheel_view_ll4);
		ll5=(LinearLayout) view.findViewById(R.id.wheel_view_ll5);

		wheel_button=(Button)view.findViewById(R.id.wheel_button);
	}
	
	public void setOnLocationSelectListener(OnLocationSelectListener onLocationSelectListener){
		if(this.onLocationSelectListener==null){
			this.onLocationSelectListener=onLocationSelectListener;
		}
	}
	
	public void destory(){
		this.onLocationSelectListener=null;
		this.context=null;
	}

	//回调
	public interface CallBackInterface  
	{  
	    public void  execute(String where);  
	} 
	
	public void setCallBack(CallBackInterface callBack){  
        this.callBack = callBack;  
	}	
}

interface OnLocationSelectListener {
	public void setOnLocationSelectListener1(String id, String name);
	public void setOnLocationSelectListener2(String id, String name);
	public void setOnLocationSelectListener3(String id, String name);
	public void setOnLocationSelectListener4(String id, String name);
	public void setOnLocationSelectListener5(String id, String name);
}

