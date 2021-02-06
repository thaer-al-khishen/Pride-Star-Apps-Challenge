package com.example.pridestarappschallenge.repositories

import android.app.Application
import com.example.pridestarappschallenge.models.BookmarkedResult
import com.example.pridestarappschallenge.room.dao.BookmarksDAO
import com.example.pridestarappschallenge.room.database.ApplicationDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BookmarkedRepository(application: Application) : CoroutineScope {
    //Enable the use of coroutines
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    //Instantiate MessageDao
    private var bookmarksDAO: BookmarksDAO?

    init {
        val db =
            ApplicationDatabase.getDatabase(
                application
            )
        bookmarksDAO = db?.bookmarksDao()
    }

    //Retrieve messages from MessageDao
    fun getBookmarkedAsLiveData() = bookmarksDAO?.getBookmarkedStoriesAsLiveData()

    fun getBookmarkedAsList() =  bookmarksDAO?.getBookmarkedStoriesAsList()


    //Use Coroutines for concurrent programming when deleting users
    fun deleteBookmarkedStory(inputUrl: String) {
        launch { deleteBookmarkedStoryBG(inputUrl) }
    }

    //Execute function on another thread
    private suspend fun deleteBookmarkedStoryBG(inputUrl: String) {
        withContext(Dispatchers.IO) {
            bookmarksDAO?.deleteBookmarkedStory(inputUrl)
        }
    }

    //Use Coroutines for concurrent programming when inserting messages
    fun insertBookmarkedStory(bookmarkedResult: BookmarkedResult) {
        launch { insertBookmarkedStoryBG(bookmarkedResult) }
    }

    //Execute function on another thread
    private suspend fun insertBookmarkedStoryBG(bookmarkedResult: BookmarkedResult) {
        withContext(Dispatchers.IO) {
            bookmarksDAO?.insertBookmarkedStory(bookmarkedResult)
        }
    }
}
