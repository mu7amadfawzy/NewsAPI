package com.tempo.news.ui.custom_views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.tempo.news.R

class CorneredImageView : ShapeableImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(
        context, attrs, defStyleAttr
    )

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        shapeAppearanceModel = shapeAppearanceModel
            .toBuilder()
            .setAllCorners(
                CornerFamily.ROUNDED,
                resources.getDimension(R.dimen.default_corner_radius)
            )
            .build()
    }

}
