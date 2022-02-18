package com.nss.nss.controller

import com.nss.nss.model.SpinerState

class ControllerHistorical {


    fun buscando(status: SpinerState): String =
        when (status) {
            SpinerState.DATE -> "asu"
            SpinerState.DBM -> "dbm"
            SpinerState.ASU -> "asu"
            SpinerState.RED -> "tipo_de_red"
            SpinerState.KIND_OF_RED -> "tipo_de_red_telefonica"
        }
}