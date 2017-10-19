package com.pactera.financialmanager.credit.main.service.study;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.FileUtils;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.views.NoscrollListView;
import com.pactera.financialmanager.credit.main.service.study.adapter.FilestudyAdapter;
import com.pactera.financialmanager.credit.main.service.study.bean.Filestudy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudyActivity extends ParentActivity implements BaseRefreshListener {

    private static final String TAG = "StudyActivity";
    private static final int COMPLETED = 0;
    private static final String URL = ParamUtils.paramIp + "/ALS7M/AttachView?";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    @Bind(R.id.et_fileName)
    EditText etFileName;
    @Bind(R.id.in_button)
    Button inButton;
    @Bind(R.id.ptr_layout)
    PullToRefreshLayout ptrLayout;
    @Bind(R.id.list_view)
    NoscrollListView listView;

    final int PAGE_SIZE = 10;
    int curPage = 1;

    private List<Filestudy> filestudies = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                FilestudyAdapter adapter = new FilestudyAdapter(StudyActivity.this, R.layout.file, filestudies);
                listView.setAdapter(adapter);
                //单击listView中的view
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Log.d(TAG, "AdapterView: " + adapterView);
                        Filestudy filestudy = new Filestudy("", "", "", "", "");
                        filestudy = (Filestudy) listView.getItemAtPosition(position);
                        listView = (NoscrollListView) adapterView;
                        Log.d(TAG, "View: " + filestudy.getAddress());
                        //获取文件地址
                        StringBuffer sb = new StringBuffer(URL);
                        try {
                            //对含中文名字的文件进行转码urf-8
                            sb.append("docNo=" + filestudy.getDocNo() + "&");
                            sb.append("attachmentNo=" + filestudy.getAttachmentNo() + "&");
                            sb.append("fileName=" + URLEncoder.encode(filestudy.getFileName(), "UTF-8") + "&");
                            sb.append("FullPath=" + filestudy.getAddress().substring(0, 15) + URLEncoder.encode(filestudy.getAddress().substring(15), "UTF-8"));
                            toast("File is downloading!");
                            downLoadFromStream(sb.toString(), filestudy.getFileName());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "url: " + sb.toString());

                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
        ptrLayout.setRefreshListener(this);
        //获取文件列表
        reqFileList("", curPage);
    }

    private void sendRequestWithOkHttpPost(final String url, final JSONObject obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, obj.toString());
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).post(body).build();
                    LogUtils.d("http", "sendRequest: " + url + obj);
                    Response response = client.newCall(request).execute();
                    //Log.d(TAG, "body: "+response.body());
                    String responseData = response.body().string();
                    LogUtils.d("http", "Response completed: : " + responseData);
                    parseJSONWithGson(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGson(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
        Log.d("http", "jsonObject: " + jsonArray);
        Gson gson = new Gson();
        filestudies = gson.fromJson(jsonArray, new TypeToken<List<Filestudy>>() {
        }.getType());
        Log.d("http", "filestudies: " + filestudies);
        Message msg = new Message();
        msg.what = COMPLETED;
        handler.sendMessage(msg);
    }

    /**
     * 下载文件
     *
     * @param urlString
     * @param fileName
     */
    public void downLoadFromStream(final String urlString, final String fileName) {
        final ProgressDialog progressDialog = new ProgressDialog(StudyActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("文件获取中，请稍候.....");
        progressDialog.show();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int byteread = 0;
                try {
                    //final String fileName = resourceId + "." + fileType;//把resourceId 与filetype拼接成文件的名称
                    final File file = new File(FileUtils.getSDdir("download"), fileName); //在刚刚建立好的目录下建立文件
                    //如果文件已存在直接打开
                    if (file.exists()) {
                        progressDialog.dismiss();
                        openFile(StudyActivity.this, file);
                        return;
                    }
                    file.createNewFile();
                    //Log.d(TAG, "urlString: "+new String(urlString.getBytes("UTF-8")));
                    //Log.d(TAG, "urlString: "+URL+java.net.URLEncoder.encode(urlString,"UTF-8"));
                    final URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    OutputStream outputStream = new FileOutputStream(file);
                    byte buffer[] = new byte[4 * 1024];
                    while ((byteread = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, byteread);
                    }
                    outputStream.close();
                    inputStream.close();
                    connection.disconnect();
                    Log.d(TAG, "file: 文件下载成功" + file.length());
                    progressDialog.dismiss();
                    openFile(StudyActivity.this, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * 打开文件
     *
     * @param context
     * @param file
     */
//    public static void openFile(Context context, File file) {
//        Intent mIntent = new Intent();
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //设置intent的Action属性
//        mIntent.setAction(Intent.ACTION_VIEW);
//        String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
//        String mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//        mIntent.setDataAndType(Uri.fromFile(file), mimetype);
//        try {
//            context.startActivity(mIntent);
//        } catch (Exception e) {
//        }
//    }
    /**
     * 调用系统应用打开文件
     * @param context
     * @param file
     */
    public void openFile(Context context,File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e("找不到打开此文件的应用！\n"+e);
            toast("找不到打开此文件的应用！\n"+e);
        }
    }

    /***根据文件后缀回去MIME类型****/

    private static String getMIMEType(File file) {
        String type="*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
        /* 获取文件的后缀名*/
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }
    private static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            { ".3gp", "video/3gpp" },
            { ".apk", "application/vnd.android.package-archive" },
            { ".asf", "video/x-ms-asf" },
            { ".avi", "video/x-msvideo" },
            { ".bin", "application/octet-stream" },
            { ".bmp", "image/bmp" },
            { ".c", "text/plain" },
            { ".class", "application/octet-stream" },
            { ".conf", "text/plain" },
            { ".cpp", "text/plain" },
            { ".doc", "application/msword" },
            { ".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { ".xls", "application/vnd.ms-excel" },
            { ".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { ".exe", "application/octet-stream" },
            { ".gif", "image/gif" },
            { ".gtar", "application/x-gtar" },
            { ".gz", "application/x-gzip" },
            { ".h", "text/plain" },
            { ".htm", "text/html" },
            { ".html", "text/html" },
            { ".jar", "application/java-archive" },
            { ".java", "text/plain" },
            { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" },
            { ".js", "application/x-javascript" },
            { ".log", "text/plain" },
            { ".m3u", "audio/x-mpegurl" },
            { ".m4a", "audio/mp4a-latm" },
            { ".m4b", "audio/mp4a-latm" },
            { ".m4p", "audio/mp4a-latm" },
            { ".m4u", "video/vnd.mpegurl" },
            { ".m4v", "video/x-m4v" },
            { ".mov", "video/quicktime" },
            { ".mp2", "audio/x-mpeg" },
            { ".mp3", "audio/x-mpeg" },
            { ".mp4", "video/mp4" },
            { ".mpc", "application/vnd.mpohun.certificate" },
            { ".mpe", "video/mpeg" },
            { ".mpeg", "video/mpeg" },
            { ".mpg", "video/mpeg" },
            { ".mpg4", "video/mp4" },
            { ".mpga", "audio/mpeg" },
            { ".msg", "application/vnd.ms-outlook" },
            { ".ogg", "audio/ogg" },
            { ".pdf", "application/pdf" },
            { ".png", "image/png" },
            { ".pps", "application/vnd.ms-powerpoint" },
            { ".ppt", "application/vnd.ms-powerpoint" },
            { ".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
            { ".prop", "text/plain" }, { ".rc", "text/plain" },
            { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
            { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
            { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
            { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
            { ".wmv", "audio/x-ms-wmv" },
            { ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
            { ".z", "application/x-compress" },
            { ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

    /**
     * 学习园地
     *
     * @param view
     */
    public void clickInStudy(View view) {
        String str1 = "";
        EditText editText1 = (EditText) findViewById(R.id.et_fileName);
        str1 = editText1.getText().toString();
        Log.d(TAG, "clickInStudy: " + str1);
        reqFileList(str1, 1);
    }

    void reqFileList(String searchKey, int pageNo) {
        try {
            String url = ParamUtils.paramIp + "/ALS7M/JSONService?method=studyList";
            JSONObject object = new JSONObject();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("pageSize", PAGE_SIZE);
            jsonObj.put("pageNo", pageNo);
            jsonObj.put("UserId", ParamUtils.UserId);
            jsonObj.put("SearchKey", searchKey);
            object.put("deviceType", "Android");
            object.put("RequestParams", jsonObj);
            sendRequestWithOkHttpPost(url, object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                reqFileList("", 1);
                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }

    @Override
    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage++;
                reqFileList("", curPage);
                // 结束刷新
                ptrLayout.finishLoadMore();
            }
        }, 2000);
    }
}
