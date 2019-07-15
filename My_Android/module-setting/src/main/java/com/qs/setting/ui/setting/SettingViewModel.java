package com.qs.setting.ui.setting;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.qs.base.router.RouterActivityPath;
import com.qs.setting.R;
import com.qs.setting.BR;
import com.qs.setting.entity.SettingEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class SettingViewModel extends BaseViewModel {

    public ObservableList<SettingItemViewModel> observableList = new ObservableArrayList<>();
    public ItemBinding<SettingItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.setting_item_recyclerview_setting);
    public final BindingRecyclerViewAdapter<SettingItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public SettingViewModel(@NonNull Application application) {
        super(application);

        observableList.add(new SettingItemViewModel(this,
                new SettingEntity("0", 1, "Demo Simple", "", "", RouterActivityPath.Setting.PAGER_DEMO), application));

    }

}
