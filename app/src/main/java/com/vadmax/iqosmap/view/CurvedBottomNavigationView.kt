package com.vadmax.iqosmap.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.vadmax.iqosmap.R


class CurvedBottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val topCurvedEdgeTreatment: BottomAppBarTopEdgeTreatment
    val materialShapeDrawable: MaterialShapeDrawable
    var fabSize = 0F
    var fabCradleMargin = 0F
    var fabCradleRoundedCornerRadius = 0F
    var cradleVerticalOffset = 0F

    init {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.CurvedBottomNavigationView, 0, 0)
        fabSize = ta.getDimension(R.styleable.CurvedBottomNavigationView_fab_size, 0F)
        fabCradleMargin = ta.getDimension(R.styleable.CurvedBottomNavigationView_fab_cradle_margin, 0F)
        fabCradleRoundedCornerRadius = ta.getDimension(R.styleable.CurvedBottomNavigationView_fab_cradle_rounded_corner_radius, 0F)
        cradleVerticalOffset = ta.getDimension(R.styleable.CurvedBottomNavigationView_cradle_vertical_offset, 0F)


        //TODO: replace with own class
        topCurvedEdgeTreatment =
            BottomAppBarTopEdgeTreatment(fabCradleMargin, fabCradleRoundedCornerRadius, cradleVerticalOffset).apply {
                fabDiameter = fabSize
            }

        val shapeAppearanceModel = ShapeAppearanceModel().apply {
            topEdge = topCurvedEdgeTreatment
        }

        materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            setTint(Color.WHITE)
            this.elevation = this@CurvedBottomNavigationView.elevation
            paintStyle = Paint.Style.FILL_AND_STROKE
        }

        background = materialShapeDrawable
    }

    fun transform(fab: FloatingActionButton) {
        if (fab.isVisible) {
            fab.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    ValueAnimator.ofFloat(materialShapeDrawable.interpolation, 0F).apply {
                        addUpdateListener { materialShapeDrawable.interpolation = it.animatedValue as Float }
                        start()
                    }
                }
            })
        } else {
            ValueAnimator.ofFloat(materialShapeDrawable.interpolation, 1F).apply {
                addUpdateListener { materialShapeDrawable.interpolation = it.animatedValue as Float }
                doOnEnd { fab.show() }
                start()
            }
        }
    }

}