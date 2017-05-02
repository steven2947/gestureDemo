package com.steven.demo.base.control;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.steven.demo.base.util.LoggerUtil;
import com.steven.demo.base.util.PreconditionUtil;


/**
 * Created by Steven on 16/4/21.
 * 片刻通知用户
 */
public class ToastControl {

    static String mText = null;
    static int FILTER_TIME = 3000;
    public static int TOAST_MSG_SHOW = 1;
    public static int TOAST_MSG_HIDE = 2;
    public static int TOAST_MSG_ACTION = 3;

    /**
     * 使用Snackbar 显示
     *
     * @param view
     * @param message
     */
    public static void showToast(View view, String message) {
        showToast(view, message, null);
        LoggerUtil.e(message);
    }

    /**
     * 使用系统内置的Toast 显示
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        showToastByOriginal(context, message);
    }

    public static void showToast(View windowView, String message, String actionName) {

        PreconditionUtil.checkNotNull(windowView);
        PreconditionUtil.checkStringNotNull(message);

        if (mText == null || !mText.equals(message)) {
            showToastBySnackBar(windowView, message, actionName);
            mText = message;
            actionDelayTime();
        } else {
            LoggerUtil.v("过滤一条内容一样的Toast通知 : " + message);
        }
    }


    private static void showToastBySnackBar(View root_view, String message, String actionName) {

        Snackbar.make(root_view, message, Snackbar.LENGTH_SHORT)
                .setAction(actionName, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);

                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);

                    }
                })
                .show();
    }


    private static void showToastByOriginal(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void actionDelayTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(FILTER_TIME);//过滤
                    mText = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static int getFilterTime() {
        return FILTER_TIME;
    }

    public static void setFilterTime(int filterTime) {
        FILTER_TIME = filterTime;
    }
}
