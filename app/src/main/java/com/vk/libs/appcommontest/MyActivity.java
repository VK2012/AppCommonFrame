package com.vk.libs.appcommontest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.vk.libs.appcommon.adapter.CommonAdapter;
import com.vk.libs.appcommon.adapter.ViewHolder;
import com.vk.libs.appcommon.ui.EventBusActivity;

import java.util.List;

/**
 * Created by vk on 2017/3/31.
 */

public class MyActivity extends EventBusActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        new MyAda(this,null,123);
    }

    private class MyAda extends CommonAdapter<String>{

        private MyAda(@NonNull Context context, List<String> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convert(ViewHolder viewHolder, String item) {

        }
    }
}