package com.pactera.financialmanager.ui.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Xml;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ConnectedListener;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.User;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HConnection {
	public static final int RESPONSE_ERROR = -1;
	public static final int RESPONSE_TYPE_JSON = 0;
	public static final int RESPONSE_TYPE_PIC = 1;
	public static final int RESPONSE_TYPE_XML = 2;
	public static final int RESPONSE_TYPE_FILE = 3;
	public static final int RESPONSE_TYPE_SIMPLE_FILE = 4;
	public static final int RESPONSE_TYPE_COMPLEX_JSON = 5; // XML包含json
	private HttpURLConnection connection;
	private Object context;
	@SuppressWarnings("unused")
	private String errorMsg, errorId;
	private static final String KEY_UID = "uid";
	private static final String KEY_TOKEN = "token";

	public static final String BOUNDARY = java.util.UUID.randomUUID().toString();
	private final String PREFIX = "--";
	private final String LINEND = "\r\n";
	private String MULTIPART_FROM_DATA = "multipart/form-data";
	private HRequest request;
	@SuppressWarnings("unused")
	private User user;
	private InputStream ins;
	private int type;
	private String path;
	private HResponse response;
	private int connectionIndex;
	public static boolean isShowLoadingProcess = true;
	private Toast toast;

	// public static Runnable connRunable;
	private List<String> additionalParam = new ArrayList<String>();
	private List<String> additionalValue = new ArrayList<String>();

	public HConnection(ParentActivity context, HRequest request) {
		
	}

	public void addConnectionParam(String key, String value, boolean utf8Encode) {
		if (utf8Encode)
			try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		// Log.i("encode", value);
		additionalParam.add(key);
		additionalValue.add(value);
	}

	public void connect(Object context, HRequest request, User user) throws Exception {
		try {
			this.context = context;
			this.request = request;
			this.user = user;
			this.type = RESPONSE_TYPE_JSON;
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
		} catch (Exception e) {
			throw new Exception("something Exception in HConnection connect!" + e);
		}
	}

	public HConnection(Object context, HRequest request, User user, InputStream pic) throws Exception {

		try {
			this.context = context;
			this.request = request;
			this.user = user;
			this.ins = pic;
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
		} catch (Exception e) {
			throw new Exception("something Exception in HConnection constructor2!" + e);
		}
	}

	public HConnection(Object context, HRequest request, User user, int type) throws Exception {
		try {
			this.context = context;
			this.request = request;
			this.user = user;
			this.type = type;
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
		} catch (Exception e) {
			throw new Exception("something Exception in HConnection constructor2!" + e);
		}
	}

	public HConnection(ConnectedListener context, HRequest request, User user, int type) throws Exception {
		try {
			this.context = context;
			this.request = request;
			this.user = user;
			this.type = type;
			if (context != null && isShowLoadingProcess)
				context.onConnectStart();
			isShowLoadingProcess = true;
			initConnection();
		} catch (Exception e) {
			throw new Exception("something Exception in HConnection constructor2!" + e);
		}
	}

	public HConnection(Object context, HRequest request, User user, int type, String path) throws Exception {
		try {
			this.context = context;
			this.request = request;
			this.user = user;
			this.type = type;
			this.path = path;
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
		} catch (Exception e) {
			throw new Exception("something Exception in HConnection constructor2!" + e);
		}
	}

	public void setIndex(int index) {
		this.connectionIndex = index;
	}

	private void addToast(ParentActivity context, int id) {
		toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(R.drawable.comm_error);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}

	public HConnection(Object context, HRequest request, User user) throws Exception {
		try {
			this.context = context;
			this.request = request;
			this.user = user;
			this.type = RESPONSE_TYPE_COMPLEX_JSON;
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("something Exception in HConnection constructor!" + e);
		}
	}

	byte[] buffer = null;

	private void initConnection() throws Exception {
		// isShowLoadingProcess = true;
		if (ins != null) {
			buffer = new byte[ins.available()];
			ins.read(buffer);
			ins.close();
		}
		new Thread() {

			public void run() {
				try {
					String url = request.getURL().replaceAll(" ", "%20");
					url = Tool.getUrlNoChinese(url);
					LogUtils.i("请求url："+url);
					if (url.startsWith("https")) {
						SSLContext ctx = SSLContext.getInstance("TLS");
						ctx.init(null, new TrustManager[] { new X509TrustManager() {
									public void checkClientTrusted(X509Certificate[] chain, String authType) {
									}

									public void checkServerTrusted(X509Certificate[] chain, String authType) {
									}

									public X509Certificate[] getAcceptedIssuers() {
										return new X509Certificate[] {};
									}
								} }, null);
						HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
						HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
							@Override
							public boolean verify(String hostname, SSLSession session) {
								return true;
							}
						});
						connection = (HttpsURLConnection) new URL(url).openConnection();
					} else {
						connection = (HttpURLConnection) new URL(url).openConnection();
					}
					connection.setConnectTimeout(HRequest.TIME_OUT);
					connection.setRequestMethod(request.getMethod());
					// connection.setRequestProperty("Accept",
					// "application/json");
					// connection.setRequestProperty("Accept-Charset", "UTF-8");
					// connection.setRequestProperty("contentType", "UTF-8");

					if (ins != null) {
						connection.setDoOutput(true);
						connection.setRequestProperty("Connection", "Keep-Alive");
						connection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
						OutputStream outStream = connection.getOutputStream();

						ByteArrayOutputStream os = new ByteArrayOutputStream();

						os.write((PREFIX + HConnection.BOUNDARY + LINEND).getBytes());
						os.write(("Content-Disposition: form-data; name=\"file1\"; filename=" + "community_avatar"
						// + LogoActivity.user.getAvatarType()
								+ 0 + " Content-Type: image/png" + LINEND).getBytes());
						os.write(LINEND.getBytes());
						os.write(buffer);
						buffer = null;
						os.write(LINEND.getBytes());
						os.write((PREFIX + HConnection.BOUNDARY + PREFIX + LINEND).getBytes());
						os.flush();
						os.close();
						// ins.close();
						byte[] imgdata = os.toByteArray();
						outStream.write(imgdata);
						outStream.flush();
						outStream.close();

					} else if (additionalParam.size() > 0) {
						connection.setDoOutput(true);
						String content = "";
						for (int i = 0; i < additionalParam.size(); i++) {
							if (i > 0)
								content += "&";
							content += additionalParam.get(i) + "=" + additionalValue.get(i);
						}
						OutputStream out = connection.getOutputStream();
						DataOutputStream dos = new DataOutputStream(out);
						dos.writeBytes(content);
						dos.flush();
						dos.close();
					}
					connection.connect();
					// 获取Cookie
					/*
					 * if (context instanceof Context) { getCookie((Context)
					 * context); } else if (context instanceof Activity)
					 * getCookie((Activity) context); else if
					 * (LoginActivity.loginActivity != null)
					 * getCookie(LoginActivity.loginActivity);
					 */

					response = new HResponse();
					try {
						// response.setResponseCode(this.response.getStatusLine()
						// .getStatusCode());
						response.setResponseCode(connection.getResponseCode());
					} catch (Exception e) {
						e.printStackTrace();
						Thread.interrupted();
						return;
					}
					int code = connection.getResponseCode();
					switch (type) {
					case RESPONSE_TYPE_PIC:
						response.inputStream = connection.getInputStream();
						break;
					case RESPONSE_TYPE_FILE:
						String fileName = Tool.getURLFileName(url);
						if (Tool.haveURLFile(path, fileName)) {
							response.dowloadFile = true;
						} else if (Tool.writeURLFile2SDCard(path, fileName, connection.getInputStream()))
							response.dowloadFile = true;
						break;
					case RESPONSE_TYPE_SIMPLE_FILE:
						fileName = Tool.getURLFileName(url);
						if (Tool.writeURLFile2SDCard(path, fileName, connection.getInputStream()))
							response.dowloadFile = true;
						break;
					case RESPONSE_TYPE_XML:
						String tmpBody = new String(ins2Byte(connection.getInputStream()), "UTF-8");
						tmpBody = tmpBody.replaceAll("&lt;", "<");
						tmpBody = tmpBody.replaceAll("&gt;", ">");
						StringBuilder sb = new StringBuilder();
						sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						sb.append(tmpBody);
						// response.xmlString = sb.toString();
						// response.xmlString = new
						// String(sb.toString().getBytes("utf-8"));
						response.xmlString = URLEncoder.encode(sb.toString(), "UTF-8");
						break;
					case RESPONSE_TYPE_COMPLEX_JSON:
						tmpBody = getJsonString(connection.getInputStream(), "UTF-8");
						if (tmpBody.startsWith("[")) {
							response.dataJsonArray = new JSONArray(tmpBody);
						} else {
							response.dataJsonObject = new JSONObject(tmpBody);
						}

						break;
					// default:
					//
					// BufferedReader bufferedReader = null;
					// if ("gzip".equals(/*
					// * this.response.getHeaders(
					// * "Content-Encoding")
					// */connection
					// .getHeaderField("Content-Encoding"))) {
					// bufferedReader = new BufferedReader(
					// new InputStreamReader(new GZIPInputStream(
					// /*
					// * this.response .getEntity().getContent()
					// */connection.getInputStream())));
					// } else {
					// bufferedReader = new BufferedReader(
					// new InputStreamReader(
					// /*
					// * this.response.getEntity().getContent ()
					// */connection.getInputStream()));
					// }
					// response.dataJsonObject =
					// parseJsonObject(bufferedReader);
					//
					// break;
					}
				} catch (SocketException e) {
					e.printStackTrace();
					// ((ParentActivity) context)
					// .removeDialog(ParentActivity.DIALOG_ID);
					// ((Activity)
					// context).dismissDialog(ParentActivity.DIALOG_ID);
					if (response == null || (response.dataJsonObject == null && response.inputStream == null
							&& response.xmlString == null && !response.dowloadFile && response.dataJsonArray == null)) {

						type = RESPONSE_ERROR;
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putString("value", "" + connectionIndex);
						msg.setData(data);
						msg.what = type;
						if (context instanceof ParentActivity) {
							((ParentActivity) context).handler.sendMessage(msg);
							Looper.prepare();
							addToast((ParentActivity) context, R.string.get_data_false);
							Looper.loop();
						} else if (context instanceof ConnectedListener) {
							((ConnectedListener) context).recieveMessageToHandle(msg);
						} else if (context instanceof ParentFragment) {
							((ParentFragment) context).handler.sendMessage(msg);
						}
					}
					// Thread.interrupted();
					// return;
				} catch (Exception e) {
					e.printStackTrace();
					// ((ParentActivity) context)
					// .removeDialog(ParentActivity.DIALOG_ID);
					// ((Activity)
					// context).dismissDialog(ParentActivity.DIALOG_ID);
					if (response == null || (response.dataJsonObject == null && response.inputStream == null
							&& response.xmlString == null && !response.dowloadFile && response.dataJsonArray == null)) {

						type = RESPONSE_ERROR;
						Message msg = new Message();
						Bundle data = new Bundle();
						data.putString("value", "" + connectionIndex);
						msg.setData(data);
						msg.what = type;
						if (context instanceof ParentActivity) {
							((ParentActivity) context).handler.sendMessage(msg);
							Looper.prepare();
							addToast((ParentActivity) context, R.string.unresolved_address);//当前无法链接
							Looper.loop();
						} else if (context instanceof ConnectedListener) {
							((ConnectedListener) context).recieveMessageToHandle(msg);
						} else if (context instanceof ParentFragment) {
							((ParentFragment) context).handler.sendMessage(msg);
						}
					}
					// Thread.interrupted();
					// return;
				} finally {
					if (context != null) {
						Message msg = new Message();
						Bundle data = new Bundle();
						if (response == null || (response.dataJsonObject == null && response.inputStream == null
								&& response.xmlString == null && !response.dowloadFile
								&& response.dataJsonArray == null)) {
							// data.putString("value", "" + errorId);
							type = RESPONSE_ERROR;
						}
						data.putString("value", "" + connectionIndex);
						msg.setData(data);
						msg.what = type;
						if (context instanceof ParentActivity)
							((ParentActivity) context).handler.sendMessage(msg);
						else if (context instanceof ConnectedListener) {
							((ConnectedListener) context).recieveMessageToHandle(msg);
						} else if (context instanceof ParentFragment) {
							((ParentFragment) context).handler.sendMessage(msg);
						}
					}
					// connection.disconnect();
				}
			}
		}.start();

		// if(context instanceof ParentActivity) {
		//
		// ((ParentActivity) context).handler.post(connRunable);
		//
		// } else if (context instanceof ConnectedListener) {
		// ((ConnectedListener) context)
		// .recieveMessageToHandle(msg);
		// }
	}

	public HResponse getResponse() {
		return getResponse(RESPONSE_TYPE_JSON);
	}

	public HResponse getResponse(int type) {
		try {
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private byte[] ins2Byte(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);
		data = null;
		in.close();
		return outStream.toByteArray();
	}

	@SuppressWarnings("unused")
	private JSONObject parseJsonObject(BufferedReader bufferedReader) throws Exception {
		StringBuilder builder = new StringBuilder();
		JSONObject jsonObject = null;
		try {
			for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
				builder.append(s);
			}
		} catch (IOException e) {
			throw new Exception("json read error!" + e);
		}
		try {
			jsonObject = new JSONObject(new String(builder.toString().getBytes("utf-8")));
		} catch (Exception e) {
			return null;
		}

		return jsonObject;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	// private void getCookie(Context context) {
	// String key = null;
	// String cookieVal = null;
	// for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
	// if (key.equalsIgnoreCase("Set-Cookie")) {
	// cookieVal = connection.getHeaderField(i);
	// if (cookieVal.startsWith(KEY_TOKEN)) {
	// PersistentConfig tmp = new PersistentConfig(context);
	// tmp.setToken(cookieVal.substring(0, cookieVal.indexOf(";")));
	// } else if (cookieVal.startsWith(KEY_UID)) {
	// PersistentConfig tmp = new PersistentConfig(context);
	// tmp.setUid(cookieVal.substring(0, cookieVal.indexOf(";")));
	// }
	// }
	// }
	// PersistentConfig tmp = new PersistentConfig(LoginActivity.loginActivity);
	// }

	@SuppressWarnings("unused")
	private void checkCookie(Context con) {
		PersistentConfig tmp = new PersistentConfig(con);
		String cookie = "";
		if (tmp.getToken() != null)
			// httpReq.addHeader("Cookie", tmp.getToken());
			cookie += tmp.getToken();
		// connection.setRequestProperty("Cookie", tmp.getToken());
		if (tmp.getUid() != null)
			cookie += ";" + tmp.getUid();
		// httpReq.addHeader("Cookie", tmp.getUid());
		// connection.setRequestProperty("Cookie", tmp.getUid());
		if (!cookie.equals("")) {
			connection.setRequestProperty("Cookie", cookie);
			// System.out.println("cookie:" + cookie);
		}
	}

	@SuppressWarnings("unused")
	private List<JSONObject> parseJsonArray(BufferedReader bufferedReader) {
		StringBuilder builder = new StringBuilder();
		JSONArray jsonArray = null;
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
				builder.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			jsonArray = new JSONArray(builder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item;
				try {
					item = jsonArray.getJSONObject(i);
					list.add(item);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public String getJsonString(InputStream inputStream, String type) throws Exception {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, type);

		int event = parser.getEventType();// 产生第一个事件
		String tmpJsonString = null;
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
				break;
			case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
				if ("getJSONReturn".equals(parser.getName())) {// 判断开始标签元素是否是student
					tmpJsonString = parser.nextText();// 得到student标签的属性值，并设置student的id
				}
				break;
			case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
				break;
			}
			event = parser.next();// 进入下一个元素并触发相应事件
		}
		return tmpJsonString;
	}

	public static class PersistentConfig {
		private static final String PREFS_NAME = "prefs_file";

		private SharedPreferences settings;

		public PersistentConfig(Context context) {
			try {
				if (context != null)
					settings = context.getSharedPreferences(PREFS_NAME, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public String getToken() {
			return settings.getString(KEY_TOKEN, "");
		}

		public void setToken(String cookie) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(KEY_TOKEN, cookie);
			editor.commit();
		}

		public String getUid() {
			return settings.getString(KEY_UID, "");
		}

		public void setUid(String cookie) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(KEY_UID, cookie);
			editor.commit();
		}

		public void setCurrentLoginID(String uid) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("loginuid", uid);
			editor.commit();
		}

		public String getCurrentLoginID() {
			return settings.getString("loginuid", "");
		}

		public void setCurrentLoginps(String ps) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("loginps", ps);
			editor.commit();
		}

		public String getCurrentLoginps() {
			return settings.getString("loginps", "");
		}

		public void setCurrentLoginMail(String mail) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("loginmail", mail);
			editor.commit();
		}

		public String getCurrentLoginMail() {
			return settings.getString("loginmail", "");
		}
	}
}
