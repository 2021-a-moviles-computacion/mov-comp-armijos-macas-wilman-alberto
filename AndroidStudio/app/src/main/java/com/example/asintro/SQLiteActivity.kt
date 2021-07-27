package com.example.asintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class SQLiteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        val textId = findViewById<TextView>(
            R.id.idUsuario
        )

        val textNombre = findViewById<TextView>(
            R.id.nombreUsuario
        )

        val textDescripcion = findViewById<TextView>(
            R.id.descripcionUsuario
        )

        val btnCrearUsuario = findViewById<Button>(
            R.id.btn_Crear
        )

        val btnBuscarUsuario = findViewById<Button>(
            R.id.btn_Buscar
        )

        val btnActualizarUsuario = findViewById<Button>(
            R.id.btn_Actualizar
        )

        btnActualizarUsuario.visibility = View.GONE

        val btnEliminarUsuario = findViewById<Button>(
            R.id.btn_Eliminar
        )

        btnEliminarUsuario.visibility = View.GONE

        btnCrearUsuario.setOnClickListener {
            if (
                (textNombre.text!="" || textNombre.text!=null) &&
                (textDescripcion.text!="" || textDescripcion.text!=null)
            ) {
                val resultadoCrear = DataBaseCompanion.TablaUsuario?.crearUsuarioFormulario(
                    textNombre.text.toString(),
                    textDescripcion.text.toString()
                )
                Log.i("SQLite","El intento de crear resultó en: ${resultadoCrear}")
                Log.i("SQLite","Usuario registrado con éxito")
            }
            textId.text=""
            textNombre.text=""
            textDescripcion.text=""
        }

        btnBuscarUsuario.setOnClickListener {
            if (textId.text!="" || textId.text!=null) {
                val resultadoBuscar = DataBaseCompanion.TablaUsuario?.consultarUsuarioID(textId.text.toString().toInt())
                textId.text=resultadoBuscar?.id.toString()
                textNombre.text=resultadoBuscar?.nombre
                textDescripcion.text=resultadoBuscar?.descripcion
                Log.i("SQLite","El intento de buscar resultó en: ${resultadoBuscar.toString()}")
                Log.i("SQLite","Usuario encontrado")
            }
            btnActualizarUsuario.visibility = View.VISIBLE
            btnEliminarUsuario.visibility = View.VISIBLE
        }

        btnActualizarUsuario.setOnClickListener {
            if (
                (textId.text!="" || textId.text!=null) &&
                (textNombre.text!="" || textNombre.text!=null) &&
                (textDescripcion.text!="" || textDescripcion.text!=null)
            ) {
                val resultadoCrear = DataBaseCompanion.TablaUsuario?.actualizarUsuarioFormulario(
                    textNombre.text.toString(),
                    textDescripcion.text.toString(),
                    textId.text.toString().toInt()
                )
                Log.i("SQLite","El intento de actualizar resultó en: ${resultadoCrear}")
                Log.i("SQLite","Usuario actualizado con éxito")
            }
            textId.text=""
            textNombre.text=""
            textDescripcion.text=""
        }

        btnEliminarUsuario.setOnClickListener {
            if (textId.text!="" || textId.text!=null) {
                val resultadoEliminar = DataBaseCompanion.TablaUsuario?.eliminarUsuarioPorID(textId.text.toString().toInt())
                Log.i("SQLite","El intento de eliminar resultó en: ${resultadoEliminar}")
                Log.i("SQLite","Usuario eliminado con éxito")
            }
            textId.text=""
            textNombre.text=""
            textDescripcion.text=""
        }

    }
}