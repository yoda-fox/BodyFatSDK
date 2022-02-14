# 代码优化
-dontshrink
# 不优化输入的类文件
-dontoptimize
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 混淆时不记录日志
-verbose
# 不做预校验
-dontpreverify
# 保留Annotation不混淆 这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 设置是否允许改变作用域
-allowaccessmodification
# 把混淆类中的方法名也混淆了
-useuniqueclassmembernames
# apk 包内所有 class 的内部结构
-dump class_files.txt
# 未混淆的类和成员
-printseeds seeds_txt
# 列出从apk中删除的代码
-printusage unused.txt
# 混淆前后的映射
-printmapping mapping.txt
# 指定混淆是采用的算法，后面的参数是一个过滤器
-optimizations !code/simplification/cast,!field/*,!class/merging/*
# 忽略警告
-ignorewarnings

#native方法不混淆
-keepclasseswithmembernames class * { # 保持 native 方法不被混淆
   native <methods>;
}

#不需混淆的Android类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class android.arch.lifecycle.**{*;}
-keep public class android.arch.lifecycle.**{*;}
-keep public class android.arch.lifecycle.**{*;}
-keep public class android.arch.lifecycle.**{*;}
-keep public class android.arch.core.internal.**{*;}
-keep class android.support.**{*;}
-keep interface android.support.**{*;}
-keep public class android.support.**{*;}
-keep public interface android.support.**{*;}

#JavaBean
-keep public class com.scale.bluetoothlibrary.** {*;}
#Gson混淆配置
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
#OkHttp3混淆配置
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**












