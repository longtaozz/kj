
package com.qs.camera.camera.callback;

import android.graphics.SurfaceTexture;

/**
 * Created by huxiaowen on 2017/12/7.
 */

public interface RenderCallback {
    void onCameraDrawerCreated(SurfaceTexture surfaceTexture, int width, int height);

    void onDrawerCreated(SurfaceTexture surfaceTexture, SurfaceTexture.OnFrameAvailableListener
            arFrameListener, int width, int height);

    void onDrawerChanged(SurfaceTexture surfaceTexture, int width, int height);
}