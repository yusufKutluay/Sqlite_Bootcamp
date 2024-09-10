package com.yusufkutluay.sqlite_bootcamp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yusufkutluay.sqlite_bootcamp.data.entity.Notes

@Database(entities = [Notes::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun getKisilerDao() : NotesDao
}