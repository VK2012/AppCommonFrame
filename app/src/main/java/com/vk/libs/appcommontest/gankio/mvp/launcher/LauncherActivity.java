package com.vk.libs.appcommontest.gankio.mvp.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vk.libs.appcommontest.gankio.mvp.main.MainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class LauncherActivity extends AppCompatActivity {

    public static final int TIMEOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.timer(TIMEOUT, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                jumpMain();
            }
        });

    }

    private void jumpMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
