package com.qs.huanxin.VM;

import android.app.Application;
import android.support.annotation.NonNull;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 会话列表
 *
 * @Author ltzz
 * @Date 2019/8/2
 */
public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    //添加好友
    public BindingCommand addFriendClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            EMMessage message = EMMessage.createTxtSendMessage("第一次聊天聊天", "123456");
//发送消息
            EMClient.getInstance().chatManager().sendMessage(message);
        }
    });
}
