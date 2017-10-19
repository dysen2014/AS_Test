package com.pactera.financialmanager.ocr;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.QuerycusActivity;
import com.pactera.financialmanager.util.LogUtils;
import com.ym.idcard.other.CameraManager;
import com.ym.idcard.other.CameraView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class IdCardCameraNew extends Activity implements SurfaceHolder.Callback, View.OnClickListener {

    public static boolean isColse = false;
    private String ToastStr = "拍照识别";
    private Camera camera;
    private CameraView cameraView;

    private boolean isPreviewRunning = false;
    private boolean isTouchAlready = false;
    private final String TAG = "IdCardCameraNew";
    private boolean isOpenLight = false;

    public static boolean isTakephoto = false;
    private String parameter = "";
    private int screenWidth,screenHeight;
    private Timer timer;
    private TimerTask task;

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
            OCRAnalysis.photoPic = paramArrayOfByte;
            gotoBack();
        }
    };

    private Camera.AutoFocusCallback mAutoFocusCallBack = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean paramBoolean, Camera paramCamera) {
            try {
                camera.takePicture(
                        mShutterCallback,
                        mPictureCallbackRaw,
                        jpegCallback);
                return;
            } catch (Exception localException2) {
                localException2.printStackTrace();
                return;
            }
        }
    };

    Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {
        }
    };

    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
        }
    };

    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private ImageView imvBack;
    private ImageView imvLight;
    private Uri targetResource = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private void setPictureSize(Camera.Parameters paramParameters) {
        List<Size> localList = paramParameters.getSupportedPictureSizes();
        if (localList == null)
            return;
        for (Size size : localList) {
            Log.v(TAG, "supported size: " + size.height + "*" + size.width);
        }
    }

    private void setPictureSize(Camera paramCamera) {
        Camera.Parameters localParameters = paramCamera.getParameters();
        localParameters.setPictureFormat(ImageFormat.JPEG);
        List<Size> localList = localParameters.getSupportedPictureSizes();
        if (localList == null)
            return;
        for (Size size : localList) {
            Log.v(TAG, "supported size: " + size.height + "*" + size.width);
        }
    }

    public boolean isTouchAlready() {
        return this.isTouchAlready;
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setFullscreen();
        setNoTitle();
        setTouchAlready(false);
        CameraManager.init(getApplication());
        getWindow().setFormat(-3);
        setContentView(R.layout.idcard_camera);
        this.cameraView = ((CameraView) findViewById(R.id.camera_view));
        this.cameraView.draw(new Canvas());
        this.surfaceView = ((SurfaceView) findViewById(R.id.preview_view));
        this.surfaceHolder = this.surfaceView.getHolder();
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(3);
        this.imvBack = (ImageView) findViewById(R.id.imv_back);
        this.imvLight = (ImageView) findViewById(R.id.imv_light);
        this.imvBack.setOnClickListener(this);
        this.imvLight.setOnClickListener(this);
        this.imvLight.setImageResource(R.drawable.imv_light_off);
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        screenWidth=display.getWidth();
        screenHeight=display.getHeight();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        parameter = getIntent().getStringExtra("Parameter");
        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                try {
                    camera.autoFocus(mAutoFocusCallBack);
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        };
        timer.schedule(task,2000);
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        paramMenu.add(0, 0, 0, "View Pictures").setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem paramMenuItem) {
                        Intent localIntent = new Intent(
                                "android.intent.action.VIEW",
                                IdCardCameraNew.this.targetResource);
                        IdCardCameraNew.this.startActivity(localIntent);
                        return true;
                    }
                });
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            this.camera.autoFocus(this.mAutoFocusCallBack);
            if ((keyCode == 4) && (event.getRepeatCount() == 0)) {
                isColse = true;
                finish();
            }
            return true;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    protected void onStop() {
        super.onStop();
    }

    public boolean onTouchEvent(MotionEvent event) {
        try {
            this.camera.autoFocus(this.mAutoFocusCallBack);
            return true;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        switch (event.getAction()) {
            case 0:
                return true;
            case 1:
                if (!isTouchAlready()) {
                    setTouchAlready(true);
                }
                break;
            default:
                return true;
        }
        try {
            this.camera.autoFocus(this.mAutoFocusCallBack);
            return true;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return true;
    }

    public void setFullscreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    public void setNoTitle() {
        requestWindowFeature(1);
    }

    public void setTouchAlready(boolean paramBoolean) {
        this.isTouchAlready = paramBoolean;
        if (!paramBoolean)
            return;
        new Thread() {
            public void run() {
                try {
                    sleep(3000L);
                    IdCardCameraNew.this.isTouchAlready = false;
                    return;
                } catch (InterruptedException localInterruptedException) {
                    localInterruptedException.printStackTrace();
                    IdCardCameraNew.this.isTouchAlready = false;
                }
            }
        }.start();
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1,
                               int paramInt2, int paramInt3) {
        if (this.isPreviewRunning)
            this.camera.stopPreview();

        try {
            this.camera.setPreviewDisplay(paramSurfaceHolder);
            this.camera.startPreview();
            this.isPreviewRunning = true;
            return;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {
        this.camera = Camera.open();
        if (camera != null){
            Camera.Parameters localParameters = this.camera.getParameters();
            setPictureSize(this.camera);
            localParameters.set("jpeg-quality", 100);
            this.camera.setParameters(localParameters);
        }
    }

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        this.camera.stopPreview();
        this.isPreviewRunning = false;
        this.camera.release();
    }

    /**
     * 返回上一级
     */
    private void gotoBack(){
        Class<?> clz = IDCardInfoReaderActivity.class;
        if(!TextUtils.isEmpty(parameter)){
            if(parameter.equals("QuerycusActivity")){
                clz = QuerycusActivity.class;
            }else if(parameter.equals("IDCardinfoReaderActivity")){
                clz = IDCardInfoReaderActivity.class;
            }
        }
        isTakephoto = true;
        Intent intent = new Intent(IdCardCameraNew.this, clz);
        startActivityForResult(intent, 1);
        finish();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.imv_back) {
            if (parameter.equals("QuerycusActivity")){
                gotoBack();
            }else{
                finish();
            }
        } else if (i == R.id.imv_light) {
            switchLight(isOpenLight);
        }
    }


    /**
     * 闪关灯打开关闭状态
     *
     * @param isOpen
     */
    private void switchLight(boolean isOpen) {
        if (this.camera == null) {
            return;
        }

        if (!isOpen) {
            turnLightOn(this.camera);
            this.imvLight.setImageResource(R.drawable.imv_light_on);
            isOpenLight = true;
        } else {
            turnLightOff(this.camera);
            this.imvLight.setImageResource(R.drawable.imv_light_off);
            isOpenLight = false;
        }
    }

    public static void turnLightOn(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if (flashModes == null) {
            // Use the screen as a flashlight (next best thing)
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }

    public static void turnLightOff(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null) {
            return;
        }
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }


    public class SizeComparator implements Comparator<Size> {
        public SizeComparator() {
        }

        public int compare(Size paramSize1, Size paramSize2) {
            return paramSize2.width * paramSize2.height - paramSize1.width
                    * paramSize1.height;
        }
    }
}