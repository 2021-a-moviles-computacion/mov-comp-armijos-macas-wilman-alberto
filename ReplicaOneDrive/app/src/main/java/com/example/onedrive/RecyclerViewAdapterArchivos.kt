package com.example.onedrive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterArchivos(
    var listaArchivos: ArrayList<Archivos>,
    var context: Context
    ): RecyclerView.Adapter<RecyclerViewAdapterArchivos.ViewHolder>()  {

        inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
            var imagen:ImageView
            var titulo:TextView
            var descripcion:TextView
            val opcion:ImageView

            init {
                imagen = view.findViewById(R.id.imv_ImagenDocumento)
                titulo = view.findViewById(R.id.txv_TituloDocumento)
                descripcion = view.findViewById(R.id.txv_Descripcion)
                opcion = view.findViewById(R.id.imv_Opciones)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_archivos_recientes,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val archivos = listaArchivos[position]
        holder.imagen.setImageResource(archivos.imagen)
        holder.titulo.text = archivos.nombreArchivo
        holder.descripcion.text = archivos.peso+" · "+archivos.fecha
        holder.opcion.setImageResource(archivos.opciones)

        holder.opcion.setOnClickListener {
            print("Opcion seleccionada")
        }

    }

    override fun getItemCount(): Int {
        return listaArchivos.size
    }


}