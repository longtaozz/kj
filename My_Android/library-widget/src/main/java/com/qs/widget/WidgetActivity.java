package com.qs.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.qs.widget.widget.QSTitleNavigationView;

public class WidgetActivity extends Activity implements QSTitleNavigationView.OnTitleClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.widget_activity_widget);

        QSTitleNavigationView titleNavigationView = findViewById(R.id.titleNaviView);
        titleNavigationView.getInstance()
                .setTitleLeftText("测试")
                .setBackBackgroud(R.color.colorAccent)
                .setAutoFinish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTitleClick(int type) {
        Toast.makeText(this, type + "", Toast.LENGTH_LONG).show();
        if (type == QSTitleNavigationView.TYPE_BACK) {
        } else {
            finish();
        }
    }
}
