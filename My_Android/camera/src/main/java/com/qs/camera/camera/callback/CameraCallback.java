/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.qs.camera.camera.callback;

/**
 * 关于相机的相关结果回调（除了打开相机和切换相机）
 */
public interface CameraCallback {
    void onResult(boolean result);
}
