package com.example.pridestarappschallenge.network.helpers.services

import com.example.pridestarappschallenge.models.HomeAPIResult
import com.example.pridestarappschallenge.network.helpers.helpers.UrlBuilder
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StoriesAPIService {

    @GET(UrlBuilder.GET_ALL_HOME)
    fun getHome(
        @Query("api-key") apiKey: String
    ): Single<HomeAPIResult>
}