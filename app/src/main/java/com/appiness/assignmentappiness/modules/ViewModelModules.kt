package com.appstreet.top_github.modules

import com.appiness.assignmentappiness.impls.MainImpl
import com.appiness.assignmentappiness.interfaces.ApplicationSchedulerProvider
import com.appiness.assignmentappiness.interfaces.SchedulerProvider
import com.appiness.assignmentappiness.modules.NetworkModule
import com.appiness.assignmentappiness.repository.MainRepository
import com.appiness.assignmentappiness.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module


object ViewModelModules {


    val viewModules: Module = module {
        single { ApplicationSchedulerProvider() as SchedulerProvider }

        single<MainRepository> { MainImpl(get(named(NetworkModule.RETROFIT_API))) }
        viewModel { MainViewModel(get(),get()) }

    }



}

