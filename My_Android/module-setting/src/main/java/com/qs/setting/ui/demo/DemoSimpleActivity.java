package com.qs.setting.ui.demo;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterActivityPath;
import com.qs.setting.BR;
import com.qs.setting.R;
import com.qs.setting.databinding.SettingActivityDemoSimpleBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.Setting.PAGER_DEMO)
public class DemoSimpleActivity extends BaseActivity<SettingActivityDemoSimpleBinding, DemoSimpleViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.setting_activity_demo_simple;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.qsTitleNavi.getInstance()
                .setTitleLeftText("Demo Simple")
                .setAutoFinish(this);

    }
}
