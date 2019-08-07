package com.qs.huanxin.listener;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HxConnectionListener implements EMConnectionListener {
    @Override
    public void onConnected() {
    }

    @Override
    public void onDisconnected(final int error) {
        if (error == EMError.USER_REMOVED) {
            // 显示帐号已经被移除
            KLog.e("帐号已经被移除");
        } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
            // 显示帐号在其他设备登录
            KLog.e("帐号在其他设备登录");
        } else {
        }
    }
}