# ReactNativeAndroid

1.执行npm install需要管理员权限启动cmd窗口

2.添加完package.json后，执行npm install

3.project的build.gradle中添加
```
allprojects {
    repositories {
        jcenter()
        maven {
            // All of React Native (JS, Android binaries) is installed from npm
            url "$rootDir/node_modules/react-native/android"
        }
    }
}

```

4.app的build.gradle中添加react依赖库，版本要与package.json一致

```
compile 'com.facebook.react:react-native:0.44.0'
```

5.报错如下错误

<font color="red">

```
Error:Conflict with dependency 'com.google.code.findbugs:jsr305' in project ':app'. Resolved versions for app (3.0.0) and test app (2.0.1) differ. See http://g.co/androidstudio/app-test-app-conflict for details.

```

</font>

解决办法：

```
android {
    configurations.all {
        resolutionStrategy.force 'com.google.code.findbugs:jsr305:1.3.9'
    }
}
```
6.配置权限

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```