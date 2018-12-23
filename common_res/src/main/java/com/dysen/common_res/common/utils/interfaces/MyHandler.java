package com.dysen.common_res.common.utils.interfaces;

import android.os.Handler;
import android.os.Message;

/**
 * Created by dysen on 2017/7/13.
 */

public interface MyHandler<T> {

	Handler handler = new Handler(){
		public String s = "";
		public Object obj = null;
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == -1){
				s = "";
			}
			if (msg.what == -2){
				s = "";
			}
			if (msg.what == -3){
				s = "";
			}
			if (msg.what == -100){
				s = "";
			}
			if (msg.obj != null){
				obj = msg.obj;
			}
		}
	};
}

