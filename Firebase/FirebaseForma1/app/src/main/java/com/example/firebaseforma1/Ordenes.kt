package com.example.firebaseforma1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.view.get
import com.example.firebaseforma1.DTOs.FirestoreProductoDto
import com.example.firebaseforma1.DTOs.FirestoreRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Ordenes : AppCompatActivity() {

    var posicionRestaurante = 0
    var posicionProducto = 0
    val listaOrdenes = arrayListOf<ObjetoOrden>()
    var precioTotal = 0.0
    var listaProductos = arrayListOf<ObjetoProducto>()
    var listaRestaurantes = arrayListOf<ObjetoRestaurante>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordenes)

        cargarDatos()

        val sp_Restaurantes = findViewById<Spinner>(R.id.sp_Restaurante)
        sp_Restaurantes.onItemSelectedListener = object:
        AdapterView.OnItemClickListener, OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posicionRestaurante=position
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posicionRestaurante=position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        val sp_Productos = findViewById<Spinner>(R.id.sp_Producto)
        sp_Productos.onItemSelectedListener = object:
            AdapterView.OnItemClickListener, OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posicionProducto=position
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                posicionProducto=position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

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

        actualizarLista()

    }

    fun actualizarLista() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaOrdenes
        )

        val ltv_ListaOrdenes = findViewById<ListView>(
            R.id.ltv_ListaPedidos
        )

        ltv_ListaOrdenes.adapter=adapter
        registerForContextMenu(ltv_ListaOrdenes)
    }


    fun cargarDatos() {
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

            }
            .addOnFailureListener {
                Log.i("Restaurante","Restaurantes no obtenidos")
            }

        val referencia2 = db
            .collection("Producto")
        referencia2
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

                agregarAlSpinner()

            }
            .addOnFailureListener {
                Log.i("Producto","Productos no obtenidos")
            }

    }

    fun agregarProducto() {
        val txv_Cantidad = findViewById<EditText>(R.id.txv_CantidadProducto)

        if (posicionRestaurante>=0 && posicionProducto>=0 && (txv_Cantidad.text!=null || txv_Cantidad.getText().toString()!="")) {
            listaOrdenes.add(
                ObjetoOrden(
                    listaRestaurantes[posicionRestaurante].nombre,
                    listaProductos[posicionProducto].nombre,
                    listaProductos[posicionProducto].precio,
                    txv_Cantidad.text.toString(),
                )
            )
        } else {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
        }

        actualizarPrecioTotal()
        actualizarLista()
    }

    fun actualizarPrecioTotal() {
        precioTotal=0.0
        val txv_precioTotal = findViewById<TextView>(R.id.txv_PrecioTotal)
        listaOrdenes.forEach {
            precioTotal = precioTotal + it.cantidadProducto.toDouble()*it.precioProducto.toDouble()
        }
        txv_precioTotal.text="$${precioTotal}"
    }

    fun completarOrden() {

    }

    fun agregarAlSpinner() {
        val sp_Restaurantes = findViewById<Spinner>(R.id.sp_Restaurante)

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaRestaurantes)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_Restaurantes.adapter = adapter1

        val sp_Productos = findViewById<Spinner>(R.id.sp_Producto)

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProductos)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_Productos.adapter = adapter2

    }


}