package com.marcoperini.sliceat.di

import com.marcoperini.sliceat.ui.AppNavigator
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.launch.LaunchViewModel
import com.marcoperini.sliceat.ui.main.MainViewModel
import com.marcoperini.sliceat.utils.sharedPreferences.KeyValueStorageFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "sliceat_prefs") }
}

val appComponents = module {
    single<Navigator> { AppNavigator(get(), get(), get()) }
}

val viewModels = module {

    viewModel {
        LaunchViewModel(get())
    }

    viewModel {
        MainViewModel()
    }
}

