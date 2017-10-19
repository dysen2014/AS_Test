package com.pactera.financialmanager.credit.main.service.study;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadThread extends Thread {
    private int threadid;
    private int startposition;
    private RandomAccessFile threadfile;
    private int threadlength;
    private String path;
    public  DownLoadThread(int threadid, int startposition,
                          RandomAccessFile threadfile, int threadlength, String path) {
        this.threadid = threadid;
        this.startposition = startposition;
        this.threadfile = threadfile;
        this.threadlength = threadlength;
        this.path = path;
    }
    public DownLoadThread() {}
    @Override
    public void run() {
        try
        {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            //指定从什么位置开始下载
            conn.setRequestProperty("Range", "bytes="+startposition+"-");
            //System.out.println(conn.getResponseCode());
            if(conn.getResponseCode() == 206)
            {
                InputStream is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                int length = 0;
                while(length < threadlength && (len = is.read(buffer)) != -1)
                {
                    threadfile.write(buffer,0,len);
                    //计算累计下载的长度
                    length += len;
                }
                threadfile.close();
                is.close();
                System.out.println("线程"+(threadid+1) + "已下载完成");
            }
        }catch(Exception ex){System.out.println("线程"+(threadid+1) + "下载出错"+ ex);}
    }

}
