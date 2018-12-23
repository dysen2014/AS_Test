package com.pactera.financialmanager.credit.main.service.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.study.StudyActivity;
import com.pactera.financialmanager.credit.main.service.study.bean.Filestudy;
import com.pactera.financialmanager.util.FiledUtil;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017-7-5.
 */

public class FilestudyAdapter {


    public static class FilesAdapter  extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

        private static File mFile;
        Context mContext;
        List<Filestudy> list;

        OnItemClickCallback callback;
        public static ViewHolder myHolder;

        public FilesAdapter(Context mContext, List<Filestudy> list) {
            this.mContext = mContext;
            this.list = list;
        }
        public FilesAdapter(Context mContext, List<Filestudy> list, OnItemClickCallback<Object>
                callback) {
            this.mContext = mContext;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.file, parent,
                    false);
            //view.setBackgroundColor(Color.RED);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            myHolder = holder;
            final Filestudy filestudy = list.get(position);
            String type = filestudy.getFileName();
            type = type.substring(type.indexOf("."));
            switch (type) {
                case ".doc":
                case ".docx":
                    holder.img.setBackgroundResource(R.drawable.ic_doc);
                    break;
                case ".ppt":
                    holder.img.setBackgroundResource(R.drawable.ic_ppt);
                    break;
                case ".excel":
                    holder.img.setBackgroundResource(R.drawable.ic_excel);
                    break;
                case ".pdf":
                    holder.img.setBackgroundResource(R.drawable.ic_pdf);
                    break;
                case ".txt":
                    holder.img.setBackgroundResource(R.drawable.ic_txt);
                    break;
            }
            holder.fileType.setText(filestudy.getDocName());
            holder.fileName.setText(filestudy.getFileName());

            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(v, filestudy);
                }
            });
            final File file = new File(FiledUtil.getSDdir("download"), filestudy.getFileName());
            if (file.exists()) {
                holder.btnOpenFile.setVisibility(View.VISIBLE);
                holder.llLay.setEnabled(false);
            }else {
                holder.btnOpenFile.setVisibility(View.INVISIBLE);
                holder.llLay.setEnabled(true);
            }
            holder.btnOpenFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new StudyActivity().openFile(mContext, file);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_type)
            TextViewMarquee fileType;
            @Bind(R.id.tv_title)
            TextViewMarquee fileName;
            @Bind(R.id.btn_open_file)
            TextView btnOpenFile;
            @Bind(R.id.iv_img)
            ImageView img;
            @Bind(R.id.ll_lay)
            LinearLayout llLay;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public static void setData(File file) {
            mFile = file;
        }
    }
}
