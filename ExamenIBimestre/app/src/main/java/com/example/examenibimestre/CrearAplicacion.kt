package com.example.examenibimestre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText

class CrearAplicacion : AppCompatActivity() {
    var idDesarrollador = 0
    var nombreDesarrollador = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_aplicacion)

        val desarrolladorID = intent.getIntExtra("idDesarrollador",0)
        idDesarrollador = desarrolladorID

        val desarrolladorNombre = intent.getStringExtra("desarrollador")
        nombreDesarrollador = desarrolladorNombre!!

        val txt_NombreDesarrollador = findViewById<EditText>(
            R.id.txt_desarrolladorCrearAplicacion
        )

        txt_NombreDesarrollador.setText(nombreDesarrollador)

        txt_NombreDesarrollador.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )


        val txt_Nombre = findViewById<EditText>(
            R.id.txt_NombreAplicacion
        )

        val txt_LenguajeProgramacion = findViewById<EditText>(
            R.id.txt_LenguajeAplicacion
        )

        val txt_Plataforma = findViewById<EditText>(
            R.id.txt_PlataformaAplicacion
        )

        val txt_PublicoObjetivo = findViewById<EditText>(
            R.id.txt_ObjetivoAplicacion
        )

        val txt_Terminado = findViewById<EditText>(
            R.id.txt_TerminadoAplicacion
        )


        val txt_Precio = findViewById<EditText>(
            R.id.txt_PrecioAplicacion
        )

        val btnCrearAplicacion = findViewById<Button>(
            R.id.btn_CrearAplicacion
        )

        val clicCrearAplicacion = btnCrearAplicacion.setOnClickListener {
            var terminadoValor = true
            if (txt_Terminado.text.toString().toInt()==0) {
                terminadoValor = false
            }
            val resultadoCrearAplicacion = DataBaseCompanion.Database?.crearAplicacion(
                idDesarrollador,
                txt_Nombre.text.toString(),
                txt_LenguajeProgramacion.text.toString(),
                txt_Plataforma.text.toString(),
                txt_PublicoObjetivo.text.toString(),
                terminadoValor,
                txt_Precio.text.toString().toDouble()
            )
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