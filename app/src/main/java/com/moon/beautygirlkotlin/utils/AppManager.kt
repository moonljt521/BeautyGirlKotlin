package com.moon.beautygirlkotlin.utils

import android.app.Activity
import android.content.Context

import java.util.Stack


class AppManager private constructor() {

    companion object {
        val instance : AppManager by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            AppManager()
        }
    }

    private var activityStack: Stack<Activity>? = Stack()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (!activityStack!!.empty()) {
            activityStack!!.lastElement()
        } else null

    }

    /**
     * 结束指定的Activity
     */
    @JvmOverloads
    fun finishActivity(activity: Activity? = activityStack!!.lastElement()) {
        //		//应用即将全部关闭，清理缓存
        if (activityStack != null && activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        if (activityStack == null || activityStack!!.size <= 0) return
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    //获取指定类名的Activity
    fun getActivity(cls: Class<*>): Activity? {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 退出应用
     *
     * @param context
     */
    fun exitApp(context: Context) {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            System.gc()
            android.os.Process.killProcess(android.os.Process.myPid())// 直接退出
        }
    }




}
/**
 * 结束当前Activity（堆栈中最后一个压入的）
 */