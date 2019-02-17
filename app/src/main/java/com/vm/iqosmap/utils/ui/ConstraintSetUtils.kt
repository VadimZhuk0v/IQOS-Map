package com.vm.iqosmap.utils.ui

import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintSet
import com.vm.iqosmap.R

object ConstraintSetUtils {

    fun centerView(
        @IdRes viewId: Int, cs: ConstraintSet,
        width: Int = ConstraintSet.WRAP_CONTENT,
        height: Int = ConstraintSet.WRAP_CONTENT
    ) {
        cs.clear(viewId)
        cs.connect(viewId, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        cs.connect(viewId, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        cs.connect(viewId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        cs.connect(viewId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

        cs.constrainWidth(viewId, width)
        cs.constrainHeight(viewId, height)
    }

    fun bottomLeftView(
        @IdRes viewId: Int, cs: ConstraintSet,
        marginLeft: Int,
        marginBottom: Int,
        width: Int = ConstraintSet.WRAP_CONTENT,
        height: Int = ConstraintSet.WRAP_CONTENT
    ) {

        cs.clear(viewId)
        cs.connect(viewId, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, marginLeft)
        cs.connect(viewId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, marginBottom)

        cs.constrainWidth(R.id.fabFilter, width)
        cs.constrainHeight(R.id.fabFilter, height)
    }

}