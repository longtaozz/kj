package com.qs.base.config;

/**
 * Created by goldze on 2018/6/21 0021.
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */

public class ModuleLifecycleReflexs {
    private static final String BaseInit = "com.qs.base.base.BaseModuleInit";
    //主业务模块
    private static final String MainInit = "com.qs.main.MainModuleInit";
    //登录验证模块
    private static final String SignInit = "com.qs.sign.SignModuleInit";
    //首页业务模块
    private static final String HomeInit = "com.qs.home.HomeModuleInit";
    //工作业务模块
    private static final String WorkInit = "com.qs.work.WorkModuleInit";
    //消息业务模块
    private static final String MsgInit = "com.qs.msg.MsgModuleInit";
    //用户业务模块
    private static final String UserInit = "com.qs.user.UserModuleInit";
    //设置业务模块
    private static final String SettingInit = "com.qs.setting.SettingModuleInit";

    public static String[] initModuleNames = {BaseInit, MainInit, SignInit, HomeInit, WorkInit, MsgInit, UserInit, SettingInit};
}
