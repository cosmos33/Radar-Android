# 在这里添加项目的代码混淆规则

# 混淆规则请参考:http://proguard.sourceforge.net/index.html#manual/usage.html

##################### 一般使用默认 #####################

# 不使用大小写混合类名,混淆后的类名为小写

-dontusemixedcaseclassnames

# 混淆第三方库

-dontskipnonpubliclibraryclasses

# 混淆时记录日志,有助于排查错误

-verbose

# 代码混淆使用的算法.

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# 代码混淆压缩比,值在0-7之间,默认为5.

-optimizationpasses 5

# 优化时允许访问并修改有修饰符的类和类的成员

-allowaccessmodification

# native方法保留
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留源文件名及行号
-keepattributes SourceFile,LineNumberTable


# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

-keep class squareup.haha.** {*;}
-keep class com.squareup.haha.** {*;}
-dontwarn com.squareup.haha.**
-keep class com.cosmos.radar.** {*;}