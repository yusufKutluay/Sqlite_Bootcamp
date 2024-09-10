package com.yusufkutluay.sqlite_bootcamp.data.repo

import com.yusufkutluay.sqlite_bootcamp.data.datasource.NotesDataSource
import com.yusufkutluay.sqlite_bootcamp.data.entity.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepository(var nda:NotesDataSource) {

    suspend fun colorsHex() : List<String> = nda.colorsHex()

    suspend fun notesSave(
        title:String,
        notes:String,
        colorPage:String,
        textColor:String,
        textSize:Int,
        currentDate:String,
        todoOk:String
    ) = nda.notesSave(title,notes,colorPage,textColor,textSize,currentDate,todoOk)

    suspend fun notesLoad() : List<Notes> = nda.notesLoad()

    suspend fun search(searchText:String) : List<Notes> = nda.search(searchText)

    suspend fun delete(noteId:Int) = nda.delete(noteId)

    suspend fun update(
        id:Int,
        title:String,
        notes:String,
        colorPage:String,
        textColor:String,
        textSize:Int,
        currentDate:String,
        todoOk:String
    ) = nda.update(id,title,notes,colorPage,textColor,textSize,currentDate,todoOk)

}