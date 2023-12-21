package com.translate589.study.di

import android.content.Context
import com.translate589.study.Toasty
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {
    @Provides
    @Singleton
    fun provideToasty(
        @ApplicationContext context: Context
    ) = Toasty(context)
}