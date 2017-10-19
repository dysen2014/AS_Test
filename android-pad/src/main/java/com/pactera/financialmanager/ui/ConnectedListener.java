package com.pactera.financialmanager.ui;
import android.os.Message;

public interface ConnectedListener {
	public void onConnected(Message msg);
	public void onConnectStart();
	public void recieveMessageToHandle(Message msg);
}
