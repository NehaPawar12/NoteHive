package com.example.notehive.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notehive.model.Note

@Database(entities = [Note::class], version = 1)

abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile//changes made in one thread visible to other
        private var instance:NoteDatabase? = null//holds the singleton instance of the database
        private val LOCK = Any()//used to synchronize access to the database, only one thread can access the database at a time

        operator fun invoke(context: Context) = instance?:
        synchronized(LOCK){
            instance?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
    }
}