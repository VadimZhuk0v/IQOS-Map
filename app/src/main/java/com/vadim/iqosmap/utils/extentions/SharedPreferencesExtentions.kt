package com.vadim.iqosmap.utils.extentions

import android.content.SharedPreferences

fun SharedPreferences.putBoolean(key: String, value: Boolean){
    this.edit()
        .putBoolean(key, value)
        .apply()
}

fun SharedPreferences.putStringSet(key: String, value: Set<String>){
    this.edit()
        .putStringSet(key, value)
        .apply()
}