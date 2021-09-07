package com.example.firebaseforma1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Producto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        val btn_CrearProducto = findViewById<Button>(R.id.btn_CrearProducto)
        btn_CrearProducto.setOnClickListener {
            crearProducto()
        }

    }

    fun crearProducto() {
        val txt_NombreProducto = findViewById<TextView>(R.id.txv_Nombre)
        val txt_PrecioProducto = findViewById<TextView>(R.id.txv_Precio)
        if ((txt_NombreProducto.text != null) || (txt_NombreProducto.text!="")
            && (txt_PrecioProducto.text != null) || (txt_PrecioProducto.text!="")) {
            val nuevoProducto = hashMapOf<String, Any>(
                "nombre" to txt_NombreProducto.text.toString(),
                "precio" to txt_PrecioProducto.text.toString()
            )
            val db = Firebase.firestore
            val referencia = db.collection("Producto")
                .add(nuevoProducto)
                .addOnSuccessListener {
                    txt_NombreProducto.text=""
                    txt_PrecioProducto.text=""
                }
                .addOnFailureListener {

                }
        }
    }


}