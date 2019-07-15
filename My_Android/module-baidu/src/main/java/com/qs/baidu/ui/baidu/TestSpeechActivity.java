package com.qs.baidu.ui.baidu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.luck.picture.lib.permissions.RxPermissions;
import com.qs.baidu.R;
import com.qs.baidu.databinding.BaiduSpeechActivityBinding;
import com.qs.base.base.BaseMyActivity;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 百度语音识别demo
 * 所含包 bdasr_V3_20190515_c9eed5d.jar
 * libBaiduSpeechSDK.so
 * libbd_easr_s1_merge_normal_20151216.dat.so
 * libbdEASRAndroid.so
 * libbdSpilWakeup.so
 * libvad.dnn.so
 *
 * @Author ltzz
 * @Date 2019/7/15
 */
public class TestSpeechActivity extends BaseMyActivity<BaiduSpeechActivityBinding, BaseViewModel> implements EventListener {
    @Override
    protected boolean statusBarDarkFont() {
        return true;
    }

    @Override
    protected View titleLayout() {
        return binding.speechStr;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.baidu_speech_activity;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    //通过工厂创建语音识别的事件管理器。注意识别事件管理器只能维持一个，请勿同时使用多个实例。即创建一个新的识别事件管理器后，之前的那个置为null，并不再使用。
    private EventManager asr;
    // 离线命令词，需要改成true
    private boolean enableOffline = true;
    //权限是否申请成功
    private boolean isPermission = false;
    //录音是否开启
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //申请权限
        initPermission();
        //初始化控件
        initView();
        //通过工厂创建语音识别的事件管理器。注意识别事件管理器只能维持一个，请勿同时使用多个实例。即创建一个新的识别事件管理器后，之前的那个置为null，并不再使用。
        asr = EventManagerFactory.create(this, "asr");
        //注册识别监听
        asr.registerListener(this);
        if (enableOffline) {
            //初始化离线命令词条
            loadOfflineEngine();
        }
    }

    private void initView() {
        binding.speechBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPermission) {
                    ToastUtils.showShort("请开启所需识别权限，否则识别讲无法使用");
                    return;
                }
                if (isOpen)
                    speechStop();
                else
                    speechStart();
            }
        });
    }

    //    申请权限
    private void initPermission() {
        RxPermissions permissions = new RxPermissions(TestSpeechActivity.this);
        permissions.request(perms).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                isPermission = aBoolean;
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    /**
     * 开始语音识别
     */
    private void speechStart() {
        binding.speechBt.setText("结 束");
        isOpen=true;
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; //开始命令词条

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 2000);
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.OUT_FILE, "/storage/emulated/0/baiduASR/outfile.pcm");
        params.put(SpeechConstant.ACCEPT_AUDIO_DATA, true);
        params.put(SpeechConstant.DISABLE_PUNCTUATION, false);
        //  params.put(SpeechConstant.NLU, "enable");
        // params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 800);
        // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        //  params.put(SpeechConstant.PROP ,20000);
        String json = null; //可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        KLog.e("输入参数：" + json);
        asr.send(event, json, null, 0, 0);
    }

    //结束语音识别
    private void speechStop() {
        binding.speechBt.setText("开 始");
        isOpen=false;
        //发送结束命令
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        //需要实现EventListener的输出事件回调接口。该类需要处理SDK在识别过程中的回调事件。
        String logTxt = "";
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            if (params.contains("\"nlu_result\"")) {
                if (length > 0 && data.length > 0) {
                    logTxt = "语义解析结果：" + new String(data, offset, length);
                    binding.speechStr.setText(logTxt);
                }
            }
        }
        KLog.e(logTxt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            //卸载掉离线识别功能
            unloadOfflineEngine();
        }
    }

    private void unloadOfflineEngine() {
        //发送命令卸载离线引擎
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0);
    }

    @SuppressLint("InlinedApi")
    String[] perms = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
