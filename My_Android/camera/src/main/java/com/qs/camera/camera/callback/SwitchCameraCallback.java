/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.qs.camera.camera.callback;

/**
 * 切换相机的结果回调
 */
public interface SwitchCameraCallback {

    /**
     * @param result 切换是否成功
     * @param rear   true表示后置摄像头 , false 前置摄像头
     */
    void onCameraSwitch(boolean result, boolean rear);
}
