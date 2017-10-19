package com.pactera.financialmanager.ui.commontool;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;

import java.util.List;

/**
 * 我的建档客户-对私客户
 *
 * @author Administrator
 */
public class DevicesBTAdapter extends BaseAdapter {
    private Context context;
    private List<BluetoothDevice> list;

    public DevicesBTAdapter(Activity context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HolderView holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_btdevice, null);
            holder = new HolderView();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvMacAddress = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tvStatucs = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tvAction = (TextView) convertView.findViewById(R.id.tv_action);
            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }
        if (position % 2 != 0) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.lvtopbg));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        // 单个item值
        BluetoothDevice device = list.get(position);
        holder.tvName.setText(device.getName());
        holder.tvMacAddress.setText(device.getAddress());
        holder.tvStatucs.setOnClickListener(new ConnectListener(device.getName(), device.getAddress()));
        holder.tvAction.setOnClickListener(new ReadIDCardListener());
        return convertView;
    }

    public void setlist(List<BluetoothDevice> list) {
        this.list = list;
    }

    class HolderView {
        private TextView tvName;
        private TextView tvMacAddress;
        private TextView tvStatucs;
        private TextView tvAction;
    }

    class ReadIDCardListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //btTools.readIDCardinfo();
        }

    }


    /**
     * 连接
     */
    class ConnectListener implements View.OnClickListener {
        private String devName;
        private String devMacAddress;

        public ConnectListener(String devName, String devMacAddress) {
            this.devName = devName;
            this.devMacAddress = devMacAddress;
        }

        @Override
        public void onClick(View v) {
//            Settings.saveIdCardDeviceMac(context, devMacAddress);
//            BluetoothUtil.connectBlueTooth(context, devName, devMacAddress);
        }
    }

}