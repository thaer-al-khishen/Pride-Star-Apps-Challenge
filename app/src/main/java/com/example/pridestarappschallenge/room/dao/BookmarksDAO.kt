package com.example.pridestarappschallenge.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pridestarappschallenge.models.BookmarkedResult

@Dao
interface BookmarksDAO {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarkedStory(bookMarkedResult: BookmarkedResult)

    @Transaction
    @Query("SELECT * from bookmarked_table")
    fun getBookmarkedStoriesAsLiveData(): LiveData<List<BookmarkedResult>>

    @Transaction
    @Query("SELECT * from bookmarked_table")
    fun getBookmarkedStoriesAsList(): List<BookmarkedResult>

    @Query("DELETE FROM bookmarked_table where url= :inputUrl")
    fun deleteBookmarkedStory(inputUrl: String)
}
