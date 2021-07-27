package com.example.examenibimestre

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CrearDesarrollador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_desarrollador)

        val txt_Nombre = findViewById<EditText>(
            R.id.txt_NombreDesarrollador
        )

        val txt_Instruccion = findViewById<EditText>(
            R.id.txt_InstruccionDesarrollador
        )

        val txt_Empresa = findViewById<EditText>(
            R.id.txt_EmpresaDesarrollador
        )

        val txt_Edad = findViewById<EditText>(
            R.id.txt_EdadDesarrollador
        )

        val txt_Sexo = findViewById<EditText>(
            R.id.txt_SexoDesarrollador
        )

        val crearDesarrollador = findViewById<Button>(
            R.id.btn_CrearDesarrollador
        )

        /*
        val desarrollador = Desarrollador()
        desarrollador.setNombre(txt_Nombre.text.toString())
        desarrollador.setInstruccion(txt_Instruccion.text.toString())
        desarrollador.setEmpresa(txt_Empresa.text.toString())
        desarrollador.setEdad(txt_Edad.text.toString().toInt())
        desarrollador.setSexo(txt_Sexo.text.toString().toCharArray().get(0))
        */

        val clicCrearDesarrollador = crearDesarrollador.setOnClickListener {
            val resultadoCrearDesarrollador = DataBaseCompanion.Database?.crearDesarrollador(
                txt_Nombre.text.toString(),
                txt_Instruccion.text.toString(),
                txt_Empresa.text.toString(),
                txt_Edad.text.toString().toInt(),
                txt_Sexo.text.toString().toCharArray().get(0)
            )
            if (resultadoCrearDesarrollador == true) {
                this.finish()
            }

        }

    }

    fun openActivity(
        clase: Class<*>,
    ) {
        val myIntent = Intent(
            this,
            clase
        )
        startActivity(myIntent)
    }

}