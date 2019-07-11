package com.qs.user.ui.me;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qs.base.router.RouterFragmentPath;
import com.qs.user.BR;
import com.qs.user.R;
import com.qs.user.databinding.FragmentMeBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.viewadapter.image.ViewAdapter;

@Route(path = RouterFragmentPath.User.PAGER_ME)
public class MeFragment extends BaseFragment<FragmentMeBinding, MeViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();

        ViewAdapter.setCircleImageUri(binding.ivAvatar, "", R.mipmap.ic_launcher,
                R.mipmap.ic_launcher_round);

        viewModel.mDataChange.mUserInfoObservable.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        if (viewModel.mDataChange.mUserInfoObservable.get()) {
                            changeData();
                        }
                    }
                });
    }

    private void changeData() {
        ViewAdapter.setCircleImageUri(binding.ivAvatar, viewModel.mUserInfoEntity.get().getAvatar(),
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round);
//        binding.tvUsername.setText(viewModel.mUserInfoEntity.get().getName());
    }
}
