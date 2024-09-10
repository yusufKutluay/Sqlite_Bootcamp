package com.yusufkutluay.sqlite_bootcamp.di

import android.content.Context
import androidx.room.Room
import com.yusufkutluay.sqlite_bootcamp.data.datasource.NotesDataSource
import com.yusufkutluay.sqlite_bootcamp.data.repo.NotesRepository
import com.yusufkutluay.sqlite_bootcamp.room.Database
import com.yusufkutluay.sqlite_bootcamp.room.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNotesRepository(nds:NotesDataSource) : NotesRepository{
        return NotesRepository(nds)
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(ndao:NotesDao) : NotesDataSource{
        return NotesDataSource(ndao)
    }

    @Provides
    @Singleton
    fun provideNotesDao(@ApplicationContext context: Context) : NotesDao{
        val db = Room.databaseBuilder(context,Database::class.java,"notes.sqlite")
            .createFromAsset("todonot.sqlite").build()
        return db.getKisilerDao()
    }

}