package com.example.pridestarappschallenge.base

//Resource component that is going to be used in network calls
data class Resource<T>(val throwable: Throwable?, val data: T?)