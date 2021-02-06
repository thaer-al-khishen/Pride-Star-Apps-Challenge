package com.example.pridestarappschallenge.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.example.pridestarappschallenge.base.BaseViewModel
import com.example.pridestarappschallenge.models.BookmarkedResult
import com.example.pridestarappschallenge.repositories.BookmarkedRepository

//Create viewmodel for bookmarks page
class BookmarksViewModel @ViewModelInject constructor(application: Application):  BaseViewModel() {

    //Instantiate repository
    private var repository: BookmarkedRepository =
        BookmarkedRepository(application)

    fun getBookmarkedAsLiveData() = repository.getBookmarkedAsLiveData()

    fun getBookmarkedAsList() = repository.getBookmarkedAsList()

    fun insertBookmarked(bookmarkedResult: BookmarkedResult) {
        repository.insertBookmarkedStory(bookmarkedResult)
    }

    fun deleteBookmarked(inputUrl: String) = repository.deleteBookmarkedStory(inputUrl)
}
