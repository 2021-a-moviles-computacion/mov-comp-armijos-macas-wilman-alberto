package com.example.examen2bimestre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        val btnIrDesarrollador = findViewById<Button>(
            R.id.btn_IrDesarrolladores
        )

        btnIrDesarrollador.setOnClickListener {
            openActivity(PrincipalDesarrollador::class.java)
        }

        val btnSalirAplicacion = findViewById<Button>(
            R.id.btn_SalirAplicacion
        )

        btnSalirAplicacion.setOnClickListener {
            finish()
            System.exit(0)
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