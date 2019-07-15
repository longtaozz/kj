package com.qs.base.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qs.base.R;


public class CommomDialog extends Dialog implements View.OnClickListener {
    private TextView titleTxt;
    private TextView messageTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private int viewId = 0;

    private Context mContext;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private String message;


    public CommomDialog(Context context, OnCloseListener listener) {
        super(context, R.style.base_dialog);
        this.mContext = context;
        this.listener = listener;
    }

    public CommomDialog(Context context, int viewId, OnCloseListener listener) {
        super(context, R.style.base_dialog);
        this.viewId = viewId;
        this.mContext = context;
        this.listener = listener;
    }


    public CommomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommomDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommomDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewId != 0)
            setContentView(viewId);
        else
            setContentView(R.layout.base_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        submitTxt = findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        if (!TextUtils.isEmpty(title)) {
            titleTxt = findViewById(R.id.title);
            titleTxt.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            messageTxt = findViewById(R.id.message_txt);
            messageTxt.setText(message);
        }
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel) {
            if (listener != null) {
                listener.onClick(this, false);
            }
            this.dismiss();

        } else if (i == R.id.submit) {
            if (listener != null) {
                listener.onClick(this, true);
            }

        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}