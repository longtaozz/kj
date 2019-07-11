package com.qs.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qs.main.R;
import com.qs.main.imageloader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StatusBarUtil;
import me.goldze.mvvmhabit.utils.StringUtils;

public class SplashActivity extends Activity {

    private Banner bannerSplash;
    private TextView tvSkip;
    private RelativeLayout rlSplash;
    private RelativeLayout rlStartPage;

    private static final String KEY_FIRST_INSTALL = "key_first_install";

    List<Integer> images;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        StatusBarUtil.immersive(this);

        tvSkip = findViewById(R.id.tv_skip);
        bannerSplash = findViewById(R.id.banner_splash);
        rlSplash = findViewById(R.id.rl_splash);
        rlStartPage = findViewById(R.id.rl_start_page);

        //判断是否第一次启动
        if (!SPUtils.getInstance().getBoolean(KEY_FIRST_INSTALL)) {
            rlSplash.setVisibility(View.VISIBLE);
            rlStartPage.setVisibility(View.GONE);
            SPUtils.getInstance().put(KEY_FIRST_INSTALL, true);
            images = new ArrayList<>();
            images.add(R.drawable.guide_page_0);
            images.add(R.drawable.guide_page_1);
            images.add(R.drawable.guide_page_2);
            bannerSplash.setImages(images).isAutoPlay(false).setImageLoader(new GlideImageLoader()).start();

            tvSkip.setVisibility(View.GONE);
            bannerSplash.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tvSkip.setVisibility(position == images.size() - 1 ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            tvSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inMain();
                }
            });

        } else {
            rlSplash.setVisibility(View.GONE);
            rlStartPage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    inMain();
                }
            }, 3 * 1000);
            //请加载APP数据
        }

    }

    /**
     * 进入主页面
     */
    private void inMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
