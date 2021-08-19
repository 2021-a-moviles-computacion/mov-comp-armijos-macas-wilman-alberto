package com.example.onedrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listaArchivos = ArrayList<Archivos>()
        llenarListaArchivos(listaArchivos)

        val recyclerArchivos = findViewById<RecyclerView>(R.id.rcv_Archivos)

        recyclerArchivos.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerArchivos.adapter = RecyclerViewAdapterArchivos(listaArchivos,this)

        val listaBibliotecas = ArrayList<Bibliotecas>()
        llenarListaBibliotecas(listaBibliotecas)

        val recyclerBibliotecas = findViewById<RecyclerView>(R.id.rcv_Bibliotecas)

        recyclerBibliotecas.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerBibliotecas.adapter = RecyclerViewAdapterBibliotecas(listaBibliotecas,this)

    }

    fun llenarListaArchivos(listaArchivos: ArrayList<Archivos>) {
        listaArchivos.add(
            Archivos(
                R.mipmap.pdf,
                "Documento 1",
                "15 MB",
                "15 ago. a las 15:05 p.m.",
                R.drawable.ic_options
            )
        )

        listaArchivos.add(
            Archivos(
                R.mipmap.word,
                "Documento de ejemplo",
                "5 MB",
                "15 de jul. a las 12:00 a.m.",
                R.drawable.ic_options
            )
        )

        listaArchivos.add(
            Archivos(
                R.mipmap.word,
                "Tesis",
                "0 KB",
                "7 sep. 2015",
                R.drawable.ic_options
            )
        )

        listaArchivos.add(
            Archivos(
                R.mipmap.pdf,
                "Ensayo de Firebase",
                "250 KB",
                "hace 18 horas",
                R.drawable.ic_options
            )
        )

        listaArchivos.add(
            Archivos(
                R.mipmap.powerpoint,
                "Presentacion - Cloud",
                "2 MB",
                "hace 15 segundos",
                R.drawable.ic_options
            )
        )
    }

    fun colorCarpeta(): Int {
        val listaColores = ArrayList<Int>()
        listaColores.add(R.color.ODCarpetas1)
        listaColores.add(R.color.ODCarpetas2)
        listaColores.add(R.color.ODCarpetas3)
        listaColores.add(R.color.ODCarpetas4)
        print(listaColores[(0..3).random().toInt()])
        return listaColores[(0..3).random().toInt()]
    }

    fun llenarListaBibliotecas(listaBiblioteca: ArrayList<Bibliotecas>) {
        listaBiblioteca.add(
            Bibliotecas(
                "E",
                colorCarpeta(),
                "Ejemplo"
            )
        )

        listaBiblioteca.add(
            Bibliotecas(
                "SB",
                colorCarpeta(),
                "Segundo Bimestre"
            )
        )

        listaBiblioteca.add(
            Bibliotecas(
                "AM",
                colorCarpeta(),
                "Aplicaciones Móviles"
            )
        )

        listaBiblioteca.add(
            Bibliotecas(
                "NS",
                colorCarpeta(),
                "No sé"
            )
        )
    }

}