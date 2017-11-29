package com.wangkeke.reactnativedemo;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.zbar.camera.CameraPreview;
import com.yanzhenjie.zbar.camera.ScanCallback;

import java.util.List;

public class ZbarActivity extends AppCompatActivity {

    private RelativeLayout mScanCropView;
    private ImageView mScanLine;
    private ValueAnimator mScanAnimator;

    private CameraPreview mPreviewView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbar);

        // Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mPreviewView = (CameraPreview) findViewById(R.id.capture_preview);
        mScanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        mScanLine = (ImageView) findViewById(R.id.capture_scan_line);

        mPreviewView.setScanCallback(resultCallback);

        findViewById(R.id.capture_restart_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanUnKnowPermission();
            }
        });
    }

    /**
     * Accept scan result.
     */
    private ScanCallback resultCallback = new ScanCallback() {
        @Override
        public void onScanResult(String result) {
            stopScan();
//            Toast.makeText(ZbarActivity.this, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("ysdata",result);
            setResult(RESULT_OK,intent);
            ZbarActivity.this.finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mScanAnimator != null) {
            startScanUnKnowPermission();
        }
    }

    @Override
    public void onPause() {
        // Must be called here, otherwise the camera should not be released properly.
        stopScan();
        super.onPause();
    }

    /**
     * Do not have permission to request for permission and start scanning.
     */
    private void startScanUnKnowPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        startScanWithPermission();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        AndPermission.defaultSettingDialog(ZbarActivity.this).show();
                    }
                })
                .start();
    }

    /**
     * There is a camera when the direct scan.
     */
    private void startScanWithPermission() {
        if (mPreviewView.start()) {
            mScanAnimator.start();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.camera_failure)
                    .setMessage(R.string.camera_hint)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    /**
     * Stop scan.
     */
    private void stopScan() {
        mScanAnimator.cancel();
        mPreviewView.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mScanAnimator == null) {
            int height = mScanCropView.getMeasuredHeight() - 25;
            mScanAnimator = ObjectAnimator.ofFloat(mScanLine, "translationY", 0F, height).setDuration(3000);
            mScanAnimator.setInterpolator(new LinearInterpolator());
            mScanAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mScanAnimator.setRepeatMode(ValueAnimator.REVERSE);

            startScanUnKnowPermission();
        }
    }
}
