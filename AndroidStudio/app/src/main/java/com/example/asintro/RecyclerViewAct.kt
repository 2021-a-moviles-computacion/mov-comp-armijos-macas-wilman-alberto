package com.example.asintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAct : AppCompatActivity() {

    var totalLikes = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val listTrainers = arrayListOf<TrainerBean>()
        val league = LeagueBean("Kanto", "Liga Kanto")
        listTrainers.add(
            TrainerBean("Wilman","1234567890",league)
        )
        listTrainers.add(
            TrainerBean("Alberto","0123789456",league)
        )

        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rcv_entrenadores
        )

        iniciarRecyclerView(
            listTrainers,
            this,
            recyclerViewEntrenador
        )
    }

    fun iniciarRecyclerView(
        lista: List<TrainerBean>,
        actividad: RecyclerViewAct,
        recyclerView: RecyclerView
    ) {
        val adaptador = RecyclerViewAdapterClass(
            actividad::class.java,
            lista,
            recyclerView
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()

    }

    fun aumentarLikes() {
        findViewById<TextView>(R.id.tv_likes_total).text=totalLikes++.toString()
    }

}