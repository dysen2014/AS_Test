package com.guoguang.util;

import com.guoguang.jni.CHexConver;
import com.pactera.financialmanager.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author tongy 20140320 二代证驱动
 */
public class ParseSFZ {
	public static String preId = null;
	public static String prePhotoPath = null;
	private OutputStream os = null;
	private InputStream is = null;

	public ParseSFZ(BluetoothChatService mChatService) throws IOException {
		os = mChatService.getBsocket().getOutputStream();
		is = mChatService.getBsocket().getInputStream();
	}

	public People read() throws IOException {
		// TODO Auto-generated method stub
		People people = new People();
		String result;
		result = readIDCard(os, is);
		result = analysisMessageUData(2, result);
		LogUtils.d("", "读身份证信息:" + result);
		if (result.length() > 2 && "00".equals(result.substring(0, 2))) {
			result = result.substring(2);
			String[] idcards = result.split(";");
			people.setResultMsg("success");
			people.setPeopleIDCode(idcards[0]);
			people.setPeopleName(idcards[1]);
			people.setPeopleSex(idcards[2]);
			people.setPeopleNation(idcards[3]);
			people.setPeopleBirthday(idcards[4]);
			people.setPeopleAddress(idcards[5]);
			people.setDepartment(idcards[6]);
			people.setStartDate(idcards[7].split("-")[0]);
			people.setEndDate(idcards[7].split("-")[1]);
			// byte[] photo =
			// parsePhoto(CHexConver.hexStringToBytes(idcards[8]));
			byte[] photo = readSdFileBytes(idcards[8]);
			people.setPhoto(photo);
		} else {
			people.setResultMsg(result);
		}

		return people;
	}

	/**
	 * 获得sdcard文件的byte数组
	 */
	public static byte[] readSdFileBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	// ---------------------------------------------IDCARD-----------------------------------------------------------------
	private String readIDCard(OutputStream os, InputStream is)
			throws IOException {
		String msg = "7E2542FFFFFF";
		byte[] buff0 = CHexConver.hexStringToBytes(msg);
		os.write(buff0);
		// find
		String msg1 = "AAAAAA96690003200122";
		byte[] buff1 = CHexConver.hexStringToBytes(msg1);
		os.write(buff1);
		String result1 = getDevRespForIDCARD(is);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String msg2 = "AAAAAA96690003200221";
		byte[] buff2 = CHexConver.hexStringToBytes(msg2);
		os.write(buff2);
		result1 = getDevRespForIDCARD(is);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String msg3 = "AAAAAA96690003300132";
		byte[] buff3 = CHexConver.hexStringToBytes(msg3);
		os.write(buff3);
		result1 = getDevRespForIDCARD(is);

		return result1;
	}

	/**
	 * 按二代证规范收报文 通信加密
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String getDevRespForIDCARD(InputStream is) throws IOException {
		int start = 0;
		byte[] STX = new byte[5];
		byte[] DATALEN = new byte[2];
		byte[] DATA = null;
		String stx;
		String hexStrLen = null;
		String data = null;
		read(STX, is, start, 5);
		stx = CHexConver.byte2HexStr(STX);
		if ("AAAAAA9669".equals(stx)) {
			read(DATALEN, is, start, 2);
			hexStrLen = CHexConver.byte2HexStr(DATALEN);
			int dlen = CHexConver.hexStr2Int(hexStrLen);
			DATA = new byte[dlen];
			read(DATA, is, start, dlen);
			data = CHexConver.byte2HexStr(DATA);
		}
		return stx + hexStrLen + data;
	}

	// --------------------------------------------------SOCKET--------------------------------------------------------------
	private int read(byte[] buf, InputStream is, int start, int len)
			throws IOException {
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

	/**
	 * 分析返回数据单元
	 * 
	 * @param type
	 * @param data
	 * @return
	 * @throws IOException
	 */
	private String analysisMessageUData(int type, String data)
			throws IOException {
		if (data.startsWith("AAAAAA96690508")) {
			return "00" + IDCARDUtilTest.getIdcardString(data);
		} else if (data.equals("1B")) {
			return "21" + "超时";
		} else if ("AAAAAA9669000400008084".equals(data)) {// id卡数据
			return "22查询无卡[8084]";
		} else if ("AAAAAA9669000400008085".equals(data)) {
			return "23选择无卡[8085]";
		} else if ("AAAAAA9669000400004145".equals(data)) {
			return "24读取无卡[4145]";
		} else {
			return "29失败[" + data + "]";
		}
	}

	public class People {
		private String resultMsg;
		private String peopleName;
		private String peopleSex;
		private String peopleNation;
		private String peopleBirthday;
		private String peopleAddress;
		private String peopleIDCode;
		private String department;
		private String startDate;
		private String endDate;
		private byte[] photo;

		public byte[] getPhoto() {
			return photo;
		}

		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}

		public String getPeopleName() {
			return peopleName;
		}

		public void setPeopleName(String peopleName) {
			this.peopleName = peopleName;
		}

		public String getPeopleSex() {
			return peopleSex;
		}

		public void setPeopleSex(String peopleSex) {
			this.peopleSex = peopleSex;
		}

		public String getPeopleNation() {
			return peopleNation;
		}

		public void setPeopleNation(String peopleNation) {
			this.peopleNation = peopleNation;
		}

		public String getPeopleBirthday() {
			return peopleBirthday;
		}

		public void setPeopleBirthday(String peopleBirthday) {
			this.peopleBirthday = peopleBirthday;
		}

		public String getPeopleAddress() {
			return peopleAddress;
		}

		public void setPeopleAddress(String peopleAddress) {
			this.peopleAddress = peopleAddress;
		}

		public String getPeopleIDCode() {
			return peopleIDCode;
		}

		public void setPeopleIDCode(String peopleIDCode) {
			this.peopleIDCode = peopleIDCode;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getResultMsg() {
			return resultMsg;
		}

		public void setResultMsg(String resultMsg) {
			this.resultMsg = resultMsg;
		}

	}
}
