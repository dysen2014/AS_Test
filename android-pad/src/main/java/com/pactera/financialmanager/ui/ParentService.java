package com.pactera.financialmanager.ui;

import com.pactera.financialmanager.ui.service.HConnection;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class ParentService extends Service implements ConnectedListener{

	public int connectionIndex;
	public Handler handler;
	public ParentService() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				onConnected(msg);
			}
		};
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onConnected(Message msg) {
		try {
			switch (msg.what) {
			case HConnection.RESPONSE_ERROR:
				Bundle data = msg.getData();
				connectionIndex = HConnection.RESPONSE_ERROR;
				break;

			default:
				data = msg.getData();
				connectionIndex = Integer.parseInt(data.getString("value"));
				break;
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public void onConnectStart() {
		
	}

	@Override
	public void recieveMessageToHandle(Message msg) {
		handler.sendMessage(msg);
	}

}
