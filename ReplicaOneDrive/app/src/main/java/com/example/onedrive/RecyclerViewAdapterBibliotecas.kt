package com.example.onedrive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterBibliotecas(
    var listaBibliotecas: ArrayList<Bibliotecas>,
    var context: Context
): RecyclerView.Adapter<RecyclerViewAdapterBibliotecas.ViewHolder>()  {

    inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var abreviatura:TextView
        var color:Int
        var titulo:TextView

        init {
            abreviatura = view.findViewById(R.id.txv_abrvTitulo)
            color = R.color.ODGris
            titulo = view.findViewById(R.id.txv_DescripcionBib)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_bibliotecas_compartidas,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val biblioteca = listaBibliotecas[position]
        holder.abreviatura.text=biblioteca.abreviacionTitulo
        holder.abreviatura.setBackgroundResource(biblioteca.colorRecuadro)
        holder.titulo.text=biblioteca.titulo
    }

    override fun getItemCount(): Int {
        return listaBibliotecas.size
    }


}