package com.qs.sign.ui.register;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterActivityPath;
import com.qs.sign.BR;
import com.qs.sign.R;
import com.qs.sign.databinding.ActivityRegisterBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 注册页面
 */
@Route(path = RouterActivityPath.Sign.PAGER_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }
}
