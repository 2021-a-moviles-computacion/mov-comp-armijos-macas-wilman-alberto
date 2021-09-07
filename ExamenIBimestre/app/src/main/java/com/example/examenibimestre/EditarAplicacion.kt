package com.example.examenibimestre

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class EditarAplicacion : AppCompatActivity() {
    var idAplicacion = 0
    var idDesarrolladorAplicacion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_aplicacion)

        val txt_desarrolladorAplicacion = findViewById<EditText>(
            R.id.txt_desarrolladorEditarAplicacionEdit
        )

        val txt_nombreAplicacion = findViewById<EditText>(
            R.id.txt_NombreAplicacionEdit
        )

        val desarrolladorID = intent.getIntExtra("idDesarrollador",0)
        idDesarrolladorAplicacion = desarrolladorID

        val desarrollador = intent.getStringExtra("desarrollador")
        txt_desarrolladorAplicacion.setText(desarrollador)


        val aplicacion = intent.getParcelableExtra<Aplicacion>("Aplicacion")
        txt_nombreAplicacion.setText(aplicacion!!.getNombre()!!)
        idAplicacion = aplicacion.getIdAplicacion()!!

        txt_desarrolladorAplicacion.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        txt_nombreAplicacion.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        val txt_LenguajeProgramacion = findViewById<EditText>(
            R.id.txt_LenguajeAplicacionEdit
        )

        txt_LenguajeProgramacion.setText(aplicacion.getLenguajeProgramacion())

        val txt_Plataforma = findViewById<EditText>(
            R.id.txt_PlataformaAplicacionEdit
        )

        txt_Plataforma.setText(aplicacion.getPlataforma())

        val txt_PublicoObjetivo = findViewById<EditText>(
            R.id.txt_ObjetivoAplicacionEdit
        )

        txt_PublicoObjetivo.setText(aplicacion.getPublicoObjetivo())

        val txt_Terminado = findViewById<EditText>(
            R.id.txt_TerminadoAplicacionEdit
        )

        txt_Terminado.setText(aplicacion.isTerminado().toString())


        val txt_Precio = findViewById<EditText>(
            R.id.txt_PrecioAplicacionEdit
        )

        txt_Precio.setText(aplicacion.getPrecio().toString())

        val editarAplicacion = findViewById<Button>(
            R.id.btn_EditarAplicacion
        )

        val clicEditarAplicacion = editarAplicacion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Aplicación")
            builder.setMessage("¿Está seguro que desea editar la Aplicación?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    var terminadoValor=true
                    if (txt_Terminado.text.toString()=="false") {
                        terminadoValor=false
                    }
                    DataBaseCompanion.Database!!.actualizarAplicacion(
                        idAplicacion,
                        idDesarrolladorAplicacion,
                        txt_nombreAplicacion.text.toString(),
                        txt_LenguajeProgramacion.text.toString(),
                        txt_Plataforma.text.toString(),
                        txt_PublicoObjetivo.text.toString(),
                        terminadoValor,
                        txt_Precio.text.toString().toDouble()
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

        val volverAplicacion = findViewById<Button>(
            R.id.btn_regresarVerAplicacion
        )

        val clicVolverAplicacion = volverAplicacion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Aplicación")
            builder.setMessage("¿Está seguro que desea volver a la lista de Aplicaciones?")
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