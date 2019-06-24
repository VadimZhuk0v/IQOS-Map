package com.vadmax.iqosmap.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.vadmax.iqosmap.R

abstract class BaseDialogFragment<VM : BaseViewModel<*>, B : ViewDataBinding> : DialogFragment() {

    abstract val layoutId: Int

    protected abstract val viewModel: VM
    protected lateinit var binding: B

    open val dialogWith: Int? = null
    open val dialogIsCancelable = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        lifecycle.addObserver(viewModel)
        binding.lifecycleOwner = this
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        isCancelable = dialogIsCancelable

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupDialogStyle()
    }

    open fun setupDialogStyle() {
        val context = context ?: return

        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_rounded_dialog))

        dialogWith?.let {
            dialog?.window?.setLayout(it, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

       // dialog?.window?.setWindowAnimations(R.style.AppTheme_Dialog)
    }

}