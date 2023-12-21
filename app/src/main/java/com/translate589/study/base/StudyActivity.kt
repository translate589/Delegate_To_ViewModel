package com.translate589.study.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.translate589.study.databinding.ActivityStudyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyActivity: AppCompatActivity() {

    private var _binding: ActivityStudyBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException()

    override fun onCreate(savedInstanceState: Bundle?) {
        // splash
        super.onCreate(savedInstanceState)
        _binding = ActivityStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}