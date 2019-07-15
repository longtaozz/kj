package com.qs.baidu.ui.baidu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.qs.baidu.BR;
import com.qs.baidu.R;
import com.qs.baidu.VM.TsetActivityViewModel;
import com.qs.baidu.databinding.BaiduPictureActivityBinding;
import com.qs.baidu.util.ImgUtil;
import com.qs.base.base.BaseMyActivity;

import java.io.InputStream;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 图片识别demo
 *
 * @Author ltzz
 * @Date 2019/7/13
 */
public class TsetActivity extends BaseMyActivity<BaiduPictureActivityBinding, TsetActivityViewModel> {
    @Override
    protected boolean statusBarDarkFont() {
        return true;
    }

    @Override
    protected View titleLayout() {
        return null;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.baidu_picture_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InputStream is = null;
        try {
            is = getAssets().open("test_img.jpg");
        } catch (Exception e) {
            KLog.e("错误", e.getMessage());
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        viewModel.imgGeneral(ImgUtil.getBitmapByte(bitmap), "2", null,handler);

    }

}
