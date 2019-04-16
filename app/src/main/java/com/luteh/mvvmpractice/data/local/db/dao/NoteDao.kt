package com.luteh.mvvmpractice.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.luteh.mvvmpractice.data.model.db.Note

/**
 * Created by Luthfan Maftuh on 12/04/2019.
 * Email luthfanmaftuh@gmail.com
 */
@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    /** Delete all [Note] data from table note_table */
    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    /**
     * Get all [Note] datas from table note_table
     * The data list ordered by $priority in Descending
     * Wrap List<Note> with LiveData to observe any changed [Note] data on note_table
     */
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}