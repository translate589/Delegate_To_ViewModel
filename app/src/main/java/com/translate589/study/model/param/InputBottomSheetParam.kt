package com.translate589.study.model.param

import androidx.annotation.StringRes
import java.io.Serializable

data class InputBottomSheetParam(
    @StringRes val titleResId: Int,
    @StringRes val subTitleResId: Int,
    @StringRes val unitResId: Int,
    val minCondition: Float,
    val maxCondition: Float,
    inline val onConfirm: (Float) -> Unit
) : Serializable {
    constructor(
        titleResId: Int,
        subTitleResId: Int,
        unitResId: Int,
        minCondition: Int,
        maxCondition: Int,
        onConfirm: (Float) -> Unit
    ): this(
        titleResId,
        subTitleResId,
        unitResId,
        minCondition.toFloat(),
        maxCondition.toFloat(),
        onConfirm,
    )
}