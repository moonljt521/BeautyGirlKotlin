package com.moon.beautygirlkotlin.glide

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * author: moon
 * created on: 18/4/25 下午2:38
 * description:
 */
class GlideCircleTransForm : BitmapTransformation {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    }


    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return this.circleCrop(pool, toTransform)!!
    }


    constructor( ctx : Context) : super()

    fun getId(): String {

        return javaClass.name
    }


    private fun circleCrop(pool: BitmapPool?, source: Bitmap?): Bitmap? {
        if (source == null) return null

        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        // TODO this could be acquired from the pool too
        val squared = Bitmap.createBitmap(source, x, y, size, size)

        var result: Bitmap? = pool?.get(size, size, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        return result
    }
}