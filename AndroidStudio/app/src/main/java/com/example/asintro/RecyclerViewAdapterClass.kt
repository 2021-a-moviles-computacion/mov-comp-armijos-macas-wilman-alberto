package com.example.asintro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterClass(
    private val contexto: Class<*>,
    private val listaEntrenador: List<TrainerBean>,
    private val recycleView: RecyclerView
    ): RecyclerView.Adapter<RecyclerViewAdapterClass.MyViewHolder>() {
        inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
            val nombreTV: TextView
            val cedulaTV: TextView
            val likesTV: TextView
            val darLikeBTN: Button
            var numeroLikes = 0

            init {
                nombreTV = view.findViewById(R.id.tv_nombre)
                cedulaTV = view.findViewById(R.id.tv_cedula)
                likesTV = view.findViewById(R.id.tv_likes)
                darLikeBTN = view.findViewById(R.id.btn_like)
            }

            fun anadirLikes() {
                likesTV.text= numeroLikes++.toString()
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_layout,
            parent,
            false
        )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenador = listaEntrenador[position]
        holder.nombreTV.text=entrenador.name
        holder.cedulaTV.text=entrenador.description
        holder.darLikeBTN.text="Like ${entrenador.name}"
    }

    override fun getItemCount(): Int {
        return listaEntrenador.size
    }


}

