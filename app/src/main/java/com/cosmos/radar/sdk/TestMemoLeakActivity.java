package com.cosmos.radar.sdk;

import android.app.Activity;
import android.os.Bundle;

public class TestMemoLeakActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setTitle("Memo Leak");
        LeakClass.testLeak.add(this);
    }
}
