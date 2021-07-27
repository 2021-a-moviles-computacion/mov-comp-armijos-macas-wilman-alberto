package com.example.examenibimestre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

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

        listaActual()


        val btnIrCrearAplicacion = findViewById<Button>(
            R.id.btn_IrCrearAplicacion
        )

        val irCrearAplicacion = btnIrCrearAplicacion.setOnClickListener {
            openActivityParameters(CrearAplicacion::class.java,desarrollador.getIdDesarrollador()!!,desarrollador.getNombre()!!,null)
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
                if (DataBaseCompanion.Database != null) {
                    DataBaseCompanion.Database!!.eliminarAplicacion(aplicacionSeleccionada.getIdAplicacion()!!,idDesarrollador)
                    adapter?.remove(adapter!!.getItem(selectedItemPosition))
                    adapter?.notifyDataSetChanged()
                }
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