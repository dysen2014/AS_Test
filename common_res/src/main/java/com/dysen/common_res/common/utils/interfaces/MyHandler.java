package com.dysen.common_res.common.utils.interfaces;

import android.os.Handler;
import android.os.Message;

/**
 * Created by dysen on 2017/7/13.
 */

public interface MyHandler<T> {

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == -1){

			}
			if (msg.what == -100){

			}
			if (msg.obj != null){

			}
		}
	};
}

