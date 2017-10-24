package com.pactera.financialmanager.credit.main.service.rate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.contact_library.adapter.ContactCustomerAdp;
import com.dysen.contact_library.bean.ContactBean;
import com.dysen.contact_library.utils.OnItemClickCallback;
import com.dysen.contact_library.views.WordsNavigation;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.rate.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dysen.common_res.common.utils.ParamUtils.url;

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

    private static List<ContactBean> listData = new ArrayList<>();
    List<Customer> customer = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null){

                try {
                    customer = parseList(HttpThread.parseJSONWithGson(msg.obj.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (customer!= null){
                for(Customer cus : customer){

                    ContactBean contactBean = new ContactBean(cus.getCustomerName(), cus.getCertID());
                    listData.add(contactBean);
                }
                //对集合排序
                Collections.sort(listData, new Comparator<ContactBean>() {
                    @Override
                    public int compare(ContactBean lhs, ContactBean rhs) {
                        //根据拼音进行排序
                        return lhs.getPinyin().compareTo(rhs.getPinyin());
                    }
                });

                //初始化列表
                initListView();
            }
        }
    };
    private GridLayoutManager layoutManager;
    private boolean move;

    public static List<Customer> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<Customer> list = gson.fromJson(jsonData, new TypeToken<List<Customer>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_customer);
        ButterKnife.bind(this);
        //初始化数据
        initData();
        //初始化列表
        initListView();
        //设置列表点击滑动监听
        words.setOnWordsChangeListener(this);
    }

    private void initListView() {
        layoutManager = new GridLayoutManager(this, 1);
        dataRecyclerView.setLayoutManager(layoutManager);
        dataRecyclerView.setAdapter(new ContactCustomerAdp(this, R.layout.contact_customer_item, listData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name",listData.get(info).getName());
                bundle.putString("id",listData.get(info).getCertID());
                intent.putExtra("data",bundle);
//                toast("info"+info+"\nname:"+bundle.getString("name")+"\tid:"+bundle.getString("id"));
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
//        reqCustBody();
    }

    void reqCustBody() {
        //利率测算(选择客户)		UserId:登陆用户id,CustomerName:客户名称，CustomerType:客户类型, CertID:证件号码，CurPage:页码, PageSize:每页条数
        JSONObject jsonObject = ParamUtils.setParams("rate", "crmCustomerList", new Object[]{ParamUtils.UserId, "", "", "", curPage, ParamUtils.pageSize}, 6);
        HttpThread.sendRequestWithOkHttp(url, jsonObject, handler);
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
        LogUtils.v("firstVisibleItem="+firstVisibleItem+"\tvisibleItemCount="+visibleItemCount+"\ttotalItemCount="+totalItemCount);
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
//                dataRecyclerView.scrollToPosition(i);
                //找到开头的一个即可
                moveToPosition(i);
                return;
            }
        }
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            dataRecyclerView.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = dataRecyclerView.getChildAt(n - firstItem).getTop();
            dataRecyclerView.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            dataRecyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(-1);
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void setData(List<Customer> customer) {
        if (customer!= null){
            listData.clear();
            for(Customer cus : customer){

                ContactBean contactBean = new ContactBean(cus.getCustomerName(), cus.getCertID());
                listData.add(contactBean);
            }
            //对集合排序
            Collections.sort(listData, new Comparator<ContactBean>() {
                @Override
                public int compare(ContactBean lhs, ContactBean rhs) {
                    //根据拼音进行排序
                    return lhs.getPinyin().compareTo(rhs.getPinyin());
                }
            });
        }
    }
}