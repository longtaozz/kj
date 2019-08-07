/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.qs.camera.camera.manager;

import android.content.Context;

import com.baidu.ar.ARController;


/**
 * ARController manager
 */
public class ControllerManager {

    private static ControllerManager mControllerManager;

    /**
     * 设置单例 只能有一个arController实例
     */
    private ARController mController;

    private ControllerManager(Context context) {
        mController = new ARController(context);
    }

    public static ControllerManager getInstance(Context context) {
        if (mControllerManager == null) {
            mControllerManager = new ControllerManager(context);
        }
        return mControllerManager;
    }

    public ARController getArController() {
        return mController;
    }

    public void release() {
        mController.release();
        mControllerManager = null;
    }
}
