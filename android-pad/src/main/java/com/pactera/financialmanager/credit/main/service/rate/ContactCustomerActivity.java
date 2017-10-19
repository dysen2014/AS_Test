package com.pactera.financialmanager.credit.main.service.rate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.contact_library.adapter.ContactCustomerAdp;
import com.dysen.contact_library.bean.ContactBean;
import com.dysen.contact_library.utils.OnItemClickCallback;
import com.dysen.contact_library.views.WordsNavigation;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.rate.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 2017/10/10.
 *
 * @Info
 */
public class ContactCustomerActivity extends ParentActivity implements
        WordsNavigation.onWordsChangeListener, AbsListView.OnScrollListener {

    @Bind(R.id.data)
    RecyclerView dataRecyclerView;
    @Bind(R.id.words)
    WordsNavigation words;
    @Bind(R.id.tv)
    TextView tv;
    private Handler handler;
    private static List<ContactBean> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_customer);
        ButterKnife.bind(this);
        //初始化数据
//        initData();
        //初始化列表
        initListView();

        //设置列表点击滑动监听
        handler = new Handler();
        words.setOnWordsChangeListener(this);
    }

    private void initListView() {
        LinearLayoutManager mManagerColor = new GridLayoutManager(this, 3);
        dataRecyclerView.setLayoutManager(mManagerColor);
        List<Integer> list = new ArrayList<>();
        for(ContactBean contactBean : listData){
            int char_ascii = contactBean.getHeaderWord().toUpperCase().charAt(0);

            list.add(char_ascii);
            LogUtils.v("list before:"+list);
            Collections.sort(list);
            LogUtils.v("list after:"+list);
        }

        dataRecyclerView.setAdapter(new ContactCustomerAdp(this, R.layout.contact_customer_item, listData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name",listData.get(info).getName());
                bundle.putString("id",listData.get(info).getId());
                intent.putExtra("data",bundle);
                toast("info"+info+"\nname:"+bundle.getString("name")+"\tid:"+bundle.getString("id"));
                LogUtils.v("view id:"+view.getId()+"\tname:"+bundle.getString("name")+"\tid:"+bundle.getString("id"));
                setResult(0, intent);
                finish();
            }

            @Override
            public void onLongClick(View view, Integer info) {

            }
        }));
    }

    /**
     * 初始化联系人列表信息
     */
    private void initData() {
        listData = new ArrayList<>();
        listData.add(new ContactBean("Dave"));
        listData.add(new ContactBean("张晓飞"));
        listData.add(new ContactBean("杨光福"));
        listData.add(new ContactBean("阿钟"));
        listData.add(new ContactBean("胡继群"));
        listData.add(new ContactBean("徐歌阳"));
        listData.add(new ContactBean("钟泽兴"));
        listData.add(new ContactBean("宋某人"));
        listData.add(new ContactBean("刘某人"));
        listData.add(new ContactBean("Tony"));
        listData.add(new ContactBean("老刘"));
        listData.add(new ContactBean("隔壁老王"));
        listData.add(new ContactBean("安传鑫"));
        listData.add(new ContactBean("温松"));
        listData.add(new ContactBean("成龙"));
        listData.add(new ContactBean("那英"));
        listData.add(new ContactBean("刘甫"));
        listData.add(new ContactBean("沙宝亮"));
        listData.add(new ContactBean("张猛"));
        listData.add(new ContactBean("张大爷"));
        listData.add(new ContactBean("张哥"));
        listData.add(new ContactBean("张娃子"));
        listData.add(new ContactBean("樟脑丸"));
        listData.add(new ContactBean("吴亮"));
        listData.add(new ContactBean("Tom"));
        listData.add(new ContactBean("阿三"));
        listData.add(new ContactBean("肖奈"));
        listData.add(new ContactBean("贝微微"));
        listData.add(new ContactBean("赵二喜"));
        listData.add(new ContactBean("曹光"));
        listData.add(new ContactBean("姜宇航"));

        //对集合排序
        Collections.sort(listData, new Comparator<ContactBean>() {
            @Override
            public int compare(ContactBean lhs, ContactBean rhs) {
                //根据拼音进行排序
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }

    //手指按下字母改变监听回调
    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当滑动列表的时候，更新右侧字母列表的选中状态
        words.setTouchIndex(listData.get(firstVisibleItem).getHeaderWord());
    }

    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < listData.size(); i++) {
            String headerWord = listData.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
//                dataRecyclerView.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        tv.setText(words);
        tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //1s后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }, 500);
    }

    public static void setData(List<Customer> customer) {

        for(Customer cus : customer){

            ContactBean contactBean = new ContactBean(cus.getCustomerName(), cus.getCertID());
            listData.add(contactBean);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(-1);
        }
        return super.onKeyDown(keyCode, event);
    }
}