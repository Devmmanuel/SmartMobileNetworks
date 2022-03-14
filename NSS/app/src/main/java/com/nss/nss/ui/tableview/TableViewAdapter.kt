package com.nss.nss.ui.tableview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nss.nss.R
import com.nss.nss.data.db.Historico
import com.nss.nss.databinding.ItemTableviewBinding


class TableViewAdapter() :
    RecyclerView.Adapter<TableViewAdapter.ViewHolder>() {


    private var listaHistoricos: MutableList<Historico> = arrayListOf()

    fun setData(lista: List<Historico>) {
        listaHistoricos = lista.toMutableList()
        listaHistoricos.add(0,setHeader())
        notifyDataSetChanged()
    }

    private fun setHeader(): Historico {
        return Historico(0, "Fecha", "Dbm", "Asu", "Codigo", "Red", "Tipo Red")
    }

    class ViewHolder(private val binding: ItemTableviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historico: Historico){
            with(binding){
                tvId.text=historico.id.toString()
                tvFecha.text=historico.fecha
                tvDbm.text=historico.dbm
                tvAsu.text=historico.asu
                tvCodigo.text=historico.codigo
                tvRed.text=historico.red
                tvTipoRed.text=historico.tipoRed
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemTableviewBinding.inflate(layoutInflater,viewGroup,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listaHistoricos[position])
    }


    override fun getItemCount() = listaHistoricos.size

}
