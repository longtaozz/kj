package com.qs.setting.ui.demo;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.qs.base.router.RouterActivityPath;
import com.qs.setting.BR;
import com.qs.setting.R;
import com.qs.setting.entity.DemoEntity;
import com.qs.setting.entity.SettingEntity;
import com.qs.setting.ui.setting.SettingItemViewModel;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class DemoSimpleViewModel extends BaseViewModel {

    public ObservableList<DemoSimpleItemViewModel> observableList = new ObservableArrayList<>();
    public ItemBinding<DemoSimpleItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_recyclerview_demo);
    public final BindingRecyclerViewAdapter<DemoSimpleItemViewModel> adapter = new BindingRecyclerViewAdapter<>();

    public DemoSimpleViewModel(@NonNull Application application) {
        super(application);

        observableList.add(new DemoSimpleItemViewModel(this,
                new DemoEntity("0", 1, application.getString(R.string.setting_photo_select), "", "", RouterActivityPath.Base.PAGER_PHOTO_MAIN), application));

        observableList.add(new DemoSimpleItemViewModel(this,
                new DemoEntity("1", 1, application.getString(R.string.setting_slide_tab), "", "", RouterActivityPath.Base.PAGER_SLIDE_TAB), application));

        observableList.add(new DemoSimpleItemViewModel(this,
                new DemoEntity("2", 1, application.getString(R.string.setting_skin), "", "", RouterActivityPath.Setting.PAGER_SKIN), application));

        observableList.add(new DemoSimpleItemViewModel(this,
                new DemoEntity("3", 1, application.getString(R.string.setting_qrcode), "", "", ""), application));

    }

}
