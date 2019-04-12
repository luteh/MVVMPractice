package com.luteh.mvvmpractice

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel



/**
 * Created by Luthfan Maftuh on 12/04/2019.
 * Email luthfanmaftuh@gmail.com
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        repository = NoteRepository(application)
        allNotes = repository.getAllNotes()
    }

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNoteDatas(): LiveData<List<Note>> {
        return allNotes
    }
}