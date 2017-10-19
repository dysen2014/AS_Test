package com.pactera.financialmanager.ui.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.pactera.financialmanager.ui.ConnectedListener;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 *
 * @author JAY
 *
 */
public class HttpConnSoap {
	private int connectionIndex;
	private ArrayList<NameValuePair> nameValuePairs;
	private HRequest request;
	public static boolean isShowLoadingProcess = true;
	private Object context;
	private int type;
	public static final int RESPONSE_TYPE_JSON = 0;
	public static final int RESPONSE_ERROR = -1;
	private HResponse response;


	public HttpConnSoap(Object context, HRequest request,
						ArrayList<NameValuePair> nameValuePairs) {
		this.context = context;
		this.request = request;
		this.nameValuePairs = nameValuePairs;
		this.type=RESPONSE_TYPE_JSON;

		if (context != null && isShowLoadingProcess) {
			if (context instanceof ParentActivity) {
				((ParentActivity) context).onConnectStart();
			} else if (context instanceof ParentFragment) {
				Activity theAct = ((ParentFragment) context).getActivity();
				if (theAct instanceof ParentActivity) {
					((ParentActivity) theAct).onConnectStart();
				}
			}
		}
		isShowLoadingProcess = true;
		initConnection();
	}

	private void initConnection() {
		new Thread() {
			public void run() {
				String nameSpace = request.getURL().replaceAll(" ", "%20");
				String methodName = request.getMethod();
//				String nameSpace = "http://192.168.1.105:8083/bmc_hb/services/T001157";
//				String methodName = "getJSON";
				Log.i("1111111", "nameSpace=" + nameSpace + ",methodName="
						+ methodName);
				SoapObject soapObject = new SoapObject(nameSpace, methodName);

				// TODO Auto-generated method stub
				for (int i = 0; i < nameValuePairs.size(); i++) {
					soapObject.addProperty(nameValuePairs.get(i).getName()
							.toString(), nameValuePairs.get(i).getValue()
							.toString());

				}
				Log.i("1111111", "soapObject:"+soapObject.toString());

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);

				envelope.bodyOut = soapObject;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(soapObject);
				HttpTransportSE httpTransportSE = new HttpTransportSE(nameSpace);
//				httpTransportSE.debug=true;
				response = new HResponse();
				try {
					httpTransportSE.call(nameSpace, envelope);
					SoapObject object = (SoapObject) envelope.bodyIn;
					Log.i("1111111",envelope.bodyIn.toString());
					if (object!=null) {
						String tmpBody=object.getProperty(0).toString();
						if (tmpBody.startsWith("[")) {
							response.dataJsonArray = new JSONArray(tmpBody);
							Log.i("1111111",
									response.dataJsonArray.toString());
						} else {
							response.dataJsonObject = new JSONObject(tmpBody);
							Log.i("1111111",
									response.dataJsonObject.toString());
						}
					}
				} catch (Exception e) {
					type = RESPONSE_ERROR;
					Log.i("11111111", "e:"+e.toString());
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putString("value", "" + connectionIndex);
					msg.setData(data);
					msg.what = type;
					if (context instanceof ParentActivity) {
						((ParentActivity) context).handler.sendMessage(msg);
						Looper.prepare();
						Looper.loop();
					} else if (context instanceof ConnectedListener) {
						((ConnectedListener) context)
								.recieveMessageToHandle(msg);
					} else if (context instanceof ParentFragment) {
						((ParentFragment) context).handler.sendMessage(msg);
					}
				} finally {
					if (context != null) {
						Message msg = new Message();
						Bundle data = new Bundle();
						if (response == null) {
							type = RESPONSE_ERROR;
						}
						data.putString("value", "" + connectionIndex);
						msg.setData(data);
						msg.what = type;
						if (context instanceof ParentActivity)
							((ParentActivity) context).handler.sendMessage(msg);
						else if (context instanceof ConnectedListener) {
							((ConnectedListener) context)
									.recieveMessageToHandle(msg);
						} else if (context instanceof ParentFragment) {
							((ParentFragment) context).handler.sendMessage(msg);
						}
					}

				}



			}
		}.start();
	}

	public void setIndex(int index) {
		this.connectionIndex = index;
	}

	public HResponse getResponse(int type) {
		return response;
	}

}
