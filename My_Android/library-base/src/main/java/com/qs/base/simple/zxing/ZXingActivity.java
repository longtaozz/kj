package com.qs.base.simple.zxing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.qs.base.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ZXingActivity extends AppCompatActivity implements
        View.OnClickListener,
        QRCodeView.Delegate {

    private ZXingView mZXingView;
    boolean isOpen = false;

    private String mFromAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_zxing);
        initView();

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(perms).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(ZXingActivity.this);
                } else {
                    Toast.makeText(ZXingActivity.this,
                            R.string.base_permissions_refuse_camera_sdcard, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void initView() {

        mZXingView = (ZXingView) findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
        mFromAct = getIntent().getStringExtra("mFromAct");

        findViewById(R.id.open_flashlight).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.open_flashlight) {
            if (isOpen) {
                mZXingView.closeFlashlight();
                isOpen = false;
            } else {
                mZXingView.openFlashlight();
                isOpen = true;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        mZXingView.closeFlashlight();
        isOpen = false;
        super.onStop();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();

        mZXingView.stopCamera();
        mZXingView.stopSpotAndHiddenRect();

        if (!StringUtils.isEmpty(result)) {
            ToastUtils.showLong(result);
            //TODO 处理你的逻辑使用RxBus将值传回

            //采用ARouter+RxBus实现组件间通信
            RxBus.getDefault().post(result);
            finish();
        } else {
            mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
            mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
        }
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
    }

    @SuppressLint("InlinedApi")
    String[] perms = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
