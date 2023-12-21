package com.translate589.study.model.param

import android.widget.Toast
import androidx.annotation.StringRes
import java.io.Serializable

interface ToastParam

data class ToastParamWithString(
    val msg: String,
    val duration: Int = Toast.LENGTH_SHORT
) : Serializable, ToastParam

data class ToastParamWithResource(
    @StringRes val msgResId: Int,
    val duration: Int = Toast.LENGTH_SHORT
) : Serializable, ToastParam
