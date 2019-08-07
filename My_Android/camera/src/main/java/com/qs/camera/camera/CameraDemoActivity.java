//package com.qs.camera.camera;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.res.Configuration;
//import android.graphics.SurfaceTexture;
//import android.opengl.GLSurfaceView;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//
//import com.baidu.ar.ARController;
//import com.baidu.ar.DuMixSource;
//import com.baidu.ar.DuMixTarget;
//import com.baidu.ar.util.SystemInfoUtil;
//import com.qs.base.base.BaseMyActivity;
//import com.qs.huanxin.R;
//import com.qs.huanxin.databinding.HuanxinCameraDemoBinding;
//import com.qs.huanxin.ui.camera.callback.CameraCallback;
//import com.qs.huanxin.ui.camera.callback.PreviewCallback;
//import com.qs.huanxin.ui.camera.callback.RenderCallback;
//import com.qs.huanxin.ui.camera.callback.StartCameraCallback;
//import com.qs.huanxin.ui.camera.manager.CameraManager;
//import com.qs.huanxin.ui.camera.manager.ControllerManager;
//import com.qs.huanxin.ui.camera.util.GLConfigChooser;
//import com.qs.huanxin.ui.camera.util.MyDuMixCallback;
//import com.qs.huanxin.ui.camera.util.MyRenderer;
//import com.tbruyelle.rxpermissions2.RxPermissions;
//
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//import me.goldze.mvvmhabit.base.BaseViewModel;
//import me.goldze.mvvmhabit.utils.KLog;
//
///**
// * 摄像头优化demo
// *
// * @Author ltzz
// * @Date 2019/8/5
// */
//public class CameraDemoActivity extends BaseMyActivity<HuanxinCameraDemoBinding, BaseViewModel> {
//
//    //camera管理器
//    private CameraManager cameraManager;
//    private GLSurfaceView glSurfaceView;
//    private MyRenderer myRenderer;
//    //是否授权
//    private boolean isPermission = false;
//
//    /**
//     * ar sdk 接口ARController
//     */
//    private ARController mARController;
//    /**
//     * AR输入参数类
//     */
//    private DuMixSource mDuMixSource;
//    /**
//     * 返回参数类
//     */
//    private DuMixTarget mDuMixTarget;
//
//    @Override
//    protected boolean statusBarDarkFont() {
//        return false;
//    }
//
//    @Override
//    protected View titleLayout() {
//        return null;
//    }
//
//    @Override
//    public int initContentView(Bundle savedInstanceState) {
//        return R.layout.huanxin_camera_demo;
//    }
//
//    @Override
//    public int initVariableId() {
//        return 0;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        cameraManager = new CameraManager();
//        setupViews();
//        // 6.0以下版本直接同意使用权限
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            setupViews();
//        } else {
//            //权限请求
//            getPermission(0);
//        }
//    }
//
//    //权限请求
//    private void getPermission(int type) {
//        RxPermissions permissions = new RxPermissions(this);
//        permissions.request(perms).subscribe(new Observer<Boolean>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//
//            @Override
//            public void onNext(Boolean aBoolean) {
//                isPermission = aBoolean;
//                if (aBoolean) {
//                    startCamera();
//                } else {
//                    getPermission(0);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });
//    }
//
//    private void setupViews() {
//
//
//        glSurfaceView = binding.glSfview;
//        glSurfaceView.setEGLContextClientVersion(2);
//        myRenderer = new MyRenderer(isScreenOrientationLandscape(this));
//        myRenderer.setFrameListener(
//                new SurfaceTexture.OnFrameAvailableListener() {
//                    @Override
//                    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
//                        glSurfaceView.requestRender();
//                    }
//                }
//        );
//        glSurfaceView.setEGLConfigChooser(new GLConfigChooser());
//        glSurfaceView.setRenderer(myRenderer);
//        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//
//    }
//
//    /**
//     * 判断屏幕是否是横屏状态
//     *
//     * @param context
//     */
//    public boolean isScreenOrientationLandscape(Context context) {
//        // 获取设置的配置信息
//        Configuration cf = context.getResources().getConfiguration();
//        // 获取屏幕方向
//        int ori = cf.orientation;
//        if (ori == cf.ORIENTATION_LANDSCAPE) {
//            return true;
//        }
//        return false;
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mARController != null) {
//            mARController.resume();
//            mARController.onAppear();
//        }
//        // 打开相机
//        getPermission(1);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mARController != null) {
//            mARController.pause();
//        }
//        cameraManager.stopCamera(null, true);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        ControllerManager.getInstance(this).release();
//
//        if (mARController != null) {
//            mARController.release();
//            mARController = null;
//        }
//    }
//
//
//    /**
//     * 开始打开相机
//     */
//    private void startCamera() {
//        SurfaceTexture surfaceTexture = myRenderer.getCameraTexture();
//        cameraManager.startCamera(surfaceTexture, new StartCameraCallback() {
//            @Override
//            public void onCameraStart(boolean result, SurfaceTexture surfaceTexture, int width, int height) {
//                if (result) {
//                    if (mARController == null) {
//                        showView();
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 开始渲染View
//     */
//    public void showView() {
//
//        mARController = ControllerManager.getInstance(this).getArController();
//        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (mARController != null) {
//                    return mARController.onTouchEvent(motionEvent);
//                }
//                return false;
//            }
//        });
//
//        cameraManager.setPreviewCallback(new PreviewCallback() {
//            @Override
//            public void onPreviewFrame(byte[] data, int width, int height) {
//                if (mARController != null) {
//                    mARController.onCameraPreviewFrame(data, width, height);
//                }
//                KLog.e(data);
//            }
//        });
//        myRenderer.setRenderCallback(new RenderCallback() {
//            @Override
//            public void onCameraDrawerCreated(SurfaceTexture surfaceTexture, int width, int height) {
//                mDuMixSource = new DuMixSource(surfaceTexture, width, height);
//                mDuMixSource.setArKey("0");
//                mDuMixSource.setArType(5);
//            }
//
//            @Override
//            public void onDrawerCreated(SurfaceTexture surfaceTexture, SurfaceTexture.OnFrameAvailableListener
//                    arFrameListener, int width, int height) {
//                if (isScreenOrientationLandscape(CameraDemoActivity.this)) {
//                    mDuMixTarget = new DuMixTarget(surfaceTexture, arFrameListener, height, width, true);
//                } else {
//                    mDuMixTarget = new DuMixTarget(surfaceTexture, arFrameListener, width, height, true);
//                }
//
//                if (mDuMixSource != null) {
//                    mDuMixSource.setCameraSource(null);
//                }
//
//                if (mARController != null) {
//                    mARController.setup(mDuMixSource, mDuMixTarget, new MyDuMixCallback());
//                    // todo update 需要封装此函数
//                    mARController.resume();
//                }
//            }
//
//            @Override
//            public void onDrawerChanged(SurfaceTexture surfaceTexture, int width, int height) {
//                if (mARController != null) {
//                    if (SystemInfoUtil.isScreenOrientationLandscape(CameraDemoActivity.this)) {
//                        mARController.reSetup(surfaceTexture, height, width);
//                    } else {
//                        mARController.reSetup(surfaceTexture, width, height);
//                    }
//                }
//            }
//        });
//    }
//
//    String[] perms = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.RECORD_AUDIO
//    };
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        quit();
//    }
//
//    /**
//     * 退出逻辑
//     * 1. 先关闭相机
//     * 2. 通知外部回调
//     * 3. 释放资源
//     */
//    private void quit() {
//        if (cameraManager != null) {
//            cameraManager.stopCamera(new CameraCallback() {
//                @Override
//                public void onResult(final boolean result) {
//                    try {
//                        if (result) {
//                            finish();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, false);
//        }
//    }
//
//}
