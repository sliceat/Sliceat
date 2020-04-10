package com.marcoperini.sliceat.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
//    single { KeyValueStorageFactory.build(context = androidContext(), name = "memoarr_prefs") }
}

val appComponents = module {
//    single<Navigator> { AppNavigator(get(), get(), get()) }
}

val viewModels = module {
//    viewModel {
//        SplashViewModel(get())
//    }
}

