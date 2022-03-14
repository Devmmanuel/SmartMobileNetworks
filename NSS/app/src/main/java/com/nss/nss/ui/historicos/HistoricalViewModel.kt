package com.nss.nss.ui.historicos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nss.nss.data.db.Historico
import com.nss.nss.data.db.HistoricoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoricalViewModel @Inject constructor(private val historicoDao: HistoricoDao) : ViewModel() {

    private val _historicos = MutableLiveData<List<Historico>>()
    val historicos: MutableLiveData<List<Historico>>
        get() = _historicos

    fun getAllHistorics(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = historicoDao.getAllHistoricos()
            withContext(Dispatchers.Main){
                _historicos.value=response
            }
        }
    }


}