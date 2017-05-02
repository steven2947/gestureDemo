package com.steven.demo.gesture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

import com.steven.demo.base.clean.presentation.BaseActivity;
import com.steven.demo.base.control.ToastControl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Steven.He
 * @date 2017/4/28
 */

public class SaveGestureActivity extends BaseActivity {

    private static final String TAG = "SaveGestureActivity";

    @BindView(R.id.gov_gestures)
    GestureOverlayView govGestures;

    private boolean mGestureDrawn;
    private Gesture mCurrentGesture;
    private String mGesturename;

    private GestureLibrary gLib;

    Unbinder unbinder;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, SaveGestureActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_save);
        unbinder = ButterKnife.bind(this);

        gLib = GestureManager.getGestureLibrary(this);

        govGestures.addOnGestureListener(mGestureListener);
        govGestures.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
    }

    /**
     * our gesture listener
     */
    private GestureOverlayView.OnGestureListener mGestureListener = new GestureOverlayView.OnGestureListener() {
        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            mGestureDrawn = true;
            Log.d(TAG, "andar");
        }

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            mCurrentGesture = overlay.getGesture();
        }

        @Override
        public void onGestureEnded(GestureOverlayView gestureView, MotionEvent motion) {
            Log.d(TAG, "bahar");
        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
            Log.d(TAG, "cancel");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.DELETE:
                reDrawGestureView();
                break;
            case R.id.Save:
                if (mGestureDrawn) {
                    saveWithName();
                } else {
                    ToastControl.showToast(this, "请先画一个手势");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetEverything() {
        mGestureDrawn = false;
        mCurrentGesture = null;
        mGesturename = "";
    }

    private void reDrawGestureView() {
        govGestures.clear(false);
//        unbinder.unbind();
//        setContentView(R.layout.activity_gesture_save);
//        unbinder = ButterKnife.bind(this);
//        govGestures.removeAllOnGestureListeners();
//        govGestures.addOnGestureListener(mGestureListener);
        resetEverything();
    }

    private void saveWithName() {
        AlertDialog.Builder namePopup = new AlertDialog.Builder(this);
        namePopup.setTitle("输入这个笔画的名字");

        final EditText nameField = new EditText(this);
        namePopup.setView(nameField);
        namePopup.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!nameField.getText().toString().matches("")) {
                    mGesturename = nameField.getText().toString();
                    saveGesture();
                } else {
                    saveWithName();
                    ToastControl.showToast(SaveGestureActivity.this, "请输入名字");
                }
            }
        });
        namePopup.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mGesturename = "";
                return;
            }
        });

        namePopup.show();
    }

    private void saveGesture() {
        gLib.addGesture(mGesturename, mCurrentGesture);
        if (!gLib.save()) {
            Log.e(TAG, "保存失败");
        } else {
            ToastControl.showToast(this, "保存成功");
        }
        reDrawGestureView();
    }
}
