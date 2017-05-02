package com.steven.demo.gesture;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;

/**
 * @author Steven.He
 * @date 2017/4/27
 */

public class GestureManager {

    /**
     * 初始化手势库
     *
     * @param context
     * @return
     */
    public static GestureLibrary getGestureLibrary(Context context) {
        GestureLibrary gLib = GestureLibraries.fromFile(context.getExternalFilesDir(null) + "/" + "gesture.txt");
        gLib.load();
        return gLib;
    }

}
