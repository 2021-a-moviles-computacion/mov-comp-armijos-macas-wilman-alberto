package com.example.asintro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class IntentExplicitParameters : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_explicit_parameters)

        val nombre = intent?.getStringExtra("nombre")
        val apellido = intent?.getStringExtra("apellido")
        val edad = intent?.getIntExtra("edad",0)
        val trainer = intent?.getParcelableExtra<TrainerBean>("entrenador")

        val btn_respuesta_intent = findViewById<Button>(
            R.id.btn_intent_respuesta
        )

        Log.i("intent-explicito-parametros","${nombre}")
        Log.i("intent-explicito-parametros","${apellido}")
        Log.i("intent-explicito-parametros","${edad}")
        Log.i("intent-explicito-parametros","${trainer}")

        btn_respuesta_intent.setOnClickListener {
            val intentDevolverParametros = Intent()
            intentDevolverParametros.putExtra("nombreModificado","Alberto")
            intentDevolverParametros.putExtra("edadModificada",21)
            intentDevolverParametros.putExtra("entrenadorModificado",
            TrainerBean("Alberto","top 500",LeagueBean("Nombre","Descripcion")))

            setResult(
                RESULT_OK,
                intentDevolverParametros
            )
            finish()

        }

    }
}