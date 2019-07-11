package com.qs.user.ui.me;

import android.app.Activity;
import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qs.base.contract.UserInfoEntity;
import com.qs.base.global.SPKeyGlobal;
import com.qs.base.router.RouterActivityPath;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.command.BindingFunction;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MeViewModel extends BaseViewModel {

    public ObservableField<UserInfoEntity> mUserInfoEntity = new ObservableField<>();
    private Disposable subscribe;
    public DataChangeObservable mDataChange = new DataChangeObservable();

    public MeViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        initData();
    }

    public void initData() {

    }

    public class DataChangeObservable {
        public ObservableBoolean mUserInfoObservable = new ObservableBoolean(false);
    }

    //登录按钮点击事件
    public BindingCommand startLoginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //采用ARouter+RxBus实现组件间通信
            ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();

            subscribe = RxBus.getDefault().toObservable(UserInfoEntity.class)
                    .subscribe(new Consumer<UserInfoEntity>() {
                        @Override
                        public void accept(UserInfoEntity userInfoEntity) throws Exception {
                            //登录成功后重新刷新数据
                            mUserInfoEntity.set(userInfoEntity);
                            initData();
                            //解除注册
                            RxSubscriptions.remove(subscribe);
                        }
                    });
            RxSubscriptions.add(subscribe);
        }
    });
    //退出登录按钮点击事件
    public BindingCommand outLoginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            SPUtils.getInstance().put(SPKeyGlobal.USER_INFO, "");
            initData();
        }
    });

    public BindingCommand toSettingOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ARouter.getInstance().build(RouterActivityPath.Setting.PAGER_SETTING).navigation();
        }
    });

    public BindingCommand toUserDetailOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            Bundle bundle = new Bundle();
            bundle.putParcelable("userInfoEntity", new UserInfoEntity("", "", "测试一号",
                    "http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg", 1, 1, ""));

            ARouter.getInstance().build(RouterActivityPath.User.PAGER_USERDETAIL)
                    .with(bundle)
                    .navigation();

            subscribe = RxBus.getDefault().toObservable(UserInfoEntity.class)
                    .subscribe(new Consumer<UserInfoEntity>() {
                        @Override
                        public void accept(UserInfoEntity userInfoEntity) throws Exception {
                            mUserInfoEntity.set(userInfoEntity);
                            mDataChange.mUserInfoObservable.set(true);
                        }
                    });
            RxSubscriptions.add(subscribe);
        }
    });

}
