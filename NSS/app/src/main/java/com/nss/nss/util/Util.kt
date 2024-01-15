package com.nss.nss.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun obtenerFecha(): String {
    val ahora = System.currentTimeMillis()
    val fecha = Date(ahora)
    val df: DateFormat = SimpleDateFormat("dd/MM/yy")
    return df.format(fecha)
}
