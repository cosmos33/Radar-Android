package com.cosmos.radar.sdk;

import android.app.Application;

import com.cosmos.radar.core.Radar;
import com.cosmos.radar.core.RadarConfig;
import com.cosmos.radar.lag.anr.ANRKit;
import com.cosmos.radar.lag.lag.LagKit;
import com.cosmos.radar.memory.alert.MemoryAlertKit;
import com.cosmos.radar.memory.leak.MemoryLeakKit;
import com.cosmos.radar.pagespeed.PageLaunchTimeKit;

public class RadarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RadarConfig.Builder builder =
                new RadarConfig.Builder(this, "26e61d33cefc4e2cab629715b6aa260f")
                        .printDebugLog(BuildConfig.DEBUG)
                        .analyzeLeakForeground(BuildConfig.DEBUG)
                        .forceTurnOn(BuildConfig.DEBUG)
                        .userId(null)
                        .appVersionName("1.0.0_radar_sdk")
                        .appVersionCode(10000)
                        .channel("debug")
                        .kits(
                                new ANRKit(),               // ANR
                                new LagKit(),              // 卡顿
                                new PageLaunchTimeKit(),         // 页面启动时间
                                new MemoryLeakKit(),         // 内存泄露
                                new MemoryAlertKit()         // 内存峰值报警
                        );
        Radar.with(builder.build());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
