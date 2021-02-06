package com.example.pridestarappschallenge.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Room database entity
@Entity(tableName = "bookmarked_table")
data class BookmarkedResult (
    @PrimaryKey
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "abstract")
    var `abstract`: String,
    @ColumnInfo(name = "multimedia")
    var multimedia: String,
    @ColumnInfo(name = "published_date")
    var publishedDate: String,
    @ColumnInfo(name = "title")
    var title: String){
    constructor() : this("", "", "", "", "")
}