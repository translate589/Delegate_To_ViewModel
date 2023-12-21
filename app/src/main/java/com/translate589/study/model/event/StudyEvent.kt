package com.translate589.study.model.event

import com.translate589.study.model.param.InputBottomSheetParam
import com.translate589.study.model.param.ToastParam

internal sealed interface StudyEvent {
    sealed interface ViewModelToViewEvent : StudyEvent {
        // region [Region] VM -> V Event
        data class ShowInputBottomSheet(
            val param: InputBottomSheetParam
        ) : ViewModelToViewEvent
        data class ShowToast(
            val param: ToastParam
        ) : ViewModelToViewEvent
        // endregion
    }

    sealed interface ViewToViewModelEvent : StudyEvent {
        // region [Region] V -> VM Event
        object PressedWeightInputArea : ViewToViewModelEvent
        object PressedHeightInputArea : ViewToViewModelEvent
        // endregion
    }
}