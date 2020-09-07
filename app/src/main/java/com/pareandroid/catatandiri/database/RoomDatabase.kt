package com.pareandroid.catatandiri.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.pareandroid.catatandiri.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class RoomDatabase : androidx.room.RoomDatabase() {
    abstract fun noteDao(): NoteDao?

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null
        fun getDatabase(context: Context): RoomDatabase? {
            if (INSTANCE == null) {
                synchronized(RoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder(
                                context.applicationContext,
                                RoomDatabase::class.java,
                                "note_database"
                            )
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}