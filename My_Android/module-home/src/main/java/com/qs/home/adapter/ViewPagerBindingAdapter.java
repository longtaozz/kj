package com.qs.home.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.qs.home.databinding.HomeItemViewpagerBinding;
import com.qs.home.ui.home.ViewPagerItemViewModel;

import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;

/**
 * Created by goldze on 2018/6/21.
 */

public class ViewPagerBindingAdapter extends BindingViewPagerAdapter<ViewPagerItemViewModel> {
    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, int position, ViewPagerItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        HomeItemViewpagerBinding _binding = (HomeItemViewpagerBinding) binding;
        item.clickEvent.observe((LifecycleOwner) _binding.getRoot().getContext(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.showShort(s);
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
