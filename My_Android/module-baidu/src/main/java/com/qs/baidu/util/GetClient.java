package com.qs.baidu.util;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.speech.AipSpeech;

/**
 * 初始化baidu sdk client获取access_token
 *
 * @Author ltzz
 * @Date 2019/7/13
 */
public class GetClient {
    //设置APPID/AK/SK
    public static final String APP_ID = "16795635";
    public static final String API_KEY = "mleopX0g8IgEdDGc8R8aGQOh";
    public static final String SECRET_KEY = "m41Te4L5WfCgVOFPpFBGdVIYL59ubgjf";

    //图片
    private static AipImageClassify client;
    //语音
    private static AipSpeech speechClient;

    /**
    * 图片识别初始化
    * @Author ltzz
    * @Date 2019/7/13
    */
    public static AipImageClassify getImgInstance() {
        synchronized (GetClient.class) {
            if (client == null) {
                // 初始化一个AipImageClassify
                client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

                // 可选：设置网络连接参数
                client.setConnectionTimeoutInMillis(2000);
                client.setSocketTimeoutInMillis(60000);
            }
        }
        return client;
    }

    /**
     * 语音识别初始化
     * @return
     */
    public static AipSpeech getSpeechInstance(){
        synchronized (GetClient.class) {
            if (client == null) {
                // 初始化一个AipImageClassify
                speechClient = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

                // 可选：设置网络连接参数
                speechClient.setConnectionTimeoutInMillis(2000);
                speechClient.setSocketTimeoutInMillis(60000);
            }
        }
        return speechClient;
    }
}
