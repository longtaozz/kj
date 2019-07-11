package com.qs.home.ui.attention;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import com.qs.base.utils.RetrofitClient;
import com.qs.home.BR;
import com.qs.home.R;
import com.qs.home.databinding.ItemAttentionHeadBinding;
import com.qs.home.databinding.ItemAttentionLeftBinding;
import com.qs.home.databinding.ItemAttentionRightBinding;
import com.qs.home.entity.AttentionEntity;
import com.qs.home.imageloader.GlideImageLoader;
import com.qs.home.service.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.viewadapter.image.ViewAdapter;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class AttentionViewModel extends BaseViewModel {

    private static final String MultiRecycleType_Head = "head";
    private static final String MultiRecycleType_Left = "left";
    private static final String MultiRecycleType_Right = "right";

    private int itemIndex = 0;
    public MutableLiveData<AttentionItemViewModel> deleteItemLiveData = new MutableLiveData();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //下拉刷新完成
        ObservableBoolean finishRefreshing = new ObservableBoolean(false);
        //上拉加载完成
        ObservableBoolean finishLoadmore = new ObservableBoolean(false);
    }

    public AttentionViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (observableList == null || observableList.size() == 0) {
            onRefreshCommand.execute();
        }

    }

    //给RecyclerView添加ObservableList
    public ObservableList<AttentionItemViewModel> observableList = new ObservableArrayList<>();
    //RecyclerView多布局添加ItemBinding
    public ItemBinding<AttentionItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<AttentionItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, AttentionItemViewModel item) {
            //通过item的类型, 动态设置Item加载的布局
            String itemType = item.mItemEntity.get().getItemType();
            if (MultiRecycleType_Head.equals(itemType)) {
                //设置头布局
                itemBinding.set(BR.viewModel, R.layout.item_attention_head);
            } else if (MultiRecycleType_Left.equals(itemType)) {
                //设置左布局
                itemBinding.set(BR.viewModel, R.layout.item_attention_left);
            } else if (MultiRecycleType_Right.equals(itemType)) {
                //设置右布局
                itemBinding.set(BR.viewModel, R.layout.item_attention_right);
            }
        }
    });
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象
    public final BindingRecyclerViewAdapter<AttentionItemViewModel> adapter = new BindingRecyclerViewAdapter<AttentionItemViewModel>() {
        @Override
        public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, AttentionItemViewModel item) {
            super.onBindBinding(binding, variableId, layoutRes, position, item);

            String itemType = item.mItemEntity.get().getItemType();
            if (MultiRecycleType_Head.equals(itemType)) {
                //设置头布局
                ItemAttentionHeadBinding itemAttentionHeadBinding = (ItemAttentionHeadBinding) binding;

                itemAttentionHeadBinding.banner.setImages(item.mItemEntity.get().getImages()).setImageLoader(new GlideImageLoader()).start();

//                //设置banner样式
//                itemAttentionHeadBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//                //设置图片加载器
//                itemAttentionHeadBinding.banner.setImageLoader(new GlideImageLoader());
//                //设置图片集合
//                itemAttentionHeadBinding.banner.setImages(item.mItemEntity.get().getImages());
//                //设置banner动画效果
//                itemAttentionHeadBinding.banner.setBannerAnimation(Transformer.DepthPage);
//                //设置标题集合（当banner样式有显示title时）
//                itemAttentionHeadBinding.banner.setBannerTitles(titles);
//                //设置自动轮播，默认为true
//                itemAttentionHeadBinding.banner.isAutoPlay(true);
//                //设置轮播时间
//                itemAttentionHeadBinding.banner.setDelayTime(1500);
//                //设置指示器位置（当banner模式中有指示器时）
//                itemAttentionHeadBinding.banner.setIndicatorGravity(BannerConfig.CENTER);
//                //banner设置方法全部调用完毕时最后调用
//                itemAttentionHeadBinding.banner.start();
            } else if (MultiRecycleType_Left.equals(itemType)) {
                //设置左布局
                ItemAttentionLeftBinding itemAttentionLeftBinding = (ItemAttentionLeftBinding) binding;
                ViewAdapter.setImageUri(
                        itemAttentionLeftBinding.ivImage,
                        item.mItemEntity.get().getImg(),
                        R.drawable.image_placeholder,
                        R.drawable.image_placeholder);
            } else if (MultiRecycleType_Right.equals(itemType)) {
                //设置右布局
                ItemAttentionRightBinding itemAttentionRightBinding = (ItemAttentionRightBinding) binding;
                ViewAdapter.setRoundedImageUri(
                        itemAttentionRightBinding.ivImage,
                        item.mItemEntity.get().getImg(),
                        R.drawable.image_placeholder,
                        R.drawable.image_placeholder,
                        10);
            }

        }
    };

    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @SuppressLint("CheckResult")
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");
//            requestNetWork();

            //TODO 模拟网络刷新，
            Observable.just("")
                    .delay(1, TimeUnit.SECONDS)
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//界面关闭自动取消
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) {
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) {
                            //刷新完成收回
                            uc.finishRefreshing.set(!uc.finishRefreshing.get());

                            observableList.clear();
                            //模拟一部分假数据
                            for (int i = 0; i < 10; i++) {
                                AttentionEntity.ItemsEntity item = new AttentionEntity.ItemsEntity();
                                item.setId(-1);
                                item.setName("055大型驱逐舰数量已高达8艘,为何052D驱逐舰还在继续下饺子?" + itemIndex++);
                                item.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554379596439&di=072127529ab53255e4c839f4896a6690&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn09%2F40%2Fw480h360%2F20180505%2Fd596-fzyqqir1691495.jpg");
                                AttentionItemViewModel itemViewModel = new AttentionItemViewModel(AttentionViewModel.this, item);
                                if (i == 0) {
                                    List<AttentionEntity.ItemsEntity.ItemBanner> images = new ArrayList<>();
                                    AttentionEntity.ItemsEntity.ItemBanner itemBanner = new AttentionEntity.ItemsEntity.ItemBanner();
                                    itemBanner.setImage("http://pic7.58cdn.com.cn/p1/big/n_v2e86f10b57dda47d8a3359223fe8c013d.jpg");
                                    images.add(itemBanner);
                                    itemBanner = new AttentionEntity.ItemsEntity.ItemBanner();
                                    itemBanner.setImage("http://pic8.nipic.com/20100721/4943220_131129269631_2.jpg");
                                    images.add(itemBanner);
                                    itemBanner = new AttentionEntity.ItemsEntity.ItemBanner();
                                    itemBanner.setImage("http://img0.imgtn.bdimg.com/it/u=3567599474,1808313878&fm=26&gp=0.jpg");
                                    images.add(itemBanner);
                                    itemBanner = new AttentionEntity.ItemsEntity.ItemBanner();
                                    itemBanner.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554357027343&di=67cb653b6c4fc6cb217d25afcb05ce7a&imgtype=0&src=http%3A%2F%2Fimg.ph.126.net%2FtUmAKo2mJ5kbaxbvxdVmbA%3D%3D%2F2507379092555461040.jpg");
                                    images.add(itemBanner);
                                    itemBanner = new AttentionEntity.ItemsEntity.ItemBanner();
                                    itemBanner.setImage("http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=d1e5a08fc1ef7609280691dc46b4c9b9/4a36acaf2edda3cca1c81c320be93901213f92f0.jpg");
                                    images.add(itemBanner);
                                    itemViewModel.mItemEntity.get().setImages(images);
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Head);
                                } else if (i % 3 == 0) {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Left);
                                } else {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Right);
                                }
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        }
                    });
        }
    });
    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @SuppressWarnings("unchecked")
        @SuppressLint("CheckResult")
        @Override
        public void call() {
            if (itemIndex > 50) {
                uc.finishLoadmore.set(!uc.finishLoadmore.get());
                return;
            }
            //模拟网络上拉加载更多
            Observable.just("")
                    .delay(3, TimeUnit.SECONDS) //延迟3秒
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))//界面关闭自动取消
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) {
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) {
                            //刷新完成收回
                            uc.finishLoadmore.set(!uc.finishLoadmore.get());
                            //模拟一部分假数据
                            for (int i = 0; i < 10; i++) {
                                AttentionEntity.ItemsEntity item = new AttentionEntity.ItemsEntity();
                                item.setId(-1);
                                item.setName("055大型驱逐舰数量已高达8艘,为何052D驱逐舰还在继续下饺子?" + itemIndex++);
                                AttentionItemViewModel itemViewModel = new AttentionItemViewModel(AttentionViewModel.this, item);
                                if (i == 0) {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Left);
                                } else if (i % 3 == 0) {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Left);
                                } else {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Right);
                                }
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        }
                    });
        }
    });

    /**
     * 网络请求方法，在ViewModel中调用，Retrofit+RxJava充当Repository，即可视为Model层
     */
    @SuppressLint("CheckResult")
    @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
    public void requestNetWork() {
        RetrofitClient.getInstance().create(ApiService.class)
                .getDemoAttention(1)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                    }
                })
                .subscribe(new Consumer<BaseResponse<AttentionEntity>>() {
                    @Override
                    public void accept(BaseResponse<AttentionEntity> response) {
                        itemIndex = 0;
                        //清除列表
                        observableList.clear();
                        //请求成功
                        if (response.getCode() == 1) {
                            //将实体赋给LiveData
                            for (int i = 0; i < response.getResult().getItems().size(); i++) {
                                AttentionEntity.ItemsEntity entity = response.getResult().getItems().get(i);
                                AttentionItemViewModel itemViewModel = new AttentionItemViewModel(AttentionViewModel.this, entity);

                                if (i == 0) {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Head);
                                } else if (i % 3 == 0) {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Left);
                                } else {
                                    itemViewModel.mItemEntity.get().setItemType(MultiRecycleType_Right);
                                }
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) {
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get());
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() {
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get());
                    }
                });
    }

    /**
     * 删除条目
     *
     * @param itemViewModel AttentionItemViewModel
     */
    public void deleteItem(AttentionItemViewModel itemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList.remove(itemViewModel);
    }

    /**
     * 获取条目下标
     *
     * @param itemViewModel AttentionItemViewModel
     * @return int
     */
    public int getPosition(AttentionItemViewModel itemViewModel) {
        return observableList.indexOf(itemViewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
