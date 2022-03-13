package com.nss.nss.di

import android.content.Context
import androidx.room.Room
import com.nss.nss.data.db.SmartMobileNetworksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object ModuleDb {

    private const val DATABASE_NAME = "SmartMovileNetworks"

    @Provides
    @Singleton
    fun provideSmartMobileNetworkDb(@ApplicationContext context: Context):SmartMobileNetworksDatabase{
        return  return Room.databaseBuilder(
            context,
            SmartMobileNetworksDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHistoricalDao(db:SmartMobileNetworksDatabase)=db.historicalDao()


}