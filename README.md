# ReactNativeAndroid

##### Android原生项目集成RN & 开发环境运行方式：

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

7.开发环境生成jsbundle,在app/src/main下新建assets目录，然后执行如下命令

```
//原生项目加入react native页面，创建assets，以及index.android.bundle文件后，用于写入js代码
react-native bundle --platform android --dev false --entry-file index.android.js --bundle-output app/src/main/assets/index.android.bundle --assets-dest app/src/main/res/

```
8.运行即可看到执行效果


##### debug调试方式：
1.配置reactApplication下如下方法,true代表debug环境，false代表开发环境：
```
    @Override
    public boolean getUseDeveloperSupport() {
        return true;
    }
```

2.执行npm start命令，启动本地服务器，然后运行项目，摇晃手机，会弹出选择框，选择Dev Settings设置Debug server host & port for device选项，设置格式：ip地址+端口号（8081）

3.设置好后返回，会自动加载项目，此时运行成功

4.开发时开启热更新模式，修改代码后可实时显示运行效果，方式：摇晃手机，在弹出的选择框中选择Enable Hot Reloading即可，然后随便修改下代码，ctrl+s即可自动更新页面

##### 打签名包方式

1.app目录下build.gradle添加如下配置,签名文件放置在app目录下：

```
    signingConfigs {
        release {
            storeFile file('android.keystore')
            storePassword 'wangkeke'
            keyAlias 'android.keystore'
            keyPassword 'wangkeke'
        }

    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }
```

2.打开Terminal命令行模式,执行gradlew assembleRelease,待执行完毕，会在build/outputs/apk目录下生成app-reease.apk签名包

#####设置支持的 SO 库构架：

```
    ndk {
            // 设置支持的 SO 库构架，注意这里要根据你的实际情况来设置
            abiFilters "armeabi-v7a", "x86"
        }
```