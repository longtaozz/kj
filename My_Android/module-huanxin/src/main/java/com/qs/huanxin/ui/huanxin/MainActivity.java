package com.qs.huanxin.ui.huanxin;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.qs.base.base.BaseMyActivity;
import com.qs.base.router.RouterActivityPath;
import com.qs.huanxin.BR;
import com.qs.huanxin.R;
import com.qs.huanxin.VM.MainViewModel;
import com.qs.huanxin.databinding.HuanxinConversationListBinding;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * 聊天会话列表页面
 *
 * @Author ltzz
 * @Date 2019/8/2
 */
@Route(path = RouterActivityPath.Base.LIST)
public class MainActivity extends BaseMyActivity<HuanxinConversationListBinding, MainViewModel> {
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
        return R.layout.huanxin_conversation_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EaseConversationListFragment easeConversationListFragment = new EaseConversationListFragment();
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.list, easeConversationListFragment)   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }
}
