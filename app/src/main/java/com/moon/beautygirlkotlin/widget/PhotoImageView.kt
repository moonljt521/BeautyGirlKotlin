//package com.moon.beautygirlkotlin.widget
//
//import android.annotation.TargetApi
//import android.content.Context
//import android.content.res.Configuration
//import android.graphics.*
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Parcelable
//import android.util.AttributeSet
//import android.util.Log
//import android.view.GestureDetector
//import android.view.MotionEvent
//import android.view.ScaleGestureDetector
//import android.view.View
//import android.view.animation.AccelerateDecelerateInterpolator
//import android.widget.ImageView
//import android.widget.OverScroller
//import android.widget.Scroller
//import com.moon.beautygirlkotlin.widget.PhotoImageView.DoubleTapZoom.Companion.ZOOM_TIME
//
///**
// * author: moon
// * created on: 18/4/25 下午5:55
// * description:
// */
//class PhotoImageView : ImageView{
//
//    constructor(context : Context ) :super(context){
//        sharedConstructing(context)
//    }
//
//    constructor(context : Context ,attrs : AttributeSet) :super(context,attrs){
//        sharedConstructing(context)
//    }
//
//    constructor(context : Context ,attrs : AttributeSet , defStyle : Int) :super(context,attrs ,defStyle){
//        sharedConstructing(context)
//    }
//
//    private val DEBUG = "DEBUG"
//
//    //
//    // SuperMin and SuperMax multipliers. Determine how much the image can be
//    // zoomed below or above the zoom boundaries, before animating ic_back to the
//    // min/max zoom boundary.
//    //
//    private val SUPER_MIN_MULTIPLIER = .75f
//
//    private val SUPER_MAX_MULTIPLIER = 1.25f
//
//    //
//    // Scale of image ranges from minScale to maxScale, where minScale == 1
//    // when the image is stretched to fit view.
//    //
//    private var normalizedScale = 0f
//
//    //
//    // Matrix applied to image. MSCALE_X and MSCALE_Y should always be equal.
//    // MTRANS_X and MTRANS_Y are the other values used. prevMatrix is the matrix
//    // saved prior to the screen rotating.
//    //
//    lateinit var matrix: Matrix
//
//    private var prevMatrix:Matrix
//
//    private enum class State {
//        NONE, DRAG, ZOOM, FLING, ANIMATE_ZOOM
//    }
//
//    ;
//
//    private var state: State? = null
//
//    private var minScale = 0f
//
//    private var maxScale = 0f
//
//    private var superMinScale = 0f
//
//    private var superMaxScale = 0f
//
//    private var m: FloatArray? = null
//
//
//    private var fling: Fling? = null
//
//    private var mScaleType: ImageView.ScaleType? = null
//
//    private var imageRenderedAtLeastOnce: Boolean = false
//
//    private var onDrawReady: Boolean = false
//
//    private var delayedZoomVariables: ZoomVariables? = null
//
//    //
//    // Size of view and previous view size (ie before rotation)
//    //
//    private var viewWidth: Int = 0
//    private var viewHeight:Int = 0
//    private var prevViewWidth:Int = 0
//    private var prevViewHeight:Int = 0
//
//    //
//    // Size of image when it is stretched to fit view. Before and After rotation.
//    //
//    private var matchViewWidth: Float = 0f
//    private var matchViewHeight:Float = 0f
//    private var prevMatchViewWidth:Float = 0.toFloat()
//    private var prevMatchViewHeight:Float = 0.toFloat()
//
//    private var mScaleDetector: ScaleGestureDetector? = null
//
//    private var mGestureDetector: GestureDetector? = null
//
//    private var doubleTapListener: GestureDetector.OnDoubleTapListener? = null
//
//    private var userTouchListener: View.OnTouchListener? = null
//
//    private var touchImageViewListener: OnTouchImageViewListener? = null
//
//
//    private fun sharedConstructing(context: Context) {
//
//        super.setClickable(true)
//        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
//        mGestureDetector = GestureDetector(context, GestureListener())
//        matrix = Matrix()
//        prevMatrix = Matrix()
//        m = FloatArray(9)
//        normalizedScale = 1f
//        if (mScaleType == null) {
//            mScaleType = ImageView.ScaleType.FIT_CENTER
//        }
//        minScale = 1f
//        maxScale = 3f
//        superMinScale = SUPER_MIN_MULTIPLIER * minScale
//        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale
//        setImageMatrix(matrix)
//        setScaleType(ImageView.ScaleType.MATRIX)
//        setState(State.NONE)
//        onDrawReady = false
//        super.setOnTouchListener(PrivateOnTouchListener())
//    }
//
//
//    override fun setOnTouchListener(l: View.OnTouchListener) {
//
//        userTouchListener = l
//    }
//
//
//    fun setOnTouchImageViewListener(l: OnTouchImageViewListener) {
//
//        touchImageViewListener = l
//    }
//
//
//    fun setOnDoubleTapListener(l: GestureDetector.OnDoubleTapListener) {
//
//        doubleTapListener = l
//    }
//
//
//    override fun setImageResource(resId: Int) {
//
//        super.setImageResource(resId)
//        savePreviousImageValues()
//        fitImageToView()
//    }
//
//
//    override fun setImageBitmap(bm: Bitmap) {
//
//        super.setImageBitmap(bm)
//        savePreviousImageValues()
//        fitImageToView()
//    }
//
//
//    override fun setImageDrawable(drawable: Drawable?) {
//
//        super.setImageDrawable(drawable)
//        savePreviousImageValues()
//        fitImageToView()
//    }
//
//
//    override fun setImageURI(uri: Uri?) {
//
//        super.setImageURI(uri)
//        savePreviousImageValues()
//        fitImageToView()
//    }
//
//
//    override fun setScaleType(type: ImageView.ScaleType?) {
//
//        if (type == ImageView.ScaleType.FIT_START || type == ImageView.ScaleType.FIT_END) {
//            throw UnsupportedOperationException(
//                    "TouchImageView does not support FIT_START or FIT_END")
//        }
//        if (type == ImageView.ScaleType.MATRIX) {
//            super.setScaleType(ImageView.ScaleType.MATRIX)
//        } else {
//            mScaleType = type
//            if (onDrawReady) {
//                //
//                // If the image is already rendered, scaleType has been called programmatically
//                // and the TouchImageView should be updated with the new scaleType.
//                //
//                setZoom(this)
//            }
//        }
//    }
//
//
//    override fun getScaleType(): ImageView.ScaleType? {
//
//        return mScaleType
//    }
//
//
//    /**
//     * Returns false if image is in initial, unzoomed state. False, otherwise.
//     *
//     * @return true if image is zoomed
//     */
//    fun isZoomed(): Boolean {
//
//        return normalizedScale != 1f
//    }
//
//
//    /**
//     * Return a Rect representing the zoomed image.
//     *
//     * @return rect representing zoomed image
//     */
//    fun getZoomedRect(): RectF {
//
//        if (mScaleType == ImageView.ScaleType.FIT_XY) {
//            throw UnsupportedOperationException("getZoomedRect() not supported with FIT_XY")
//        }
//        val topLeft = transformCoordTouchToBitmap(0f, 0f, true)
//        val bottomRight = transformCoordTouchToBitmap(viewWidth.toFloat(), viewHeight.toFloat(), true)
//
//        val w = getDrawable().getIntrinsicWidth().toFloat()
//        val h = getDrawable().getIntrinsicHeight().toFloat()
//        return RectF(topLeft.x / w, topLeft.y / h, bottomRight.x / w, bottomRight.y / h)
//    }
//
//
//    /**
//     * Save the current matrix and view dimensions
//     * in the prevMatrix and prevView variables.
//     */
//    private fun savePreviousImageValues() {
//
//        if (matrix != null && viewHeight != 0 && viewWidth != 0) {
//            matrix!!.getValues(m)
//            prevMatrix!!.setValues(m)
//            prevMatchViewHeight = matchViewHeight
//            prevMatchViewWidth = matchViewWidth
//            prevViewHeight = viewHeight
//            prevViewWidth = viewWidth
//        }
//    }
//
//
//    override fun onSaveInstanceState(): Parcelable? {
//
//        val bundle = Bundle()
//        bundle.putParcelable("instanceState", super.onSaveInstanceState())
//        bundle.putFloat("saveScale", normalizedScale)
//        bundle.putFloat("matchViewHeight", matchViewHeight)
//        bundle.putFloat("matchViewWidth", matchViewWidth)
//        bundle.putInt("viewWidth", viewWidth)
//        bundle.putInt("viewHeight", viewHeight)
//        matrix!!.getValues(m)
//        bundle.putFloatArray("matrix", m)
//        bundle.putBoolean("imageRendered", imageRenderedAtLeastOnce)
//        return bundle
//    }
//
//
//    override fun onRestoreInstanceState(state: Parcelable) {
//
//        if (state is Bundle) {
//            normalizedScale = state.getFloat("saveScale")
//            m = state.getFloatArray("matrix")
//            prevMatrix!!.setValues(m)
//            prevMatchViewHeight = state.getFloat("matchViewHeight")
//            prevMatchViewWidth = state.getFloat("matchViewWidth")
//            prevViewHeight = state.getInt("viewHeight")
//            prevViewWidth = state.getInt("viewWidth")
//            imageRenderedAtLeastOnce = state.getBoolean("imageRendered")
//            super.onRestoreInstanceState(state.getParcelable<Parcelable>("instanceState"))
//            return
//        }
//
//        super.onRestoreInstanceState(state)
//    }
//
//
//    protected override fun onDraw(canvas: Canvas) {
//
//        onDrawReady = true
//        imageRenderedAtLeastOnce = true
//        if (delayedZoomVariables != null) {
//            setZoom(delayedZoomVariables!!.scale, delayedZoomVariables!!.focusX, delayedZoomVariables!!.focusY,
//                    delayedZoomVariables!!.scaleType)
//            delayedZoomVariables = null
//        }
//        super.onDraw(canvas)
//    }
//
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//
//        super.onConfigurationChanged(newConfig)
//        savePreviousImageValues()
//    }
//
//
//    /**
//     * Get the max zoom multiplier.
//     *
//     * @return max zoom multiplier.
//     */
//    fun getMaxZoom(): Float {
//
//        return maxScale
//    }
//
//
//    /**
//     * Set the max zoom multiplier. Default value: 3.
//     *
//     * @param max max zoom multiplier.
//     */
//    fun setMaxZoom(max: Float) {
//
//        maxScale = max
//        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale
//    }
//
//
//    /**
//     * Get the min zoom multiplier.
//     *
//     * @return min zoom multiplier.
//     */
//    fun getMinZoom(): Float {
//
//        return minScale
//    }
//
//
//    /**
//     * Get the current zoom. This is the zoom relative to the initial
//     * scale, not the original resource.
//     *
//     * @return current zoom multiplier.
//     */
//    fun getCurrentZoom(): Float {
//
//        return normalizedScale
//    }
//
//
//    /**
//     * Set the min zoom multiplier. Default value: 1.
//     *
//     * @param min min zoom multiplier.
//     */
//    fun setMinZoom(min: Float) {
//
//        minScale = min
//        superMinScale = SUPER_MIN_MULTIPLIER * minScale
//    }
//
//
//    /**
//     * Reset zoom and translation to initial state.
//     */
//    fun resetZoom() {
//
//        normalizedScale = 1f
//        fitImageToView()
//    }
//
//
//    /**
//     * Set zoom to the specified scale. Image will be centered by default.
//     */
//    fun setZoom(scale: Float) {
//
//        setZoom(scale, 0.5f, 0.5f)
//    }
//
//
//    /**
//     * Set zoom to the specified scale. Image will be centered around the point
//     * (focusX, focusY). These floats range from 0 to 1 and denote the focus point
//     * as a fraction from the left and top of the view. For example, the top left
//     * corner of the image would be (0, 0). And the bottom right corner would be (1, 1).
//     */
//    fun setZoom(scale: Float, focusX: Float, focusY: Float) {
//
//        setZoom(scale, focusX, focusY, mScaleType)
//    }
//
//
//    /**
//     * Set zoom to the specified scale. Image will be centered around the point
//     * (focusX, focusY). These floats range from 0 to 1 and denote the focus point
//     * as a fraction from the left and top of the view. For example, the top left
//     * corner of the image would be (0, 0). And the bottom right corner would be (1, 1).
//     */
//    fun setZoom(scale: Float, focusX: Float, focusY: Float, scaleType: ImageView.ScaleType?) {
//        //
//        // setZoom can be called before the image is on the screen, but at this point,
//        // image and view sizes have not yet been calculated in onMeasure. Thus, we should
//        // delay calling setZoom until the view has been measured.
//        //
//        if (!onDrawReady) {
//            delayedZoomVariables = ZoomVariables(scale, focusX, focusY, scaleType!!)
//            return
//        }
//
//        if (scaleType != mScaleType) {
//            setScaleType(scaleType)
//        }
//        resetZoom()
//        scaleImage(scale.toDouble(), (viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), true)
//        matrix.getValues(m)
//        m!![Matrix.MTRANS_X] = -(focusX * getImageWidth() - viewWidth * 0.5f)
//        m!![Matrix.MTRANS_Y] = -(focusY * getImageHeight() - viewHeight * 0.5f)
//        matrix.setValues(m)
//        fixTrans()
//        setImageMatrix(matrix)
//    }
//
//
//    /**
//     * Set zoom parameters equal to another TouchImageView. Including scale, position,
//     * and ScaleType.
//     */
//    fun setZoom(img: PhotoImageView) {
//
//        val center = img.getScrollPosition()
//        setZoom(img.getCurrentZoom(), center!!.x, center.y, img.getScaleType())
//    }
//
//
//    /**
//     * Return the point at the center of the zoomed image. The PointF coordinates range
//     * in value between 0 and 1 and the focus point is denoted as a fraction from the left
//     * and top of the view. For example, the top left corner of the image would be (0, 0).
//     * And the bottom right corner would be (1, 1).
//     *
//     * @return PointF representing the scroll position of the zoomed image.
//     */
//    fun getScrollPosition(): PointF? {
//
//        val drawable = getDrawable() ?: return null
//        val drawableWidth = drawable!!.getIntrinsicWidth()
//        val drawableHeight = drawable!!.getIntrinsicHeight()
//
//        val point = transformCoordTouchToBitmap((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), true)
//        point.x /= drawableWidth.toFloat()
//        point.y /= drawableHeight.toFloat()
//        return point
//    }
//
//
//    /**
//     * Set the focus point of the zoomed image. The focus points are denoted as a fraction from the
//     * left and top of the view. The focus points can range in value between 0 and 1.
//     */
//    fun setScrollPosition(focusX: Float, focusY: Float) {
//
//        setZoom(normalizedScale, focusX, focusY)
//    }
//
//
//    /**
//     * Performs boundary checking and fixes the image matrix if it
//     * is out of bounds.
//     */
//    private fun fixTrans() {
//
//        matrix.getValues(m)
//        val transX = m!![Matrix.MTRANS_X]
//        val transY = m!![Matrix.MTRANS_Y]
//
//        val fixTransX = getFixTrans(transX, viewWidth.toFloat(), getImageWidth())
//        val fixTransY = getFixTrans(transY, viewHeight.toFloat(), getImageHeight())
//
//        if (fixTransX != 0f || fixTransY != 0f) {
//            matrix.postTranslate(fixTransX, fixTransY)
//        }
//    }
//
//
//    /**
//     * When transitioning from zooming from focus to zoom from center (or vice versa)
//     * the image can become unaligned within the view. This is apparent when zooming
//     * quickly. When the content size is less than the view size, the content will often
//     * be centered incorrectly within the view. fixScaleTrans first calls fixTrans() and
//     * then makes sure the image is centered correctly within the view.
//     */
//    private fun fixScaleTrans() {
//
//        fixTrans()
//        matrix.getValues(m)
//        if (getImageWidth() < viewWidth) {
//            m[Matrix.MTRANS_X] = (viewWidth - getImageWidth()) / 2
//        }
//
//        if (getImageHeight() < viewHeight) {
//            m[Matrix.MTRANS_Y] = (viewHeight - getImageHeight()) / 2
//        }
//        matrix!!.setValues(m)
//    }
//
//
//    private fun getFixTrans(trans: Float, viewSize: Float, contentSize: Float): Float {
//
//        val minTrans: Float
//        val maxTrans: Float
//
//        if (contentSize <= viewSize) {
//            minTrans = 0f
//            maxTrans = viewSize - contentSize
//        } else {
//            minTrans = viewSize - contentSize
//            maxTrans = 0f
//        }
//
//        if (trans < minTrans) {
//            return -trans + minTrans
//        }
//        return if (trans > maxTrans) {
//            -trans + maxTrans
//        } else 0f
//    }
//
//
//    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
//
//        return if (contentSize <= viewSize) {
//            0f
//        } else delta
//    }
//
//
//    private fun getImageWidth(): Float {
//
//        return matchViewWidth * normalizedScale
//    }
//
//
//    private fun getImageHeight(): Float {
//
//        return matchViewHeight * normalizedScale
//    }
//
//
//    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//
//        val drawable = getDrawable()
//        if (drawable == null || drawable!!.getIntrinsicWidth() == 0 ||
//                drawable!!.getIntrinsicHeight() == 0) {
//            setMeasuredDimension(0, 0)
//            return
//        }
//
//        val drawableWidth = drawable!!.getIntrinsicWidth()
//        val drawableHeight = drawable!!.getIntrinsicHeight()
//        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
//        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
//        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
//        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
//        viewWidth = setViewSize(widthMode, widthSize, drawableWidth)
//        viewHeight = setViewSize(heightMode, heightSize, drawableHeight)
//
//        //
//        // Set view dimensions
//        //
//        setMeasuredDimension(viewWidth, viewHeight)
//
//        //
//        // Fit content within view
//        //
//        fitImageToView()
//    }
//
//
//    /**
//     * If the normalizedScale is equal to 1, then the image is made to fit the screen. Otherwise,
//     * it is made to fit the screen according to the dimensions of the previous image matrix. This
//     * allows the image to maintain its zoom after rotation.
//     */
//    private fun fitImageToView() {
//
//        val drawable = getDrawable()
//        if ( drawable.getIntrinsicWidth() == 0 ||
//                drawable.getIntrinsicHeight() == 0) {
//            return
//        }
//
//        val drawableWidth = drawable!!.getIntrinsicWidth()
//        val drawableHeight = drawable!!.getIntrinsicHeight()
//
//        //
//        // Scale image for view
//        //
//        var scaleX = viewWidth.toFloat() / drawableWidth
//        var scaleY = viewHeight.toFloat() / drawableHeight
//
//        when (mScaleType) {
//            ImageView.ScaleType.CENTER ->
//                scaleY = 1f
//            scaleX = scaleY
//                    ImageView.ScaleType . CENTER_CROP -> scaleY = Math.max(scaleX, scaleY)
//            scaleX = scaleY
//
//                    ImageView.ScaleType . CENTER_INSIDE -> {
//                scaleY = Math.min(1f, Math.min(scaleX, scaleY))
//                scaleX = scaleY
//                scaleY = Math.min(scaleX, scaleY)
//                scaleX = scaleY
//            }
//
//            ImageView.ScaleType.FIT_CENTER -> scaleY = Math.min(scaleX, scaleY)
//            scaleX = scaleY
//
//                    ScaleType.FIT_XY -> {
//            }
//
//            else ->
//                //
//                // FIT_START and FIT_END not supported
//                //
//                throw UnsupportedOperationException(
//                        "TouchImageView does not support FIT_START or FIT_END")
//        }
//
//        //
//        // Center the image
//        //
//        val redundantXSpace = viewWidth - scaleX * drawableWidth
//        val redundantYSpace = viewHeight - scaleY * drawableHeight
//        matchViewWidth = viewWidth - redundantXSpace
//        matchViewHeight = viewHeight - redundantYSpace
//        if (!isZoomed() && !imageRenderedAtLeastOnce) {
//            //
//            // Stretch and center image to fit view
//            //
//            matrix.setScale(scaleX, scaleY)
//            matrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2)
//            normalizedScale = 1f
//        } else {
//            //
//            // These values should never be 0 or we will set viewWidth and viewHeight
//            // to NaN in translateMatrixAfterRotate. To avoid this, call savePreviousImageValues
//            // to set them equal to the current values.
//            //
//            if (prevMatchViewWidth == 0f || prevMatchViewHeight == 0f) {
//                savePreviousImageValues()
//            }
//
//            prevMatrix!!.getValues(m)
//
//            //
//            // Rescale Matrix after rotation
//            //
//            m!![Matrix.MSCALE_X] = matchViewWidth / drawableWidth * normalizedScale
//            m!![Matrix.MSCALE_Y] = matchViewHeight / drawableHeight * normalizedScale
//
//            //
//            // TransX and TransY from previous matrix
//            //
//            val transX = m!![Matrix.MTRANS_X]
//            val transY = m!![Matrix.MTRANS_Y]
//
//            //
//            // Width
//            //
//            val prevActualWidth = prevMatchViewWidth * normalizedScale
//            val actualWidth = getImageWidth()
//            translateMatrixAfterRotate(Matrix.MTRANS_X, transX, prevActualWidth, actualWidth,
//                    prevViewWidth, viewWidth, drawableWidth)
//
//            //
//            // Height
//            //
//            val prevActualHeight = prevMatchViewHeight * normalizedScale
//            val actualHeight = getImageHeight()
//            translateMatrixAfterRotate(Matrix.MTRANS_Y, transY, prevActualHeight, actualHeight,
//                    prevViewHeight, viewHeight, drawableHeight)
//
//            //
//            // Set the matrix to the adjusted scale and translate values.
//            //
//            matrix!!.setValues(m)
//        }
//        fixTrans()
//        setImageMatrix(matrix)
//    }
//
//
//    /**
//     * Set view dimensions based on layout params
//     */
//    private fun setViewSize(mode: Int, size: Int, drawableWidth: Int): Int {
//
//        val viewSize: Int
//        when (mode) {
//            View.MeasureSpec.EXACTLY -> viewSize = size
//
//            View.MeasureSpec.AT_MOST -> viewSize = Math.min(drawableWidth, size)
//
//            View.MeasureSpec.UNSPECIFIED -> viewSize = drawableWidth
//
//            else -> viewSize = size
//        }
//        return viewSize
//    }
//
//
//    /**
//     * After rotating, the matrix needs to be translated. This function finds the area of image
//     * which was previously centered and adjusts translations so that is again the center,
//     * post-rotation.
//     *
//     * @param axis Matrix.MTRANS_X or Matrix.MTRANS_Y
//     * @param trans the value of trans in that axis before the rotation
//     * @param prevImageSize the width/height of the image before the rotation
//     * @param imageSize width/height of the image after rotation
//     * @param prevViewSize width/height of view before rotation
//     * @param viewSize width/height of view after rotation
//     * @param drawableSize width/height of drawable
//     */
//    private fun translateMatrixAfterRotate(axis: Int, trans: Float, prevImageSize: Float, imageSize: Float, prevViewSize: Int, viewSize: Int, drawableSize: Int) {
//
//        if (imageSize < viewSize) {
//            //
//            // The width/height of image is less than the view's width/height. Center it.
//            //
//            m!![axis] = (viewSize - drawableSize * m!![Matrix.MSCALE_X]) * 0.5f
//        } else if (trans > 0) {
//            //
//            // The image is larger than the view, but was not before rotation. Center it.
//            //
//            m!![axis] = -((imageSize - viewSize) * 0.5f)
//        } else {
//            //
//            // Find the area of the image which was previously centered in the view. Determine its distance
//            // from the left/top side of the view as a fraction of the entire image's width/height. Use that percentage
//            // to calculate the trans in the new view width/height.
//            //
//            val percentage = (Math.abs(trans) + 0.5f * prevViewSize) / prevImageSize
//            m!![axis] = -(percentage * imageSize - viewSize * 0.5f)
//        }
//    }
//
//
//    private fun setState(state: State) {
//
//        this.state = state
//    }
//
//
//    fun canScrollHorizontallyFroyo(direction: Int): Boolean {
//
//        return canScrollHorizontally(direction)
//    }
//
//
//    override fun canScrollHorizontally(direction: Int): Boolean {
//
//        matrix!!.getValues(m)
//        val x = m!![Matrix.MTRANS_X]
//
//        if (getImageWidth() < viewWidth) {
//            return false
//        } else if (x >= -1 && direction < 0) {
//            return false
//        } else if (Math.abs(x) + viewWidth.toFloat() + 1f >= getImageWidth() && direction > 0) {
//            return false
//        }
//
//        return true
//    }
//
//
//    /**
//     * Gesture Listener detects a single click or long click and passes that on
//     * to the view's listener.
//     *
//     * @author Ortiz
//     */
//    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
//
//        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//
//            return if (doubleTapListener != null) {
//                doubleTapListener!!.onSingleTapConfirmed(e)
//            } else performClick()
//        }
//
//
//        override fun onLongPress(e: MotionEvent) {
//
//            performLongClick()
//        }
//
//
//        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//
//            if (fling != null) {
//                //
//                // If a previous fling is still active, it should be cancelled so that two flings
//                // are not run simultaenously.
//                //
//                fling!!.cancelFling()
//            }
//            fling = Fling(velocityX.toInt(), velocityY.toInt())
//            compatPostOnAnimation(fling!!)
//            return super.onFling(e1, e2, velocityX, velocityY)
//        }
//
//
//        override fun onDoubleTap(e: MotionEvent): Boolean {
//
//            var consumed = false
//            if (doubleTapListener != null) {
//                consumed = doubleTapListener!!.onDoubleTap(e)
//            }
//            if (state == State.NONE) {
//                val targetZoom = if (normalizedScale == minScale) maxScale else minScale
//                val doubleTap = DoubleTapZoom(targetZoom, e.x, e.y, false)
//                compatPostOnAnimation(doubleTap)
//                consumed = true
//            }
//            return consumed
//        }
//
//
//        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
//
//            return if (doubleTapListener != null) {
//                doubleTapListener!!.onDoubleTapEvent(e)
//            } else false
//        }
//    }
//
//    interface OnTouchImageViewListener {
//
//        fun onMove()
//    }
//
//    /**
//     * Responsible for all touch events. Handles the heavy lifting of drag and also sends
//     * touch events to Scale Detector and Gesture Detector.
//     *
//     * @author Ortiz
//     */
//    private inner class PrivateOnTouchListener : View.OnTouchListener {
//
//        //
//        // Remember last point position for dragging
//        //
//        private val last = PointF()
//
//
//        override fun onTouch(v: View, event: MotionEvent): Boolean {
//
//            mScaleDetector!!.onTouchEvent(event)
//            mGestureDetector!!.onTouchEvent(event)
//            val curr = PointF(event.x, event.y)
//
//            if (state == State.NONE || state == State.DRAG || state == State.FLING) {
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        last.set(curr)
//                        if (fling != null) {
//                            fling!!.cancelFling()
//                        }
//                        setState(State.DRAG)
//                    }
//
//                    MotionEvent.ACTION_MOVE -> if (state == State.DRAG) {
//                        val deltaX = curr.x - last.x
//                        val deltaY = curr.y - last.y
//                        val fixTransX = getFixDragTrans(deltaX, viewWidth.toFloat(), getImageWidth())
//                        val fixTransY = getFixDragTrans(deltaY, viewHeight.toFloat(), getImageHeight())
//                        matrix!!.postTranslate(fixTransX, fixTransY)
//                        fixTrans()
//                        last.set(curr.x, curr.y)
//                    }
//
//                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> setState(State.NONE)
//                }
//            }
//
//            setImageMatrix(matrix)
//
//            //
//            // User-defined OnTouchListener
//            //
//            if (userTouchListener != null) {
//                userTouchListener!!.onTouch(v, event)
//            }
//
//            //
//            // OnTouchImageViewListener is set: TouchImageView dragged by user.
//            //
//            if (touchImageViewListener != null) {
//                touchImageViewListener!!.onMove()
//            }
//
//            //
//            // indicate event was handled
//            //
//            return true
//        }
//    }
//
//    /**
//     * ScaleListener detects user two finger scaling and scales image.
//     *
//     * @author Ortiz
//     */
//    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//
//        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
//
//            setState(State.ZOOM)
//            return true
//        }
//
//
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//
//            scaleImage(detector.scaleFactor.toDouble(), detector.focusX, detector.focusY, true)
//
//            //
//            // OnTouchImageViewListener is set: TouchImageView pinch zoomed by user.
//            //
//            if (touchImageViewListener != null) {
//                touchImageViewListener!!.onMove()
//            }
//            return true
//        }
//
//
//        override fun onScaleEnd(detector: ScaleGestureDetector) {
//
//            super.onScaleEnd(detector)
//            setState(State.NONE)
//            var animateToZoomBoundary = false
//            var targetZoom = normalizedScale
//            if (normalizedScale > maxScale) {
//                targetZoom = maxScale
//                animateToZoomBoundary = true
//            } else if (normalizedScale < minScale) {
//                targetZoom = minScale
//                animateToZoomBoundary = true
//            }
//
//            if (animateToZoomBoundary) {
//                val doubleTap = DoubleTapZoom(targetZoom, (viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(),
//                        true)
//                compatPostOnAnimation(doubleTap)
//            }
//        }
//    }
//
//
//    private fun scaleImage(deltaScale: Double, focusX: Float, focusY: Float, stretchImageToSuper: Boolean) {
//        var deltaScale = deltaScale
//
//        val lowerScale: Float
//        val upperScale: Float
//        if (stretchImageToSuper) {
//            lowerScale = superMinScale
//            upperScale = superMaxScale
//        } else {
//            lowerScale = minScale
//            upperScale = maxScale
//        }
//
//        val origScale = normalizedScale
//        normalizedScale *= deltaScale.toFloat()
//        if (normalizedScale > upperScale) {
//            normalizedScale = upperScale
//            deltaScale = (upperScale / origScale).toDouble()
//        } else if (normalizedScale < lowerScale) {
//            normalizedScale = lowerScale
//            deltaScale = (lowerScale / origScale).toDouble()
//        }
//
//        matrix!!.postScale(deltaScale.toFloat(), deltaScale.toFloat(), focusX, focusY)
//        fixScaleTrans()
//    }
//
//
//    /**
//     * DoubleTapZoom calls a series of runnables which apply
//     * an animated zoom in/out graphic to the image.
//     *
//     * @author Ortiz
//     */
//    private inner class DoubleTapZoom internal constructor(private val targetZoom: Float, focusX: Float, focusY: Float, private val stretchImageToSuper: Boolean) : Runnable {
//
//        private val startTime: Long
//
//        private val startZoom: Float
//
//        private val bitmapX: Float
//        private val bitmapY: Float
//
//        private val interpolator = AccelerateDecelerateInterpolator()
//
//        private val startTouch: PointF
//
//        private val endTouch: PointF
//
//
//        init {
//
//            setState(State.ANIMATE_ZOOM)
//            startTime = System.currentTimeMillis()
//            this.startZoom = normalizedScale
//            val bitmapPoint = transformCoordTouchToBitmap(focusX, focusY, false)
//            this.bitmapX = bitmapPoint.x
//            this.bitmapY = bitmapPoint.y
//
//            //
//            // Used for translating image during scaling
//            //
//            startTouch = transformCoordBitmapToTouch(bitmapX, bitmapY)
//            endTouch = PointF((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat())
//        }
//
//
//        override fun run() {
//
//            val t = interpolate()
//            val deltaScale = calculateDeltaScale(t)
//            scaleImage(deltaScale, bitmapX, bitmapY, stretchImageToSuper)
//            translateImageToCenterTouchPosition(t)
//            fixScaleTrans()
//            setImageMatrix(matrix)
//
//            //
//            // OnTouchImageViewListener is set: double tap runnable updates listener
//            // with every frame.
//            //
//            if (touchImageViewListener != null) {
//                touchImageViewListener!!.onMove()
//            }
//
//            if (t < 1f) {
//                //
//                // We haven't finished zooming
//                //
//                compatPostOnAnimation(this)
//            } else {
//                //
//                // Finished zooming
//                //
//                setState(State.NONE)
//            }
//        }
//
//
//        /**
//         * Interpolate between where the image should start and end in order to translate
//         * the image so that the point that is touched is what ends up centered at the end
//         * of the zoom.
//         */
//        private fun translateImageToCenterTouchPosition(t: Float) {
//
//            val targetX = startTouch.x + t * (endTouch.x - startTouch.x)
//            val targetY = startTouch.y + t * (endTouch.y - startTouch.y)
//            val curr = transformCoordBitmapToTouch(bitmapX, bitmapY)
//            matrix!!.postTranslate(targetX - curr.x, targetY - curr.y)
//        }
//
//
//        /**
//         * Use interpolator to get t
//         */
//        private fun interpolate(): Float {
//
//            val currTime = System.currentTimeMillis()
//            var elapsed = (currTime - startTime) / ZOOM_TIME
//            elapsed = Math.min(1f, elapsed)
//            return interpolator.getInterpolation(elapsed)
//        }
//
//
//        /**
//         * Interpolate the current targeted zoom and get the delta
//         * from the current zoom.
//         */
//        private fun calculateDeltaScale(t: Float): Double {
//
//            val zoom = (startZoom + t * (targetZoom - startZoom)).toDouble()
//            return zoom / normalizedScale
//        }
//
//        private companion object {
//
//            private val ZOOM_TIME = 500f
//        }
//    }
//
//
//    /**
//     * This function will transform the coordinates in the touch event to the coordinate
//     * system of the drawable that the imageview contain
//     *
//     * @param x x-coordinate of touch event
//     * @param y y-coordinate of touch event
//     * @param clipToBitmap Touch event may occur within view, but outside image content. True, to clip
//     * return value
//     * to the bounds of the bitmap size.
//     * @return Coordinates of the point touched, in the coordinate system of the original drawable.
//     */
//    private fun transformCoordTouchToBitmap(x: Float, y: Float, clipToBitmap: Boolean): PointF {
//
//        matrix.getValues(m)
//        val origW = getDrawable().getIntrinsicWidth().toFloat()
//        val origH = getDrawable().getIntrinsicHeight().toFloat()
//        val transX = m!![Matrix.MTRANS_X]
//        val transY = m!![Matrix.MTRANS_Y]
//        var finalX = (x - transX) * origW / getImageWidth()
//        var finalY = (y - transY) * origH / getImageHeight()
//
//        if (clipToBitmap) {
//            finalX = Math.min(Math.max(finalX, 0f), origW)
//            finalY = Math.min(Math.max(finalY, 0f), origH)
//        }
//
//        return PointF(finalX, finalY)
//    }
//
//
//    /**
//     * Inverse of transformCoordTouchToBitmap. This function will transform the coordinates in the
//     * drawable's coordinate system to the view's coordinate system.
//     *
//     * @param bx x-coordinate in original bitmap coordinate system
//     * @param by y-coordinate in original bitmap coordinate system
//     * @return Coordinates of the point in the view's coordinate system.
//     */
//    private fun transformCoordBitmapToTouch(bx: Float, by: Float): PointF {
//
//        matrix!!.getValues(m)
//        val origW = getDrawable().getIntrinsicWidth().toFloat()
//        val origH = getDrawable().getIntrinsicHeight().toFloat()
//        val px = bx / origW
//        val py = by / origH
//        val finalX = m!![Matrix.MTRANS_X] + getImageWidth() * px
//        val finalY = m!![Matrix.MTRANS_Y] + getImageHeight() * py
//        return PointF(finalX, finalY)
//    }
//
//
//    /**
//     * Fling launches sequential runnables which apply
//     * the fling graphic to the image. The values for the translation
//     * are interpolated by the Scroller.
//     *
//     * @author Ortiz
//     */
//    private inner class Fling internal constructor(velocityX: Int, velocityY: Int) : Runnable {
//
//        internal var scroller: CompatScroller? = null
//
//        internal var currX: Int = 0
//        internal var currY: Int = 0
//
//
//        init {
//
//            setState(State.FLING)
//            scroller = CompatScroller(context)
//            matrix!!.getValues(m)
//
//            val startX = m!![Matrix.MTRANS_X].toInt()
//            val startY = m!![Matrix.MTRANS_Y].toInt()
//            val minX: Int
//            val maxX: Int
//            val minY: Int
//            val maxY: Int
//
//            if (getImageWidth() > viewWidth) {
//                minX = viewWidth - getImageWidth().toInt()
//                maxX = 0
//            } else {
//                maxX = startX
//                minX = maxX
//            }
//
//            if (getImageHeight() > viewHeight) {
//                minY = viewHeight - getImageHeight().toInt()
//                maxY = 0
//            } else {
//                maxY = startY
//                minY = maxY
//            }
//
//            scroller!!.fling(startX, startY, velocityX.toInt(), velocityY.toInt(), minX,
//                    maxX, minY, maxY)
//            currX = startX
//            currY = startY
//        }
//
//
//        fun cancelFling() {
//
//            if (scroller != null) {
//                setState(State.NONE)
//                scroller!!.forceFinished(true)
//            }
//        }
//
//
//        override fun run() {
//
//            //
//            // OnTouchImageViewListener is set: TouchImageView listener has been flung by user.
//            // Listener runnable updated with each frame of fling animation.
//            //
//            if (touchImageViewListener != null) {
//                touchImageViewListener!!.onMove()
//            }
//
//            if (scroller!!.isFinished) {
//                scroller = null
//                return
//            }
//
//            if (scroller!!.computeScrollOffset()) {
//                val newX = scroller!!.currX
//                val newY = scroller!!.currY
//                val transX = newX - currX
//                val transY = newY - currY
//                currX = newX
//                currY = newY
//                matrix!!.postTranslate(transX.toFloat(), transY.toFloat())
//                fixTrans()
//                setImageMatrix(matrix)
//                compatPostOnAnimation(this)
//            }
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
//    private inner class CompatScroller(context: Context) {
//
//        internal lateinit var scroller: Scroller
//
//        internal lateinit var overScroller: OverScroller
//
//        internal var isPreGingerbread: Boolean = false
//
//
//        val isFinished: Boolean
//            get() = if (isPreGingerbread) {
//                scroller.isFinished
//            } else {
//                overScroller.isFinished
//            }
//
//
//        val currX: Int
//            get() = if (isPreGingerbread) {
//                scroller.currX
//            } else {
//                overScroller.currX
//            }
//
//
//        val currY: Int
//            get() = if (isPreGingerbread) {
//                scroller.currY
//            } else {
//                overScroller.currY
//            }
//
//
//        init {
//
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
//                isPreGingerbread = true
//                scroller = Scroller(context)
//            } else {
//                isPreGingerbread = false
//                overScroller = OverScroller(context)
//            }
//        }
//
//
//        fun fling(startX: Int, startY: Int, velocityX: Int, velocityY: Int, minX: Int, maxX: Int, minY: Int, maxY: Int) {
//
//            if (isPreGingerbread) {
//                scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY)
//            } else {
//                overScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY)
//            }
//        }
//
//
//        fun forceFinished(finished: Boolean) {
//
//            if (isPreGingerbread) {
//                scroller.forceFinished(finished)
//            } else {
//                overScroller.forceFinished(finished)
//            }
//        }
//
//
//        fun computeScrollOffset(): Boolean {
//
//            if (isPreGingerbread) {
//                return scroller.computeScrollOffset()
//            } else {
//                overScroller.computeScrollOffset()
//                return overScroller.computeScrollOffset()
//            }
//        }
//    }
//
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    private fun compatPostOnAnimation(runnable: Runnable) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            postOnAnimation(runnable)
//        } else {
//            postDelayed(runnable, (1000 / 60).toLong())
//        }
//    }
//
//
//    private inner class ZoomVariables(var scale: Float, var focusX: Float, var focusY: Float, var scaleType: ImageView.ScaleType)
//
//
//    private fun printMatrixInfo() {
//
//        val n = FloatArray(9)
//        matrix!!.getValues(n)
//        Log.d(DEBUG, "Scale: " + n[Matrix.MSCALE_X] + " TransX: " + n[Matrix.MTRANS_X] + " TransY: " +
//                n[Matrix.MTRANS_Y])
//    }
//}