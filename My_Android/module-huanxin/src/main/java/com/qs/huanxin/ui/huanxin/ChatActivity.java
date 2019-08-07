package com.qs.huanxin.ui.huanxin;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.base.BaseMyActivity;
import com.qs.base.router.RouterActivityPath;

/**
* 聊天界面
* @Author ltzz
* @Date 2019/8/1
*/
@Route(path = RouterActivityPath.Base.CHAT)
public class ChatActivity extends BaseMyActivity {
    @Override
    protected boolean statusBarDarkFont() {
        return false;
    }

    @Override
    protected View titleLayout() {
        return null;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}
