package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PrincipalDesarrollador : AppCompatActivity() {
    var selectedItemPosition = 0
    var adapter: ArrayAdapter<Desarrollador>? = null
    val responseCode = 404
    var listaDesarrolladores = ArrayList<Desarrollador>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_desarrollador)

        actualList()

        val btnIrCrearDesarrollador = findViewById<Button>(
            R.id.btn_IrCrearDesarrollador
        )

        btnIrCrearDesarrollador.setOnClickListener {
            openActivity(CrearDesarrollador::class.java)
        }

        val btnVolverPrincipal = findViewById<Button>(
            R.id.btn_RegresarPaginaPrincipal
        )

        btnVolverPrincipal.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Desarrolladores")
            builder.setMessage("¿Está seguro que desea volver a la Pagina Principal?")
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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, view, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_desarrolladores,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        selectedItemPosition = posicion
    }



    override fun onContextItemSelected(item: MenuItem): Boolean {
        var desarrolladorSeleccionado = listaDesarrolladores[selectedItemPosition]
        return when(item?.itemId){
            R.id.mi_ver_editarDes -> {
                openActivityParameters(EditarDesarrollador::class.java,desarrolladorSeleccionado)
                adapter?.notifyDataSetChanged()
                return true
            }

            R.id.mi_eliminarDes -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar")
                builder.setMessage("¿Está seguro que desea eliminar el Desarrollador ${desarrolladorSeleccionado.getNombre()}?")
                builder.setPositiveButton(
                    "Sí", DialogInterface.OnClickListener { dialog, id ->
                        if (listaDesarrolladores != null) {
                            FirestoreDatabase().deleteDesarrolladorFS(desarrolladorSeleccionado.getIdDesarrollador()!!)
                            adapter?.remove(adapter!!.getItem(selectedItemPosition))
                            adapter?.notifyDataSetChanged()
                            actualList()
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

            R.id.mi_verAplicacionesDes -> {
                    openActivityParameters(PrincipalAplicacion::class.java,desarrolladorSeleccionado)
                    return true
                }
            else -> super.onContextItemSelected(item)
        }

    }

    override fun onResume() {
        super.onResume()
        actualList()
    }

    fun actualList(){
        val listaDesarrolladores = ArrayList<Desarrollador>()
        this.listaDesarrolladores = listaDesarrolladores
        val db = Firebase.firestore
        db.collection("Desarrollador").get().addOnSuccessListener {
            for (desarrollador in it) {
                listaDesarrolladores.add(
                    Desarrollador(
                        desarrollador.getString("idDesarrollador"),
                        desarrollador.getString("nombreDesarrollador"),
                        desarrollador.getString("instruccionDesarrollador"),
                        desarrollador.getString("empresaDesarrollador"),
                        desarrollador.getDouble("edadDesarrollador")!!.toInt(),
                        desarrollador.getString("sexoDesarrollador")!!.toCharArray()[0]
                    )
                )
            }
            Log.i("firebase-firestore","Se obtuvo la lista de desarrolladores correctamente con tamaño ${listaDesarrolladores.size}")

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaDesarrolladores
            )

            val listViewDesarrolladores = findViewById<ListView>(
                R.id.ltv_Desarrolladores
            )

            listViewDesarrolladores.adapter=adapter
            registerForContextMenu(listViewDesarrolladores)

        }
            .addOnFailureListener {
                Log.i("firebase-firestore","No se pudo obtener la lista de desarrolladores")
            }
        Log.i("firebase-firestore","El tamaño de la lista en la pagina principal es ${listaDesarrolladores.size}")
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

    fun openActivityParameters(
        clase: Class<*>,
        desarrollador: Desarrollador
    ) {
        val myIntent = Intent(
            this,
            clase
        )
        myIntent.putExtra("desarrollador",desarrollador)
        startActivityForResult(myIntent, responseCode)
    }

}
