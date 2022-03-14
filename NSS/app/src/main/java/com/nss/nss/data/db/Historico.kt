package com.nss.nss.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Historico(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val fecha:String,
    val dbm:String,
    val asu:String,
    val codigo:String,
    val red:String,
    val tipoRed:String
)
