package com.qs.user.ui.userdetail;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qs.base.contract.UserInfoEntity;
import com.qs.base.router.RouterActivityPath;
import com.qs.user.BR;
import com.qs.user.R;
import com.qs.user.databinding.UserActivityUserDetailBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.viewadapter.image.ViewAdapter;

@Route(path = RouterActivityPath.User.PAGER_USERDETAIL)
public class UserDetailActivity extends BaseActivity<UserActivityUserDetailBinding, UserDetailViewModel> {

    //拿到路由过来的参数
    @Autowired()
    UserInfoEntity userInfoEntity;

    @Override
    public void initParam() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.user_activity_user_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        binding.qsTitleNavi.getInstance()
                .setAutoFinish(this)
                .setTitleLeftText(getString(R.string.user_user_detail));

        viewModel.setUserInfo(userInfoEntity);

        ViewAdapter.setCircleImageUri(binding.ivAvatar, userInfoEntity.getAvatar(), R.mipmap.ic_launcher, R.mipmap.ic_launcher_round);
    }
}

