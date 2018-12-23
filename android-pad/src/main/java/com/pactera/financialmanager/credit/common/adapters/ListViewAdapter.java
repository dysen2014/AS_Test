package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.financialmanager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 11/7/2017.
 */

public class ListViewAdapter {

    public static class TabAdapter extends BaseAdapter {

        Context mContext;
        List<String> listName = new ArrayList<>();
        List<Integer> listImg = new ArrayList<>();

        public TabAdapter(Context context, String[] names, int[] imgAs) {
            for (int i = 0; i < names.length; i++) {
                listImg.add(imgAs[i]);
                listName.add(names[i]);
            }
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return listName.size();
        }

        @Override
        public Object getItem(int position) {
            return listName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tabImg.setImageResource(listImg.get(position));
            viewHolder.tabName.setText(listName.get(position));

            return view;
        }

        public class ViewHolder {
            @Bind(R.id.tab_img)
            ImageView tabImg;
            @Bind(R.id.tab_name)
            TextView tabName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
