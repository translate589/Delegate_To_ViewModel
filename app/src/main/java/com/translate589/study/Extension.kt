package com.translate589.study

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.Serializable

internal fun <T> Fragment.collectLatestWithLaunch(
    stateFlow: StateFlow<T>,
    onAction: suspend (value: T) -> Unit
) = lifecycleScope
    .launch {
        stateFlow
            .flowWithLifecycle(lifecycle)
            .collectLatest(onAction)
    }

internal fun <T> Fragment.collectWithLaunch(
    stateFlow: StateFlow<T>,
    onAction: suspend (value: T) -> Unit
) = lifecycleScope
    .launch {
        stateFlow
            .flowWithLifecycle(lifecycle)
            .collect(onAction)
    }

internal fun <T> Fragment.collectLatestWithLaunch(
    sharedFlow: SharedFlow<T>,
    onAction: suspend (value: T) -> Unit
) = lifecycleScope
    .launch {
        sharedFlow
            .flowWithLifecycle(lifecycle)
            .collectLatest(onAction)
    }

internal fun <T> Fragment.collectWithLaunch(
    sharedFlow: SharedFlow<T>,
    onAction: suspend (value: T) -> Unit
) = lifecycleScope
    .launch {
        sharedFlow
            .flowWithLifecycle(lifecycle)
            .collect(onAction)
    }

internal inline val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

internal inline val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )

internal fun Fragment.sendDataToBackStack(
    block: (SavedStateHandle) -> Unit
) {
    findNavController()
        .previousBackStackEntry
        ?.run {
            block(savedStateHandle)
        }
}

infix fun View.bindOnClick(event: () -> Unit) {
    setOnClickListener {
        event()
    }
}

inline fun <reified T : Serializable> Bundle.getBottomSheetParam(key: String? = null): T {
    val tempKey = key ?: T::class.simpleName
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        getSerializable(tempKey)
    } else {
        getSerializable(tempKey, T::class.java)
    } as T
}

internal fun <T : Serializable> Bundle.putBottomSheetParam(param: T, key: String? = null): Bundle {
    putSerializable(key ?: param::class.java.simpleName, param)
    return this
}

internal fun Int.isEven() = rem(2) == 0
internal fun Int.isOdd() = isEven().not()
internal fun Float.isEven() = toInt().isEven()
internal fun Float.isOdd() = toInt().isOdd()