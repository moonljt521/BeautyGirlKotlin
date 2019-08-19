package com.moon.beautygirlkotlin.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;


public final class AppManager {
	private static Stack<Activity> activityStack = new Stack<>();
	private static volatile AppManager instance;
	private AppManager(){}

	public static AppManager getAppManager(){
		if(instance==null){
			synchronized (AppManager.class){
				if (instance == null){
					instance =new AppManager();
				}
			}
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		if (!activityStack.empty()){
			return activityStack.lastElement();
		}

		return null;
	}
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		finishActivity(activityStack.lastElement());
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
//		//应用即将全部关闭，清理缓存
//		if(activityStack.size()==1){
//			((AppContext)activity.getApplication()).clearWebViewCache();
//
//		}
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	//获取指定类名的Activity
	public Activity getActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				return activity ;
			}
	  }
		return null;
	}
	
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
			if (null != activityStack.get(i)){
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用
	 * @param context
	 */
	public void exitApp(Context context){
		try {
			finishAllActivity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.gc();
			android.os.Process.killProcess(android.os.Process.myPid());// 直接退出
		}
	}


	

}