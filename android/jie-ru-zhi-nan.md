# Android SDK 使用指南

---

## 通过Gradle集成

### 添加仓库地址
Radar支持[JCenter仓库](http://jcenter.bintray.com/com/cosmos/rifle/)

目前，Radar拆分成四个库模块，应用根据自身需要接入对应模块。

- com.cosmos.radar:core             核心库，必须依赖
- com.cosmos.radar:lag              卡顿监测
- com.cosmos.radar:memory           内存泄露以及内存峰值报警
- com.cosmos.radar:pagespeed        页面启动慢交互

其中`core`为基础模块，必须引入，其他模块根据业务需要进行引入。

### 集成依赖
def radarVersion = "1.1.1"
```
dependencies {
    implementation "com.cosmos.radar:core:$radarVersion"
    implementation "com.cosmos.radar:lag:$radarVersion"
    implementation "com.cosmos.radar:memory:$radarVersion"
    implementation "com.cosmos.radar:pagespeed:$radarVersion"
}
```

### 混淆设置
为避免混淆Rifle，在Proguard混淆文件中增加以下配置：

```
# 内存泄露检测库
-keep class squareup.haha.** {*;}
-keep class com.squareup.haha.** {*;}
-dontwarn com.squareup.haha.**

# radar相关
-dontwarn com.cosmos.radar.**
-keep class com.cosmos.radar.**{*;}
```

## 集成Gradle插件
如果应用有经过`proguard`混淆，为了方便查看后台收集到的堆栈信息，需要配置插件来提交`mapping`文件以及带带符号表的SO文件。插件将会在打Release包的时候（assembleRelease任务执行时）提交mapping文件以。

#### 添加插件依赖
在Project的build.gradle下面添加编译期依赖：

```
buildscript {
    dependencies {
        classpath 'com.cosmos.rifle:plugin:1.3.8'
    }
}
```

#### 应用插件
在Module的build.gradle文件中应用插件：

```
apply plugin: 'rifle.plugin'
rifleConfig {
    appId = "4af037b83e77345j3443io5d8d67395"          // 管理后台申请的应用APPID
    appKey = "e843359357823432hkj37b715e68cf6"         // 管理后台申请的应用APPKEY
    versionName = android.defaultConfig.versionName     // 当前应用的版本名称
}
```

## 初始化Rifle

在Application的onCreate方法中，加入如下代码初始化Radar

```
RadarConfig.Builder builder =
        new RadarConfig.Builder(this, "后台申请的APPID")
                .debuggable(true)           // 设置成true会输出日志到logcat
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
```

## 更新用户ID
程序运行期间，如果用户ID更换之后，可以通过API进行更新

```
Radar.setUserId("23748");
```

## 添加自定义属性

为了方便排查问题，可以通过添加自定义属性，添加的属性将会被带到后台，方便查看。

```
Radar.putUserKeyValue("diyAttribute", "ssss");
```


## 更多功能
目前Radar内部在记录卡顿等信息时，需要获取页面（Activity）的名称，默认获取方式为获取页面的类名。
改方式的缺点是，如果业务有部分web页面，Activity都是同一个类，但是内容（URL）不一样，需要重写页面名称获取方式

通过`RadarConfig.Builder`进行设置
```
.pageNameProvider(new IPageNameProvider() {
    @Override
    public String getPageName(Activity activity) {
        if (activity instanceof WebViewActivity) {
            return xxxx;
        }
        return null;
    }
})
```

