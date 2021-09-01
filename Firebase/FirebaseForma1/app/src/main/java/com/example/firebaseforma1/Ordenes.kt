package com.example.firebaseforma1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.example.firebaseforma1.DTOs.FirestoreProductoDto
import com.example.firebaseforma1.DTOs.FirestoreRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Ordenes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordenes)

        extraerRestaurantes()
        extraerProductos()

        val sp_Restaurantes = findViewById<Spinner>(R.id.sp_Restaurante)
        sp_Restaurantes.onItemSelectedListener = object:
        AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.i("item-select","Item selected was ${sp_Restaurantes.get(position)}")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val txv_Cantidad = findViewById<EditText>(R.id.txv_CantidadProducto)

        val btn_AgregarProducto = findViewById<Button>(R.id.btn_AgregarOrden)
        btn_AgregarProducto.setOnClickListener {
            agregarProducto()
        }

        val btn_CompletarPedido = findViewById<Button>(R.id.btn_CompletarOrden)
        btn_CompletarPedido.setOnClickListener {
            completarOrden()
        }

    }

    fun agregarProducto() {

    }

    fun completarOrden() {

    }

    fun extraerRestaurantes(){
        val sp_Restaurantes = findViewById<Spinner>(R.id.sp_Restaurante)
        val listaRestaurantes = ArrayList<ObjetoRestaurante>()

        val db = Firebase.firestore

        val referencia = db
            .collection("Restaurante")
        referencia
            .get()
            .addOnSuccessListener {
                for (restaurante in it) {
                    val restauranteObtenido: FirestoreRestauranteDto? =
                        restaurante.toObject(FirestoreRestauranteDto::class.java)
                        listaRestaurantes.add(
                            ObjetoRestaurante(
                                restauranteObtenido!!.nombre.toString()
                            )
                        )
                    }
                Log.i("Restaurante","Restaurantes obtenidos")

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaRestaurantes)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sp_Restaurantes.adapter = adapter

                Log.i("Restaurante","lista de restaurantes")
                listaRestaurantes.forEach {
                    Log.i("Restaurante","${it.nombre}")
                }
            }
            .addOnFailureListener {
                Log.i("Restaurante","Restaurantes no obtenidos")
            }

    }

    fun extraerProductos() {
        val sp_Producto = findViewById<Spinner>(R.id.sp_Producto)
        val listaProductos = ArrayList<ObjetoProducto>()

        val db = Firebase.firestore

        val referencia = db
            .collection("Producto")
        referencia
            .get()
            .addOnSuccessListener {
                for (producto in it) {
                val productoObtenido: FirestoreProductoDto? =
                    producto.toObject(FirestoreProductoDto::class.java)
                    listaProductos.add(
                        ObjetoProducto(
                            productoObtenido!!.nombre.toString(),
                            productoObtenido.precio.toString()
                        )
                    )
            }
                Log.i("Producto","Productos no obtenidos")

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProductos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sp_Producto.adapter = adapter

                Log.i("Producto","lista de productos")
                listaProductos.forEach {
                    Log.i("Producto","${it.nombre}")
                }

            }
            .addOnFailureListener {
                Log.i("Producto","Productos no obtenidos")
            }


    }
}