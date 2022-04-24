package com.example.beatoreat.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beatoreat.MainViewModel
import com.example.beatoreat.model.Environment

class MainViewModelFactory: ViewModelProvider.NewInstanceFactory {

    private val environment: Environment
    constructor(environment: Environment) {
        this.environment = environment
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return MainViewModel(environment.title) as T
        }
        return super.create(modelClass)
    }

}