package com.cosmos.radar.sdk;

import android.app.Application;

import com.cosmos.radar.core.Radar;
import com.cosmos.radar.core.RadarConfig;
import com.cosmos.radar.lag.RadarLagKit;
import com.cosmos.radar.memory.alert.RadarMemoAlertKit;
import com.cosmos.radar.memory.leak.RadarMemoLeakKit;
import com.cosmos.radar.pagespeed.RadarPageTimeKit;

public class RadarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RadarConfig.Builder builder =
                new RadarConfig.Builder(this, "26e61d33cefc4e2cab629715b6aa260f")
                        .debuggable(true)
                        .userId(null)
                        .appVersionName("1.0.0_radar_sdk")
                        .appVersionCode(10000)
                        .channel("debug")
                        .kits(
                                new RadarLagKit(),              // 卡顿
                                new RadarPageTimeKit(),         // 页面启动时间
                                new RadarMemoLeakKit(),         // 内存泄露
                                new RadarMemoAlertKit()         // 内存峰值报警
                        );
        Radar.with(builder.build());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
