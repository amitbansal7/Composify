package com.example.composify.locoEarnings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composify.locoEarnings.repositories.EarningsRepository

class EarningsViewModelProviderFactory(private val newsRepository: EarningsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EarningsViewModel(newsRepository) as T
    }
}