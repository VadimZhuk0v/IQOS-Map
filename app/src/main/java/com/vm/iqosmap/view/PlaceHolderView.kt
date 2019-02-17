package com.vm.iqosmap.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import com.vm.iqosmap.R


class PlaceHolderView : FrameLayout {

    private lateinit var tvError: TextView
    private lateinit var tvRepeat: TextView
    private lateinit var ivPlaceHolder: ImageView
    private lateinit var lavProgress: LottieAnimationView
    private lateinit var llErrorContainer: LinearLayout

    private var dataContainer: View? = null

    var state: HolderState = HolderState.Hide
        private set(value) {
            field = value
        }

    private var repeatEvent: () -> Unit = {}
        set(value) {
            field = value
            if (::tvRepeat.isInitialized) {
                tvRepeat.setOnClickListener { value() }
            }
        }


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init()
    }

    fun setDataContainer(dataContainer: View) {
        this.dataContainer = dataContainer
        init()
        when (state) {
            is HolderState.Hide -> {}
            is HolderState.Progress -> showProgress()
            is HolderState.Error -> showError(
                (state as HolderState.Error).message,
                (state as HolderState.Error).imgRes,
                (state as HolderState.Error).needRepeat
            )
        }
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_placeholder_view, this, false)

        llErrorContainer = view.findViewById(R.id.layout_placeholder_view_ll_error_container)
        tvError = view.findViewById(R.id.layout_placeholder_view_tv_error)
        ivPlaceHolder = view.findViewById(R.id.layout_placeholder_view_iv_placeholder)
        lavProgress = view.findViewById(R.id.progress)
        tvRepeat = view.findViewById(R.id.layout_placeholder_view_tv_repeat)
        lavProgress.visibility = View.GONE

        tvRepeat.setOnClickListener { repeatEvent() }

        lavProgress.playAnimation()

        this.addView(view)
    }

    fun showProgress() {
        this.visibility = View.VISIBLE
        dataContainer?.visibility = View.INVISIBLE
        llErrorContainer.visibility = View.GONE
        lavProgress.progress = 0F
        lavProgress.visibility = View.VISIBLE
        tvRepeat.visibility = View.GONE

        state = HolderState.Progress
    }

    fun hideProgress() {
        dataContainer?.visibility = View.VISIBLE
        llErrorContainer.visibility = View.GONE
        lavProgress.visibility = View.GONE
        tvRepeat.visibility = View.GONE

        state = HolderState.Hide
        this.visibility = View.GONE
    }

    fun setOnRepeatClickListener(event: () -> Unit) {
        repeatEvent = event
    }

    fun showError(error: String, imageId: Int, repeatVisible: Boolean = false) {
        this.visibility = View.VISIBLE
        dataContainer?.visibility = View.GONE
        lavProgress.visibility = View.GONE

        llErrorContainer.visibility = View.VISIBLE
        tvError.text = error
        ivPlaceHolder.setImageResource(imageId)

        if (repeatVisible)
            tvRepeat.visibility = View.VISIBLE
        else
            tvRepeat.visibility = View.GONE

        state = HolderState.Error(error, imageId, repeatVisible)
    }

    fun hideError() {
        this.visibility = View.GONE
        dataContainer?.visibility = View.VISIBLE
        llErrorContainer.visibility = View.GONE
        lavProgress.visibility = View.GONE
        tvRepeat.visibility = View.GONE

        state = HolderState.Hide
    }

    sealed class HolderState {
        object Progress : HolderState()
        object Hide : HolderState()
        data class Error(val message: String, @DrawableRes val imgRes: Int = 0, val needRepeat: Boolean) : HolderState()
    }

}
