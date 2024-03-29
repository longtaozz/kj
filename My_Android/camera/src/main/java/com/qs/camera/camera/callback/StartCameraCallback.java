/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.qs.camera.camera.callback;

import android.graphics.SurfaceTexture;

/**
 * 打开相机的回调
 */
public interface StartCameraCallback {
    void onCameraStart(boolean result, SurfaceTexture surfaceTexture, int width, int height);
}
