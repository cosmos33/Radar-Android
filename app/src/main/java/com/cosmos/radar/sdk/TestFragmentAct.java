package com.cosmos.radar.sdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.TextView;

import com.cosmos.radar.sdk.R;

import java.io.File;


public class TestFragmentAct extends Activity {

    private static final int READ_PHONE_STATE = 1000;

    public static int checkSelfPermission(Context context, String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        } else {
            return context.checkPermission(permission, Process.myPid(), Process.myUid());
        }
    }

    public static void requestPermissions(final Activity activity, final String[] permissions, final int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(permissions, requestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_PHONE_STATE);
            }
        }

        setContentView(R.layout.activity_main);
        findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestFragmentAct.this, TestPageSpeedActivity.class));
            }
        });
        findViewById(R.id.click_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.click_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestFragmentAct.this, TestMemoLeakActivity.class));
            }
        });
        findViewById(R.id.click_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestFragmentAct.this, TestMemoLeakActivity2.class));
            }
        });

        findViewById(R.id.click_btn_memory_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestFragmentAct.this, TestMemoryAlertActivity.class));
            }
        });

        ((TextView) findViewById(R.id.textView)).setText("存储位置：" + new File(getExternalCacheDir(), "radar_cache_files").getAbsolutePath());

        findViewById(R.id.click_btn_test_lag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestFragmentAct.this, TestLagActivity.class));
            }
        });
    }

    private Object getInstance() {
        return this;
    }

    private int count = 0;
    private static final int MAX_COUNT = 1;

}
