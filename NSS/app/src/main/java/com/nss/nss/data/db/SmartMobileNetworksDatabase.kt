package com.nss.nss.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Historico::class], version = 1, exportSchema = false)

abstract class SmartMobileNetworksDatabase: RoomDatabase(){

    abstract fun historicalDao():HistoricoDao

}