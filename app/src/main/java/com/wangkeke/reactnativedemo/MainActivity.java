package com.wangkeke.reactnativedemo;

import android.content.Intent;

import com.facebook.react.ReactActivity;

import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Nullable;

public class MainActivity extends ReactActivity {

    public static ArrayBlockingQueue<String> mQueue = new ArrayBlockingQueue<String>(1);

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "ReactNativeDemo";
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 200){
            String result = data.getStringExtra("ysdata");
            if(result != null && !result.equals("")){
                mQueue.add(result);
            }else {
                mQueue.add("暂无数据");
            }
        }else {
            mQueue.add("没有回调···");
        }

    }
}
