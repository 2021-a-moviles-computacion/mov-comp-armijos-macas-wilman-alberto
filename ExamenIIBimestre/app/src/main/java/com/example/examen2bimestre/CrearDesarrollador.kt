package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CrearDesarrollador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_desarrollador)

        val txt_Cedula = findViewById<EditText>(
            R.id.txt_idDesarrollador
        )

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


        crearDesarrollador.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Crear Desarrollador")
            builder.setMessage("¿Está seguro que desea crear el nuevo Desarrollador?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener  { dialog, id ->
                    FirestoreDatabase().createDesarrolladorFS(
                        txt_Cedula.text.toString(),
                        txt_Nombre.text.toString(),
                        txt_Instruccion.text.toString(),
                        txt_Empresa.text.toString(),
                        txt_Edad.text.toString().toInt(),
                        txt_Sexo.text.toString().toCharArray().get(0)
                    )
                    txt_Cedula.setText("")
                    txt_Nombre.setText("")
                    txt_Instruccion.setText("")
                    txt_Empresa.setText("")
                    txt_Edad.setText("")
                    txt_Sexo.setText("")
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
            R.id.btn_RegresarCrearDesarrollador
        )

        volverDesarrollador.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Crear Desarrollador")
            builder.setMessage("¿Está seguro que desea volver a la lista de Desarrolladores?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    // Aquí la lógica
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