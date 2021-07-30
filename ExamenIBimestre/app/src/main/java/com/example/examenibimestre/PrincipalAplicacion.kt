package com.example.examenibimestre

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class PrincipalAplicacion : AppCompatActivity() {
    var selectedItemPosition = 0
    var adapter: ArrayAdapter<Desarrollador>? = null
    val responseCode = 404
    var idDesarrollador = 0
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

        val irCrearAplicacion = btnIrCrearAplicacion.setOnClickListener {
            openActivityParameters(CrearAplicacion::class.java,desarrollador.getIdDesarrollador()!!,desarrollador.getNombre()!!,null)
        }

        val btnVolverDesarrollador = findViewById<Button>(
            R.id.btn_RegresarAplicacionesDesarrollador
        )

        val regresarDesarrollador = btnVolverDesarrollador.setOnClickListener {
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
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataBaseCompanion.Database!!.consultarListaAplicaciones(this.idDesarrollador)
        )

        val listViewAplicaciones = findViewById<ListView>(
            R.id.ltv_Aplicaciones
        )

        listViewAplicaciones.adapter=adapter
        registerForContextMenu(listViewAplicaciones)
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
        var aplicacionSeleccionada = DataBaseCompanion.Database!!.consultarListaAplicaciones(idDesarrollador)[selectedItemPosition]
        return when(item?.itemId){
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
                        if (DataBaseCompanion.Database != null) {
                            DataBaseCompanion.Database!!.eliminarAplicacion(aplicacionSeleccionada.getIdAplicacion()!!,idDesarrollador)
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
        idDesarrollador: Int,
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


}