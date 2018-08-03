package com.hxb.easynavigitiondemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hxb.easynavigition.view.EasyNavigitionBar;
import com.hxb.easynavigitiondemo.R;
import com.hxb.easynavigitiondemo.ui.normal.NormalFirstFragment;
import com.hxb.easynavigitiondemo.ui.normal.NormalSecondFragment;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EasyNavigitionBar navigitionBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<android.support.v4.app.Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        navigitionBar = (EasyNavigitionBar)findViewById(R.id.navigitionBar);

        fragments.add(new NormalFirstFragment());
        fragments.add(new NormalSecondFragment());
        fragments.add(new NormalFirstFragment());
        fragments.add(new NormalSecondFragment());

        navigitionBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .mode(EasyNavigitionBar.MODE_ADD)
                .addIcon(R.mipmap.add_image)
                .fragmentManager(getSupportFragmentManager())
                .build();

    }

    public EasyNavigitionBar getNavigitionBar() {
        return navigitionBar;
    }

}
