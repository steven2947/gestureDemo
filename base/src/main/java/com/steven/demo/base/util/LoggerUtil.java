package com.steven.demo.base.util;


import android.util.Log;

import com.steven.demo.base.Constant;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Steven on 16/5/20.
 */
public class LoggerUtil {
    public static final String TAG = "Steven.He_LOG";
    public static final int SHOW_METHOD_COUNT = 5;

    public static void e(String key, String e) {
        if (Constant.DEBUG) {
            Log.e(TAG, e);
        }
    }

    public static void w(String key, String v) {

        if (Constant.DEBUG) {
            Log.w(TAG, v);
        }
    }


    public static void e(String key, Throwable e) {
        if (Constant.DEBUG) {
            Log.e(TAG, key, e);
        }
    }

    public static void v(String key, String v) {
        if (Constant.DEBUG) {
            Log.v(TAG,v);
        }
    }

    public static void d(String key, String v) {
        if (Constant.DEBUG) {
            Log.d(TAG,v);
        }
    }

    public static void e(String v) {
        e(TAG, v);
    }

    public static void e(Exception e) {
        e(getExceptionStackTrace(e));
    }

    public static void e(Throwable e) {
        e((Exception) e);
    }


    public static void v(String v) {
        v(TAG, v);
    }

    public static void d(String v) {
        d(TAG, v);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static String getExceptionStackTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            exception.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }


}
