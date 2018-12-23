package com.pactera.financialmanager.credit.main.service.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.study.StudyActivity;
import com.pactera.financialmanager.credit.main.service.study.bean.BookCaseModel;
import com.pactera.financialmanager.ui.ParentActivity;

import java.util.List;


/**
 * Created by Tiger on 2017/10/19.
 */

public class BookCaseAdpater extends RecyclerView.Adapter<BookCaseAdpater.ViewHolder> {

    private List<BookCaseModel> mBookList;
    private Context mContext;
    int[] imgId = new int[]{R.mipmap.study_jgfg, R.mipmap.study_xdzd, R.mipmap.study_cpzc, R.mipmap
            .study_hyzc,
            R.mipmap.study_xtgn, R.mipmap.study_czwb, R.mipmap.study_ywdy, R.mipmap.study_dxal};

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View bookView;
        private ImageView bookImage;
        private TextView bookTitle;
        LinearLayout llItem;

        public ViewHolder(View itemView) {
            super(itemView);

            bookView = itemView;
            bookImage = (ImageView) itemView.findViewById(R.id.bookcase_image);
            bookTitle = (TextView) itemView.findViewById(R.id.bookcase_title);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    public BookCaseAdpater(List<BookCaseModel> bookList, Context context) {
        mBookList = bookList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("", "viewType" + viewType);
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.bookcase_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postion = holder.getAdapterPosition();
                BookCaseModel model = mBookList.get(postion);
//                Toast.makeText(view.getContext(),
//                        "点击了第"+postion+"个"+model.getItemNo(),
//                        Toast.LENGTH_SHORT).show();
                ParentActivity.setSelectorItemColor(holder.llItem, R.color
                        .view_selected, R.drawable.cardview_bg_boder);
                Intent intent = new Intent();
                intent.putExtra("DocNo", model.getItemNo());
                intent.setClass(view.getContext(), StudyActivity.class);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BookCaseModel model = mBookList.get(position);

//        try {
//            Field field = R.drawable.class.getField("learning_bg_" + position);
//            int key = field.getInt(new R.drawable());
//            holder.bookImage.setImageResource(key);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        holder.bookImage.setImageResource(imgId[position]);
        holder.bookTitle.setText(model.getItemName());
    }

    @Override
    public int getItemCount() {

        return mBookList.size();
    }
}
