package com.yusufkutluay.sqlite_bootcamp.uix.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yusufkutluay.sqlite_bootcamp.data.repo.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesPageViewModel @Inject constructor(var nRepo : NotesRepository) : ViewModel() {

    val colorlist = mutableStateOf<List<String>>(emptyList())

    init {
        colorsHex()
    }

    fun colorsHex(){
        CoroutineScope(Dispatchers.Main).launch {
            colorlist.value = nRepo.colorsHex()
        }
    }

    fun notesSave(
        title:String,
        notes:String,
        colorPage:String,
        textColor:String,
        textSize:Int,
        currentDate:String,
        todoOk:String
    ){
        CoroutineScope(Dispatchers.Main).launch {
            nRepo.notesSave(title,notes,colorPage,textColor,textSize,currentDate,todoOk)
        }
    }



}