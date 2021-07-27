package com.example.examenibimestre

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class EditarDesarrollador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_desarrollador)

        val txt_nombre = findViewById<EditText>(
            R.id.txt_NombreDesarrolladorEdit
        )

        val desarrollador = intent.getParcelableExtra<Desarrollador>("desarrollador")
        txt_nombre.setText(desarrollador!!.getNombre()!!)

        txt_nombre.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        val txt_instruccion = findViewById<EditText>(
            R.id.txt_InstruccionDesarrolladorEdit
        )

        txt_instruccion.setText(desarrollador.getInstruccion())

        val txt_empresa = findViewById<EditText>(
            R.id.txt_EmpresaDesarrolladorEdit
        )

        txt_empresa.setText(desarrollador.getEmpresa())

        val txt_edad = findViewById<EditText>(
            R.id.txt_EdadDesarrolladorEdit
        )

        txt_edad.setText(desarrollador.getEdad().toString())

        val txt_sexo = findViewById<EditText>(
            R.id.txt_SexoDesarrolladorEdit
        )

        txt_sexo.setText(desarrollador.getSexo().toString())

        val editarDesarrollador = findViewById<Button>(
            R.id.btn_EditarDesarrollador
        )

        val idDesarrollador = desarrollador.getIdDesarrollador()

        val clicEditarDesarrollador = editarDesarrollador.setOnClickListener {
            DataBaseCompanion.Database!!.actualizarDesarrollador(
                idDesarrollador!!,
                txt_nombre.text.toString(),
                txt_instruccion.text.toString(),
                txt_empresa.text.toString(),
                txt_edad.text.toString().toInt(),
                txt_nombre.text.toString().toCharArray()[0]
            )
            this.finish()
        }

        val volverDesarrollador = findViewById<Button>(
            R.id.btn_regresarVerDesarrollador
        )

        val clicVolverDesarrollador = volverDesarrollador.setOnClickListener {
            this.finish()
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