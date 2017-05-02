package com.steven.demo.gesture;

import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.steven.demo.base.clean.presentation.BaseActivity;
import com.steven.demo.base.control.ToastControl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Steven.He
 * @date 2017/4/27
 */

public class GestureActivity extends BaseActivity {

    private GestureLibrary gLib;

    @BindView(R.id.gov_gestures)
    GestureOverlayView govGestures;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, GestureActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        ButterKnife.bind(this);

        gLib = GestureManager.getGestureLibrary(this);

        //设置监听回调
        govGestures.setGestureStrokeAngleThreshold(90.0f);
        govGestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, android.gesture.Gesture gesture) {

                //手势库根据设别匹配度来排序
                ArrayList<Prediction> predictions = gLib.recognize(gesture);

                if (predictions.size() > 0) {
                    //第一个匹配度最高
                    Prediction prediction = predictions.get(0);
                    if (prediction.score > 1.0) {//大于一个阈值
                        ToastControl.showToast(GestureActivity.this, prediction.name);
                    }
                }
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
