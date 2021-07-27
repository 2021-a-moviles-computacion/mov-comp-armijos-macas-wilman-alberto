package com.example.examenibimestre

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLite(contexto: Context?): SQLiteOpenHelper(
    contexto,
    "examenMoviles",
    null,
    1
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearDesarrollador="""
            create table Desarrollador(
            	idDesarrollador integer primary key autoincrement,
            	nombre varchar(50),
            	instruccion varchar(50),
            	empresa varchar(50),
            	edad integer,
            	sexo character(1)
            )
        """.trimIndent()
        db?.execSQL(scriptCrearDesarrollador)

        val scriptCrearAplicacion="""
            create table Aplicacion(
            	idAplicacion integer primary key autoincrement,
            	idDesarrolladorAplicacion integer,
            	nombre varchar(50),
            	lenguajeProgramacion varchar(50),
            	plataforma varchar(50),
            	publicoObjetivo varchar(50),
            	terminado integer,
            	precio double
            )
        """.trimIndent()
        db?.execSQL(scriptCrearAplicacion)

    }


    // PARA CREAR OBJETOS
    fun crearDesarrollador(
        nombre: String,
        instruccion: String,
        empresa: String,
        edad: Int,
        sexo: Char
    ):Boolean {
        val conexionEscritura = writableDatabase
        val aGuardar = ContentValues()
        aGuardar.put("nombre",nombre)
        aGuardar.put("instruccion",instruccion)
        aGuardar.put("empresa",empresa)
        aGuardar.put("edad",edad)
        aGuardar.put("sexo",sexo.toString())
        val resultadoEscritura: Long = conexionEscritura.insert(
            "Desarrollador",
            null,
            aGuardar
        )
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    fun crearAplicacion(
        idDesarrollador: Int,
        nombre: String,
        lenguajeProgramacion: String,
        plataforma: String,
        publicoObjetivo: String,
        terminado: Boolean,
        precio: Double
    ):Boolean {
        val conexionEscritura = writableDatabase
        val aGuardar = ContentValues()
        aGuardar.put("idDesarrolladorAplicacion",idDesarrollador)
        aGuardar.put("nombre",nombre)
        aGuardar.put("lenguajeProgramacion",lenguajeProgramacion)
        aGuardar.put("plataforma",plataforma)
        aGuardar.put("publicoObjetivo",publicoObjetivo)
        if (terminado) {
            aGuardar.put("terminado",1)
        }
        else {
            aGuardar.put("terminado",0)
        }
        aGuardar.put("precio",precio)
        val resultadoEscritura: Long = conexionEscritura.insert(
            "Aplicacion",
            null,
            aGuardar
        )
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1
    }

    // PARA CONSULTAR LISTA DE OBJETOS

    fun consultarListaDesarrolladores(): ArrayList<Desarrollador>{
        val scriptConsultarDesarrolladores = """
            select * from Desarrollador
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoLectura = baseDatosLectura.rawQuery(scriptConsultarDesarrolladores, null)
        val listaDesarrolladores = arrayListOf<Desarrollador>()

        if (resultadoLectura.moveToFirst()) {
            do {
                val desarrolladorEncontrado = Desarrollador(0,"","","",0,'H')
                val idDesarrolladorObtenido = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val instruccion = resultadoLectura.getString(2)
                val empresa = resultadoLectura.getString(3)
                val edad = resultadoLectura.getString(4).toInt()
                val sexo = resultadoLectura.getString(5).toCharArray()[0]
                if (idDesarrolladorObtenido!=null) {
                    desarrolladorEncontrado.setIdDesarrollador(idDesarrolladorObtenido)
                    desarrolladorEncontrado.setNombre(nombre)
                    desarrolladorEncontrado.setInstruccion(instruccion)
                    desarrolladorEncontrado.setEmpresa(empresa)
                    desarrolladorEncontrado.setEdad(edad)
                    desarrolladorEncontrado.setSexo(sexo)

                    listaDesarrolladores.add(desarrolladorEncontrado)
                }
            } while (resultadoLectura.moveToNext())
        }
        baseDatosLectura.close()
        resultadoLectura.close()
        Log.i("listaDesarrolladores","El tamaño de la lista es: ${listaDesarrolladores.size}")
        println("${listaDesarrolladores.size}")
        return listaDesarrolladores
    }


    fun consultarListaAplicaciones(idDesarrolladorAplicacion: Int): ArrayList<Aplicacion>{
        val scriptConsultarAplicaciones = """
            select * from Aplicacion where idDesarrolladorAplicacion=${idDesarrolladorAplicacion}
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoLectura = baseDatosLectura.rawQuery(scriptConsultarAplicaciones, null)
        val listaAplicaciones = arrayListOf<Aplicacion>()
        val aplicacionEncontrada = Aplicacion(0,0,"","","","",false,0.0)
        if (resultadoLectura.moveToFirst()) {
            do {
                val idAplicacionObtenido = resultadoLectura.getInt(0)
                val idDesarrolladorAplicacionObtenido = resultadoLectura.getInt(1)
                val nombre = resultadoLectura.getString(2)
                val lenguajeProgramacion = resultadoLectura.getString(3)
                val plataforma = resultadoLectura.getString(4)
                val publicoObjetivo = resultadoLectura.getString(5)
                var terminado = true
                if (resultadoLectura.getString(6).toInt()==0) {
                    terminado = false
                }
                val precio = resultadoLectura.getDouble(7)
                if (idAplicacionObtenido!=null) {
                    aplicacionEncontrada.setIdAplicacion(idAplicacionObtenido)
                    aplicacionEncontrada.setIdDesarrolladorAplicacion(idDesarrolladorAplicacionObtenido)
                    aplicacionEncontrada.setNombre(nombre)
                    aplicacionEncontrada.setLenguajeProgramacion(lenguajeProgramacion)
                    aplicacionEncontrada.setPlataforma(plataforma)
                    aplicacionEncontrada.setPublicoObjetivo(publicoObjetivo)
                    aplicacionEncontrada.setTerminado(terminado)
                    aplicacionEncontrada.setPrecio(precio)
                    listaAplicaciones.add(aplicacionEncontrada)
                }
            } while (resultadoLectura.moveToNext())
        }
        baseDatosLectura.close()
        resultadoLectura.close()
        return listaAplicaciones
    }

    // PARA CONSULTAR UN OBJETO UNITARIO


    fun consultarDesarrollador(idDesarrollador: Int): Desarrollador{
        val scriptConsultarDesarrollador = """
            select * from Desarrollador where idDesarrollador=${idDesarrollador}
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoLectura = baseDatosLectura.rawQuery(scriptConsultarDesarrollador, null)
        //val arregloUsuario = arrayListOf<Usuario>()
        val desarrolladorEncontrado = Desarrollador(0,"","","",0,'H')
        if (resultadoLectura.moveToFirst()) {
            do {
                val idDesarrolladorObtenido = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val instruccion = resultadoLectura.getString(2)
                val empresa = resultadoLectura.getString(3)
                val edad = resultadoLectura.getString(4).toInt()
                val sexo = resultadoLectura.getString(5).toCharArray()[0]
                if (idDesarrolladorObtenido!=null) {
                    desarrolladorEncontrado.setIdDesarrollador(idDesarrolladorObtenido)
                    desarrolladorEncontrado.setNombre(nombre)
                    desarrolladorEncontrado.setInstruccion(instruccion)
                    desarrolladorEncontrado.setEmpresa(empresa)
                    desarrolladorEncontrado.setEdad(edad)
                    desarrolladorEncontrado.setSexo(sexo)
                    //arreloUsuaroi.add(usuarioEncontrado)
                }
            } while (resultadoLectura.moveToNext())
        }
        baseDatosLectura.close()
        resultadoLectura.close()
        return desarrolladorEncontrado
    }

    fun consultarAplicacion(idAplicacion: Int, idDesarrolladorAplicacion: Int): Aplicacion{
        val scriptConsultarAplicacion = """
            select * from Aplicacion where idAplicacion=${idAplicacion} and Desarrollador.idDesarrollador=${idDesarrolladorAplicacion}
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoLectura = baseDatosLectura.rawQuery(scriptConsultarAplicacion, null)
        //val arregloUsuario = arrayListOf<Usuario>()
        val aplicacionEncontrada = Aplicacion(0,0,"","","","",true,0.0)
        if (resultadoLectura.moveToFirst()) {
            do {
                val idAplicacionObtenido = resultadoLectura.getInt(0)
                val idDesarrolladorAplicacionObtenido = resultadoLectura.getInt(1)
                val nombre = resultadoLectura.getString(2)
                val lenguajeProgramacion = resultadoLectura.getString(3)
                val plataforma = resultadoLectura.getString(4)
                val publicoObjetivo = resultadoLectura.getString(5)
                var terminado = true
                if (resultadoLectura.getString(6).toInt()==0) {
                    terminado = false
                }
                val precio = resultadoLectura.getDouble(7)
                if (idAplicacionObtenido!=null) {
                    aplicacionEncontrada.setIdAplicacion(idAplicacionObtenido)
                    aplicacionEncontrada.setIdDesarrolladorAplicacion(idDesarrolladorAplicacionObtenido)
                    aplicacionEncontrada.setNombre(nombre)
                    aplicacionEncontrada.setLenguajeProgramacion(lenguajeProgramacion)
                    aplicacionEncontrada.setPlataforma(plataforma)
                    aplicacionEncontrada.setPublicoObjetivo(publicoObjetivo)
                    aplicacionEncontrada.setTerminado(terminado)
                    aplicacionEncontrada.setPrecio(precio)
                    //arreloUsuaroi.add(usuarioEncontrado)
                }
            } while (resultadoLectura.moveToNext())
        }
        baseDatosLectura.close()
        resultadoLectura.close()
        return aplicacionEncontrada
    }



    // PARA ACTUALIZAR OBJETOS

    fun actualizarDesarrollador(
        idDesarrollador: Int,
        nombre: String,
        instruccion: String,
        empresa: String,
        edad: Int,
        sexo: Char
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre",nombre)
        valoresActualizar.put("instruccion",instruccion)
        valoresActualizar.put("empresa",empresa)
        valoresActualizar.put("edad",edad)
        valoresActualizar.put("sexo",sexo.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "Desarrollador",
            valoresActualizar,
            "idDesarrollador=?",
            arrayOf(idDesarrollador.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun actualizarAplicacion(
        idAplicacion: Int,
        idDesarrolladorAplicacion: Int,
        nombre: String,
        lenguajeProgramacion: String,
        plataforma: String,
        publicoObjetivo: String,
        terminado: Boolean,
        precio: Double
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre",nombre)
        valoresActualizar.put("lenguajeProgramacion",lenguajeProgramacion)
        valoresActualizar.put("plataforma",plataforma)
        valoresActualizar.put("publicoObjetivo",publicoObjetivo)
        if (terminado) {
            valoresActualizar.put("terminado",1)
        }
        else {
            valoresActualizar.put("terminado",0)
        }
        valoresActualizar.put("precio",precio)
        val resultadoActualizacion = conexionEscritura.update(
            "Aplicacion",
            valoresActualizar,
            "idAplicacion=? and idDesarrolladorAplicacion=?",
            arrayOf(idAplicacion.toString(), idDesarrolladorAplicacion.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }


    // PARA ELIMINAR OBJETOS

    fun eliminarDesarrollador(idDesarrollador: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete(
            "Desarrollador",
            "idDesarrollador=?",
            arrayOf(idDesarrollador.toString())
        )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun eliminarAplicacion(idAplicacion: Int, idDesarrolladorAplicacion: Int): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete(
            "Aplicacion",
            "idAplicacion=? and idDesarrolladorAplicacion=?",
            arrayOf(idAplicacion.toString(), idDesarrolladorAplicacion.toString())
        )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}