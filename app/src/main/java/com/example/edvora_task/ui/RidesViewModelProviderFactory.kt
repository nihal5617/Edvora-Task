package com.example.edvora_task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.edvora_task.repository.RidesRepository

class RidesViewModelProviderFactory(
    val ridesRepository: RidesRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RidesViewModel(ridesRepository) as T
    }
}