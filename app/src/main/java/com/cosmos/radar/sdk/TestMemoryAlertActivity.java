package com.cosmos.radar.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cosmos.radar.memory.alert.MemoryInfo;
import com.cosmos.radar.memory.alert.RadarMemoAlertKit;

import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by chenwangwang on 2019-04-28.
 */
public class TestMemoryAlertActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Queue<Object> objectList = new ConcurrentLinkedQueue<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_memory_alert);

        TextView allTip = findViewById(R.id.top_tip_all);
        final TextView currentTip = findViewById(R.id.top_tip_current);

        TextView mallocJava = findViewById(R.id.malloc_java);
        TextView mallocNative = findViewById(R.id.malloc_native);
        TextView freeJava = findViewById(R.id.free_java);
        final TextView freeNative = findViewById(R.id.free_native);

        MemoryInfo memoryInfo = RadarMemoAlertKit.getMemoryInfo();
        allTip.setText("maxJava: " + memoryInfo.getMaxJavaMemory() / 1048576 + "mb,  maxNative: " + memoryInfo.getMaxNativeMemory() / 1048576 + "mb");
        updateCurrentMemoryInfo(memoryInfo, currentTip);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final MemoryInfo memoryInfoLocal = RadarMemoAlertKit.getMemoryInfo();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateCurrentMemoryInfo(memoryInfoLocal, currentTip);
                        }
                    });
                }
            }
        }).start();

        mallocJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1024 * 900; i++) {
                            objectList.add(new Object());
                        }
                    }
                }).start();
            }
        });

        freeJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1024 * 900; i++) {
                            objectList.poll();
                        }
                        Runtime.getRuntime().gc();
                    }
                }).start();
            }
        });

        mallocNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mallocNativeMemory();
                    }
                }).start();
            }
        });

        freeNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        freeNativeMemory();
                    }
                }).start();
            }
        });

    }

    private void updateCurrentMemoryInfo(MemoryInfo memoryInfo, TextView currentTip) {
        currentTip.setText("curJava: " + memoryInfo.getJavaMemory() / 1048576 + "mb, curNative: " + memoryInfo.getNativeMemory() / 1048576 + "mb");
    }

    private native void mallocNativeMemory();
    private native void freeNativeMemory();
}
