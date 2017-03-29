package com.vk.libs.appcommontest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TestActivity extends AppCompatActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        List<ActivityInfo> targets = new ArrayList<>();

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activities = packageInfo.activities;

            for(ActivityInfo tmp:activities){
                if(tmp.name.contains(".test."))
                    targets.add(tmp);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        MyAdapter myAdapter = new MyAdapter(targets);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,ActivityInfo activityInfo) {
                try {
                    Intent intent = new Intent(TestActivity.this,Class.forName(activityInfo.name));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }



}
