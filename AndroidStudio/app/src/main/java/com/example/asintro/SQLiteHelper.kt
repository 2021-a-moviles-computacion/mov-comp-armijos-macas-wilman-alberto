package com.example.asintro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class SQLiteHelper(
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "movilesDataBase",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearUsers="""
            create table usuario(
                id integer primary key autoincrement,
                nombre varchar(50),
                desrcipcion varchar(100)
            )
        """.trimIndent()
        Log.i("onCreateDataBase","Creando la tabla de usuarios")
        db?.execSQL(scriptCrearUsers)
    }

    fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String,
    ):Boolean {
        val conexionEscritura = writableDatabase
        val aGuardar = ContentValues()
        aGuardar.put("nombre",nombre)
        aGuardar.put("descripcion",descripcion)
        val resultadoEscritura: Long = conexionEscritura.insert(
            "usuario",
            null,
            aGuardar
        )
        conexionEscritura.close()
        return if (resultadoEscritura.toInt()==-1) false else true
    }

    fun consultarUsuarioID(id: Int): Usuario{
        val scriptConsultarUsuario = """
            select * from usuario where id=${id}
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        //val arregloUsuario = arrayListOf<Usuario>()
        val usuarioEncontrado = Usuario(0,"","")
        if (resultadoLectura.moveToFirst()) {
            do {
                val id = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val descripcion = resultadoLectura.getString(2)
                if (id!=null) {
                    usuarioEncontrado.id=id
                    usuarioEncontrado.nombre=nombre
                    usuarioEncontrado.descripcion=descripcion
                    //arreloUsuaroi.add(usuarioEncontrado)
                }
            } while (resultadoLectura.moveToNext())
        }
        baseDatosLectura.close()
        resultadoLectura.close()
        return usuarioEncontrado
    }

    fun eliminarUsuarioPorID(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete(
            "usuario",
            "id=?",
            arrayOf(id.toString())
        )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt()==-1) false else true
    }

    fun actualizarUsuarioFormulario(
        nombre: String,
        descripcion: String,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre",nombre)
        valoresActualizar.put("descripcion",descripcion)
        val resultadoActualizacion = conexionEscritura.update(
            "usuario",
            valoresActualizar,
            "id=?",
            arrayOf(id.toString())
        )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt()==-1) false else true
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}