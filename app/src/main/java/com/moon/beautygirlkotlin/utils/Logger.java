package com.moon.beautygirlkotlin.utils;

import android.util.Log;

/**
 * Created by sprit_moon on 15/9/9.
 */
public class Logger {


    private static final String TAG = "moon";
    
    public static boolean DEBUG = false;

    public static void i(String value) {
        if (DEBUG) {
            Log.i(TAG, value);

        }
    }

    public static void i(String tag,String value) {
        if (DEBUG) {
            Log.i(tag, value);
        }
    }


    public static void e(String tag, String value) {
        if (DEBUG) {
            Log.e(tag, value);
        }
    }

    public static void d(String value) {
        if (DEBUG) {
            Log.d(TAG, value);
        }

    }

    public static void v(String value) {
        if (DEBUG) {

            Log.v(TAG, value);
        }

    }

    public static void e(String value) {
        if (DEBUG) {

            Log.e(TAG, value);
        }
    }
}
