package com.qs.huanxin.debug;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.luck.picture.lib.permissions.RxPermissions;
import com.qs.base.base.BaseMyActivity;
import com.qs.huanxin.BR;
import com.qs.huanxin.R;
import com.qs.huanxin.VM.DebugViewModel;
import com.qs.huanxin.databinding.HuanxinLoginActivityBinding;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 组件单独运行时的调试界面，不会被编译进release里
 * Created by goldze on 2018/6/21
 */

public class DebugActivity extends BaseMyActivity<HuanxinLoginActivityBinding, DebugViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.huanxin_login_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected boolean statusBarDarkFont() {
        return true;
    }

    @Override
    protected View titleLayout() {
        return null;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions permissions = new RxPermissions(DebugActivity.this);
        permissions.request(perms).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @SuppressLint("InlinedApi")
    String[] perms = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
}
