package com.qs.component;

import android.support.v7.app.AppCompatDelegate;

import com.qs.base.config.ModuleLifecycleConfig;

import me.goldze.mvvmhabit.base.BaseApplication;
import skin.support.SkinCompatManager;

/**
 * Created by goldze on 2018/6/21 0021.
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        //....
        //初始化组件(靠后)
        ModuleLifecycleConfig.getInstance().initModuleLow(this);

        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinAllActivityEnable(true)
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

}


