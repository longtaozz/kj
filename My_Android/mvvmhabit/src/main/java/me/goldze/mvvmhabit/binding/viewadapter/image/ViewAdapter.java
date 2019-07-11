package me.goldze.mvvmhabit.binding.viewadapter.image;


import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import me.goldze.mvvmhabit.R;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes", "errorRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes, int errorRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes).error(errorRes))
                    .into(imageView);
        }
    }

    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"url", "placeholderRes", "errorRes", "roundingRadius"}, requireAll = false)
    public static void setRoundedImageUri(ImageView imageView, String url, int placeholderRes, int errorRes, int roundingRadius) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions mRequestOptions = RequestOptions
                    .bitmapTransform(roundedCorners)
                    .override(300, 300)
                    .placeholder(placeholderRes)
                    .error(errorRes);

            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(mRequestOptions)
                    .into(imageView);
        }
    }

    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"url", "placeholderRes", "errorRes"}, requireAll = false)
    public static void setCircleImageUri(ImageView imageView, String url, int placeholderRes, int errorRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            RequestOptions mRequestOptions = RequestOptions
                    .circleCropTransform()
                    .placeholder(placeholderRes)
                    .error(errorRes);

            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(mRequestOptions)
                    .into(imageView);
        }
    }

}

