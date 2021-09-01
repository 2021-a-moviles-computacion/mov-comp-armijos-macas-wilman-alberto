package com.example.firebaseforma1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Restaurante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurante)

        val btn_CrearRestaurante = findViewById<Button>(R.id.btn_CrearRestaurante)
        btn_CrearRestaurante.setOnClickListener {
            crearRestaurante()
        }

    }

    fun crearRestaurante() {
        val txt_NombreRestaurante = findViewById<TextView>(R.id.txv_NombreRestaurante)
        if ((txt_NombreRestaurante.text != null) || (txt_NombreRestaurante.text!="")) {
            val nuevoRestaurante = hashMapOf<String, Any>(
                "nombre" to txt_NombreRestaurante.text.toString(),
            )
            val db = Firebase.firestore
            val referencia = db.collection("Restaurante")
                .add(nuevoRestaurante)
                .addOnSuccessListener {
                    txt_NombreRestaurante.text=""
                }
                .addOnFailureListener {

                }
        }
    }

}