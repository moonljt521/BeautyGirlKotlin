//package com.moon.beautygirlkotlin.widget
//
//import android.content.Context
//import android.support.v4.view.ViewPager
//import android.util.AttributeSet
//import android.view.View
//
///**
// * author: moon
// * created on: 18/4/25 下午5:21
// * description:
// */
//class CustomViewPager : ViewPager {
//
//    constructor(context : Context ) :super(context)
//
//    constructor(context : Context ,attrs : AttributeSet) :super(context,attrs)
//
//    override fun canScroll(v: View?, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
//        if (v is PhotoImageView){
//            return v.canScrollHorizontallyFroyo( -dx )
//        }
//
//        return super.canScroll(v, checkV, dx, x, y)
//
//    }
//
//}