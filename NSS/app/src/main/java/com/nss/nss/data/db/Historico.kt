package com.nss.nss.data.db

import androidx.room.Entity

@Entity
data class Historico(
    val id:Int,
    val fecha:String,
    val dbm:String,
    val asu:String,
    val codigo:String,
    val red:String,
    val tipoRed:String
)
