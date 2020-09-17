package com.marcoperini.sliceat.di

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.marcoperini.sliceat.database.AppDatabase
import com.marcoperini.sliceat.database.AppRepository
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
import com.marcoperini.sliceat.ui.maps.Contract
import com.marcoperini.sliceat.ui.maps.Provider
import com.marcoperini.sliceat.ui.maps.network.Backend
import com.marcoperini.sliceat.ui.maps.ui.MapsViewModel
import com.marcoperini.sliceat.utils.Constants.Companion.DATABASE_NAME
import com.marcoperini.sliceat.utils.VolleyRequest
import com.marcoperini.sliceat.utils.sharedpreferences.APIController
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorageFactory
import com.marcoperini.sliceat.utils.sharedpreferences.ServiceInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "sliceat_prefs") }
}

val appComponents = module {
    single { createGson() }
    single<Navigator> { AppNavigator(get()) }
    single { Provider(backend = get()) } bind Contract::class
    single { Backend(gson = get(), resources = get()) }
}

val databaseComponents = module {
    //database.builder
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE_NAME).build() }

    //TableDao()
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().localsDao() }
    single { get<AppDatabase>().allergieDao() }

    //repository
    single { AppRepository(get()) }
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
    viewModel { MapsViewModel(scheduler = AndroidSchedulers.mainThread(), contract = get(), repository = get()) }
}

val volleyComponents = module {
    single { VolleyRequest(context = get()) }
    single<ServiceInterface> { APIController(get(), get()) }
}

private fun createGson(): Gson = GsonBuilder().create()
