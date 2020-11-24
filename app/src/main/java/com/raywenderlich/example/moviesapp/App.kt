package com.raywenderlich.example.moviesapp

import android.app.Application
import androidx.work.*
import com.raywenderlich.example.moviesapp.di.appModules
import com.raywenderlich.example.moviesapp.workers.PokemonDatabaseWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class App() : Application() {

    companion object {
        private lateinit var instance: App
//        private val service by lazy { buildApiService()}
//        val remoteApi by lazy {PokemonApi(service)}
        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Koin initialization
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }

        val workManager = WorkManager.getInstance(applicationContext)
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_ROAMING).build()

        /*
        Immediate work -- Enable when we want to test immediate work (not scheduled)
         */

//            val request =
//                OneTimeWorkRequestBuilder<PokemonDatabaseWorker>()
//                    .setConstraints(constraints)
//                    .build()
//            workManager.enqueue(request)

        /*
        Periodic work -- Enable when we want to continue with the normal behaviour (DB sync with 15 min interval)
         */

        val request = PeriodicWorkRequestBuilder<PokemonDatabaseWorker>(15, TimeUnit.MINUTES)//repeats each 15 minutes
            .setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork("PokemonDatabaseWork", ExistingPeriodicWorkPolicy.KEEP, request)


    }


}