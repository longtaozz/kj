package com.qs.widget.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qs.widget.R;
import com.qs.widget.WidgetBaseUtils;

public class QSTitleNavigationView extends LinearLayout {

    private ImageView ivBack;
    private TextView tvTitleLeft;
    private TextView tvTitleCenter;
    private TextView tvTitleRight;
    private RelativeLayout rlTitle;

    private Activity mActivity;

    public static final int TYPE_BACK = 0x01;
    public static final int TYPE_RIGHT = 0x02;

    public QSTitleNavigationView(Context context) {
        super(context);
        init(context);
    }

    public QSTitleNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("InflateParams")
    public QSTitleNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {

        LayoutInflater.from(context).inflate(R.layout.view_title_navigation, this);
        ivBack = findViewById(R.id.iv_back);
        tvTitleLeft = findViewById(R.id.tv_title_left);
        tvTitleCenter = findViewById(R.id.tv_title_center);
        tvTitleRight = findViewById(R.id.tv_title_right);
        rlTitle = findViewById(R.id.rl_title);

        tvTitleRight.setVisibility(INVISIBLE);

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null) {
                    mActivity.finish();
                    return;
                }

                if (mOnTitleClickListener != null) {
                    mOnTitleClickListener.onTitleClick(TYPE_BACK);
                }
            }
        });
        tvTitleRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTitleClickListener != null) {
                    mOnTitleClickListener.onTitleClick(TYPE_RIGHT);
                }
            }
        });
    }

    public QSTitleNavigationView getInstance() {
        return this;
    }

    public QSTitleNavigationView setAutoFinish(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    public QSTitleNavigationView setTextColor(int colorId) {
        tvTitleLeft.setTextColor(colorId);
        tvTitleCenter.setTextColor(colorId);
        tvTitleRight.setTextColor(colorId);
        return this;
    }

    public QSTitleNavigationView setTitleLeftTextColor(int colorId) {
        tvTitleLeft.setTextColor(colorId);
        return this;
    }

    public QSTitleNavigationView setTitleCenterTextColor(int colorId) {
        tvTitleCenter.setTextColor(colorId);
        return this;
    }

    public QSTitleNavigationView setTitleRightTextColor(int colorId) {
        tvTitleRight.setTextColor(colorId);
        return this;
    }

    public QSTitleNavigationView setBackImageView(int resId) {
        ivBack.setBackgroundResource(resId);
        return this;
    }

    public QSTitleNavigationView setBackVisibility(int visibility) {
        ivBack.setVisibility(visibility);
        return this;
    }

    public QSTitleNavigationView setBackBackgroud(int resId) {
        rlTitle.setBackgroundResource(resId);
        return this;
    }

    public QSTitleNavigationView setTitleLeftText(String text) {
        tvTitleLeft.setText(text);
        return this;
    }

    public QSTitleNavigationView setTitleCenterText(String text) {
        tvTitleCenter.setText(text);
        return this;
    }

    public QSTitleNavigationView setTitleRightText(String text) {
        tvTitleRight.setText(text);
        tvTitleRight.setVisibility(VISIBLE);
        return this;
    }

    public QSTitleNavigationView setTitleNaviHeight(int heightDP) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, WidgetBaseUtils.dp2px(heightDP, getContext()));
        rlTitle.setLayoutParams(layoutParams);
        return this;
    }

    private OnTitleClickListener mOnTitleClickListener;

    public void setOnTitleClickListener(OnTitleClickListener listener) {
        this.mOnTitleClickListener = listener;
    }

    public static interface OnTitleClickListener {
        void onTitleClick(int type);
    }
}
