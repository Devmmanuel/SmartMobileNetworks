package com.nss.nss.ui.imformacion_device

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nss.nss.R

class AdaptadorImformationDevice(private val dataDevice: List<String>) :
    RecyclerView.Adapter<AdaptadorImformationDevice.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texto = view.findViewById<TextView>(R.id.tv_dato)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.texto.text = dataDevice[position]
    }

    override fun getItemCount() = dataDevice.size

}
