package com.translate589.study.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.translate589.study.Toasty
import com.translate589.study.base.BaseFragment
import com.translate589.study.bottomsheet.InputBottomSheetFragment
import com.translate589.study.collectLatestWithLaunch
import com.translate589.study.databinding.FragmentStudyBinding
import com.translate589.study.model.event.StudyEvent.ViewModelToViewEvent.ShowInputBottomSheet
import com.translate589.study.model.event.StudyEvent.ViewModelToViewEvent.ShowToast
import com.translate589.study.model.event.StudyEvent.ViewToViewModelEvent
import com.translate589.study.model.event.StudyEvent.ViewToViewModelEvent.PressedHeightInputArea
import com.translate589.study.model.event.StudyEvent.ViewToViewModelEvent.PressedWeightInputArea
import com.translate589.study.model.param.ToastParamWithResource
import com.translate589.study.model.param.ToastParamWithString
import com.translate589.study.putBottomSheetParam
import com.translate589.study.viewmodel.StudyViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
internal class StudyFragment: BaseFragment() {

    private var _binding: FragmentStudyBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException()

    private val vm: StudyViewModel by viewModels()

    @Inject lateinit var toasty: Toasty

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initComponent() = with(binding) {
        areaInputHeight bindEvent PressedWeightInputArea
        areaInputWeight bindEvent PressedHeightInputArea
    }

    override fun observeFlow() {
        collectLatestWithLaunch(vm.heightValue) {
            binding.tvHeightValue.text = it.toString()
        }
        collectLatestWithLaunch(vm.weightValue) {
            binding.tvWeightValue.text = it.toString()
        }
    }

    override fun observeEvent() {
        collectLatestWithLaunch(vm.commonEvent) { event ->
            Timber.w("Translate589 observeEvent ${event::class.simpleName}")
            when (event) {
                is ShowInputBottomSheet -> showInputBottomSheet(event)
                is ShowToast            -> showToast(event)
            }
        }
    }

    private fun showInputBottomSheet(event: ShowInputBottomSheet) {
        InputBottomSheetFragment()
            .apply {
                arguments = Bundle().putBottomSheetParam(event.param)
            }
            .show(childFragmentManager, "InputBottomSheetFragment")
    }

    private fun showToast(event: ShowToast) {
        when (event.param) {
            is ToastParamWithString   -> event.param.showToast()
            is ToastParamWithResource -> event.param.showToast()
        }
    }

    private fun ToastParamWithString.showToast() {
        toasty.showToast(msg)
    }

    private fun ToastParamWithResource.showToast() {
        toasty.showToast(msgResId)
    }

    private infix fun View.bindEvent(event: ViewToViewModelEvent) {
        setOnClickListener {
            vm.performEvent(event)
        }
    }
}