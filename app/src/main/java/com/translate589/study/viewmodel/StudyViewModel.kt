package com.translate589.study.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.translate589.study.R
import com.translate589.study.model.event.StudyEvent.ViewModelToViewEvent
import com.translate589.study.model.event.StudyEvent.ViewModelToViewEvent.ShowInputBottomSheet
import com.translate589.study.model.event.StudyEvent.ViewModelToViewEvent.ShowToast
import com.translate589.study.model.event.StudyEvent.ViewToViewModelEvent
import com.translate589.study.model.event.StudyEvent.ViewToViewModelEvent.PressedHeightInputArea
import com.translate589.study.model.event.StudyEvent.ViewToViewModelEvent.PressedWeightInputArea
import com.translate589.study.model.param.InputBottomSheetParam
import com.translate589.study.model.param.ToastParamWithString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class StudyViewModel @Inject constructor(
): ViewModel() {
    private val _commonEvent = MutableSharedFlow<ViewModelToViewEvent>()
    val commonEvent = _commonEvent
        .shareIn(
            viewModelScope,
            SharingStarted.Eagerly
        )

    private val _heightValue = MutableStateFlow(0f)
    val heightValue = _heightValue.asStateFlow()

    private val _weightValue = MutableStateFlow(0f)
    val weightValue = _weightValue.asStateFlow()

    fun performEvent(event: ViewToViewModelEvent) {
        Timber.w("Translate589 performEvent ${event::class.simpleName}")
        when (event) {
            PressedWeightInputArea -> performPressedWeightInputArea()
            PressedHeightInputArea -> performPressedHeightInputArea()
        }
    }

    private fun performPressedWeightInputArea() {
        val param = InputBottomSheetParam(
            titleResId= R.string.bottom_sheet_title_input_type_2,
            subTitleResId= R.string.unit_available_input_range,
            unitResId = R.string.label_unit_weight,
            minCondition= 30,
            maxCondition= 300,
        ) {
            internalSaveWeightInput(it)
        }
        ShowInputBottomSheet(param).fire()
    }

    private fun performPressedHeightInputArea() {
        val param = InputBottomSheetParam(
            titleResId= R.string.bottom_sheet_title_input_type_1,
            subTitleResId= R.string.unit_available_input_range,
            unitResId = R.string.label_unit_height,
            minCondition= 0,
            maxCondition= 300,
        ) {
            internalSaveHeightInput(it)
        }
        ShowInputBottomSheet(param).fire()
    }

    private fun internalSaveHeightInput(height: Float) {
        _heightValue.value = height
        ShowToast(
            ToastParamWithString("internalSaveHeightInput -> $height")
        ).fire()
    }

    private fun internalSaveWeightInput(weight: Float) {
        _weightValue.value = weight
        ShowToast(
            ToastParamWithString("internalSaveHeightInput -> $weight")
        ).fire()
    }

    private fun ViewModelToViewEvent.fire() = viewModelScope.launch {
        _commonEvent.emit(this@fire)
    }
}