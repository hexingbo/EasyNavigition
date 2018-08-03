package com.hxb.easynavigitiondemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hxb.easynavigitiondemo.R;

/**
 * Created by Administrator on 2018/7/9.
 */

public class DemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_list);
    }

    public void NoAddNavigitionBar(View view) {
        startActivity(new Intent(DemoListActivity.this, NormalActivity.class));
    }

    public void AddNavigitionBar(View view) {
        startActivity(new Intent(DemoListActivity.this, AddActivity.class));
    }

    public void OnWeibo(View view) {
        startActivity(new Intent(DemoListActivity.this, WeiboActivity.class));
    }
}
