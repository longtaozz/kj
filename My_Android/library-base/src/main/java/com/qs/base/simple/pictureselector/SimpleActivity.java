package com.qs.base.simple.pictureselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qs.base.R;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_activity, btn_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_other);
        btn_activity = (Button) findViewById(R.id.btn_activity);
        btn_fragment = (Button) findViewById(R.id.btn_fragment);
        btn_activity.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        if (i == R.id.btn_activity) {
            intent = new Intent(SimpleActivity.this, PhotoMainActivity.class);
            startActivity(intent);

        } else if (i == R.id.btn_fragment) {
            intent = new Intent(SimpleActivity.this, PhotoFragmentActivity.class);
            startActivity(intent);

        }
    }
}
