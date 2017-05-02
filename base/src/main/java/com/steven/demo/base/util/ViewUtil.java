package com.steven.demo.base.util;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Steven.He
 * @date 2017/4/11
 */

public class ViewUtil {

    /**
     * 清除动画相关
     *
     * @param v
     */
    public static void clear(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null).setStartDelay(0);
    }


    /**
     * listView 中的一项的动态外观
     * 用于整行隐藏显示
     * 而Visibility的方法会整行变空白
     *
     * @param view
     * @param isHide
     */
    public static boolean dynamicHideW(View view, boolean isHide) {
        int pixels;
        if (isHide) {
            //动态隐藏
            pixels = 0;
        } else {
            pixels = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = pixels;
//        params.setMargins(10, 5, 10, 5);
        view.setLayoutParams(params);

        return pixels == 0;
    }

    /**
     * listView 中的一项的动态外观
     * 用于整行隐藏显示
     * 而Visibility的方法会整行变空白
     *
     * @param view
     * @param isHide
     */
    public static boolean dynamicHideH(View view, boolean isHide) {
        int pixels;
        if (isHide) {
            //动态隐藏
            pixels = 0;
        } else {
            pixels = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);
        params.height = pixels;
//        params.setMargins(10, 5, 10, 5);
        view.setLayoutParams(params);

        return pixels == 0;
    }

}
