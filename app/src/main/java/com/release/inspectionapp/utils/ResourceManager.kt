package com.release.inspectionapp.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

open class ResourceManager @Inject constructor(
    private val context: Context
) {

    fun getString(@StringRes string: Int): String {
        return when (string != 0) {
            true -> context.getString(string)
            else -> ""
        }
    }
}
