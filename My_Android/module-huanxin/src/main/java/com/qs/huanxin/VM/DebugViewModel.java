package com.qs.huanxin.VM;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.qs.base.router.RouterActivityPath;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * bug模式activityVM
 *
 * @Author ltzz
 * @Date 2019/8/1
 */
public class DebugViewModel extends BaseViewModel {
    public DebugViewModel(@NonNull Application application) {
        super(application);
    }

    //用户名
    public ObservableField<String> userName = new ObservableField<>("123456");
    //密码
    public ObservableField<String> password = new ObservableField<>("123456");

    //登录点击事件
    public BindingCommand loginClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //注册,一般放在服务端注册，demo中就直接在客户端注册了
                    try {
                        //这好像是个同步请求
                        EMClient.getInstance().createAccount(userName.get(), password.get());
                        //注册成功
                        hxLogin();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        //此处我们根据错误类型可以判断是什么原因引起的注册失败,我们只列出常见的原因1.网络连接失败2.用户名已注册
                        //http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html这是官方的文档,如果还需其它错误码，这里有各种错误代码,供我们参考
                        switch (e.getErrorCode()) {
                            case EMError.SERVER_BUSY:
                            case EMError.SERVER_UNKNOWN_ERROR:
                            case EMError.SERVER_NOT_REACHABLE:
                            case EMError.SERVER_TIMEOUT:
                            case EMError.NETWORK_ERROR://网络错误
                                ToastUtils.showShort("网络有问题,请稍后再试");
                                break;
                            case EMError.USER_ALREADY_EXIST:
                                //用户名已存在
                                hxLogin();
                                break;
                            case EMError.USER_REG_FAILED://注册失败
                                ToastUtils.showShort("注册失败");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }).start();

        }
    });

    //退出登录
    public BindingCommand outClick=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            EMClient.getInstance().logout(true, new EMCallBack() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    KLog.e("退出登录成功");
                }

                @Override
                public void onProgress(int progress, String status) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onError(int code, String message) {
                    // TODO Auto-generated method stub
                    KLog.e("退出登录失败");
                    KLog.e(message);
                }
            });
        }
    });

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.showShort("登录成功");
                    Log.d("main", "登录聊天服务器成功！");
                    ARouter.getInstance().build(RouterActivityPath.Base.LIST).navigation();
                    break;
            }
        }
    };

    //环信登录
    public void hxLogin() {
        //登录成功进入到回话列表页面
        EMClient.getInstance().login(userName.get(), password.get(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //耗时操作，在线程中完成
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                }).start();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }
}
