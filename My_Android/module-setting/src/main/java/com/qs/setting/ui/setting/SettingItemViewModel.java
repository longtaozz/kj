package com.qs.setting.ui.setting;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qs.base.contract.UserInfoEntity;
import com.qs.base.simple.pictureselector.PhotoMainActivity;
import com.qs.base.simple.slidetab.SlidingTabActivity;
import com.qs.base.simple.zxing.ZXingActivity;
import com.qs.setting.entity.SettingEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.KLog;

public class SettingItemViewModel extends ItemViewModel<SettingViewModel> {

    public ObservableField<SettingEntity> entity = new ObservableField<>();

    private Application mApplication;

    public SettingItemViewModel(@NonNull SettingViewModel viewModel, SettingEntity entity, Application application) {
        super(viewModel);
        this.entity.set(entity);
        this.mApplication = application;
    }

    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (entity.get().getId()) {
                default:
                    ARouter.getInstance().build(entity.get().getRouterPath()).navigation();
                    break;
            }
        }
    });

}
