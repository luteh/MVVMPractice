package com.luteh.mvvmpractice

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.Database



/**
 * Created by Luthfan Maftuh on 12/04/2019.
 * Email luthfanmaftuh@gmail.com
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as NoteDatabase
        }
    }
}