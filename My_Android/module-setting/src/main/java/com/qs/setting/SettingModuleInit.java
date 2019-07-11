package com.qs.setting;

import android.app.Application;

import com.qs.base.base.IModuleInit;

import me.goldze.mvvmhabit.utils.KLog;

public class SettingModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("设置模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("设置模块初始化 -- onInitLow");
        return false;
    }
}
