package com.qs.baidu.ui.baidu;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.qs.baidu.BR;
import com.qs.baidu.R;
import com.qs.baidu.VM.TsetActivityViewModel;
import com.qs.baidu.databinding.BaiduCameraActivityBinding;
import com.qs.baidu.util.ImgUtil;
import com.qs.base.base.BaseMyActivity;

import java.io.IOException;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 摄像头识别demo
 *
 * @Author ltzz
 * @Date 2019/7/13
 */
public class TestCameraActivity extends BaseMyActivity<BaiduCameraActivityBinding, TsetActivityViewModel> implements SurfaceHolder.Callback, Camera.PreviewCallback {
    @Override
    protected boolean statusBarDarkFont() {
        return true;
    }

    @Override
    protected View titleLayout() {
        return null;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        //设置屏幕全屏
//        final Window win = getWindow();
//        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return R.layout.baidu_camera_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private boolean is = true;
    //图片识别处理
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            is = true;
        }
    };

    //摄像头
    private Camera mCamera;
    private SurfaceView mySV;
    //摄像头控制器
    private SurfaceHolder surfaceHolder;

    private void initView() {
        mySV = binding.surfView;
        surfaceHolder = mySV.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //添加摄像头回调
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {//打开摄像头
            mCamera = Camera.open();
            //设置摄像头预览画面
            mCamera.setPreviewDisplay(surfaceHolder);//holder
            //帧回调
            mCamera.setPreviewCallback(this);
            //设置摄像头的旋转角度
            mCamera.setDisplayOrientation(90);
            //0表示摄像头初始角度是0向左，90度表示正，向上
            mCamera.startPreview();//启动
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//        Camera.Parameters parameters = mCamera.getParameters();
//        parameters.setPreviewSize(previewWidth, previewHeight);
//        parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);
//        parameters.setPreviewFrameRate(10);
//        mCamera.setParameters(parameters);
//        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if (is) {
            is = false;
            Bitmap bitmap = ImgUtil.PreviewFrameToBitMap(bytes, camera);
            byte[] b = ImgUtil.getBitmapByte(bitmap);
            KLog.e(b);
            viewModel.imgGeneral(b, "2", null, handler);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关掉请求线程
        viewModel.fixedThreadPool.shutdown();
    }
}
