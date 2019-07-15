package com.qs.msg.ui.msg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterFragmentPath;
import com.qs.msg.BR;
import com.qs.msg.R;
import com.qs.msg.databinding.MsgFragmentMsgBinding;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * Created by goldze on 2018/6/21
 */
@Route(path = RouterFragmentPath.Msg.PAGER_MSG)
public class MsgFragment extends BaseFragment<MsgFragmentMsgBinding, MsgViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.msg_fragment_msg;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
    }
}
