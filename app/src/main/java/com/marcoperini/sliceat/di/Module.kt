package com.marcoperini.sliceat.di

import androidx.room.Room
import com.marcoperini.sliceat.database.UsersDatabase
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.ui.AppNavigator
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.authentication.firstscreen.AuthenticationViewModel
import com.marcoperini.sliceat.ui.authentication.signin.signin1.SignIn1ViewModel
import com.marcoperini.sliceat.ui.authentication.signin.signin2.SignIn2ViewModel
import com.marcoperini.sliceat.ui.authentication.signin.signin3.SignIn3ViewModel
import com.marcoperini.sliceat.ui.authentication.signin.signin4.SignIn4ViewModel
import com.marcoperini.sliceat.ui.authentication.signin.signin5.SignIn5ViewModel
import com.marcoperini.sliceat.ui.launch.LaunchViewModel2
import com.marcoperini.sliceat.ui.main.MainViewModel
import com.marcoperini.sliceat.utils.Constants.Companion.USER_DATABASE_NAME
import com.marcoperini.sliceat.utils.VolleyRequest
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorageFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "sliceat_prefs") }
}

val appComponents = module {
    single<Navigator> { AppNavigator(get()) }
}

val databaseComponents = module {
    single { Room.databaseBuilder(androidContext(), UsersDatabase::class.java, USER_DATABASE_NAME).build() }
    single { get<UsersDatabase>().userDao() } //UsersTableDao()
    single { UsersRepository(get()) }
}

val viewModels = module {
    viewModel { LaunchViewModel2(prefs = get()) }
    viewModel { AuthenticationViewModel() }
    viewModel { MainViewModel() }
    viewModel { SignIn1ViewModel(repository = get(), prefs = get()) }
    viewModel { SignIn2ViewModel(prefs = get()) }
    viewModel { SignIn3ViewModel(prefs = get()) }
    viewModel { SignIn4ViewModel(prefs = get()) }
    viewModel { SignIn5ViewModel(repository = get()) }
}

val volleyComponents = module {
    single { VolleyRequest(context = androidContext())}
}
