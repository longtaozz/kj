package com.qs.base.interfaces;

import android.app.Dialog;

/**
* 弹窗监听
* @author admin
* @time 2019/4/17 15:36
**/
public interface DialogListener {
    void complete(Dialog dialog, boolean confirm);
}
