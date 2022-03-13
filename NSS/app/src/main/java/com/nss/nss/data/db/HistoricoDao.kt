package com.nss.nss.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoricoDao {

    @Insert
    fun insertHistorico(historico: Historico)

    @Query("SELECT * FROM HISTORICO")
    fun getAllHistoricos():List<Historico>
}