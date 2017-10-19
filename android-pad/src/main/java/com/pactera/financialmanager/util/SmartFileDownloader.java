package com.pactera.financialmanager.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.pactera.financialmanager.db.FileService;
import com.pactera.financialmanager.ui.ParentActivity;

/**
 * �ļ�������
 * 
 * @author
 */
public class SmartFileDownloader {
	private static final String TAG = "SmartFileDownloader";
	private Context context;
	private FileService fileService;
	/* �������ļ����� */
	private int downloadSize = 0;
	/* ԭʼ�ļ����� */
	private int fileSize = 0;
	/* �߳��� */
	private SmartDownloadThread[] threads;
	/* ���ر����ļ� */
	private File saveFile;
	/* ������߳����صĳ��� */
	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
	/* ÿ���߳����صĳ��� */
	private int block;
	/* ����·�� */
	private String downloadUrl;

	/**
	 * ��ȡ�߳���
	 */
	public int getThreadSize() {
		return threads.length;
	}

	/**
	 * ��ȡ�ļ���С
	 * 
	 * @return
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * �ۼ������ش�С
	 * 
	 * @param size
	 */
	protected synchronized void append(int size) {
		downloadSize += size;
	}

	/**
	 * ����ָ���߳�������ص�λ��
	 * 
	 * @param threadId
	 *            �߳�id
	 * @param pos
	 *            ������ص�λ��
	 */
	protected void update(int threadId, int pos) {
		this.data.put(threadId, pos);
	}

	/**
	 * �����¼�ļ�
	 */
	protected synchronized void saveLogFile() {
		this.fileService.update(this.downloadUrl, this.data);
	}

	/**
	 * �����ļ�������
	 * 
	 * @param downloadUrl
	 *            ����·��
	 * @param path
	 *            �ļ���·��
	 * @param threadNum
	 *            �����߳���
	 */
	public SmartFileDownloader(Context context, String downloadUrl,
			String path,
			/* File fileSaveDir, */int threadNum) {
		try {
			this.context = context;
			this.downloadUrl = downloadUrl;
			fileService = new FileService(this.context);
			URL url = new URL(this.downloadUrl);
			StringBuilder sbDir = new StringBuilder();
			sbDir.append(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			sbDir.append(File.separator);
			sbDir.append(ParentActivity.ROOT_DIR);
			sbDir.append(File.separator);
			sbDir.append(path);
			File fileSaveDir = new File(sbDir.toString());
			if (!fileSaveDir.exists())
				fileSaveDir.mkdirs();

			this.threads = new SmartDownloadThread[threadNum];
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", downloadUrl);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			printResponseHeader(conn);
			if (conn.getResponseCode() == 200) {
				this.fileSize = conn.getContentLength();// �����Ӧ��ȡ�ļ���С
				if (this.fileSize <= 0)
					throw new RuntimeException("Unkown file size ");

				// String filename = getFileName(conn);
				String filename = Tool.getURLFileName(this.downloadUrl);
				this.saveFile = new File(fileSaveDir, filename);/* �����ļ� */
				Map<Integer, Integer> logdata = fileService
						.getData(downloadUrl);
				if (logdata.size() > 0) {
					for (Map.Entry<Integer, Integer> entry : logdata.entrySet())
						data.put(entry.getKey(), entry.getValue());
				}
				// �ж�ÿ���߳���Ҫ���ص��ļ���С
				this.block = (this.fileSize % this.threads.length) == 0 ? this.fileSize
						/ this.threads.length
						: this.fileSize / this.threads.length + 1;

				// �ж���һ���Ѿ����ص��ļ���С
				if (this.data.size() == this.threads.length) {
					for (int i = 0; i < this.threads.length; i++) {
						this.downloadSize += this.data.get(i + 1);
					}
					print("�Ѿ����صĳ���" + this.downloadSize);
				}
			} else {
				throw new RuntimeException("server no response ");
			}
		} catch (Exception e) {
			print(e.toString());
			throw new RuntimeException("don't connection this url");
		}
	}

	/**
	 * ��ʼ�����ļ�
	 * 
	 * @param smartDownloadProgressListener
	 * 
	 * @param listener
	 *            �������������ı仯,�����Ҫ�˽�ʵʱ���ص�����,��������Ϊnull
	 * @return �������ļ���С
	 * @throws Exception
	 */
	public int download(SmartDownloadProgressListener listener)
			throws Exception {
		try {
			RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
			if (this.fileSize > 0)
				randOut.setLength(this.fileSize);
			randOut.close();
			URL url = new URL(this.downloadUrl);
			// �ж���������߳������ݿ��б���Ĳ�һ����ô��Ϊ��Ҫ��ͷ��ʼ����
			if (this.data.size() != this.threads.length) {
				this.data.clear();// ������
				for (int i = 0; i < this.threads.length; i++) {
					this.data.put(i + 1, 0);
				}
			}
			for (int i = 0; i < this.threads.length; i++) {
				int downLength = this.data.get(i + 1);
				if (downLength < this.block
						&& this.downloadSize < this.fileSize) { // ���߳�δ�������ʱ,��������
					this.threads[i] = new SmartDownloadThread(this, url,
							this.saveFile, this.block, this.data.get(i + 1),
							i + 1);
					this.threads[i].setPriority(7);
					this.threads[i].start();
				} else {
					this.threads[i] = null;
				}
			}
			// this.fileService.save(this.downloadUrl, this.data);
			boolean notFinish = true;// ����δ���
			while (notFinish) {// ѭ���ж��Ƿ��������
//				Thread.sleep(900);
				notFinish = false;// �ٶ��������
				for (int i = 0; i < this.threads.length; i++) {
					if (this.threads[i] != null && !this.threads[i].isFinish()) {
						notFinish = true;// ����û�����
						if (this.threads[i].getDownLength() == -1) {// �������ʧ��,����������
							this.threads[i] = new SmartDownloadThread(this,
									url, this.saveFile, this.block,
									this.data.get(i + 1), i + 1);
							this.threads[i].setPriority(7);
							this.threads[i].start();
						}
					}
				}
				if (listener != null)
					listener.onDownloadSize(this.downloadSize);
			}
			fileService.delete(this.downloadUrl);
		} catch (Exception e) {
			print(e.toString());
			throw new Exception("file download fail");
		}
		return this.downloadSize;
	}

	/**
	 * ��ȡHttp��Ӧͷ�ֶ�
	 * 
	 * @param http
	 * @return
	 */
	public static Map<String, String> getHttpResponseHeader(
			HttpURLConnection http) {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
		}
		return header;
	}

	/**
	 * ��ӡHttpͷ�ֶ�
	 * 
	 * @param http
	 */
	public static void printResponseHeader(HttpURLConnection http) {
		Map<String, String> header = getHttpResponseHeader(http);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			print(key + entry.getValue());
		}
	}

	// ��ӡ��־
	private static void print(String msg) {
		Log.i(TAG, msg);
	}

}