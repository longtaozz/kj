package com.qs.sign.ui.register;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.qs.sign.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class RegisterViewModel extends BaseViewModel {

    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showLong(getApplication().getString(R.string.sign_register));
        }
    });

    public static int KEY_COLOR_TYPE = 0;

    public BindingCommand onSwitchClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KEY_COLOR_TYPE = KEY_COLOR_TYPE == 1 ? 0 : 1;
            SPUtils.getInstance().put("KEY_COLOR_TYPE", KEY_COLOR_TYPE);

        }
    });


}
