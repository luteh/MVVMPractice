package com.luteh.mvvmpractice

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.Database
import android.os.AsyncTask
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull




/**
 * Created by Luthfan Maftuh on 12/04/2019.
 * Email luthfanmaftuh@gmail.com
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var instance: NoteDatabase? = null

        /**
         * To get singleton instance of [NoteDatabase]
         */
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

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance!!).execute()
            }
        }

        private class PopulateDbAsyncTask(db: NoteDatabase) : AsyncTask<Void, Void, Void>() {
            private val noteDao: NoteDao

            init {
                noteDao = db.noteDao()
            }

            override fun doInBackground(vararg voids: Void): Void? {
                noteDao.insert(Note("Title 1", "Description 1", 1))
                noteDao.insert(Note("Title 2", "Description 2", 2))
                noteDao.insert(Note("Title 3", "Description 3", 3))
                return null
            }
        }
    }
}