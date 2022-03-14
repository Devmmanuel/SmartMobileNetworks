package com.nss.nss.ui.pruebas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nss.nss.data.db.Historico
import com.nss.nss.data.db.HistoricoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PruebasViewModel @Inject constructor(
    private val historicoDao: HistoricoDao):
    ViewModel() {

    fun insertPrueba(historico: Historico){
        viewModelScope.launch (Dispatchers.IO){
            historicoDao.insertHistorico(historico)
        }
    }
}