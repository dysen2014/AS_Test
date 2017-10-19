package com.pactera.financialmanager.ui.productcenter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.CircleImageView;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.OffersInfo;
import com.pactera.financialmanager.ui.photoview.PhotoView;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠活动
 *
 * @author JAY
 */
public class PromotionsActivity extends ParentActivity implements
        OnClickListener {
//
    private static CircleImageView imageView_big;// 大图
    private static CircleImageView imageView_more;// 更多
    private CircleImageView[] imageView = new CircleImageView[3];
    private ImageLoader loader = ImageLoader.getInstance();
    private DisplayImageOptions optionsCircle;


    LinearLayout promotions_ll;
    LinearLayout linearLayout_img;
    private HConnection HCon;
    private final int returnIndex = 1;
    private List<OffersInfo> listOffersInfo;
    int DOWNLOAD_ICON = 0;
    int DOWNLOAD_IMG = 1;
    int showIndex = 0;
    ImageView titleLeft;


//	ImageDownloader imageDownloader;

    LinearLayout promotions_info_ll;
    //	ImageView promotions_imageView_close;
    PhotoView promotions_imageView_info;
    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        // 初始化
        findViews();
//		imageDownloader = new ImageDownloader(this);
        // 绑定监听器
        bindOnClickListener();
        initImageLoaderOption();
        initTitle(this, R.drawable.yingxiaoguanli);
        getData();

    }

    @Override
    protected void onDestroy() {
        if (listOffersInfo != null) {
            listOffersInfo.clear();
        }
        promotions_imageView_info.exitAndClear();
        super.onDestroy();
    }

    private void getData() {

        HCon = RequestInfo(this, requestType.Other,
                InterfaceInfo.Promotions_Get, "", returnIndex);
        if (HCon == null) {
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
                    for (int i = 0; i < tmpJsonArray.length() && i < 3; i++) {
                        tmpOffersInfo = new OffersInfo();
                        tmpJsonObject02 = (JSONObject) tmpJsonArray.get(i);
                        if (!tmpJsonObject02.getString("infoStatus").equals(
                                "03")) {

                            tmpOffersInfo.infoName = Tool.getJsonValue(
                                    tmpJsonObject02, "infoName");

                            tmpOffersInfo.ipID = Tool.getJsonValue(
                                    tmpJsonObject02, "ipID");

                            tmpOffersInfo.infoStatus = Tool.getJsonValue(
                                    tmpJsonObject02, "infoStatus");

                            tmpOffersInfo.ipIcon = Tool.getJsonValue(
                                    tmpJsonObject02, "ipIcon");

                            tmpOffersInfo.infoType = Tool.getJsonValue(
                                    tmpJsonObject02, "infoType");

                            tmpOffersInfo.ipFile = Tool.getJsonValue(
                                    tmpJsonObject02, "ipFile");

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
            int len = listOffersInfo.size();
            if (len == 0) {
            } else if (len == 1) {
                setImage(0, 0, 0);
            } else if (len == 2) {
                setImage(0, 1, 1);
            } else {
                setImage(0, 1, 2);
            }
        }
    }


    /***
     * 第1，2，3张图显示图片
     **/
    private void setImage(int first, int second, int third) {
        setItem(listOffersInfo.get(first), imageView[0], DOWNLOAD_ICON);
        setItem(listOffersInfo.get(second), imageView[1], DOWNLOAD_ICON);
        setItem(listOffersInfo.get(third), imageView[2], DOWNLOAD_ICON);
        setItem(listOffersInfo.get(0), imageView_big, DOWNLOAD_IMG);
    }

    private void setItem(OffersInfo offersInfo, ImageView theImg, int intType) {

        String dowloadURL = "";// 下载链接

        if (intType == DOWNLOAD_ICON) {
            dowloadURL = offersInfo.ipIcon;
        } else if (intType == DOWNLOAD_IMG) {
            dowloadURL = offersInfo.ipFile;
        }
//		Log.i("1111111", dowloadURL);
        if (dowloadURL.endsWith(".jpg") || dowloadURL.endsWith(".JPG")
                || dowloadURL.endsWith(".png") || dowloadURL.endsWith(".PNG")) {

            loader.displayImage(dowloadURL, theImg,optionsCircle);
            //imageDownloader.download(dowloadURL, theImg);

        } else {
            Toast.makeText(this, "图片格式不正确", Toast.LENGTH_SHORT).show();
        }

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
    private void bindOnClickListener() {
        imageView_big.setOnClickListener(this);
        imageView_more.setOnClickListener(this);
        imageView[0].setOnClickListener(this);
        imageView[1].setOnClickListener(this);
        imageView[2].setOnClickListener(this);
//		promotions_imageView_close.setOnClickListener(this);
    }

    private void findViews() {
        imageView_big = (CircleImageView) findViewById(R.id.promotions_imageView_big);
        imageView_more = (CircleImageView) findViewById(R.id.promotions_imageView_more);
        imageView[0] = (CircleImageView) findViewById(R.id.promotions_imageView1);
        imageView[1] = (CircleImageView) findViewById(R.id.promotions_imageView2);
        imageView[2] = (CircleImageView) findViewById(R.id.promotions_imageView3);
        promotions_ll = (LinearLayout) findViewById(R.id.activity_promotions_ll);
        promotions_info_ll = (LinearLayout) findViewById(R.id.activity_promotions_info_ll);
//		promotions_imageView_close = (ImageView) findViewById(R.id.promotions_imageView_close);
        promotions_imageView_info = (PhotoView) findViewById(R.id.promotions_imageView_info);

        linearLayout_img = (LinearLayout) findViewById(R.id.activity_promotions_ll_img);

        int ScreenHegit = Tool.getScreenHeight(this);
        int imgH = ScreenHegit * 120 / 800;

        imageView[0].setLayoutParams(new LayoutParams(imgH, imgH));
        imageView[1].setLayoutParams(new LayoutParams(imgH, imgH));
        imageView[2].setLayoutParams(new LayoutParams(imgH, imgH));
        imageView_more.setLayoutParams(new LayoutParams(imgH, imgH));
        int imgH_big = ScreenHegit * 600 / 800;
        imageView_big.setLayoutParams(new LayoutParams(imgH_big, imgH_big));

        int ScreenWidth = Tool.getScreenWidth(this);
        int imgW = ScreenWidth * 200 / 1280;
        linearLayout_img.setLayoutParams(new LinearLayout.LayoutParams(imgW,
                LayoutParams.MATCH_PARENT));

        // Log.i("1111111", "imgH:"+imgH);
    }


    @Override
    protected void onResume() {
        super.onResume();
        titleLeft = (ImageView) findViewById(R.id.com_img_back);
        titleLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BackEvents();
            }
        });
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.promotions_imageView_big:
                if (!isShow) {
                    showInfo(showIndex);
                } else {
                    subUpAnim();
                }
                break;
//		case R.id.promotions_imageView_close:
//			subUpAnim();
//			break;
            case R.id.promotions_imageView_more:
                if (!isShow) {
                    intent.setClass(this, PromotionsMoreActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.promotions_imageView1:
                if (!isShow) {
                    if (listOffersInfo != null && listOffersInfo.size() >= 1) {
                        showIndex = 0;
                        setItem(listOffersInfo.get(showIndex), imageView_big,
                                DOWNLOAD_IMG);
                    } else {
                        imageView_big.setImageResource(R.drawable.promotions_demo8);
                    }
                }
                break;
            case R.id.promotions_imageView2:
                if (!isShow) {
                    if (listOffersInfo != null && listOffersInfo.size() >= 2) {
                        showIndex = 1;
                        setItem(listOffersInfo.get(showIndex), imageView_big,
                                DOWNLOAD_IMG);
                    } else {
                        imageView_big.setImageResource(R.drawable.promotions_demo7);
                    }
                }
                break;
            case R.id.promotions_imageView3:
                if (!isShow) {
                    if (listOffersInfo != null && listOffersInfo.size() >= 3) {
                        showIndex = 2;
                        setItem(listOffersInfo.get(showIndex), imageView_big,
                                DOWNLOAD_IMG);
                    } else {
                        imageView_big.setImageResource(R.drawable.promotions_demo5);
                    }
                }
                break;
            default:
                break;
        }
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

    /**
     * 进入退出动画
     *
     * @param laySub
     */
    private void subUpAnim() {

        AnimatorSet as = new AnimatorSet();
        float height = promotions_info_ll.getHeight();
        float width = promotions_info_ll.getWidth();
        if (promotions_info_ll.getY() == 0) {
            ObjectAnimator translationRight = ObjectAnimator.ofFloat(
                    promotions_info_ll, "Y", 0f, height);
//            ObjectAnimator.ofFloat(promotions_info_ll,"X",0f,width);//退出时有背景占屏效果
            ObjectAnimator translation = ObjectAnimator.ofFloat(
                    promotions_info_ll, "alpha", 1f, 1f);
            as.playTogether(translationRight, translation);
            as.setDuration(500);
            as.start();
//			Log.i("1111111", "1");
            isShow = false;
        } else {
            ObjectAnimator translationLeft = ObjectAnimator.ofFloat(
                    promotions_info_ll, "Y", height, 0f);
            ObjectAnimator translation = ObjectAnimator.ofFloat(
                    promotions_info_ll, "alpha", 1f, 1f);
            as.playTogether(translationLeft, translation);
            as.setDuration(500);
            as.start();
//			Log.i("1111111", "2");
            isShow = true;
        }

    }

    public void showInfo(int index) {

        if (listOffersInfo != null && listOffersInfo.size() > index) {
            setItem(listOffersInfo.get(index), promotions_imageView_info, DOWNLOAD_IMG);
        }

//        else {
//			switch (index) {
//			case 0:
//				promotions_imageView_info
//						.setImageResource(R.drawable.promotions_demo8);
//				break;
//			case 1:
//				promotions_imageView_info
//						.setImageResource(R.drawable.promotions_demo7);
//				break;
//			case 2:
//				promotions_imageView_info
//						.setImageResource(R.drawable.promotions_demo5);
//				break;
//			default:
//				break;
//			}
//		}
        subUpAnim();
    }

}
