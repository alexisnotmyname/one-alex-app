package com.alexc.ph.data.db.di

import android.content.Context
import androidx.room.Room
import com.alexc.ph.data.db.OneAlexDatabase
import com.alexc.ph.data.model.todolist.TodoListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class  DatabaseModule {

    @Provides
    fun provideTodoListDao(oneAlexDatabase: OneAlexDatabase): TodoListDao {
        return oneAlexDatabase.todoListDao()
    }

    @Provides
    @Singleton
    fun provideOneAlexDatabase(@ApplicationContext appContext: Context): OneAlexDatabase {
        return Room.databaseBuilder(
            appContext,
            OneAlexDatabase::class.java,
            "OneAlexDatabase"
        ).build()
    }
}