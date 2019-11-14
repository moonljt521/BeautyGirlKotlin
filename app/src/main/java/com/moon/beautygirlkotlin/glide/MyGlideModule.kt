package com.moon.beautygirlkotlin.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.GlideModule

/**
 * author: moon
 * created on: 18/4/28 上午11:25
 * description:
 */
class MyGlideModule: GlideModule {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        val calculator = MemorySizeCalculator(builder)
//        val defaultMemoryCacheSize = calculator.memoryCacheSize
//        val defaultBitmapPoolSize = calculator.bitmapPoolSize
//
//        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toLong()
//        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toLong()
//
//        builder?.setMemoryCache(LruResourceCache(customMemoryCacheSize))
//        builder?.setBitmapPool(LruBitmapPool(customBitmapPoolSize))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    }


}