package com.example.pridestarappschallenge.network.helpers.modules

import com.example.pridestarappschallenge.utils.IRxSchedulers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

//Create AppModule that can be injected with Hilt
@Module
@InstallIn(ApplicationComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun getIRxSchedulers(): IRxSchedulers = object : IRxSchedulers {
        override fun main(): Scheduler = AndroidSchedulers.mainThread()
        override fun io(): Scheduler = Schedulers.io()
    }
}