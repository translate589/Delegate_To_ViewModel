package com.translate589.study.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewModelToViewEvent
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewModelToViewEvent.ApplyAndDismissSheet
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent.PressedApplyFromBottom
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent.UpdateConditionRange
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent.UpdateInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InputBottomSheetViewModel @Inject constructor(
):ViewModel() {
    private val _eventFlow = MutableSharedFlow<ViewModelToViewEvent>()
    val eventFlow = _eventFlow
        .shareIn(
            viewModelScope,
            SharingStarted.Eagerly
        )

    private val _conditionRange = MutableSharedFlow<ClosedFloatingPointRange<Float>>()

    private val _inputValue = MutableStateFlow(0f)
    private val _isInRange = combine(
        _inputValue,
        _conditionRange,
    ) { input, range ->
        input in range
    }

    private val _inputRegexError = MutableStateFlow(false)
    val isInputError = combine(
        _isInRange,
        _inputRegexError
    ) { condition1, condition2 ->
        condition1 and condition2.not()
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        true
    )

    fun performEvent(event: ViewToViewModelEvent) {
        when(event) {
            PressedApplyFromBottom  -> performPressedApplyFromBottom()
            is UpdateInput          -> performUpdateInput(event)
            is UpdateConditionRange -> performUpdateParam(event)
        }
    }

    private fun performPressedApplyFromBottom() {
        ApplyAndDismissSheet(_inputValue.value).fire()
    }

    private fun performUpdateInput(event: UpdateInput) {
        val regex = Regex("^[0-9]+(.[0-9]+)?\$")
        regex.matchEntire(event.inputString)?.run {
            _inputRegexError.value = false
            _inputValue.value = value.toFloat()
        } ?: run {
            _inputRegexError.value = true
        }
    }

    private fun performUpdateParam(event:UpdateConditionRange) {
        viewModelScope.launch {
            with(event) {
                _conditionRange.emit(start..end)
            }
        }
    }

    private fun ViewModelToViewEvent.fire() = viewModelScope.launch {
        _eventFlow.emit(this@fire)
    }
}