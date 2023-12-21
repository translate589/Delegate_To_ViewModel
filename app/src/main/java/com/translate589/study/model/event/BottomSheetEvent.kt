package com.translate589.study.model.event

internal sealed interface BottomSheetEvent {
    sealed interface InputCarboSheetEvent:BottomSheetEvent {
        sealed interface ViewModelToViewEvent : InputCarboSheetEvent {
            // region [Region] VM -> V Event
            data class ApplyAndDismissSheet(
                val carbo: Float
            ) : ViewModelToViewEvent
            // endregion
        }

        sealed interface ViewToViewModelEvent : InputCarboSheetEvent {
            // region [Region] V -> VM Event
            object PressedApplyFromBottom : ViewToViewModelEvent
            data class UpdateConditionRange(
                val start: Float,
                val end: Float
            ) : ViewToViewModelEvent
            data class UpdateInput(
                val inputString: String
            ) : ViewToViewModelEvent
            // endregion
        }
    }
}

