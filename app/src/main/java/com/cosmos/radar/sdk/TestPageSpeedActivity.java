package com.cosmos.radar.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by chenwangwang on 2019-05-06.
 */
public class TestPageSpeedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TextView textView = new TextView(this);
        setContentView(textView);
        textView.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
        textView.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
        ((FrameLayout.LayoutParams) textView.getLayoutParams()).gravity = Gravity.CENTER;
        textView.setText("看日志，已经记录慢交互日志");
    }
}
