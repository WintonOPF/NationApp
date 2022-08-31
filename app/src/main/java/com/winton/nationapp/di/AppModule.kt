package com.winton.nationapp.di

import android.app.Application
import androidx.room.Room
import com.winton.nationapp.data.NationDatabase
import com.winton.nationapp.data.NationRepository
import com.winton.nationapp.data.NationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNationDatabase(app: Application): NationDatabase {
        return Room.databaseBuilder(
            app,
            NationDatabase::class.java,
            "nation_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNationRepository(db: NationDatabase): NationRepository{
        return NationRepositoryImpl(db.dao)
    }
}