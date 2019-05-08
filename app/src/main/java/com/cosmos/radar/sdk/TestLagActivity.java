package com.cosmos.radar.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chenwangwang on 2019-05-06.
 */
public class TestLagActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lag);
        findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
