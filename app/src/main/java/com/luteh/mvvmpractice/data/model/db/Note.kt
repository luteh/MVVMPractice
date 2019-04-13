package com.luteh.mvvmpractice.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Luthfan Maftuh on 12/04/2019.
 * Email luthfanmaftuh@gmail.com
 */
@Entity(tableName = "note_table")
data class Note(
    val title: String,
    val description: String,
    val priority: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}