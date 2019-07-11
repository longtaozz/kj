package com.qs.home.ui.home;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qs.base.router.RouterFragmentPath;
import com.qs.home.R;
import com.qs.home.BR;
import com.qs.home.adapter.ViewPagerBindingAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeViewModel extends BaseViewModel {

    public final String[] mTitles = new String[]{"关注", "推荐", "热点", "深圳", "视频", "科技"};
    public final List<Fragment> mFragmentList = new ArrayList<>();

    public DataChangeObservable mDataChange = new DataChangeObservable();

    public class DataChangeObservable {
        public ObservableBoolean isChange = new ObservableBoolean(false);
    }

    public HomeViewModel(@NonNull Application application) {
        super(application);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        // DEMO 实际请使用自己的
        for (String mTitle : mTitles) {
            mFragmentList.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_ATTENTION).navigation());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataChange.isChange.set(true);
            }
        }, 200);
    }

    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {

        }
    });
}
