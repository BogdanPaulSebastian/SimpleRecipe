# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn okhttp3.internal.platform.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keepnames class com.google.android.play.core.review.ReviewManagerFactory
-keepnames interface * implements java.io.Serializable
-keepnames class * implements java.io.Serializable

-keep class net.paulbogdan.simplerecipe.model.** { *; }

-keepclassmembers class net.paulbogdan.simplerecipe.model.** {
    *;
}

-keepclassmembers class **$WhenMappings {
    <fields>;
}