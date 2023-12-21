package com.translate589.study.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal abstract class BaseFragment : Fragment() {
    abstract fun initComponent()
    abstract fun observeFlow()
    abstract fun observeEvent()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeFlow()
        observeEvent()
    }

    internal fun exitPage() {
        findNavController().popBackStack()
    }

    internal fun NavDirections.moveInternal() {
        findNavController().navigate(this)
    }
}