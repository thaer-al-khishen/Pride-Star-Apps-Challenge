package com.example.pridestarappschallenge.utils

import io.reactivex.Scheduler

interface IRxSchedulers {
    fun main(): Scheduler
    fun io(): Scheduler
}
