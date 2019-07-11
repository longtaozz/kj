package com.qs.setting.ui.skin;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.qs.setting.BuildConfig;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import skin.support.SkinCompatManager;

public class SkinViewModel extends BaseViewModel {

    static final String KEY_THEME_MODEL = "key_theme_model";
    static final String VALUE_THEME_DAYTIME = "value_theme_daytime";
    static final String VALUE_THEME_NIGHT = "value_theme_night";


    public UIChangeObservable mUIChange = new UIChangeObservable();

    public class UIChangeObservable {
        ObservableField<String> mThemeChangeObservable = new ObservableField<>();
    }


    public SkinViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public BindingCommand onDefaultClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            if (BuildConfig.isBuildModule.equals("true")) {
                ToastUtils.showLong("组件为单独apk未配置");
                return;
            }

            SPUtils.getInstance().put(KEY_THEME_MODEL, "");
            mUIChange.mThemeChangeObservable.set("");
            SkinCompatManager.getInstance().restoreDefaultTheme();

        }
    });


    public BindingCommand onDaytimeClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            if (BuildConfig.isBuildModule.equals("true")) {
                ToastUtils.showLong("组件为单独apk未配置");
                return;
            }

            SPUtils.getInstance().put(KEY_THEME_MODEL, VALUE_THEME_DAYTIME);
            mUIChange.mThemeChangeObservable.set(VALUE_THEME_DAYTIME);
            SkinCompatManager.getInstance().loadSkin("daytime.skin", null,
                    SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);

        }
    });

    public BindingCommand onNightClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            if (BuildConfig.isBuildModule.equals("true")) {
                ToastUtils.showLong("组件为单独apk未配置");
                return;
            }

            SPUtils.getInstance().put(KEY_THEME_MODEL, VALUE_THEME_NIGHT);
            mUIChange.mThemeChangeObservable.set(VALUE_THEME_NIGHT);
            SkinCompatManager.getInstance().loadSkin("night.skin", null,
                    SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
        }
    });

}
