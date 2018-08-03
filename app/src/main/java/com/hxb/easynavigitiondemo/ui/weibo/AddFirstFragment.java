package com.hxb.easynavigitiondemo.ui.weibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hxb.easynavigitiondemo.R;
import com.hxb.easynavigitiondemo.ui.WeiboActivity;
import com.hxb.easynavigitiondemo.ui.NormalActivity;

/**
 * Created by Administrator on 2018/6/2.
 */

public class AddFirstFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);

        Button bt01 = view.findViewById(R.id.bt01);
        bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof WeiboActivity) {
                    ((WeiboActivity) getActivity()).getNavigitionBar().setMsgPointCount(2, 109);
                    ((WeiboActivity) getActivity()).getNavigitionBar().setMsgPointCount(0, 5);
                    ((WeiboActivity) getActivity()).getNavigitionBar().setHintPoint(3, true);
                } else if (getActivity() instanceof NormalActivity) {
                    ((NormalActivity) getActivity()).getNavigitionBar().setMsgPointCount(2, 109);
                    ((NormalActivity) getActivity()).getNavigitionBar().setMsgPointCount(0, 5);
                    ((NormalActivity) getActivity()).getNavigitionBar().setHintPoint(3, true);
                }
            }
        });


        Button bt02 = view.findViewById(R.id.bt02);
        bt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof WeiboActivity) {
                    ((WeiboActivity) getActivity()).getNavigitionBar().clearAllMsgPoint();
                    ((WeiboActivity) getActivity()).getNavigitionBar().clearAllHintPoint();
                } else if (getActivity() instanceof NormalActivity) {
                    ((NormalActivity) getActivity()).getNavigitionBar().clearAllMsgPoint();
                    ((NormalActivity) getActivity()).getNavigitionBar().clearAllHintPoint();
                }

            }
        });

        Button bt03 = view.findViewById(R.id.bt03);
        bt03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof WeiboActivity) {
                    ((WeiboActivity) getActivity()).getNavigitionBar().selectTab(1);
                    ((AddSecondFragment) (((WeiboActivity) getActivity()).getNavigitionBar().getAdapter().getItem(1))).showToast("嘻嘻哈哈嗝");
                }
            }
        });


        return view;
    }


}
