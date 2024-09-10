package com.yusufkutluay.sqlite_bootcamp.uix.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import com.yusufkutluay.sqlite_bootcamp.data.repo.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

@HiltViewModel
class DetailPageViewModel @Inject constructor(var nRepo : NotesRepository): ViewModel(){

    val colorlist = mutableStateOf<List<String>>(emptyList())

    init {
        colorsHex()
    }

    fun colorsHex(){
        CoroutineScope(Dispatchers.Main).launch {
            colorlist.value = nRepo.colorsHex()
        }
    }

    fun update(
        id:Int,
        title:String,
        notes:String,
        colorPage:String,
        textColor:String,
        textSize:Int,
        currentDate:String,
        todoOk:String
    ){
        CoroutineScope(Dispatchers.Main).launch {
            nRepo.update(id,title,notes,colorPage,textColor,textSize,currentDate,todoOk)
        }
    }

    fun delete(noteId:Int){
        CoroutineScope(Dispatchers.Main).launch {
            nRepo.delete(noteId)
        }
    }

}