package com.example.pridestarappschallenge.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.pridestarappschallenge.BuildConfig
import com.example.pridestarappschallenge.base.BaseViewModel
import com.example.pridestarappschallenge.base.Resource
import com.example.pridestarappschallenge.models.StoriesResult
import com.example.pridestarappschallenge.network.helpers.services.StoriesAPIService
import com.example.pridestarappschallenge.utils.IRxSchedulers

//Create viewmodel for home page
class HomeStoriesViewModel @ViewModelInject constructor(private val api: StoriesAPIService, private val schedulers: IRxSchedulers, @Assisted state: SavedStateHandle) :
    BaseViewModel() {

    //The mutable livedata variable that will be observed
    private val _home: MutableLiveData<Resource<List<StoriesResult>>> = MutableLiveData()
    val home: LiveData<Resource<List<StoriesResult>>>
        get() = _home

    //Launch this function as soon as the viewmodel is created
    init {
        getHome()
    }

    //Make an api call to get home stories with the help of RX java
    fun getHome() {
        addToDisposable(api.getHome(BuildConfig.apiKey)

            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())

            .doFinally {
            }
            .doOnError{
                _home.postValue(Resource(it,null))}

            .subscribe({ response ->
                _home.postValue(Resource(null, response.storiesResults)) }, {})
        )
    }
}
