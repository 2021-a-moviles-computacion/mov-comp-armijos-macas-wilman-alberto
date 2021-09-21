package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PrincipalAplicacion : AppCompatActivity() {
    var selectedItemPosition = 0
    var adapter: ArrayAdapter<Desarrollador>? = null
    var listaAplicaciones = ArrayList<Aplicacion>()
    val responseCode = 404
    var idDesarrollador = ""
    var nombreDesarrollador = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_aplicacion)

        val desarrollador = intent.getParcelableExtra<Desarrollador>("desarrollador")
        idDesarrollador = desarrollador!!.getIdDesarrollador()!!
        nombreDesarrollador = desarrollador.getNombre()!!

        val txt_desarrollador = findViewById<EditText>(
            R.id.txt_desarrolladorAplicacion
        )

        txt_desarrollador.setText(nombreDesarrollador)
        txt_desarrollador.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )


        listaActual()


        val btnIrCrearAplicacion = findViewById<Button>(
            R.id.btn_IrCrearAplicacion
        )

        btnIrCrearAplicacion.setOnClickListener {
            openActivityParameters(CrearAplicacion::class.java,desarrollador.getIdDesarrollador()!!,desarrollador.getNombre()!!,null)
        }

        val btnVolverDesarrollador = findViewById<Button>(
            R.id.btn_RegresarAplicacionesDesarrollador
        )

        btnVolverDesarrollador.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Aplicaciones")
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


    override fun onResume() {
        super.onResume()
        listaActual()
    }

    fun listaActual(){
        val listaAplicaciones = ArrayList<Aplicacion>()
        this.listaAplicaciones = listaAplicaciones
        val db = Firebase.firestore
        db.collection("Aplicacion").whereEqualTo("idDesarrolladorAplicacion",idDesarrollador).get().addOnSuccessListener {
            for (aplicacion in it) {
                listaAplicaciones.add(
                    Aplicacion(
                        aplicacion.getString("idAplicacion"),
                        aplicacion.getString("idDesarrolladorAplicacion"),
                        aplicacion.getString("nombreDesarrolladorAplicacion"),
                        aplicacion.getString("nombreAplicacion"),
                        aplicacion.getString("lenguajeAplicacion"),
                        aplicacion.getString("plataformaAplicacion"),
                        aplicacion.getString("publicoObjetivoAplicacion"),
                        aplicacion.getBoolean("terminadoAplicacion"),
                        aplicacion.getDouble("precioAplicacion"),
                        aplicacion.getString("latitudAplicacion"),
                        aplicacion.getString("longitudAplicacion")
                    )
                )
            }
            Log.i("firebase-firestore","Se obtuvo la lista de aplicaciones correctamente")

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaAplicaciones
            )

            val listViewAplicaciones = findViewById<ListView>(
                R.id.ltv_Aplicaciones
            )

            listViewAplicaciones.adapter=adapter
            registerForContextMenu(listViewAplicaciones)

        }
            .addOnFailureListener {
                Log.i("firebase-firestore","No se pudo obtener la lista de aplicaciones")
            }

    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, view, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_aplicaciones,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        selectedItemPosition = posicion
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        var aplicacionSeleccionada = this.listaAplicaciones[selectedItemPosition]
        return when(item?.itemId){
            R.id.mi_ver_mapa -> {
                openActivityMap(MapaAplicacion::class.java, aplicacionSeleccionada.getNombre()!!, aplicacionSeleccionada.getLatitud()!!, aplicacionSeleccionada.getLongitud()!!)
                return true
            }
            R.id.mi_ver_editarAp -> {
                openActivityParameters(EditarAplicacion::class.java,idDesarrollador,nombreDesarrollador,aplicacionSeleccionada)
                adapter?.notifyDataSetChanged()
                return true
            }
            R.id.mi_eliminarAp -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar")
                builder.setMessage("¿Está seguro que desea eliminar la Aplicación ${aplicacionSeleccionada.getNombre()}?")
                builder.setPositiveButton(
                    "Sí", DialogInterface.OnClickListener { dialog, id ->
                        if (listaAplicaciones != null) {
                            FirestoreDatabase().deleteAplicacionFS(aplicacionSeleccionada.getIdAplicacion()!!)
                            adapter?.remove(adapter!!.getItem(selectedItemPosition))
                            adapter?.notifyDataSetChanged()
                            listaActual()
                        }
                        dialog.cancel()
                    }
                )
                builder.setNegativeButton(
                    "No", DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    }
                )
                builder.show()

                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun openActivityParameters(
        clase: Class<*>,
        idDesarrollador: String,
        nombreDesarrollador: String,
        aplicacion: Aplicacion?=null
    ) {
        val myIntent = Intent(
            this,
            clase
        )
        myIntent.putExtra("idDesarrollador",idDesarrollador)
        myIntent.putExtra("desarrollador",nombreDesarrollador)
        myIntent.putExtra("Aplicacion",aplicacion)
        startActivityForResult(myIntent, responseCode)
    }

    fun openActivityMap(
        clase: Class<*>,
        nombreAplicacion: String,
        latitud: String,
        longitud: String
    ) {
        val myIntent = Intent(
            this,
            clase
        )
        myIntent.putExtra("nombreAplicacion",nombreAplicacion)
        myIntent.putExtra("latitudMapa",latitud)
        myIntent.putExtra("longitudMapa",longitud)
        startActivityForResult(myIntent, responseCode)
    }

}