package com.pactera.financialmanager.ui.productcenter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.OffersInfo;
import com.pactera.financialmanager.ui.photoview.PhotoView;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

/**
 * 优惠活动(更多)
 *
 * @author JAY
 */
public class PromotionsMoreActivity extends ParentActivity {

    LinearLayout promotions_more_ll;
    GridView more_gridView;
    private HConnection HCon;
    private final int returnIndex = 1;
    private List<OffersInfo> listOffersInfo;
    public static final int DOWNLOAD_ICON = 0;
    public static final int DOWNLOAD_IMG = 1;
    int showIndex = 0;

    LinearLayout promotions_info_ll;
    //	ImageView promotions_imageView_close;
    PhotoView promotions_imageView_info;
    ImageView ImageView_big;
    boolean isShow = false;
    ImageView titleLeft;

    private ImageLoader loader = ImageLoader.getInstance();
    private DisplayImageOptions optionsCircle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions_more);
        initTitle(this, R.drawable.yingxiaoguanli);
        // 初始化
        findViews();
        // more_gridView.setColumnWidth(promotions_more_ll.getWidth()/10);
        initImageLoaderOption();
        getData();
        more_gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                if (!isShow) {
                    showInfo(position);
                }

            }
        });
//		promotions_imageView_close.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				subUpAnim();
//			}
//		});
    }

    private void initImageLoaderOption() {
        optionsCircle = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.load_failed)
                .showImageOnLoading(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


    }
    private void getData() {

        if (Tool.haveNet(this)) {
            HCon = RequestInfo(this, requestType.Other,
                    InterfaceInfo.Promotions_Get, "", returnIndex);
            //
            // if (Tool.haveNet(this)) {
            // // TODO Auto-generated method stub
            // // String pwdStr =
            // Tool.ecodeByMD5(LogoActivity.user.getPassword());
            // // String loginStr = LogoActivity.user.getUserCode();
            // HRequest request = null;
            // request = new HRequest("T000014?method=getJSON&userCode="
            // + /* loginStr */109001 + "&password="
            // + /* pwdStr */"96E79218965EB72C92A549DD5A330112" + "&seriNo="
            // + "DLXJT06FF18P&brId=BR1101102"
            // + "&chnlCode=eee&transCode=T000014&infoType=01", "GET");
            // try {
            // HCon = new HConnection(this, request, LogoActivity.user,
            // HConnection.RESPONSE_TYPE_COMPLEX_JSON);
            // HCon.setIndex(returnIndex);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        } else {
            noNet();
        }

    }

    // 无网情况下加载缓存
    private void noNet() {

        InputStream ips = null;
        try {
            ips = Tool.readURLFileFromSDCard(OFFER_PATH, OFFER_JSONSAVE);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        JSONObject tmpJsonObject = null;
        if (ips == null)
            return;
        else {
            try {
                tmpJsonObject = new JSONObject(Tool.ips2String(ips, null));
                if (ips != null)
                    ips.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tmpJsonObject != null) {
                readJson(tmpJsonObject);
            }
        }
    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                break;
            case returnIndex:
                HResponse res = HCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonObject = res.dataJsonObject;
                String jsonString = tmpJsonObject.toString();
                readJson(tmpJsonObject);
                try {
                    Tool.writeURLFile2SDCard(OFFER_PATH, OFFER_JSONSAVE,
                            Tool.string2Ips(jsonString, null));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
        }
    }

    private void readJson(JSONObject tmpJsonObject) {
        JSONArray tmpJsonArray = null;
        if (tmpJsonObject.has("group"))
            try {
                tmpJsonArray = tmpJsonObject.getJSONArray("group");
                if (tmpJsonArray.length() > 0) {

                    listOffersInfo = new ArrayList<OffersInfo>();
                    OffersInfo tmpOffersInfo;
                    JSONObject tmpJsonObject02;
                    for (int i = 0; i < tmpJsonArray.length(); i++) {
                        tmpOffersInfo = new OffersInfo();
                        tmpJsonObject02 = (JSONObject) tmpJsonArray.get(i);
                        if (!tmpJsonObject02.getString("infoStatus").equals(
                                "03")) {

                            if (tmpJsonObject02.has("infoName")) {
                                tmpOffersInfo.infoName = tmpJsonObject02
                                        .getString("infoName");
                            }
                            if (tmpJsonObject02.has("ipID")) {
                                tmpOffersInfo.ipID = tmpJsonObject02
                                        .getString("ipID");
                            }
                            if (tmpJsonObject02.has("infoStatus")) {
                                tmpOffersInfo.infoStatus = tmpJsonObject02
                                        .getString("infoStatus");
                            }
                            if (tmpJsonObject02.has("ipIcon")) {
                                tmpOffersInfo.ipIcon = tmpJsonObject02
                                        .getString("ipIcon");
                            }
                            if (tmpJsonObject02.has("infoType")) {
                                tmpOffersInfo.infoType = tmpJsonObject02
                                        .getString("infoType");
                            }
                            if (tmpJsonObject02.has("ipFile")) {
                                tmpOffersInfo.ipFile = tmpJsonObject02
                                        .getString("ipFile");
                            }
                            listOffersInfo.add(tmpOffersInfo);
                        } else {
                            if (tmpJsonObject02.has("ipIcon"))
                                Tool.delSDFile(OFFER_PATH, Tool
                                        .getURLFileName(tmpJsonObject02
                                                .getString("ipIcon")));
                            if (tmpJsonObject02.has("ipFile"))
                                Tool.delSDFile(OFFER_PATH, Tool
                                        .getURLFileName(tmpJsonObject02
                                                .getString("ipFile")));
                        }
                    }
                    tmpOffersInfo = null;
                    tmpJsonObject02 = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        if (listOffersInfo != null) {
            PromotionsMoreAdapter infoAdapter = new PromotionsMoreAdapter(this,
                    listOffersInfo);
            more_gridView.setAdapter(infoAdapter);
        }

    }

    public void setItem(OffersInfo offersInfo, ImageView theImg, int intType) {

        String dowloadURL = "";
        if (intType == DOWNLOAD_ICON) {
            dowloadURL = offersInfo.ipIcon;
        } else if (intType == DOWNLOAD_IMG) {
            dowloadURL = offersInfo.ipFile;
        }
        if (dowloadURL.endsWith(".jpg") || dowloadURL.endsWith(".JPG")
                || dowloadURL.endsWith(".png") || dowloadURL.endsWith(".PNG")) {

            loader.displayImage(dowloadURL, theImg,optionsCircle);
        } else {
            Toast.makeText(PromotionsMoreActivity.this, "图片格式不正确",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews() {
        titleLeft = (ImageView) findViewById(R.id.com_img_back);
        titleLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BackEvents();
            }
        });
        promotions_info_ll = (LinearLayout) findViewById(R.id.activity_promotions_info_ll);
//		promotions_imageView_close = (ImageView) findViewById(R.id.promotions_imageView_close);
        promotions_imageView_info = (PhotoView) findViewById(R.id.promotions_imageView_info);

        promotions_more_ll = (LinearLayout) findViewById(R.id.promotions_more_ll);
        more_gridView = (GridView) findViewById(R.id.promotions_more_gridview);
        ImageView_big = (ImageView) findViewById(R.id.promotions_imageView_info);
        ImageView_big.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                subUpAnim();
            }
        });
    }

    @Override
    public void onBackPressed() {
        BackEvents();
    }

    private void BackEvents() {
        if (!isShow) {
            super.onBackPressed();
        } else {
            subUpAnim();
        }
    }

    @Override
    protected void onDestroy() {
        if (listOffersInfo != null) {
            for (int i = 0; i < listOffersInfo.size(); i++) {
                cleanBitmaFromCache(listOffersInfo.get(i).ipIcon);
            }
            listOffersInfo.clear();
        }
        super.onDestroy();
    }

    public void showInfo(int index) {
        subUpAnim();
        setItem(listOffersInfo.get(index), promotions_imageView_info,
                DOWNLOAD_IMG);
    }

    /**
     * 进入退出动画
     *
     * @param laySub
     */
    private void subUpAnim() {

        AnimatorSet as = new AnimatorSet();
        float height = promotions_info_ll.getHeight();

        if (promotions_info_ll.getY() == 0) {
            ObjectAnimator translationRight = ObjectAnimator.ofFloat(
                    promotions_info_ll, "Y", 0f, height);
            ObjectAnimator translation = ObjectAnimator.ofFloat(
                    promotions_info_ll, "alpha", 1f, 1f);
            as.playTogether(translationRight, translation);
            as.setDuration(500);
            as.start();
            Log.i("1111111", "1");
            isShow = false;
        } else {
            ObjectAnimator translationLeft = ObjectAnimator.ofFloat(
                    promotions_info_ll, "Y", height, 0f);
            ObjectAnimator translation = ObjectAnimator.ofFloat(
                    promotions_info_ll, "alpha", 1f, 1f);
            as.playTogether(translationLeft, translation);
            as.setDuration(500);
            as.start();
            Log.i("1111111", "2");
            isShow = true;
        }

    }

}


/**
 * @author JAY
 */
class PromotionsMoreAdapter extends BaseAdapter {
    private PromotionsMoreActivity activity;
    private List<OffersInfo> data;
    private static LayoutInflater inflater = null;

    public PromotionsMoreAdapter(PromotionsMoreActivity a, List<OffersInfo> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position - 1);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = View.inflate(activity, R.layout.item_promotions_more, null);
        ImageView item_img;
        TextView item_name;
        item_img = (ImageView) vi.findViewById(R.id.promotions_more_img);
        item_name = (TextView) vi.findViewById(R.id.promotions_more_tv);

        activity.setItem(data.get(position), item_img,
                PromotionsMoreActivity.DOWNLOAD_ICON);
        item_name.setText(data.get(position).infoName);
        Log.i("1111111", "" + position);
        return vi;
    }

}
