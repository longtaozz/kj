package com.qs.baidu.debug;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.luck.picture.lib.permissions.RxPermissions;
import com.qs.baidu.R;
import com.qs.baidu.ui.baidu.TestCameraActivity;
import com.qs.baidu.ui.baidu.TestSpeechActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 组件单独运行时的调试界面，不会被编译进release里
 * Created by goldze on 2018/6/21
 */

public class DebugActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidu_main_activity);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions permissions = new RxPermissions(DebugActivity.this);
                permissions.request(perms).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(DebugActivity.this, TestCameraActivity.class);
                            startActivity(intent);
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
        });
        //语音识别按钮
        findViewById(R.id.speech).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebugActivity.this, TestSpeechActivity.class);
                startActivity(intent);
            }
        });
    }


    @SuppressLint("InlinedApi")
    String[] perms = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };
}
