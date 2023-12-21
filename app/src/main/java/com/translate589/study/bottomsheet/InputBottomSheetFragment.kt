package com.translate589.study.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.translate589.study.collectLatestWithLaunch
import com.translate589.study.collectWithLaunch
import com.translate589.study.databinding.BottomSheetInputBinding
import com.translate589.study.getBottomSheetParam
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewModelToViewEvent.ApplyAndDismissSheet
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent.PressedApplyFromBottom
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent.UpdateConditionRange
import com.translate589.study.model.event.BottomSheetEvent.InputCarboSheetEvent.ViewToViewModelEvent.UpdateInput
import com.translate589.study.model.param.InputBottomSheetParam
import com.translate589.study.viewmodel.InputBottomSheetViewModel
import timber.log.Timber

internal class InputBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetInputBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException()

    private val vm: InputBottomSheetViewModel by viewModels()

    private val param: InputBottomSheetParam by lazy {
        requireArguments().getBottomSheetParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
        observeFlow()
        observeEvent()

        vm.performEvent(UpdateConditionRange(param.minCondition, param.maxCondition))
    }

    private fun initComponent() = with(binding) {
        tvBottomSheetTitle.setText(param.titleResId)
        tvBottomSheetSubTitle.text = requireContext()
            .getString(
                param.subTitleResId,
                param.minCondition.toString(),
                param.maxCondition.toString(),
                requireContext().getString(param.unitResId)
            )
        tvUnit.setText(param.unitResId)
        btnBottomConfirm bindEvent PressedApplyFromBottom

        with(edtInputBox) {
            maxLines = 1
            addTextChangedListener { s ->
                s?.run {
                    vm.performEvent(UpdateInput(toString()))
                }
            }
        }
    }

    private fun observeFlow() {
        collectLatestWithLaunch(vm.isInputError) {
            binding.btnBottomConfirm.isEnabled = it
        }
    }

    private fun observeEvent() {
        collectWithLaunch(vm.eventFlow) { event ->
            Timber.w("Translate589 observeEvent -> $event")
            when (event) {
                is ApplyAndDismissSheet -> performApplyAndDismiss(event)
            }
        }
    }

    private fun performApplyAndDismiss(event: ApplyAndDismissSheet) {
        param.onConfirm(event.carbo)
        dismiss()
    }

    private infix fun View.bindEvent(event: ViewToViewModelEvent) {
        setOnClickListener {
            vm.performEvent(event)
        }
    }
}