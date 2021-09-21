package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class EditarDesarrollador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_desarrollador)

        val desarrollador = intent.getParcelableExtra<Desarrollador>("desarrollador")

        val txt_cedula = findViewById<EditText>(
            R.id.txt_CedulaDesarrolladorEdit
        )

        txt_cedula.setText(desarrollador!!.getIdDesarrollador())

        txt_cedula.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        val txt_nombre = findViewById<EditText>(
            R.id.txt_NombreDesarrolladorEdit
        )
        txt_nombre.setText(desarrollador!!.getNombre()!!)

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

        editarDesarrollador.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Desarrollador")
            builder.setMessage("¿Está seguro que desea editar el Desarrollador?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    FirestoreDatabase().updateDesarrolladorFS(
                        idDesarrollador!!,
                        txt_nombre.text.toString(),
                        txt_instruccion.text.toString(),
                        txt_empresa.text.toString(),
                        txt_edad.text.toString().toInt(),
                        txt_sexo.text.toString().toCharArray()[0]
                    )
                    dialog.cancel()
                }
            )
            builder.setNegativeButton(
                "No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                }
            )
            builder.show()

        }

        val volverDesarrollador = findViewById<Button>(
            R.id.btn_regresarVerDesarrollador
        )

        volverDesarrollador.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Desarrollador")
            builder.setMessage("¿Está seguro que desea volver a la lista de Desarrolladores?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    this.finish()
                }
            )
            builder.setNegativeButton(
                "No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                }
            )
            builder.show()
        }

    }

}