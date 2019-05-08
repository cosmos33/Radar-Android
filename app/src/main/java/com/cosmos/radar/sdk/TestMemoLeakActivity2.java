package com.cosmos.radar.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class TestMemoLeakActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setTitle("Memo Leak");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TEST", "开始泄漏咯");
            }
        }, 60 * 1000);
    }
}
