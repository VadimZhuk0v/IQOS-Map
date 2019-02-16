package com.vadim.iqosmap.ui.filter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseFragment
import com.vadim.iqosmap.databinding.FragmentFilterBinding
import com.vadim.iqosmap.utils.CategoryEnum
import com.vadim.iqosmap.utils.extentions.cheangeImageAnimated
import com.vadim.iqosmap.utils.ui.UiUtils

private const val FRAGMENT_LAYOUT_ID = R.layout.fragment_filter

class FilterFragment : BaseFragment<FilterViewModel, FragmentFilterBinding>() {

    companion object {
        const val TAG = "FilterFragment"
        fun newInstance() = FilterFragment()
    }

    override val layoutId = FRAGMENT_LAYOUT_ID

    override fun createViewModel() = provideViewModel { FilterViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val colorTransparent = ContextCompat.getColor(view!!.context, android.R.color.transparent)
        UiUtils.registerCircularRevealAnimation(view.context, view, colorTransparent, colorTransparent)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeFilters()
    }

    private fun observeFilters() {
        viewModel.ldCategories.observe(this, Observer {
            reverseImage(binding.ivIconSticks, CategoryEnum.STICKS, it)
            reverseImage(binding.ivIconDevices, CategoryEnum.DEVICES, it)
            reverseImage(binding.ivIconAccessories, CategoryEnum.ACCESSORIES, it)
            reverseImage(binding.ivIconService, CategoryEnum.SERVICE, it)
            reverseImage(binding.ivIconFriendly, CategoryEnum.FRIENDLY, it)
        })
    }

    private fun reverseImage(
        imageView: ImageView,
        categoryEnum: CategoryEnum,
        enabledCategory: Set<CategoryEnum>,
        withAnimation: Boolean = false
    ) {
        if (enabledCategory.contains(categoryEnum)) {
            imageView.cheangeImageAnimated(categoryEnum.icon, withAnimation)
        } else {
            imageView.cheangeImageAnimated(categoryEnum.iconPale, withAnimation)
        }
    }

    private fun setListeners() {
        binding.llSticks.setOnClickListener { reverseCategory(CategoryEnum.STICKS, binding.ivIconSticks) }
        binding.llDevices.setOnClickListener { reverseCategory(CategoryEnum.DEVICES, binding.ivIconDevices) }
        binding.llAccessories.setOnClickListener {
            reverseCategory(
                CategoryEnum.ACCESSORIES,
                binding.ivIconAccessories
            )
        }
        binding.llService.setOnClickListener { reverseCategory(CategoryEnum.SERVICE, binding.ivIconService) }
        binding.llFriendly.setOnClickListener { reverseCategory(CategoryEnum.FRIENDLY, binding.ivIconFriendly) }

        binding.flApply.setOnClickListener {
            viewModel.enableFilters()
            (parentFragment as? IFilterCallBack)?.hideFilterFragment()
        }

        binding.flCancel.setOnClickListener { (parentFragment as? IFilterCallBack)?.hideFilterFragment() }
    }

    private fun reverseCategory(categoryEnum: CategoryEnum, imageView: ImageView) {
        val categories = viewModel.ldCategories.value!!
        if (categories.contains(categoryEnum)) {
            viewModel.ldCategories.value!!.remove(categoryEnum)
        } else {
            viewModel.ldCategories.value!!.add(categoryEnum)
        }
        reverseImage(imageView, categoryEnum, viewModel.ldCategories.value!!, true)
    }

}
