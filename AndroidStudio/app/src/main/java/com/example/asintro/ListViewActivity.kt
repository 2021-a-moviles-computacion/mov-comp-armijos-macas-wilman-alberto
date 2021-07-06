package com.example.asintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class ListViewActivity : AppCompatActivity() {
    var selectedItemPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        val trainersArray = MemoryDataBase.trainerBeanArray

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            trainersArray
        )

        val listViewEjemplo = findViewById<ListView>(
            R.id.ltv_ejemplo
        )
        listViewEjemplo.adapter=adapter

        val btnChangeNumber = findViewById<Button>(
            R.id.btn_agregar
        )
        btnChangeNumber.setOnClickListener { addTrainer(
            TrainerBean("Hello","World",LeagueBean("Hola","Mundo")),
            trainersArray,
            adapter
        ) }


        listViewEjemplo.setOnItemLongClickListener{adapterView, view, position, id ->
            Log.i("listViewEjemplo","click done ${position}")
            return@setOnItemLongClickListener true
        }

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Titulo")
        builder.setMessage("Mensaje")

        val seleccionUsuario = booleanArrayOf(
            true,
            false,
            false
        )

        val opciones = resources.getStringArray(R.array.string_array_dialog_options)
        builder.setMultiChoiceItems(
            opciones,
            seleccionUsuario,
            { dialog, which, isChecked ->
                Log.i("listView", "${which} ${isChecked}")
            }
        )

        builder.setPositiveButton(
            "Si",
            {
                dialog,which -> Log.i("List-view","Si")
            }
        )

        builder.setNegativeButton(
            "No",
            null
        )

        val dialogo = builder.create()
        dialogo.show()



        //registerForContextMenu(listViewEjemplo)

    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        selectedItemPosition = id

        Log.i("list-view","${selectedItemPosition}")
        Log.i("list-view","${MemoryDataBase.trainerBeanArray[id]}")

    }

    fun addTrainer(
        value: TrainerBean,
        array: ArrayList<TrainerBean>,
        adapter: ArrayAdapter<TrainerBean>,
    ) {
        array.add(value)
        adapter.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId) {
            R.id.mi_editar -> {
                Log.i("list-view","Editar ${MemoryDataBase.trainerBeanArray[selectedItemPosition]}")
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("list-view","Eliminar ${MemoryDataBase.trainerBeanArray[selectedItemPosition]}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


}
