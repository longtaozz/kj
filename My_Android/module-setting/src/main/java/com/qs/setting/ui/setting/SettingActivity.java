package com.qs.setting.ui.setting;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterActivityPath;
import com.qs.setting.BR;
import com.qs.setting.R;
import com.qs.setting.databinding.SettingActivitySettingBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.Setting.PAGER_SETTING)
public class SettingActivity extends BaseActivity<SettingActivitySettingBinding, SettingViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.setting_activity_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.qsTitleNavi.getInstance()
                .setTitleLeftText(getString(R.string.setting_component))
                .setAutoFinish(this);
    }
}
