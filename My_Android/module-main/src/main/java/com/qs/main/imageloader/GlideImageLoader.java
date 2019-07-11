package com.qs.main.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object object, ImageView imageView) {

        Glide.with(imageView.getContext())
                .load(object)
                .apply(new RequestOptions())
                .into(imageView);
    }
}