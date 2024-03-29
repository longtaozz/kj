package com.qs.sign.ui.login;

import android.databinding.Observable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterActivityPath;
import com.qs.sign.BR;
import com.qs.sign.R;
import com.qs.sign.databinding.SignActivityLoginBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 一个MVVM模式的登陆界面
 * 作为登录验证模块的路由页
 */
@Route(path = RouterActivityPath.Sign.PAGER_LOGIN)
public class LoginActivity extends BaseActivity<SignActivityLoginBinding, LoginViewModel> {
    //ActivityLoginBinding类是databinding框架自定生成的,对应activity_login.xml
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.sign_activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.uc.pSwitchObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pSwitchObservable.get()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.sign_show_psw_press);
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.sign_show_psw);
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }
}

