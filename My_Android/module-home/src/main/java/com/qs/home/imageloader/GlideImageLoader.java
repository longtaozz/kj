package com.qs.home.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.qs.base.R;
import com.qs.home.entity.AttentionEntity;
import com.youth.banner.loader.ImageLoader;

import me.goldze.mvvmhabit.binding.viewadapter.image.ViewAdapter;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object object, ImageView imageView) {

        //Glide 加载图片简单用法
        if (object instanceof AttentionEntity.ItemsEntity.ItemBanner) {
            AttentionEntity.ItemsEntity.ItemBanner itemBanner = (AttentionEntity.ItemsEntity.ItemBanner) object;
            ViewAdapter.setImageUri(imageView, itemBanner.getImage(), R.drawable.ic_placeholder, R.drawable.image_placeholder);
        } else {
            ViewAdapter.setImageUri(imageView, (String) object, R.drawable.ic_placeholder, R.drawable.image_placeholder);
        }

    }
}