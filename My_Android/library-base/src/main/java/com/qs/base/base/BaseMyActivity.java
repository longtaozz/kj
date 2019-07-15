package com.qs.base.base;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.qs.base.interfaces.DialogListener;
import com.qs.base.utils.CommomDialog;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;

public abstract class BaseMyActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //沉侵状态设置
        if (titleLayout() != null)
            ImmersionBar.with(this).statusBarDarkFont(statusBarDarkFont(), 0.2f).titleBar(titleLayout()).init();
        else
            ImmersionBar.with(this).statusBarDarkFont(statusBarDarkFont(), 0.2f).init();
    }

    //显示弹窗(没有的为null或0)
    public void showHint(String messageStr, String title, int viewId, String confirmStr, String cancelStr, final DialogListener dialogListener) {
        new CommomDialog(this, viewId, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialogListener.complete(dialog, confirm);
            }
        })
                .setTitle(title)
                .setMessage(messageStr)
                .setNegativeButton(cancelStr)
                .setPositiveButton(confirmStr)
                .show();
    }

    public void startActivity(Class<?> clz, String strName, Integer state) {
        Intent i = new Intent(this, clz);
        i.putExtra(strName, state);
        startActivity(i);
    }


    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    //状态栏颜色(true黑色，false白色)
    protected abstract boolean statusBarDarkFont();

    //是否使用沉侵状态栏
//    protected abstract boolean isStatusBarEnabled();

    protected abstract View titleLayout();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
