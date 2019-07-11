package com.qs.home.ui.attention;

import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterFragmentPath;
import com.qs.home.BR;
import com.qs.home.R;
import com.qs.home.databinding.FragmentAttentionBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

@Route(path = RouterFragmentPath.Home.PAGER_ATTENTION)
public class AttentionFragment extends BaseFragment<FragmentAttentionBinding, AttentionViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_attention;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {

        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.onLoadMoreCommand.execute();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.onRefreshCommand.execute();
            }
        });

        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.refreshLayout.finishRefresh();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.refreshLayout.finishLoadMore();
            }
        });
        //监听删除条目
        viewModel.deleteItemLiveData.observe(this, new Observer<AttentionItemViewModel>() {
            @Override
            public void onChanged(@Nullable final AttentionItemViewModel itemViewModel) {
                int index = viewModel.getPosition(itemViewModel);
                //删除选择对话框
                if (itemViewModel != null) {
                    MaterialDialogUtils.showBasicDialog(getContext(), "提示", "是否删除【" + itemViewModel.mItemEntity.get().getName() + "】？ position：" + index)
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    ToastUtils.showShort("取消");
                                }
                            }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            viewModel.deleteItem(itemViewModel);
                        }
                    }).show();
                }
            }
        });
    }
}
