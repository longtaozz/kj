package com.qs.main.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qs.base.router.RouterActivityPath;
import com.qs.base.router.RouterFragmentPath;
import com.qs.main.BR;
import com.qs.main.R;
import com.qs.main.databinding.MainActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Created by goldze on 2018/6/21
 */
@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<MainActivityMainBinding, BaseViewModel> {
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, StatusBarUtil.getStatusBarHeight(this));
//        binding.toolbar.setLayoutParams(layoutParams);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab();
    }

    private void initFragment() {
        //ARouter拿到多Fragment(这里需要通过ARouter获取，不能直接new,因为在组件独立运行时，宿主app是没有依赖其他组件，所以new不到其他组件的Fragment)
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation();
        Fragment workFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Work.PAGER_WORK).navigation();
        Fragment msgFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Msg.PAGER_MSG).navigation();
        Fragment meFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_ME).navigation();
        mFragments = new ArrayList<>();
        mFragments.add(homeFragment);
        mFragments.add(workFragment);
        mFragments.add(msgFragment);
        mFragments.add(meFragment);
        if (homeFragment != null) {
            //默认选中第一个
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, homeFragment);
            transaction.commitAllowingStateLoss();
        }
    }

    private void initBottomTab() {

        final NavigationController navigationController = binding.pagerBottomTab.material()
                .addItem(R.drawable.icon_main_1_false, R.drawable.icon_main_1_true, getString(R.string.main_bottom_nav_title_1))
                .addItem(R.drawable.icon_main_2_false, R.drawable.icon_main_2_true, getString(R.string.main_bottom_nav_title_2))
                .addItem(R.drawable.icon_main_3_false, R.drawable.icon_main_3_true, getString(R.string.main_bottom_nav_title_3))
                .addItem(R.drawable.icon_main_4_false, R.drawable.icon_main_4_true, getString(R.string.main_bottom_nav_title_4))
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment;
                for (int i = 0, j = mFragments.size(); i < j; i++) {

                    if (i == index) {
                        continue;
                    }
                    fragment = mFragments.get(i);
                    if (fragment.isAdded()) {
                        fragmentTransaction.hide(fragment);
                    }

                }
                fragment = mFragments.get(index);
                if (fragment.isAdded()) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.add(R.id.frameLayout, fragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }
}
