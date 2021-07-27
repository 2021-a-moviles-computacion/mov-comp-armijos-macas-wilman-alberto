package com.example.asintro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import org.jetbrains.annotations.Contract

class MainActivity : AppCompatActivity() {
    val responseCode = 400
    val responseCodeImplicit = 406

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnIrACicloVida1 = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        btnIrACicloVida1.setOnClickListener {
            abrirCicloVida(ACicloVida1::class.java)
        }
        val btnIrListView = findViewById<Button>(
            R.id.btn_list_view
        )
        btnIrListView.setOnClickListener {
            abrirCicloVida(ListViewActivity::class.java)
        }

        val btnIrIntent = findViewById<Button>(
            R.id.btn_goIntent
        )
        btnIrIntent.setOnClickListener {
            abrirActividadConParametros(IntentExplicitParameters::class.java)
        }

        val btnIrImplicito = findViewById<Button>(
            R.id.btn_intent_implicito
        )

        btnIrImplicito.setOnClickListener {
            val intentRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            startActivityForResult(intentRespuesta, responseCodeImplicit)
        }

        DataBaseCompanion.TablaUsuario = SQLiteHelper(this)

        val btnIrSQLiteActivity = findViewById<Button>(
            R.id.btn_SQLite
        )

        btnIrSQLiteActivity.setOnClickListener {
            abrirCicloVida(SQLiteActivity::class.java)
        }

        val btnIrRecyclerView = findViewById<Button>(
            R.id.btn_recyclerView
        )

        btnIrRecyclerView.setOnClickListener {
            abrirCicloVida(RecyclerViewAct::class.java)
        }

        val btnIrHTTP = findViewById<Button>(
            R.id.btn_Ir_HTTP
        )

        btnIrHTTP.setOnClickListener {
            abrirCicloVida(HTTPActivity::class.java)
        }

        /* if(DataBaseCompanion.TablaUsuario!=null) {
            DataBaseCompanion.TablaUsuario.crearUsuarioFormulario()
            DataBaseCompanion.TablaUsuario.consultarUsuarioPorId()
            DataBaseCompanion.TablaUsuario.consultarUsuarioPorId()
            DataBaseCompanion.TablaUsuario.consultarUsuarioPorId()
        }*/

    }

    fun abrirCicloVida(
        myClass: Class<*>
    ) {
        val explicitIntent = Intent(
            this,
            myClass
        )
        startActivity(explicitIntent)
    }

    fun abrirActividadConParametros(
        myClass: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            myClass
        )
        intentExplicito.putExtra("nombre","wilman")
        intentExplicito.putExtra("apellido","armijos")
        intentExplicito.putExtra("edad",22)
        intentExplicito.putExtra(
            "entrenador",
            TrainerBean("Wilman", "no sé",LeagueBean("Hola","Adios"))
        )
        startActivityForResult(intentExplicito, responseCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            responseCode -> {
                if (resultCode==RESULT_OK) {
                    Log.i("intent-explicito","SI actualizó los datos")
                    if (data!=null) {
                        val nombre = data.getStringExtra("nombreModificado")
                        val edad = data.getIntExtra("edadModificada",0)
                        val trainer = data.getParcelableExtra<TrainerBean>("entrenadorModificado")
                        Log.i("intent-explicito","${nombre}")
                        Log.i("intent-explicito","${edad}")
                        Log.i("intent-explicito","${trainer}")

                    }
                }
            }
            responseCode -> {
                if (resultCode==RESULT_OK) {
                    if (data!=null) {
                        if (data.data!=null) {
                            val cursor = contentResolver.query(
                                data.data!!,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            cursor?.moveToFirst()
                            val indiceTelefono = cursor?.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                            val telefono = cursor?.getString(
                                indiceTelefono!!
                            )
                            cursor?.close()
                            Log.i("resultado","Telefono: ${telefono}")
                        }
                    }
                }
            }
        }
    }

}

