package com.dysen.common_res.common.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dysen.common_res.R;

import java.util.List;

/**
 * Created by dysen on 11/30/2017.
 * 公用Adapter
 */
public class CommonAdapter {

    public class BaseAdapter extends BaseQuickAdapter<Class<?>, BaseViewHolder> {

        public BaseAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Class<?> item) {
            helper.setText(R.id.text, "");
            helper.setImageResource(R.id.icon, 0);
        }
    }
}