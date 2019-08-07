package com.qs.base.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterActivityPath {
    /**
     * 主业务组件
     */
    public static class Main {
        private static final String MAIN = "/main";
        /*主业务界面*/
        public static final String PAGER_MAIN = MAIN + "/Main";
    }

    /**
     * 身份验证组件
     */
    public static class Sign {
        private static final String SIGN = "/sign";
        /*登录界面*/
        public static final String PAGER_LOGIN = SIGN + "/Login";
        /*注册页面*/
        public static final String PAGER_REGISTER = SIGN + "/Register";

    }

    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*用户详情*/
        public static final String PAGER_USERDETAIL = USER + "/UserDetail";
    }

    /**
     * 设置组件
     */
    public static class Setting {
        private static final String SETTING = "/setting";
        /*Demo Simple*/
        public static final String PAGER_DEMO = SETTING + "/Demo";
        /*设置*/
        public static final String PAGER_SETTING = SETTING + "/Setting";
        /*换肤*/
        public static final String PAGER_SKIN = SETTING + "/Skin";
    }

    /**
     * 基础组件
     */
    public static class Base {
        private static final String BASE = "/base";
        /*图片*/
        public static final String PAGER_PHOTO_MAIN = BASE + "/PhotoMain";
        /*滑动导航条*/
        public static final String PAGER_SLIDE_TAB = BASE + "/SlideTab";
        //环信聊天界面demo
        public static final String CHAT = BASE + "/ChatActivity";
        //会话列表
        public static final String LIST = BASE + "/MainActivity";
    }
}
