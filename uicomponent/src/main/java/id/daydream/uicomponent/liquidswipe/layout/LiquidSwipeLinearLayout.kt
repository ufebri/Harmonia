package id.daydream.uicomponent.liquidswipe.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import id.daydream.uicomponent.liquidswipe.base.ClipPathProvider
import id.daydream.uicomponent.liquidswipe.base.LiquidSwipeLayout
import id.daydream.uicomponent.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider

/**
 * `LiquidSwipeLinearLayout` is a custom [LinearLayout] that implements [LiquidSwipeLayout].
 */
open class LiquidSwipeLinearLayout : LinearLayout, LiquidSwipeLayout {
    // Store path in local variable rather then getting it from ClipPathProvider each time
    private var path: Path? = null

    // Backing fields for LiquidSwipeLayout variables
    private var _clipPathProvider: ClipPathProvider = LiquidSwipeClipPathProvider()
    private var _currentRevealPercent: Float = 100f

    override var clipPathProvider = _clipPathProvider
    override var currentRevealPercent: Float
        get() = _currentRevealPercent
        set(value) {
            revealForPercentage(value)
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    /**
     * Overriden from View
     */
    override fun draw(canvas: Canvas) {
        try {
            canvas.save()
            path?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Gunakan metode baru
                    canvas.clipPath(it)
                } else {
                    // Gunakan metode lama (deprecated)
                    @Suppress("DEPRECATION")
                    canvas.clipPath(it, clipPathProvider.op)
                }
            }
            super.draw(canvas)
        } finally {
            canvas.restore()
        }
    }

    override fun revealForPercentage(percent: Float) {
        if (percent == _currentRevealPercent) return
        _currentRevealPercent = percent
        path = clipPathProvider.getPath(percent, this@LiquidSwipeLinearLayout)
        invalidate()
    }
}