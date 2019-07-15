package com.qs.setting.ui.skin;

import android.databinding.Observable;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterActivityPath;
import com.qs.setting.BR;
import com.qs.setting.R;
import com.qs.setting.databinding.SettingActivitySkinBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;

import static com.qs.setting.ui.skin.SkinViewModel.KEY_THEME_MODEL;
import static com.qs.setting.ui.skin.SkinViewModel.VALUE_THEME_DAYTIME;
import static com.qs.setting.ui.skin.SkinViewModel.VALUE_THEME_NIGHT;

@Route(path = RouterActivityPath.Setting.PAGER_SKIN)
public class SkinActivity extends BaseActivity<SettingActivitySkinBinding, SkinViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.setting_activity_skin;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.qsTitleNavi.getInstance()
                .setAutoFinish(this)
                .setTitleLeftText(getString(R.string.setting_skin));

        updateSwitch();

        viewModel.mUIChange.mThemeChangeObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateSwitch();
            }
        });
    }

    private void updateSwitch() {
        switch (SPUtils.getInstance().getString(KEY_THEME_MODEL)) {
            case VALUE_THEME_DAYTIME:
                binding.switchDefault.setChecked(false);
                binding.switchDaytime.setChecked(true);
                binding.switchNight.setChecked(false);
                break;
            case VALUE_THEME_NIGHT:
                binding.switchDefault.setChecked(false);
                binding.switchDaytime.setChecked(false);
                binding.switchNight.setChecked(true);
                break;
            default:
                binding.switchDefault.setChecked(true);
                binding.switchDaytime.setChecked(false);
                binding.switchNight.setChecked(false);
                break;
        }
    }
}
