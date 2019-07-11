package com.qs.user.ui.userdetail;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.qs.base.contract.UserInfoEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.viewadapter.image.ViewAdapter;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Created by goldze on 2018/6/21.
 */

public class UserDetailViewModel extends BaseViewModel {

    public ObservableField<UserInfoEntity> mUserInfo = new ObservableField<>();

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserInfo(UserInfoEntity entity) {
        mUserInfo.set(entity);
    }

    //回传参数
    public BindingCommand backOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            // TODO 模拟修改
            mUserInfo.get().setName("测试十号");
            mUserInfo.get().setAvatar("http://sc.jb51.net/uploads/allimg/150716/14-150G6093925932.jpg");

            RxBus.getDefault().post(mUserInfo.get());
            finish();
        }
    });
}
