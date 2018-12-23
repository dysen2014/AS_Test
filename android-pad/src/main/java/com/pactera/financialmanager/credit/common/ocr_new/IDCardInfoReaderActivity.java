package com.pactera.financialmanager.credit.common.ocr_new;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.Location;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;

import java.io.File;
import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IDCardInfoReaderActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.edit_id)
    EditText editId;
    @Bind(R.id.edit_type)
    EditText editType;
    @Bind(R.id.edit_address)
    EditText editAddress;
    @Bind(R.id.edit_sex)
    EditText editSex;
    @Bind(R.id.edit_date)
    EditText editDate;
    @Bind(R.id.edit_create)
    EditText editCreate;
    @Bind(R.id.layout_create)
    LinearLayout layoutCreate;
    @Bind(R.id.edit_limit_date_start)
    EditText editLimitDateStart;
    @Bind(R.id.layout_limit_date_start)
    LinearLayout layoutLimitDateStart;
    @Bind(R.id.edit_limit_date_end)
    EditText editLimitDateEnd;
    @Bind(R.id.layout_limit_date_end)
    LinearLayout layoutLimitDateEnd;
    @Bind(R.id.btn_get_idcard_photo)
    Button btnGetIdcardPhoto;
    @Bind(R.id.btn_ok)
    Button btnOk;
    @Bind(R.id.scllowview_read_idcard)
    ScrollView scllowviewReadIdcard;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.gallery_button_front)
    Button galleryButtonFront;
    @Bind(R.id.gallery_button_back)
    Button galleryButtonBack;
    @Bind(R.id.id_card_front_button)
    Button idCardFrontButton;
    @Bind(R.id.id_card_front_button_native)
    Button idCardFrontButtonNative;
    @Bind(R.id.id_card_back_button)
    Button idCardBackButton;
    @Bind(R.id.id_card_back_button_native)
    Button idCardBackButtonNative;
    @Bind(R.id.info_text_view)
    TextView infoTextView;
    @Bind(R.id.ll_scan_type)
    LinearLayout llScanType;

    public static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    public static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final String KEY_ID_CARD = "IDCard";
    public static final  String KEY_ID_NAME = "IDName";
    public static final String KEY_NO_ID_CARD = "IDNoCard";
    @Bind(R.id.fl_idcard)
    FrameLayout flIdcard;
    Activity activity = IDCardInfoReaderActivity.this;
    private Intent intent;
    public IDCardResult idCard;
    private boolean recognizerFlag;

    private boolean checkGalleryPermission() {
        int ret = ActivityCompat.checkSelfPermission(IDCardInfoReaderActivity.this, Manifest.permission
                .READ_EXTERNAL_STORAGE);
        if (ret != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IDCardInfoReaderActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);
            return false;
        }
        return true;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.read_idcard_info_new);
        ButterKnife.bind(this);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {

        txtTitle.setText("身份证识别");

        llScanType.setVisibility(View.GONE);
        intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 设置页面上的值
     *
     * @param idCard
     */
    private void setUIValue(IDCardResult idCard) {
        this.idCard = null;
        this.idCard = idCard;
        if (idCard == null) {
            return;
        }
        if (idCard.getName() != null) {
            editName.setText(idCard.getName().toString());
        }
        if (idCard.getIdNumber() != null) {
            editId.setText(idCard.getIdNumber().toString());
        }
        if (idCard.getGender() != null) {
            editSex.setText(idCard.getGender().toString());
        }
        if (idCard.getBirthday() != null) {
            editDate.setText(idCard.getBirthday().toString());
        }
        if (idCard.getAddress() != null) {
            editAddress.setText(idCard.getAddress().toString());
        }
        if (idCard.getEthnic() != null) {
            editType.setText(idCard.getEthnic().toString());
        }
        if (idCard.getDirection() != -1) {
//            editCreate.setText(idCard.getDirection());
        }
        if ((idCard.getWordsResultNumber() != -1)) {

        }

        btnOk.setEnabled(true);
    }

    @OnClick({R.id.btn_get_idcard_photo, R.id.btn_ok, R.id.gallery_button_front, R.id
            .gallery_button_back, R.id.txt_back, R.id.id_card_front_button, R.id.id_card_front_button_native,
            R.id.id_card_back_button, R.id.id_card_back_button_native})
    public void onViewClicked(View view) {
        intent = new Intent(activity, CameraActivity.class);
        switch (view.getId()) {
            case R.id.btn_get_idcard_photo://重新识别
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.txt_back:
            case R.id.lay_back://返回
                myReturn(recognizerFlag);
                break;
            case R.id.btn_ok://确认
                myReturn(recognizerFlag);
                break;
            case R.id.gallery_button_front:
                if (checkGalleryPermission()) {
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE_FRONT);
                }
                break;
            case R.id.gallery_button_back:
                if (checkGalleryPermission()) {
                    Intent intent2 = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE_BACK);
                }
                break;
            case R.id.id_card_front_button:
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.id_card_front_button_native:
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN,
                        OCR.getInstance().getLicense());
                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                        true);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.id_card_back_button:

                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
            case R.id.id_card_back_button_native:
                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN,
                        OCR.getInstance().getLicense());
                intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                        true);
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            myReturn(false);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 识别 返回
     */
    private void myReturn (boolean flag) {

            Intent intent = new Intent();
        if (flag){
            ParentActivity.idCard = idCard;
            intent.putExtra(KEY_ID_CARD, idCard.getIdNumber().toString());
            intent.putExtra(KEY_ID_NAME, idCard.getName().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }else {
            intent.putExtra(KEY_NO_ID_CARD, "识别失败！");
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }
    }

    public File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    private void recIDCard(String idCardSide, final String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    setUIValue(result);
                    uberPgsview.setVisibility(View.GONE);
//                    ShowDialog(activity, result.toString());
                    recognizerFlag = true;
                    toast("识别成功 ！");
                    Log.i("dysen", result.toString() + "\n" + result.getIdNumber() + "----" + result.getName());
                    int rectX = result.getAddress().getLocation().getWidth() + result.getAddress().getLocation().getLeft() + 10;
                    int rectY = result.getName().getLocation().getTop();
                    Location location = result.getIdNumber().getLocation();
                    int height = location.getTop() - rectY - 20;
                    int width = location.getWidth() + location.getLeft() - rectX + 40;
                    Bitmap ocrBitmap = BitmapFactory.decodeFile(filePath);
                    if (ocrBitmap != null) {
                        Bitmap headBitmap = Bitmap.createBitmap(ocrBitmap, rectX, rectY, width, height, null,
                                false);
                        ivHead.setImageBitmap(headBitmap);
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
//                ShowDialog(activity, error.getMessage());
                toast("识别失败，请重新识别 ！");
                uberPgsview.setVisibility(View.GONE);
                recognizerFlag = false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        llScanType.setVisibility(View.GONE);
        flIdcard.setVisibility(View.VISIBLE);
        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}