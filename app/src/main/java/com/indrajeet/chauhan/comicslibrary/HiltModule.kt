package com.indrajeet.chauhan.comicslibrary

import android.content.Context
import androidx.room.Room
import com.indrajeet.chauhan.comicslibrary.model.api.ApiService
import com.indrajeet.chauhan.comicslibrary.model.api.MarvelApiRepo
import com.indrajeet.chauhan.comicslibrary.model.connectivity.ConnectivityMonitor
import com.indrajeet.chauhan.comicslibrary.model.db.CharacterDao
import com.indrajeet.chauhan.comicslibrary.model.db.CollectionDb
import com.indrajeet.chauhan.comicslibrary.model.db.CollectionDbRepo
import com.indrajeet.chauhan.comicslibrary.model.db.CollectionDbRepoImpl
import com.indrajeet.chauhan.comicslibrary.model.db.Constants.DB
import com.indrajeet.chauhan.comicslibrary.model.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) =
        collectionDb.characterDao()

    @Provides
    fun provideNoteDao(collectionDb: CollectionDb) =
        collectionDb.noteDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao, noteDao: NoteDao): CollectionDbRepo =
        CollectionDbRepoImpl(characterDao, noteDao)

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)
}