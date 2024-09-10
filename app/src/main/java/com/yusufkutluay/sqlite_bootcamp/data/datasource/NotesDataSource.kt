package com.yusufkutluay.sqlite_bootcamp.data.datasource

import com.yusufkutluay.sqlite_bootcamp.data.entity.Notes
import com.yusufkutluay.sqlite_bootcamp.room.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesDataSource (var ndao:NotesDao){

    // NotesPage arka plan renk kodları
    suspend fun colorsHex() : List<String> = withContext(Dispatchers.IO){
        val colorhex = listOf(
            "#7B3030", "#061B37", "#AE4505", "#FFFFFF", "#000000",
            "#6B7677", "#FCE49E", "#FFEBCC", "#CCF0E1", "#FFEEF0",
            "#F5E1FF", "#FFDD57", "#FF7373", "#00C8FF", "#64D57E",
            "#F5F5F5", "#E1E8ED", "#D8D8D8", "#FAF0E6"
        )
        return@withContext colorhex
    }

    suspend fun notesSave(
        title:String,
        notes:String,
        colorPage:String,
        textColor:String,
        textSize:Int,
        currentDate:String,
        todoOk:String
    ){
        val newNote = Notes(0,title,notes,colorPage,textColor,textSize,currentDate,todoOk)
        ndao.save(newNote)
    }

    suspend fun notesLoad() : List<Notes> = withContext(Dispatchers.IO){
        return@withContext ndao.notesLoad()
    }

    suspend fun search(searchText:String) : List<Notes> = withContext(Dispatchers.IO){
        return@withContext ndao.search(searchText)
    }

    suspend fun delete(noteId:Int){
        val deleteNote = Notes(noteId,"","","","",0,"","false")
        ndao.delete(deleteNote)
    }

    suspend fun update(
        id:Int,
        title:String,
        notes:String,
        colorPage:String,
        textColor:String,
        textSize:Int,
        currentDate:String,
        todoOk:String
    ){
        val updateNotes = Notes(id,title,notes,colorPage,textColor,textSize,currentDate,todoOk)
        ndao.update(updateNotes)
    }

}