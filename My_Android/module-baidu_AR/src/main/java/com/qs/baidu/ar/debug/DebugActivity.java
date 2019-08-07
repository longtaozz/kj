package com.qs.baidu.ar.debug;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.luck.picture.lib.permissions.RxPermissions;
import com.qs.baidu.ar.R;

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
    }


    @SuppressLint("InlinedApi")
    String[] perms = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };
}
