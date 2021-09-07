package com.example.examenibimestre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIrDesarrollador = findViewById<Button>(
            R.id.btn_IrDesarrolladores
        )

        val irDesarrolladores = btnIrDesarrollador.setOnClickListener {
            openActivity(PrincipalDesarrollador::class.java)
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