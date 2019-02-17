package com.vm.iqosmap.utils

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vm.iqosmap.R

enum class CategoryEnum(
    val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconPale: Int,
    @DrawableRes val iconMap: Int,
    @ColorRes val color: Int,
    @DrawableRes val iconCluster: Int
) {

    BASE(-1, 0, 0, 0, 0, R.drawable.pin_generic, R.color.category_base, R.drawable.pin_cluster_base),

    STICKS(
              1,
              R.string.filter_sticks_title,
              R.string.filter_accessories_descriptions,
              R.drawable.icon_sticks,
              R.drawable.icon_sticks_pale,
              R.drawable.pin_sticks,
              R.color.category_sticks,
              R.drawable.pin_cluster_sticks
          ),

    DEVICES(
               2,
               R.string.filter_devices_title,
               R.string.filter_devices_descriptions,
               R.drawable.icon_devices,
               R.drawable.icon_devices_pale,
               R.drawable.pin_devices,
               R.color.category_devices,
               R.drawable.pin_cluster_devices
           ),

    ACCESSORIES(
                   3,
                   R.string.filter_accessories_title,
                   R.string.filter_accessories_descriptions,
                   R.drawable.icon_accessories,
                   R.drawable.icon_accessories_pale,
                   R.drawable.pin_accessories,
                   R.color.category_accessories,
                   R.drawable.pin_cluster_accessories
               ),

    SERVICE(
               4,
               R.string.filter_service_title,
               R.string.filter_service_descriptions,
               R.drawable.icon_service,
               R.drawable.icon_service_pale,
               R.drawable.pin_service,
               R.color.category_service,
               R.drawable.pin_cluster_service
           ),
    FRIENDLY(
                5,
                R.string.filter_friendly_title,
                R.string.filter_friendly_descriptions,
                R.drawable.icon_friendly,
                R.drawable.icon_friendly_pale,
                R.drawable.pin_friendly,
                R.color.category_sticks,
                R.drawable.pin_cluster_friendly
            );

    companion object {
        fun instanceById(id: Int): CategoryEnum {
            return when (id) {
                1 -> STICKS
                2 -> DEVICES
                3 -> ACCESSORIES
                4 -> SERVICE
                5 -> FRIENDLY
                else -> BASE
            }
        }
    }

}