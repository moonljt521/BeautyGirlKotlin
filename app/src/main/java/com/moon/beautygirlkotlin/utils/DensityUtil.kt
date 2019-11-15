package com.moon.beautygirlkotlin.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import android.view.WindowManager

/**
 * Created by star on 2017/2/24.
 */

object DensityUtil {

    private var isPad = -1

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取手机宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val size = Point()
        display.getSize(size)

        return if (DensityUtil.isPad(context)) {
            if (size.x > size.y) size.x else size.y
        } else {
            if (size.x > size.y) size.y else size.x
        }
    }

    /**
     * 获取手机高度
     *
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager ?: return 0
        val display = manager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return if (DensityUtil.isPad(context)) {
            if (size.x > size.y) size.y else size.x
        } else {
            if (size.x > size.y) size.x else size.y
        }
    }

    /**
     * 获取宽高 宽size[0] 高size[1]
     *
     * @param context
     * @return
     */
    fun getScreenSize(context: Context): IntArray {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                ?: return intArrayOf()
        val display = manager.defaultDisplay
        return intArrayOf(display.width, display.height)
    }


    /**
     * @param context
     * @return 获取状态栏的高度
     */

    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = -1
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun isNavigationBarShow(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            return realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(context).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return if (menu || back) {
                false
            } else {
                true
            }
        }
    }

    fun getNavigationBarHeight(context: Context): Int {
        if (!isNavigationBarShow(context)) {
            return 0
        }
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android")
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    fun sp2px(context: Context, sp: Float): Int {
        //        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        //        return (int) (spValue * fontScale + 0.5f);

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }

    /**
     * 获取屏幕原始宽高
     */
    fun getDpi(activity: Activity): Int {
        var dpi = 0
        val display = activity.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            dpi = dm.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dpi
    }

    /**
     * 屏幕尺寸 inch
     * @param context
     * @return
     */
    fun getScreenInch(context: Context): Double {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager ?: return 0.0
        val display = wm.defaultDisplay
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        val x = Math.pow((dm.widthPixels / dm.xdpi).toDouble(), 2.0)
        val y = Math.pow((dm.heightPixels / dm.ydpi).toDouble(), 2.0)
        // 屏幕尺寸
        return Math.sqrt(x + y)
    }

    /**
     * check whether the device is pad or not
     * @param context
     * @return
     */
    fun isPad(context: Context): Boolean {
        if (isPad > -1) return isPad == 1
        isPad = if (isGreatScreen(context) && officialPadCheck(context)) 1 else 0
        return isPad == 1
    }

    private fun isGreatScreen(context: Context): Boolean {
        val screenInches = getScreenInch(context)
        return if (screenInches >= 6.0) {
            true
        } else false
    }

    // 华为 tag-al00 不准确
    @Deprecated("")
    private fun hasTelFeature(activity: Activity): Boolean {
        val telephony = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return false
        return if (telephony.phoneType == TelephonyManager.PHONE_TYPE_NONE) {
            false
        } else true
    }

    private fun officialPadCheck(context: Context): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun forceLandscape(context: Context) {
        (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    fun forcePortrait(context: Context) {
        (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
