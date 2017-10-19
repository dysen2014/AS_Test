package com.guoguang.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.guoguang.jni.CHexConver;
import com.pactera.financialmanager.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class BluetoothChatService {

	protected static final int CONNECTION_SUCCESS = 0;
	protected static final int CONNECTION_FAIL = 1;
	protected static final int CONNECTION_INTERUPT = 2;

	private String uuid = "00001101-0000-1000-8000-00805F9B34FB";

	private BluetoothSocket bsocket = null;
	
	public BluetoothChatService(Handler mHandler) {
		// TODO Auto-generated constructor stub
	}

	public void connect(BluetoothDevice device) throws IOException {
		// TODO Auto-generated method stub
		bsocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
		bsocket.connect();
		
	}
	public boolean checkConnected(){
		boolean flag = false;
		try {
			String msgE = "1B254933";
			LogUtils.d("", "checkConnected:---------------------send Message[1B254933]-----------------------------------------------------------------");
			byte [] PRE = new byte[2];
			byte [] buffE = CHexConver.hexStringToBytes(msgE);
			bsocket.getOutputStream().write(buffE);
			flag = true;
			read(PRE, bsocket.getInputStream(), 0, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			LogUtils.d("", "checkConnected:------------------------------checked finish: "+flag+"----------------------------------------------------------------");
			
		}
		return flag;
	}
	public void disconnect() throws IOException {
		bsocket.close();
	}
	
	public BluetoothSocket getBsocket() {
		return bsocket;
	}

	public void setBsocket(BluetoothSocket bsocket) {
		this.bsocket = bsocket;
	}

	private int read(byte[] buf, InputStream is, int start, int len) throws IOException {
		int readLen = 0;
		int readLencer = 0;
		int _start = start;
		while (true) {
			readLencer = is.read(buf, _start, len - readLen);
			if (readLencer != -1) {
				readLen += readLencer;
				if (readLen == len) {
					return readLen;
				}
				_start = start + readLen;
				continue;
			} else {
				return -1;
			}
		}
	}
}
