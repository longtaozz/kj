package com.qs.camera.camera.util;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;

import com.qs.camera.camera.callback.RenderCallback;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huxiaowen on 2017/12/7.
 */

public class MyRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = MyRenderer.class.getSimpleName();
    private MyDrawer mCameraDrawer;
    private SurfaceTexture mCameraTexture;
    private int mCameraTextureID;
    private int mCameraWidth;
    private int mCameraHeight;
    private SurfaceTexture.OnFrameAvailableListener mCameraFrameListener;

    private MyDrawer mDrawer;
    private SurfaceTexture mTexture;
    private int mTextureID;
    private int mWidth;
    private int mHeight;
    private RenderCallback mRenderCallback;
    private SurfaceTexture.OnFrameAvailableListener mFrameListener;

    private volatile boolean mDraw = true;
    // 是否是横屏模式
    private boolean mScreenLandscape;

    public MyRenderer(boolean landscape) {

        mScreenLandscape = landscape;
        if (mCameraTexture == null) {
            int mCameraTextureID = createTexture(GLES10.GL_TEXTURE_2D);
            mCameraTexture = new SurfaceTexture(mCameraTextureID);
        }
        if (mTexture == null) {
            int mTextureID = createTexture(GLES10.GL_TEXTURE_2D);
            mTexture = new SurfaceTexture(mTextureID);
        }
    }

    public SurfaceTexture getCameraTexture() {
        return mCameraTexture;
    }

    public void setDraw(boolean draw) {
        mDraw = draw;
        mDrawerChanged = true;
    }

    private boolean mDrawerChanged = false;

    //    public void setCameraRenderCallback(CameraRenderCallback callback) {
    //        mCameraRenderCallback = callback;
    //    }

    public void setCameraFrameListener(SurfaceTexture.OnFrameAvailableListener listener) {
        mCameraFrameListener = listener;
        setCameraFrameListener();
    }

    private void setCameraFrameListener() {
        if (mCameraTexture != null && mCameraFrameListener != null) {
            mCameraTexture.setOnFrameAvailableListener(mCameraFrameListener);
        }
    }

    public void setRenderCallback(RenderCallback callback) {
        mRenderCallback = callback;
        if (mRenderCallback != null) {
            mRenderCallback.onCameraDrawerCreated(mCameraTexture, 1280, 720);
        }
        if (mRenderCallback != null) {
            mRenderCallback.onDrawerCreated(mTexture, mFrameListener, mWidth, mHeight);
        }
    }

    public void setFrameListener(SurfaceTexture.OnFrameAvailableListener listener) {
        // 这里仅把listener设置进来，在pro内设置给对应的Target SurfaceTexture
        mFrameListener = listener;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCameraTextureID = createTexture(GLES10.GL_TEXTURE_2D);
        mTextureID = createTexture(GLES10.GL_TEXTURE_2D);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        mWidth = width;
        mHeight = height;

        if (mCameraDrawer == null) {
            mCameraDrawer = new MyDrawer(mCameraTextureID, GLES10.GL_TEXTURE_2D, mScreenLandscape);
        }

        if (mDrawer == null) {
            mDrawer = new MyDrawer(mTextureID, GLES10.GL_TEXTURE_2D, mScreenLandscape);
            // if (mRenderCallback != null) {
            // mRenderCallback.onDrawerCreated(mTexture, width, height);
            // }
        }

        if (mRenderCallback != null) {
            mRenderCallback.onDrawerChanged(mTexture, mWidth, mHeight);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        updateDrawer();
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        try {
            if (mDraw) {
                if (mTexture != null) {
                    Log.d(TAG, "mTexture.updateTexImage(); ");
                    mTexture.updateTexImage();
                    float[] mtx = new float[16];
                    mTexture.getTransformMatrix(mtx);
                    mDrawer.draw(mtx);
                }
            } else {
                if (mCameraTexture != null) {
                    mCameraTexture.updateTexImage();
                    float[] mtx = new float[16];
                    mCameraTexture.getTransformMatrix(mtx);
                    mCameraDrawer.draw(mtx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateDrawer() {
        if (mDrawerChanged) {
            try {
                mTexture.detachFromGLContext();
            } catch (Exception e) {
                Log.e(TAG, "onSurfaceChanged attachToGLContext error!!!");
                e.printStackTrace();
            }
            try {
                mCameraTexture.detachFromGLContext();
            } catch (Exception e) {
                Log.e(TAG, "onSurfaceChanged attachToGLContext error!!!");
                e.printStackTrace();
            }
            try {
                if (mDraw) {
                    mTexture.attachToGLContext(mTextureID);
                } else {
                    mCameraTexture.attachToGLContext(mCameraTextureID);
                }
            } catch (Exception e) {
                Log.e(TAG, "onSurfaceChanged attachToGLContext error!!!");
                e.printStackTrace();
            }
            mDrawerChanged = false;
        }
    }

    public void release() {
        if (mDrawer != null) {
            mDrawer = null;
        }
        if (mTexture != null) {
            mTexture.release();
            mTexture = null;
        }
        mRenderCallback = null;
    }

    /**
     * 生成TextureID
     *
     * @param textureTarget
     *
     * @return
     */
    private int createTexture(int textureTarget) {
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(textureTarget, texture[0]);
        GLES20.glTexParameterf(textureTarget, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(textureTarget, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(textureTarget, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(textureTarget, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        return texture[0];
    }
}
