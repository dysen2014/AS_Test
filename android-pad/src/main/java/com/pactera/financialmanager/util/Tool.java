package com.pactera.financialmanager.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Tool {

	public static final String USERID = "userid";
	public static final String PASS = "pass";
	public static final String GENDER = "gender";
	final static int BUFFER_SIZE = 1024;
	@SuppressWarnings("unused")
	private final static String regxpForHtml = "(?<=<(p)>).*(?=<\\/\\1>)"; // 过滤所有以<开头以>结尾的标签
	@SuppressWarnings("unused")
	private final static String regxpZz = "ƵƵ([.]*)ƶƶ";

	private Tool() {
	}

	/** 判断有无sd卡 */
	public static boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/** 以最省内存的方式读取本地资源的图片 */
	public static Bitmap readBitMap(Context context, int resId) {
		// BitmapFactory.Options opt = new BitmapFactory.Options();
		// opt.inPreferredConfig = Bitmap.Config.RGB_565;
		// opt.inPurgeable = true;
		// opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return readBitMap(is);
	}
	

	/**
	 * 将数据格式化
	 * @param value
	 * @return
	 */
	public static String setFormatValue(String value){
		if(TextUtils.isEmpty(value)){
			value = "0.00";
		}else{
			// 字符类型转换(保留两位小数)
			double d = Double.parseDouble(value);
			value = new DecimalFormat("######0.00").format(d);
		}
		return value;
	}



	public static Bitmap readBitMap(InputStream is) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public static Bitmap readBitMap(InputStream is, Boolean inSampleSize) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		if (inSampleSize) {
			try {
				if (is.available() > 1024 * 512
						&& is.available() <= 1024 * 1024)
					opt.inSampleSize = 4;
				else if (is.available() > 1024 * 1024
						&& is.available() <= 1024 * 1024 * 2)
					opt.inSampleSize = 8;
				else if (is.available() > 1024 * 1024 * 2)
					opt.inSampleSize = 16;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		opt.inInputShareable = true;
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/** 获取URL文件名 */
	public static String getURLFileName(String url) {
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		// fileName = URLDecoder.decode(fileName);
		return fileName;
	}

	/** 获取已经下载的该文件 */
	public static File getURLFile(String filePath, String fileName) {
		StringBuilder sbDir = new StringBuilder();
		sbDir.append(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		sbDir.append(File.separator);
		sbDir.append(ParentActivity.ROOT_DIR);
		sbDir.append(File.separator);
		sbDir.append(filePath);
		sbDir.append(File.separator);
		// System.out.println("文件" + sbDir.toString() + fileName);
		String directory = sbDir.toString();
		// File dirFile = new File(directory);
		// if (!dirFile.exists()) {
		// dirFile.mkdirs();
		// }
		return new File(directory, fileName);
	}

	/** 判断是否已经下载该文件 */
	public static boolean haveURLFile(String filePath, String fileName) {
		return getURLFile(filePath, fileName).exists();
	}

	public static boolean creatRootDir() {
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			StringBuilder sbDir = new StringBuilder();
			sbDir.append(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			sbDir.append(File.separator);
			sbDir.append(ParentActivity.ROOT_DIR);
			sbDir.append(File.separator);
			String directory = sbDir.toString();
			Log.i("....directory....", directory);
			File dirFile = new File(directory);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			return true;
		} else {
			return false;
		}
	}
	public static String ToString(Object obj){
		if(obj== null){
			return "";
		}
		return obj.toString();

	}
	public static String ToString(Object obj,String defalut){
		if(obj== null){
			return defalut;
		}
		return obj.toString();

	}

	/** 网络数据写入sd卡 */
	public static boolean writeURLFile2SDCard(String filePath, String fileName,
			InputStream input) {
		String sDStateString = android.os.Environment.getExternalStorageState();
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			StringBuilder sbDir = new StringBuilder();
			sbDir.append(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			sbDir.append(File.separator);
			sbDir.append(ParentActivity.ROOT_DIR);
			sbDir.append(File.separator);
			sbDir.append(filePath);
			// sbDir.append(File.separator);

			String directory = sbDir.toString();
			// System.out.println(directory);
			File dirFile = new File(directory);
			if (!dirFile.exists()) {
				// System.out.println("11111");
				dirFile.mkdirs();
			}
			File file = new File(dirFile, fileName);
			if (!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				byte[] b = new byte[BUFFER_SIZE];
				int j = 0;
				try {
					while ((j = input.read(b)) != -1) {
						fos.write(b, 0, j);
					}
					fos.flush();
					fos.close();
					input.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					if (file.exists())
						file.delete();
					return false;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else
			return false;
		return false;
	}

	// 对url进行中文转码的通用方法
	public static String getUrlNoChinese(String allurl)
			throws UnsupportedEncodingException {
		Matcher tMat = matcherQuery("([\u4e00-\u9fa5]+)", allurl);
		while (tMat.find()) {
			String s = tMat.group(1);
			allurl = allurl.replace(s,
					URLEncoder.encode(URLEncoder.encode(s, "UTF-8"), "UTF-8"));
		}
		return allurl;
	}

	private static Matcher matcherQuery(String pats, String query) {
		Pattern pattern = Pattern.compile(pats, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(query);
		return matcher;
	}

	public static boolean writeURLFile2SDCard(String filePath, String fileName,
			InputStream input, int fileSize) {
		if (writeURLFile2SDCard(filePath, fileName, input)) {
			File file = getURLFile(filePath, fileName);
			if ((int) file.length() == fileSize) {
				Log.i("fileLength", file.length() + "");
				return true;
			} else {
				if (file.exists())
					file.delete();
				return false;
			}
		}
		return false;
	}

	/** SD卡读取File到InputStream */
	public static InputStream readURLFileFromSDCard(String filePath,
			String fileName) throws FileNotFoundException {
		File file = getSDFile(filePath, fileName);
		InputStream is = null;
		if (file.exists())
			is = new FileInputStream(file);
		return is;
	}

	/** InputStream转化为String */
	public static String ips2String(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return encoding == null ? new String(outStream.toByteArray())
				: new String(outStream.toByteArray(), encoding);
	}

	/** String转化为InputStream */
	public static InputStream string2Ips(String in, String encoding)
			throws Exception {

		ByteArrayInputStream is = null;
		if (encoding == null)
			is = new ByteArrayInputStream(in.getBytes());
		else
			is = new ByteArrayInputStream(in.getBytes(encoding));
		return is;
	}

	/** 获取SD卡上的File */
	public static File getSDFile(String path, String fileName) {
		StringBuilder sbDir = new StringBuilder();
		sbDir.append(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		sbDir.append(File.separator);
		sbDir.append(ParentActivity.ROOT_DIR);
		sbDir.append(File.separator);
		sbDir.append(path);
		sbDir.append(File.separator);

		return new File(sbDir.toString(), fileName);
	}

	/** 删除SD卡上的File */
	public static void delSDFile(String path, String fileName) {
		File file = getSDFile(path, fileName);
		if (file.exists()) {
			if (file.isFile())
				file.delete();
		}
	}

	/** 获取PDF文件的intent */
	public static Intent getPDFFileIntent(File file) {
		Uri uri = Uri.fromFile(file);
		String type = "application/pdf";
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, type);
		return intent;
	}

	public static boolean havaNetNoToast(Activity context) {
		try {
			ConnectivityManager cwjManager = (ConnectivityManager) context
					.getApplicationContext().getSystemService(
							Context.CONNECTIVITY_SERVICE);
			if (cwjManager == null) {
				return false;
			} else {
				NetworkInfo info = cwjManager.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean haveNet(Activity context) {
		Toast toast = Toast.makeText(context, R.string.unNetwork,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(R.drawable.comm_error);
		toastView.addView(imageCodeProject, 0);
		if (!havaNetNoToast(context)) {
			toast.show();
			return false;
		} else {
			return true;
		}
	}

	public static String MD5(String value) {
		MessageDigest algorithm = null;
		String MD5 = null;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(value.getBytes());
			MD5 = toHexString(algorithm.digest(), "");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return MD5;
	}

	private static String toHexString(byte[] bytes, String separator) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex).append(separator);
		}
		return hexString.toString();
	}

	public static void writeXml2Device(User user, FileOutputStream os) {
		XmlSerializer serializer = Xml.newSerializer();
		try {
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(os,
					"UTF-8");
			Writer writer = new BufferedWriter(outStreamWriter);
			serializer.setOutput(writer);
			serializer.setOutput(os, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "app-user");

			serializer.startTag("", USERID);
			serializer.text(user.getUserid() + "");
			serializer.endTag("", USERID);

			serializer.startTag("", PASS);
			serializer.text(user.getPassword() + "");
			serializer.endTag("", PASS);

			serializer.startTag("", GENDER);
			serializer.text(user.getGender() + "");
			serializer.endTag("", GENDER);

			serializer.endTag("", "app-user");
			serializer.endDocument();
			serializer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void pareDerivceXML(InputStream xml, User user) {
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(xml, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					// user = new User(context);
					break;
				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase(USERID)) {
						String s = parser.nextText();
						if (s != null && !"".equalsIgnoreCase(s))
							user.setUserid(s);
					} else if (name.equalsIgnoreCase(PASS)) {
						String s = parser.nextText();
						if (s != null && !"".equalsIgnoreCase(s))
							user.setPassword(s);
					} else if (name.equalsIgnoreCase(GENDER)) {
						String s = parser.nextText();
						if (s != null && !"".equalsIgnoreCase(s))
							user.setGender(Integer.parseInt(s));
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					break;
				}
				eventType = parser.next();
			}
			xml.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param 两点的经纬度
	 * @return 两点间距离，单位:米
	 * */
	public static double distance(double lat1, double lon1, double lat2,
			double lon2) {
		double jl_jd = 102834.74258026089786013677476285;
		double jl_wd = 111712.69150641055729984301412873;
		double b = Math.abs((lon1 - lon2) * jl_jd);
		double a = Math.abs((lat1 - lat2) * jl_wd);
		return Math.sqrt((a * a + b * b));
	}

	/**
	 * 过滤掉字符串中的非法字符（还是unicode字符，但由于private区间所以无法显示的字符）
	 * 
	 * @param s
	 * @return
	 */
	public static String filterIllegalChar(String s) {
		if (s == null || s.length() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
			if (ub == Character.UnicodeBlock.PRIVATE_USE_AREA) {
				continue;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 转码
	 * 
	 * @param s
	 * @return
	 */
	public static String unicodeToGBK2(String s) {
		if (s == null)
			return "";
		if (s.contains("<p>"))
			s.replaceAll("<p>", "");
		if (s.contains("</p>"))
			s.replace("</p>", "");
		String[] k = s.split(";");
		if (k == null)
			return "";
		StringBuilder rs = new StringBuilder();
		for (int i = 0; i < k.length; i++) {
			int strIndex = k[i].indexOf("&#");
			String newstr = k[i];
			if (strIndex > -1) {
				String kstr = "";
				if (strIndex > 0) {
					kstr = newstr.substring(0, strIndex);
					rs.append(kstr);
					newstr = newstr.substring(strIndex);
				}
				int m = Integer.parseInt(newstr.replace("&#", ""));
				char c = (char) m;
				rs.append(c);
			} else {
				rs.append(newstr);
			}
		}
		// System.out.println("专为文字" + rs.toString());
		return rs.toString();
	}

	public static final char SoftbankEmojiStart = '\uE001';
	public static final char SoftbankEmojiEnd = '\uE537';

	@SuppressWarnings("unused")
	private static String hasSoftbankEmoji(String content) {
		// String tar = SysUtils.safeParameter(content);
		String tar = content;
		if ("".equals(content) || content == null)
			return "";
		char[] tmpChar = tar.toCharArray();
		for (int i = 0; i < tmpChar.length; i++) {
			char c = tmpChar[i];
			if (c >= SoftbankEmojiStart && c <= SoftbankEmojiEnd) {
				// logger.info("find softbank emoji, hex="+Integer.toHexString(c));
				// System.out.println("find softbank emoji, hex="+Integer.toHexString(c));
				tar = tar.replaceAll(String.valueOf(c),
						"&#x" + Integer.toHexString(c));
			}
		}
		// System.out.println("颜文字" + tar);
		return tar;
	}

	public static int parseInt(String str) {
		if (isNumber(str) &&  str.length() <  String.valueOf(Integer.MAX_VALUE).length())
			return Integer.parseInt(str);

		else
			return 0;
	}

	public static long parseLong(String str) {
		if (isNumber(str))
			return Long.parseLong(str);
		else
			return 0;
	}

	public static boolean isNumber(String str) {
		if(str == null || "".equals(str)){
			return  false;
		}
		char[] chs = str.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] > '9' || chs[i] < '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 
	 * 
	 * 将指定的字符串用MD5加密32位
	 * 
	 * @param originstr
	 *            需要加密的字符串
	 * 
	 * 
	 * @return 加密后的字符串
	 */

	public static String ecodeByMD5(String originstr) {

		String result = null;

		char hexDigits[] = {// 用来将字节转换成 16 进制表示的字符

		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };

		if (originstr != null) {

			try {

				// 返回实现指定摘要算法的 MessageDigest 对象

				MessageDigest md = MessageDigest.getInstance("MD5");

				// 使用utf-8编码将originstr字符串编码并保存到source字节数组

				byte[] source = originstr.getBytes("utf-8");

				// 使用指定的 byte 数组更新摘要

				md.update(source);

				// 通过执行诸如填充之类的最终操作完成哈希计算，结果是一个128位的长整数

				byte[] tmp = md.digest();

				// 用16进制数表示需要32位

				char[] str = new char[32];

				for (int i = 0, j = 0; i < 16; i++) {

					// j表示转换结果中对应的字符位置

					// 从第一个字节开始，对 MD5 的每一个字节

					// 转换成 16 进制字符

					byte b = tmp[i];

					// 取字节中高 4 位的数字转换

					// 无符号右移运算符>>> ，它总是在左边补0

					// 0x代表它后面的是十六进制的数字. f转换成十进制就是15

					str[j++] = hexDigits[b >>> 4 & 0xf];

					// 取字节中低 4 位的数字转换

					str[j++] = hexDigits[b & 0xf];

				}

				result = new String(str);// 结果转换成字符串用于返回

			} catch (NoSuchAlgorithmException e) {

				// 当请求特定的加密算法而它在该环境中不可用时抛出此异常

				e.printStackTrace();

			} catch (UnsupportedEncodingException e) {

				// 不支持字符编码异常

				e.printStackTrace();

			}

		}

		return result;

	}

	// 使用MD5检查密码

	public static boolean checkPWD(String typPWD, String relPWD) {

		if (ecodeByMD5(typPWD).equals(ecodeByMD5(relPWD))) {

			return true;

		}

		return false;

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 圆形图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toCircleBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx, cx, cy;

		if (width <= height) {
			roundPx = width / 2;

		} else {
			roundPx = height / 2;

		}
		cx = width / 2;
		cy = height / 2;
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		paint.setColor(Color.GRAY);// 设置灰色
		paint.setStyle(Paint.Style.FILL);// 设置填满
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(cx, cy, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return output;
	}

	public static int px2dip(Context context, int pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 数组转List
	public static List<String> toList(String[] strInfo) {
		ArrayList<String> theList = new ArrayList<String>();
		for (int i = 0; i < strInfo.length; i++) {
			theList.add(strInfo[i]);
		}
		return theList;
	}

	// List转数组
	public static String[] List2String(List<String> strInfo) {
		int size = strInfo.size();
		String[] StrArr = new String[size];
		for (int i = 0; i < strInfo.size(); i++) {
			StrArr[i] = strInfo.get(i);
		}

		return StrArr;
	}

	// 获取输入框的值
	public static String getTextValue(EditText TheText) {
		if (TheText == null) {
			return "";
		}
		if (!TextUtils.isEmpty(TheText.getText().toString())) {
			return TheText.getText().toString();
		}
		return "";
	}
	public static String getTextValue(TextView TheText) {
		if (TheText == null) {
			return "";
		}
		if (!TextUtils.isEmpty(TheText.getText().toString())) {
			return TheText.getText().toString();
		}
		return "";
	}

	// 获取json里的值
	public static String getJsonValue(JSONObject temps, String keyValue) {
		if (temps == null) {
			return "";
		}
		if (temps.has(keyValue)) {
			try {
				return temps.getString(keyValue);
			} catch (JSONException e) {
				return "";
			}
		}
		return "";
	}

	// 网络请求
	public static String requestHttp(String request, String method) {
		String userCode = "";
		String seriNo = LogoActivity.user.getImei();
		String chnlCode = "02";// 默认安卓客户端
		String brCode = "";

		if (TextUtils.isEmpty(method)) {
			method = "getJSON";
		}
		if (LogoActivity.user != null) {
			userCode = LogoActivity.user.getUserCode();
			brCode = LogoActivity.user.getBrCode();

		}
		String thequest = request + "?method=" + method + "&userCode="
				+ userCode + "&brCode=" + brCode + "&seriNo=" + seriNo
				+ "&chnlCode=" + chnlCode + "&transCode=" + request;
		Log.i("requestHttp", thequest);
		return thequest;

	}

	/**
	 * 是否切换选择按钮
	 * 
	 * @param theImg
	 * @param theflag
	 */
	public static void setSelect(ImageView theImg, int theflag) {
		if (theImg != null) {
			if (theflag == ParentActivity.isTrue) {
				theImg.setImageResource(R.drawable.cusarchiving_swatch_on);
				theImg.setTag("1");
			} else {
				theImg.setImageResource(R.drawable.cusarchiving_swatch_off);
				theImg.setTag("0");
			}

		}
	}
//	public static int getSelect(ImageView theImg, int theflag) {
//		if (theImg != null) {
//
//			if (theImg.getDrawable().getCurrent().getConstantState().equals(getRe)) {
//				theImg.setImageResource(R.drawable.cusarchiving_swatch_on);
//			} else {
//				theImg.setImageResource(R.drawable.cusarchiving_swatch_off);
//			}
//
//		}
//	}

	/**
	 * 这是只有六个参数的查询
	 * 
	 * @param which
	 *            接口号
	 * @return
	 */
	public static String getRequestStr(String which, String custId) {
		String userCode = "";
		String brCode = "";
		String seriNo = "";
		String chnlCode = "02";// 默认安卓客户端
		// 这是目前测试先用的
//		if (LogoActivity.user != null) {
//			userCode = LogoActivity.user.getUserCode();
//			brCode = "1101707";
//			seriNo = "DMRJRUWQF182";
//		}
		// 这是实际要用到的
		if (LogoActivity.user != null) {
			userCode = LogoActivity.user.getUserCode();
			brCode = LogoActivity.user.getBrCode();
//			seriNo = LogoActivity.user.getImei();
			seriNo = "DLXJT06FF18P";

		}
		String requestStr = which + "?method=getJSON&userCode=" + userCode
				+ "&seriNo=" + seriNo + "&chnlCode=" + chnlCode + "&transCode="
				+ which + "&brCode=" + brCode + "&spareOne=" + custId;
		return requestStr;
	}

	/**
	 * 是否切换选择按钮
	 * 
	 * @param theImg
	 * @param theflag
	 */
	public static void setSelect(ImageView theImg, String theflag) {
		if (theImg != null) {
			if (theflag.equals(String.valueOf(ParentActivity.isTrue))) {
				theImg.setImageResource(R.drawable.cusarchiving_swatch_on);
			} else {
				theImg.setImageResource(R.drawable.cusarchiving_swatch_off);
			}

		}
	}

	/**
	 * 这是只有5个字段的
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequest(String which) {
		String userCode = "";
		String brCode = "";
		String seriNo = "";
		String chnlCode = "02";// 默认安卓客户端
		// 这是目前测试先用的
//		if (LogoActivity.user != null) {
//			userCode = LogoActivity.user.getUserCode();
//			brCode = "1101707";
//			seriNo = "DMRJRUWQF182";
//		}
		// 这是实际要用到的
		if (LogoActivity.user != null) {
			userCode = LogoActivity.user.getUserCode();
			brCode = LogoActivity.user.getBrCode();
//			seriNo = LogoActivity.user.getImei();
			seriNo = "DLXJT06FF18P";

		}
		String thequest = which + "?method=getJSON&userCode=" + userCode
				+ "&brCode=" + brCode + "&seriNo=" + seriNo + "&chnlCode="
				+ chnlCode + "&transCode=" + which;
		Log.i("requestHttp", thequest);
		return thequest;

	}

	/**
	 * 这是只有5个字段的
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequest1(String which) {
		String userCode = "";
		String brCode = "";
		String seriNo = "";
		String chnlCode = "02";// 默认安卓客户端
		// 这是目前测试先用的
//		if (LogoActivity.user != null) {
//			userCode = "109011";
//			brCode = "1101707";
//			seriNo = "DMRJRUWQF182";
//		}
		// 这是实际要用到的
		if (LogoActivity.user != null) {
			userCode = LogoActivity.user.getUserCode();
			brCode = LogoActivity.user.getBrCode();
//			seriNo = LogoActivity.user.getImei();
			seriNo = "DLXJT06FF18P";

		}
		String thequest = which + "?method=getJSON&userCode=" + userCode
				+ "&brCode=" + brCode + "&seriNo=" + seriNo + "&chnlCode="
				+ chnlCode + "&transCode=" + which;
		Log.i("requestHttp", thequest);
		return thequest;

	}

	/** 获取屏幕的高度 */
	public static int getScreenHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/** 获取屏幕的宽度 */
	public static int getScreenWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static Boolean checkInputStr(String inputStr) {
		if (TextUtils.isEmpty(inputStr) || "".equals(inputStr)) {
			return true;
		}
		return false;
	}
	public static Boolean checkValidRatio(String inputStr) {
		float input = Float.parseFloat(inputStr);
		if (0.0<= input && input<=100.0) {
			return true;
		}
		return false;
	}

	public static void getTotalHeightofListView(ListView listView,
			Context context) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View mView = mAdapter.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight();
			Log.w("HEIGHT" + i, String.valueOf(totalHeight));
		}
		if (count == 0) {
			totalHeight = Tool.dip2px(context, 45);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	/**
	 * 
	 * 获取中文字符的编码
	 * 
	 * @param chineseEncode
	 *            要编码的中文
	 * @return
	 * @throws UnsupportedEncodingException
	 *             要抛出一个转码异常
	 * 
	 */
	public static String getChineseEncode(String chineseEncode)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(URLEncoder.encode(chineseEncode, "UTF-8"),
				"UTF-8");
	}

	// 字符串转Double
	public static double DoubleParse(String theInfo) {
		double theReturn = 0;
		try {
			theReturn = Double.parseDouble(theInfo);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

		return theReturn;
	}

	// 字符串转int
	public static int IntegerParse(String theInfo) {
		int theReturn = 0;
		if (TextUtils.isEmpty(theInfo) || theInfo.length()>  String.valueOf(Integer.MAX_VALUE).length()) {
			return theReturn;
		}
		if (isNumber(theInfo)) {
			theReturn = Integer.parseInt(theInfo);
		}
		return theReturn;
	}

	/**
	 * 
	 * 权限检查
	 * 
	 * @param rightStr
	 * @return true 有权限；false, 没有权限。
	 * 
	 */
//	public static Boolean checkRightStr(String rightStr) {
//		String[] originalRightStr = Constants.RIGHTSTR;
//        if(null == originalRightStr)
//            return false;
//		List<String> rightList = Arrays.asList(originalRightStr);
//		if (rightList.contains(rightStr)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	public static void setListViewHeightByChildren(ListView lv) {
		if (lv == null) return;
		ListAdapter la = lv.getAdapter();
		if (la == null) return;

		int totalHeight = 0;
		for (int i = 0, len = la.getCount(); i < len; i++) {
			View item = la.getView(i, null, lv);
			item.measure(0, 0);
			totalHeight += item.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.height = totalHeight + lv.getDividerHeight() * (la.getCount() - 1);
	}

	public static View nextView(View v) {
		ViewParent parent = v.getParent();
		if (v == null) return null;
		if (! (parent instanceof ViewGroup)) return null;
		ViewGroup pv = (ViewGroup)parent;

		for (int i = 0; i < pv.getChildCount() - 1; i++) {
			View cur = pv.getChildAt(i);
			if (cur == v) {
				return pv.getChildAt(i + 1);
			}
		}
		return null;
	}
	
	
	// 比较时间大小
	public static boolean daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		if (days<0) {
			return false;
		}else{
			return true;
		}
		
	}

	/**
	 * @Describe 检验组织结构代码是否合法<br>
	 *           标准:GB11714-1995
	 * @since Monlyu 2009-1-11
	 */
	public static boolean cheakOrgCode(String str) {
		final String[] codeNo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "level_a", "level_b",
				"level_c", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z" };
		final String[] staVal = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
				"25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35" };
		Map map = new HashMap();
		for (int i = 0; i < codeNo.length; i++) {
			map.put(codeNo[i], staVal[i]);
		}
		final int[] wi = { 3, 7, 9, 10, 5, 8, 4, 2 };
		Pattern pat = Pattern.compile("^[0-9A-Z]{8}-[0-9X]$");
		Matcher matcher = pat.matcher(str);
		if (!matcher.matches()) {
//			System.out.println("你的表达式非法");
			return false;
		}
		String[] all = str.split("-");
		final char[] values = all[0].toCharArray();
		int parity = 0;
		for (int i = 0; i < values.length; i++) {
			final String val = Character.toString(values[i]);
			parity += wi[i] * Integer.parseInt(map.get(val).toString());
		}
		String cheak = (11 - parity % 11) == 10 ? "X" : Integer.toString((11 - parity % 11));
		return cheak.equals(all[1]);
	}
	
}