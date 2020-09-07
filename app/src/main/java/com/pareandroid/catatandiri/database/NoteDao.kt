package com.pareandroid.catatandiri.database

import androidx.room.*
import com.pareandroid.catatandiri.model.NoteModel

@Dao
interface NoteDao {

@Update
fun update(note: NoteModel)

@Insert
fun insert(note : NoteModel)

@Delete
fun delete(note :NoteModel)

@Query("SELECT * FROM notes")
fun getAll() : List<NoteModel>

@Query("SELECT * FROM notes WHERE id = :id")
fun getById(id: Int) : List<NoteModel>
}