package com.qs.home.ui.home;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.qs.base.router.RouterFragmentPath;
import com.qs.home.BR;
import com.qs.home.R;
import com.qs.home.adapter.BaseFragmentPagerAdapter;
import com.qs.home.databinding.FragmentHomeBinding;

import java.util.Arrays;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.mDataChange.isChange.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                initViewPageData();
            }
        });
    }

    @Override
    public void initViewObservable() {
    }

    private void initViewPageData() {

        //设置Adapter
        BaseFragmentPagerAdapter pagerAdapter = new BaseFragmentPagerAdapter(
                getChildFragmentManager(), viewModel.mFragmentList, Arrays.asList(viewModel.mTitles));
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setViewPager(binding.viewPager, viewModel.mTitles);
    }
}
