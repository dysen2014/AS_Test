package com.guoguang.util;

import android.util.Base64;

import com.guoguang.jni.CHexConver;
import com.guoguang.jni.JniCallTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class IDCARDUtilTest {
	private final static String ENCODING = "gb2312";
	public static Map<String, String> getIdcardMap(String idcard_message) {
		idcard_message = idcard_message.substring(28);//0-28 head +512 身份信息；+1024照片
		Map<String, String> map = new HashMap<String, String>();
		try {
			String message = JniCallTest.idcardmsg(idcard_message.substring(0, 512));
			String name = new String(CHexConver.hexStringToBytes(message.split(",")[0]), ENCODING);
			String sex = new String(CHexConver.hexStringToBytes(message.split(",")[1]), ENCODING);
			String nation = new String(CHexConver.hexStringToBytes(message.split(",")[2]), ENCODING);
			String birthday = message.split(",")[3];
			String address = new String(CHexConver.hexStringToBytes(message.split(",")[4]), ENCODING);
			String cardid = message.split(",")[5];
			String department = new String(CHexConver.hexStringToBytes(message.split(",")[6]), ENCODING);
			String startDate = message.split(",")[7];
			String endDate = message.split(",")[8];
			String photo = idcard_message.substring(512,512+1024*2);
			String valid = startDate.substring(0, 4) + ":" + startDate.substring(4, 6) + ":" + startDate.substring(6, 8) + "-" + endDate.substring(0, 4) + ":"
					+ endDate.substring(4, 6) + ":" + endDate.substring(6, 8);
			// store
			map.put("name", name);
			map.put("sex", sex);
			map.put("nation", nation);
			map.put("birthday", birthday);
			map.put("address", address);
			map.put("cardid", cardid);
			map.put("department", department);
			map.put("valid", valid);
			map.put("photo", photo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return map;
	}
	public static String getIdcardString(String idcard_message){
		String result = "";
		idcard_message = idcard_message.substring(28);//0-28 head +512 身份信息；+1024照片
		try {
			String message = JniCallTest.idcardmsg(idcard_message.substring(0, 512));
			String name = new String(CHexConver.hexStringToBytes(message.split(",")[0]), ENCODING);
			String sex = new String(CHexConver.hexStringToBytes(message.split(",")[1]), ENCODING);
			String nation = new String(CHexConver.hexStringToBytes(message.split(",")[2]), ENCODING);
			String birthday = message.split(",")[3];
			String address = new String(CHexConver.hexStringToBytes(message.split(",")[4]), ENCODING);
			String cardid = message.split(",")[5];
			String department = new String(CHexConver.hexStringToBytes(message.split(",")[6]), ENCODING);
			String startDate = message.split(",")[7];
			String endDate = message.split(",")[8];
			String photo = idcard_message.substring(512,512+1024*2);
			//endDate="长期";			
			String valid=startDate.substring(0, 4)+":"+startDate.substring(4, 6)+":"+startDate.substring(6, 8);
			if(endDate.length()<8)valid+="-"+endDate;
			else valid+="-"+endDate.substring(0, 4)+":"+endDate.substring(4, 6)+":"+endDate.substring(6, 8);
			//广东农行版的 jnicall解析不出出生日期 返回为"" id中截取 
			//cj1000演示程序中的jnicall encrypt方法有内存未释放 连续刷卡会照成程序崩溃
			if(birthday.trim().equals("")){
				birthday = cardid.substring(6,14);
			}
			String photoPath = parseIdcardBmpSaveSDcard(photo);
			/*String dstFile = "/sdcard/iasp/idcard/id.jpg";
			//BmpReader.bmpTojpg(photoPath, dstFile);
			BitMapUtils.bmp2jpg(photoPath, dstFile);
			InputStream in = new FileInputStream(dstFile);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            String androidBase64 = Base64.encodeToString(data, Base64.DEFAULT);*/
			
			// store
			result = cardid +";"+name + ";" +sex+ ";" +nation+ ";" +birthday+ ";" +address+ ";" +department+ ";" +valid+ ";" +photoPath;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	private static byte[] parseIdcardBmpSaveMem(String photo) throws Exception {
		//解析二代证图片 存到内存中
		JniCallTest jni = new JniCallTest();
		byte[] src = CHexConver.hexStringToBytes(photo);
		byte[] dst1 = new byte[38556];
    	int res1 = jni.wlt2bmp(src,1024,dst1,38556, 0);	
    	String androidBase64 = Base64.encodeToString(dst1, Base64.DEFAULT);
    	//LogUtil.padOutDebug(new String(dst1));
    	//LogUtil.padOutDebug(androidBase64);
		byte[] bmp = IDCARDUtilTest.bitmap2BMP(dst1);
		return bmp;
	}
	private static String parseIdcardBmpSaveSDcard(String photo) throws Exception {
		JniCallTest jni = new JniCallTest();
		byte[] src = CHexConver.hexStringToBytes(photo);
		byte[] dst1 = new byte[38556];
    	int res1 = jni.wlt2bmp(src,1024,dst1,38556, 0);	
		byte[] bmp = IDCARDUtilTest.bitmap2BMP(dst1);
		//存到文件中
		String path = "/sdcard/iasp/idcard";
    	File file = new File(path);
    	if(!file.exists())file.mkdirs();
    	String filename = path + File.separator + "id.bmp";
    	File bfile = new File(filename);
    	if(bfile.exists())bfile.delete();
    	FileOutputStream fos = new FileOutputStream(bfile);
    	fos.write(bmp, 0, bmp.length);
    	fos.flush();
    	fos.close();
    	return filename;
	}
	/**
	 * 位图数据转bmp文件数据
	 * 只适用于二代证照片的转换，且图片尺寸固定为102*126，24位；位图文件头及位图信息头数据固定，调色板为空。
	 * @param bitmap 位图数据
	 * @return
	 */
	public static byte[] bitmap2BMP(byte[] bitmap){
		int bitmapheadLen = BITMAPFILEHEADER.length + BITMAPINFOHEADER.length;
		int bitmapLen = bitmap.length;
		int fillcount = bitmapLen/BIWIDTH/BYTEPIXEL*FILLPOSI;//补位总数
		byte[] BMP = new byte[bitmapheadLen+bitmapLen+fillcount];
		int bmpLen = 0;
		System.arraycopy(BITMAPFILEHEADER, 0, BMP, 0, BITMAPFILEHEADER.length);
		bmpLen += BITMAPFILEHEADER.length;
		System.arraycopy(BITMAPINFOHEADER, 0, BMP, bmpLen, BITMAPINFOHEADER.length);
		bmpLen += BITMAPINFOHEADER.length;
		int bitLen = 0;
		byte[] by = new  byte[BYTEPIXEL];
		while(bitLen<bitmap.length){
			by = new byte[BYTEPIXEL];
			System.arraycopy(bitmap, bitLen, by, 0, BYTEPIXEL);
			bitLen += BYTEPIXEL;
			int len = BYTEPIXEL;
			for(int k=0;k<BYTEPIXEL;k++){
				BMP[bmpLen++] = by[--len];
			}
			if(bitLen%(BIWIDTH*BYTEPIXEL)==0){
				for(int j=0;j<FILLPOSI;j++){
					BMP[bmpLen++] = FILLBYTE;
				}
			}
		}
		return BMP;
	}
	private static int BIWIDTH = 102;
	private static int BYTEPIXEL = 3;
	private static int FILLPOSI = 2;
	private static byte FILLBYTE = 0x00;
	//位图文件头
	private static  byte[] BITMAPFILEHEADER = {0x42,0x4D,(byte) 0xCE,(byte) 0x97,0x00,0x00,0x00,0x00,0x00,0x00,
													0x36,0x00,0x00,0x00};
	//位图信息头
	private static byte[] BITMAPINFOHEADER = {0x28,0x00,0x00,0x00,0x66,0x00,0x00,0x00,0x7E,0x00,
													0x00,0x00,0x01,0x00,0x18,0x00,0x00,0x00,0x00,0x00,
													0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
													0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
}
