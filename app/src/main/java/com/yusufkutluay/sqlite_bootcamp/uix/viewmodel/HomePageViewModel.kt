package com.yusufkutluay.sqlite_bootcamp.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusufkutluay.sqlite_bootcamp.data.entity.Notes
import com.yusufkutluay.sqlite_bootcamp.data.repo.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var nRepo : NotesRepository) : ViewModel(){
    val noteList = MutableLiveData<List<Notes>>()

    init {
        notesLoad()
    }

    fun notesLoad() {
        CoroutineScope(Dispatchers.Main).launch {
            val loadedNotes = nRepo.notesLoad()
            // Notları sıralıyoruz
            val sortedNotes = loadedNotes.sortedWith(
                compareBy<Notes> { it.todoOk.toBoolean() }
                    .thenByDescending { it.id }
            )
            noteList.value = sortedNotes
        }
    }

    fun search(searchText:String){
        CoroutineScope(Dispatchers.Main).launch {
            val loadedNotes = nRepo.search(searchText)
            // Notları sıralıyoruz
            val sortedNotes = loadedNotes.sortedWith(
                compareBy<Notes> { it.todoOk.toBoolean() }
                    .thenByDescending { it.id }
            )
            noteList.value = sortedNotes

        }
    }
    fun updateSortedNotes() {
        // Mevcut listeyi sıralayıp güncelliyoruz
        val updatedList = noteList.value?.sortedWith(
            compareBy<Notes> { it.todoOk.toBoolean() }
                .thenByDescending { it.id }
        )
        noteList.postValue(updatedList!!)
    }

    fun delete(noteId:Int){
        CoroutineScope(Dispatchers.Main).launch {
            nRepo.delete(noteId)
            notesLoad()
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

}