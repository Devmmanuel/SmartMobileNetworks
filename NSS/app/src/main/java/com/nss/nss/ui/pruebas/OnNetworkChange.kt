package com.nss.nss.ui.pruebas

import com.nss.nss.ui.imformacion_device.ImformacionDispositivos

interface OnNetworkChange {

    fun saveNetworkInfoToDatabase(dbm:Int,asu:Int,info:ImformacionDispositivos)
}