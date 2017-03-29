package com.vk.libs.appcommontest.test;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk.libs.appcommon.network.NetworkUtil;

import static com.vk.libs.appcommon.network.NetworkUtil.NETWORK_CLASS_WIFI;

/**
 * Created by VK on 2016/12/12.
 */

public class NetworkActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button btn = new Button(this);
        btn.setText("检查网络");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder str = new StringBuilder();
                Pair<Integer, String> connectType = NetworkUtil.getConnectType(NetworkActivity.this);
                str.append(connectType.first);
                str.append(" , ");
                str.append(connectType.second);
                str.append('\n');
                str.append(NetworkUtil.isConnected(NetworkActivity.this));

                if(connectType.first == NETWORK_CLASS_WIFI ){
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if( wifiInfo != null) {
                        String ssid = wifiInfo.getSSID();



                        str.append('\n');
                        str.append("ssid:");
                        str.append(ssid);

                    }
                }

                textView.setText(str);
            }
        });

        textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        linearLayout.addView(btn, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(linearLayout);
    }
}
