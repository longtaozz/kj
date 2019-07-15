package com.qs.work.ui.work;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterFragmentPath;
import com.qs.work.BR;
import com.qs.work.R;
import com.qs.work.databinding.WorkFragmentWorkBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * Created by goldze on 2018/6/21
 */
@Route(path = RouterFragmentPath.Work.PAGER_WORK)
public class WorkFragment extends BaseFragment<WorkFragmentWorkBinding, WorkViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.work_fragment_work;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
    }
}
