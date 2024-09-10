package com.yusufkutluay.sqlite_bootcamp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yusufkutluay.sqlite_bootcamp.data.entity.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    suspend fun notesLoad() : List<Notes>

    @Insert
    suspend fun save(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :searchText || '%' OR notes LIKE '%' || :searchText || '%'")
    suspend fun search(searchText:String) : List<Notes>


}