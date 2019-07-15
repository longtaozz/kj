package com.qs.baidu.VM;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.qs.baidu.util.GetClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 图片识别demoVM
 *
 * @Author ltzz
 * @Date 2019/7/13
 */
public class TsetActivityViewModel extends BaseViewModel {
    public TsetActivityViewModel(@NonNull Application application) {
        super(application);
    }

    //定长线程池
    public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

    /**
     * 通用物体识别
     *
     * @param image     本地图片路径或者图片二进制数据（与imgUrl二选一）
     * @param baike_num 返回百科信息的结果数，默认不返回
     * @param imgUrl    参数为本地路径（与image二选一）
     */
    public void imgGeneral(final byte[] image, String baike_num, final String imgUrl, final Handler handler) {
        // 传入可选参数调用接口
        final HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", baike_num);

        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (image != null) {
                        JSONObject re = GetClient.getImgInstance().advancedGeneral(image, options);
                        KLog.e(re);
                        Message msg = new Message();
                        msg.obj = re;
                        handler.sendMessage(msg);
                    } else {
                        JSONObject re = GetClient.getImgInstance().advancedGeneral(imgUrl, options);
                        KLog.e(re);
                        Message msg = new Message();
                        msg.obj = re;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }
}
