package com.wangkeke.reactnativedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
    }

    private void initView() {
        tvShow = (TextView) findViewById(R.id.textView);

        String data = getIntent().getStringExtra("params");

        tvShow.setText("rn传递的值是："+data);
    }
}
