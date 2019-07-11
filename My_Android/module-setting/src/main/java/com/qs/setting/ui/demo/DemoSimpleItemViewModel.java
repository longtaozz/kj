package com.qs.setting.ui.demo;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qs.base.simple.pictureselector.PhotoMainActivity;
import com.qs.base.simple.slidetab.SlidingTabActivity;
import com.qs.base.simple.zxing.ZXingActivity;
import com.qs.setting.entity.DemoEntity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.KLog;

public class DemoSimpleItemViewModel extends ItemViewModel<DemoSimpleViewModel> {

    public ObservableField<DemoEntity> mEntity = new ObservableField<>();

    private Disposable subscribe;
    private Application mApplication;

    public DemoSimpleItemViewModel(@NonNull DemoSimpleViewModel viewModel, DemoEntity entity, Application application) {
        super(viewModel);

        this.mEntity.set(entity);
        this.mApplication = application;
    }

    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (mEntity.get().getId()) {
                case "0":
                    mApplication.startActivity(new Intent(mApplication, PhotoMainActivity.class));
                    break;
                case "1":
                    mApplication.startActivity(new Intent(mApplication, SlidingTabActivity.class));
                    break;
                case "3":
                    mApplication.startActivity(new Intent(mApplication, ZXingActivity.class));
                    subscribe = RxBus.getDefault().toObservable(String.class)
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String result) throws Exception {
                                    KLog.e(result);
                                    //解除注册
                                    RxSubscriptions.remove(subscribe);
                                }
                            });
                    RxSubscriptions.add(subscribe);
                    break;
                default:
                    ARouter.getInstance().build(mEntity.get().getRouterPath()).navigation();
                    break;
            }
        }
    });
}
