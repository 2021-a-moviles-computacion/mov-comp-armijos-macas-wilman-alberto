package com.example.asintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class SQLiteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        val textId = this.findViewById<TextView>(
            R.id.idUsuario
        )

        val btnCrearUsuario = findViewById<Button>(
            R.id.btn_Crear
        )

        btnCrearUsuario.setOnClickListener {
            if (textId.text!="") {
                textId.text="Hola"
                Log.i("crear","Se leyó el ID")
            }
        }

        val btnBuscarUsuario = findViewById<Button>(
            R.id.btn_Buscar
        )
        btnBuscarUsuario.setOnClickListener {

        }


    }
}