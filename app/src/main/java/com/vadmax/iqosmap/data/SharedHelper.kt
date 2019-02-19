package com.vadmax.iqosmap.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.vadmax.iqosmap.utils.CategoryEnum
import com.vadmax.iqosmap.utils.SharedPreferenceLiveData
import com.vadmax.iqosmap.utils.extentions.putBoolean
import com.vadmax.iqosmap.utils.extentions.putStringSet

private const val SHARED_NAME = "iqos_map_shared"

private const val FILTER_STICKS_IS_ENABLE = "FILTER_STICKS_IS_ENABLE"
private const val FILTER_DEVICES_IS_ENABLE = "FILTER_DEVICES_IS_ENABLE"
private const val FILTER_ACCESSORIES_IS_ENABLE = "FILTER_ACCESSORIES_IS_ENABLE"
private const val FILTER_SERVICE_IS_ENABLE = "FILTER_SERVICE_IS_ENABLE"
private const val FILTER_FRIENDLY_IS_ENABLE = "FILTER_FRIENDLY_IS_ENABLE"
private const val ENABLED_FILTERS = "ENABLED_FILTERS"

class SharedHelper(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    private val sharedLiveData = SharedPreferenceSetIntLiveData(sharedPreferences, ENABLED_FILTERS, emptySet())

    var filterSticksIsEnable: Boolean
        get() = sharedPreferences.getBoolean(FILTER_STICKS_IS_ENABLE, false)
        set(value) {
            sharedPreferences.putBoolean(FILTER_STICKS_IS_ENABLE, value)
            enabledFilters = if (value) enabledFilters.apply { remove(CategoryEnum.STICKS.id) }
            else enabledFilters.apply { add(CategoryEnum.STICKS.id) }
        }

    var filterDevicesIsEnable: Boolean
        get() = sharedPreferences.getBoolean(FILTER_DEVICES_IS_ENABLE, false)
        set(value) {
            sharedPreferences.putBoolean(FILTER_DEVICES_IS_ENABLE, value)
            enabledFilters = if (value) enabledFilters.apply { remove(CategoryEnum.DEVICES.id) }
            else enabledFilters.apply { add(CategoryEnum.DEVICES.id) }
        }

    var filterAccessoriesIsEnable: Boolean
        get() = sharedPreferences.getBoolean(FILTER_ACCESSORIES_IS_ENABLE, false)
        set(value) {
            sharedPreferences.putBoolean(FILTER_ACCESSORIES_IS_ENABLE, value)
            enabledFilters = if (value) enabledFilters.apply { remove(CategoryEnum.ACCESSORIES.id) }
            else enabledFilters.apply { add(CategoryEnum.ACCESSORIES.id) }
        }

    var filterServiceIsEnable: Boolean
        get() = sharedPreferences.getBoolean(FILTER_FRIENDLY_IS_ENABLE, false)
        set(value) {
            sharedPreferences.putBoolean(FILTER_SERVICE_IS_ENABLE, value)
            enabledFilters = if (value) enabledFilters.apply { remove(CategoryEnum.SERVICE.id) }
            else enabledFilters.apply { add(CategoryEnum.SERVICE.id) }
        }

    var filterFriendlyIsEnable: Boolean
        get() = sharedPreferences.getBoolean(FILTER_FRIENDLY_IS_ENABLE, false)
        set(value) {
            sharedPreferences.putBoolean(FILTER_STICKS_IS_ENABLE, value)
            enabledFilters = if (value) enabledFilters.apply { remove(CategoryEnum.FRIENDLY.id) }
            else enabledFilters.apply { add(CategoryEnum.FRIENDLY.id) }
        }

    var enabledFilters: MutableSet<Int>
        get() = sharedPreferences.getStringSet(
            ENABLED_FILTERS,
            setOf("1", "2", "3", "4", "5")
        )!!.map { it.toInt() }.toMutableSet()
        set(value) = sharedPreferences.putStringSet(ENABLED_FILTERS, value.map { it.toString() }.toSet())

    val enabledFiltersLiveData: LiveData<Set<Int>>
        get() = sharedLiveData


    private inner class SharedPreferenceSetIntLiveData(
        sharedPrefs: SharedPreferences,
        key: String,
        defValue: Set<Int>
    ) :
        SharedPreferenceLiveData<Set<Int>>(sharedPrefs, key, defValue) {
        override fun getValueFromPreferences(key: String, defValue: Set<Int>): Set<Int> = enabledFilters
    }
}


