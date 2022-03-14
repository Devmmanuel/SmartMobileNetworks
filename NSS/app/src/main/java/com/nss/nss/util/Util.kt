package com.nss.nss.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun obtenerFecha(): String {
    val ahora = System.currentTimeMillis()
    val fecha = Date(ahora)
    val df: DateFormat = SimpleDateFormat("dd/MM/yy")
    return df.format(fecha)
}
