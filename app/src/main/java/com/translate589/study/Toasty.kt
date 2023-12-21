package com.translate589.study

import android.content.Context
import android.widget.Toast

class Toasty(
    private val context: Context
) {
    private var toast: Toast? = null

    private fun internalShowToast(
        msg: String,
        duration: Int
    ) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, duration).apply {
            show()
        }
    }

    fun showToast(resId:Int, duration:Int = Toast.LENGTH_SHORT) = internalShowToast(context.getString(resId),duration)
    fun showToast(msg: String, duration:Int = Toast.LENGTH_SHORT) = internalShowToast(msg,duration)
}