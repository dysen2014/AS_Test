package com.pactera.financialmanager.credit.main.service.study.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dysen.common_res.common.utils.FileUtils;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.study.StudyActivity;
import com.pactera.financialmanager.credit.main.service.study.bean.Filestudy;

import java.io.File;
import java.util.List;

/**
 * Created by admin on 2017-7-5.
 */

public class FilestudyAdapter extends ArrayAdapter<Filestudy>{

    private static final String TAG = "FilestudyAdapter";
    private int resourceId;
    Context mContext;

    public FilestudyAdapter(Context context, int textViewResourceId, List<Filestudy> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    public View getView(int postion, View convertView, ViewGroup parent){
        Filestudy filestudy = getItem(postion);
        Log.d(TAG, "filestudy: "+filestudy);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextViewMarquee fileType = (TextViewMarquee) view.findViewById(R.id.textView);
        TextViewMarquee fileName = (TextViewMarquee) view.findViewById(R.id.textView2);
        Button btnOpenFile = (Button) view.findViewById(R.id.btn_file);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        String type = filestudy.getFileName();
        type = type.substring(type.indexOf("."));
        LogUtils.d("type:"+type);
        switch (type){
            case ".doc":
            case ".docx":
                img.setBackgroundResource(R.drawable.ic_doc);
                break;
            case ".ppt":
                img.setBackgroundResource(R.drawable.ic_ppt);
                break;
            case ".excel":
                img.setBackgroundResource(R.drawable.ic_excel);
                break;
            case ".pdf":
                img.setBackgroundResource(R.drawable.ic_pdf);
                break;
            case ".txt":
                img.setBackgroundResource(R.drawable.ic_txt);
                break;
        }
        fileType.setText(filestudy.getDocName());
        fileName.setText(filestudy.getFileName());
        final File file = new File(FileUtils.getSDdir("download"), filestudy.getFileName());
        if (file.exists()) {
            btnOpenFile.setVisibility(View.VISIBLE);
        }
        btnOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StudyActivity().openFile(mContext, file);
            }
        });
        return view;
    }
}
