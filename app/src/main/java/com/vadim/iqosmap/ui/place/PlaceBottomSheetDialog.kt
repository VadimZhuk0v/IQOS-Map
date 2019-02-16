package com.vadim.iqosmap.ui.place

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.vadim.iqosmap.BuildConfig
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseBottomSheetDialog
import com.vadim.iqosmap.databinding.BottomSheetDialogPlaceBinding
import com.vadim.iqosmap.utils.CategoryEnum
import java.util.Locale


private const val DIALOG_LAYOUT_ID = R.layout.bottom_sheet_dialog_place

private const val ARG_PLACE_ID = "place_id"

class PlaceBottomSheetDialog : BaseBottomSheetDialog<PlaceViewModel, BottomSheetDialogPlaceBinding>() {

    companion object {
        fun newInstance(placeId: Long) = PlaceBottomSheetDialog().apply {
            val args = Bundle()
            args.putLong(ARG_PLACE_ID, placeId)
            this.arguments = args
        }
    }

    override val layoutId = DIALOG_LAYOUT_ID

    private val placeId by lazy { arguments!!.getLong(ARG_PLACE_ID) }

    override fun createViewModel() = provideViewModel { PlaceViewModel(it, placeId) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeClose()
        observePlace()
        startProgress()
    }

    private fun setListeners(phone: String, latLng: LatLng, address: String) {
        binding.ivPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            startActivity(intent)
        }
        binding.ivGoogleMap.setOnClickListener {
            val q = "${latLng.latitude.toFloat()},${latLng.longitude.toFloat()}($address)"
            val uri = String.format(
                Locale.ROOT,
                "geo:0,0?q=%s",
                q
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    private fun observeClose() {
        viewModel.ldClose.observe(this, Observer {
            if (it) dismiss()
        })
    }

    private fun observePlace() {
        viewModel.ldPlace.observe(this, Observer {
            setListeners(it.phone, LatLng(it.lat.toDouble(), it.lon.toDouble()), it.name)
            setImage(it.imageUrl)
            setCategories(it.categories)
            setPhone(it.phone)
            binding.tvName.text = it.name
            binding.tvSchedulers.text = getString(R.string.schedulers_format, it.schedule)
            binding.tvAddress.text = getString(R.string.address_format, it.address)
            viewModel.hideProgresMaybe()
        })
    }

    private fun setImage(image: String) {
        Glide.with(binding.ivPlace)
            .load(image)
            .into(binding.ivPlace)
    }

    private fun setCategories(categories: List<CategoryEnum>) {
        val size = resources.getDimensionPixelSize(R.dimen.place_layer_size)
        categories.forEach {
            val frame = FrameLayout(context)
            frame.layoutParams = LinearLayout.LayoutParams(0, size).apply { weight = 1F }

            val imageView = ImageView(context)
            imageView.layoutParams = FrameLayout.LayoutParams(size, size).apply { gravity = Gravity.CENTER }

            frame.addView(imageView)
            binding.llCategories.addView(frame)

            Glide.with(imageView)
                .load(it.icon)
                .into(imageView)
        }
    }

    private fun setPhone(phone: String) {
        if (phone.isEmpty()) {
            binding.clPhone.visibility = View.GONE
        } else {
            binding.tvPhone.text = getString(R.string.phone_format, phone)
        }
    }

    private fun startProgress() {
        Handler().postDelayed({
                                  viewModel.isHolderTimeFinish = true
                                  viewModel.hideProgresMaybe()
                              }, BuildConfig.HOLDER_LOADING_TIME)
    }
}