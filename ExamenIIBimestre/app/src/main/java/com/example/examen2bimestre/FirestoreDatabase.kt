package com.example.examen2bimestre

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreDatabase() {

    fun getRandomLat(): String {
        var latitud = ""
        val db = Firebase.firestore
        db.collection("LatLong").document((1..10).random().toString()).get().addOnSuccessListener {
            latitud=it.getString("latitud").toString()
        }
        return latitud
    }

    fun getRandomLgt(): String {
        var longitud = ""
        val db = Firebase.firestore
        db.collection("LatLong").document((1..10).random().toString()).get().addOnSuccessListener {
            longitud=it.getString("longitud").toString()
        }
        return longitud
    }

    //CREAR DESARROLLADOR FIRESTORE
    fun createDesarrolladorFS(
        idDesarrollador: String,
        nombre: String,
        instruccion: String,
        empresa: String,
        edad: Int,
        sexo: Char
    ): Boolean {
        var result=false
        val db = Firebase.firestore
        val nuevoUsuario = hashMapOf<String, Any>(
            "idDesarrollador" to idDesarrollador,
            "nombreDesarrollador" to nombre,
            "instruccionDesarrollador" to instruccion,
            "empresaDesarrollador" to empresa,
            "edadDesarrollador" to edad,
            "sexoDesarrollador" to sexo.toString()
        )
        db.collection("Desarrollador").document(idDesarrollador).
        set(nuevoUsuario).addOnSuccessListener {
            Log.i("firebase-firestore","Se creo el desarrollador correctamente")
            result=true
        }
            .addOnFailureListener {
                Log.i("firebase-firestore","Fallo la creacion del desarrollador")
            }
        return result
    }

    //CREAR APLICACION FIRESTORE
    fun createAplicacionFS(
        idAplicacion: String,
        idDesarrolladorAplicacion: String,
        nombreDesarrolladorAplicacion: String,
        nombreAplicacion: String,
        lenguajeProgramacion: String,
        plataforma: String,
        publicoObjetivo: String,
        terminado: Boolean,
        precio: Double,
        latitud: String,
        longitud: String,
    ): Boolean {
        var result = false
        val db = Firebase.firestore
        val nuevoUsuario = hashMapOf<String, Any>(
            "idAplicacion" to idAplicacion,
            "idDesarrolladorAplicacion" to idDesarrolladorAplicacion,
            "nombreDesarrolladorAplicacion" to nombreDesarrolladorAplicacion,
            "nombreAplicacion" to nombreAplicacion,
            "lenguajeAplicacion" to lenguajeProgramacion,
            "plataformaAplicacion" to plataforma,
            "publicoObjetivoAplicacion" to publicoObjetivo,
            "terminadoAplicacion" to terminado,
            "precioAplicacion" to precio,
            "latitudAplicacion" to latitud,
            "longitudAplicacion" to longitud
        )
        db.collection("Aplicacion").document(idAplicacion).
        set(nuevoUsuario).addOnSuccessListener {
            Log.i("firebase-firestore","Se creo la aplicacion correctamente")
            result=true
        }
            .addOnFailureListener {
                Log.i("firebase-firestore","Fallo la creacion de la aplicacion")
            }
        return result
    }

    //LEER UN DESARROLLADOR FIRESTORE
    fun readOneDesarrolladorFS(
        idDesarrollador: String
    ): Desarrollador {
        var objDesarrollador = Desarrollador()
        val db = Firebase.firestore
        db.collection("Desarrollador").document(idDesarrollador).get().addOnSuccessListener {
            objDesarrollador.setIdDesarrollador(idDesarrollador)
            objDesarrollador.setNombre(it.getString("nombreDesarrollador"))
            objDesarrollador.setInstruccion(it.getString("instruccionDesarrollador"))
            objDesarrollador.setEmpresa(it.getString("empresaDesarrollador"))
            objDesarrollador.setEdad(it.getDouble("edadDesarrollador")!!.toInt())
            objDesarrollador.setSexo(it.getString("sexoDesarrollador")!!.toCharArray()[0])
        }
        return objDesarrollador
    }


    //LEER UNA APLICACION FIRESTORE
    fun readOneAplicacionFS(
        idAplicacion: String
    ): Aplicacion {
        var objAplicacion = Aplicacion()
        val db = Firebase.firestore
        db.collection("Aplicacion").document(idAplicacion).get().addOnSuccessListener {
            objAplicacion.setIdAplicacion(idAplicacion)
            objAplicacion.setIdDesarrolladorAplicacion(it.getString("idDesarrolladorAplicacion"))
            objAplicacion.setNombreDesarrolladorAplicacion(it.getString("nombreDesarrolladorAplicacion"))
            objAplicacion.setNombre(it.getString("nombreAplicacion"))
            objAplicacion.setLenguajeProgramacion(it.getString("lenguajeAplicacion"))
            objAplicacion.setPlataforma(it.getString("plataformaAplicacion"))
            objAplicacion.setPublicoObjetivo(it.getString("publicoObjetivoAplicacion"))
            objAplicacion.setTerminado(it.getBoolean("terminadoAplicacion"))
            objAplicacion.setPrecio(it.getDouble("precioAplicacion"))
            objAplicacion.setLatitud(it.getString("latitudAplicacion"))
            objAplicacion.setLongitud(it.getString("longitudAplicacion"))
        }
        return objAplicacion
    }

    // ACTUALIZAR DESARROLLADOR FIRESTORE
    fun updateDesarrolladorFS(
        idDesarrollador: String,
        nombre: String,
        instruccion: String,
        empresa: String,
        edad: Int,
        sexo: Char
    ): Boolean {
        var result = false
        val db = Firebase.firestore
        db.collection("Desarrollador").document(idDesarrollador).
        update(
            "nombreDesarrollador",nombre,
            "instruccionDesarrollador",instruccion,
            "empresaDesarrollador",empresa,
            "edadDesarrollador",edad,
            "sexoDesarrollador",sexo.toString()
        ).addOnSuccessListener {
            Log.i("firebase-firestore","Se actualizo el desarrollador correctamente")
            result = true
        }
            .addOnFailureListener {
                Log.i("firebase-firestore","Fallo la actualizacion del desarrollador")
            }
        return result
    }

    // ACTUALIZAR APLICACION FIRESTORE
    fun updateAplicacionFS(
        idAplicacion: String,
        nombreAplicacion: String,
        lenguajeProgramacion: String,
        plataforma: String,
        publicoObjetivo: String,
        terminado: Boolean,
        precio: Double,
        latitud: String,
        longitud: String
    ):Boolean {
        var result = false
        val db = Firebase.firestore
        db.collection("Aplicacion").document(idAplicacion).
        update(
            "nombreAplicacion",nombreAplicacion,
            "lenguajeAplicacion",lenguajeProgramacion,
            "plataformaAplicacion",plataforma,
            "publicoObjetivoAplicacion",publicoObjetivo,
            "terminadoAplicacion",terminado,
            "precioAplicacion",precio,
            "latitudAplicacion",latitud,
            "longitudAplicacion",longitud
        ).addOnSuccessListener {
            Log.i("firebase-firestore","Se actualizo la aplicacion correctamente")
            result = true
        }
            .addOnFailureListener {
                Log.i("firebase-firestore","Fallo la actualizacion de la aplicacion")
            }
        return result
    }


    // ELIMINAR DESARROLLADOR FIRESTORE
    fun deleteDesarrolladorFS(
        idDesarrollador: String
    ):Boolean {
        var result = false
        val db = Firebase.firestore
        db.collection("Desarrollador").document(idDesarrollador).delete().addOnSuccessListener {
            Log.i("firebase-firestore","Se elimino el desarrollador correctamente")
            result = true
        }
            .addOnFailureListener {
                Log.i("firebase-firestore","Fallo la eliminacion del desarrollador")
            }
        return result
    }

    // ELIMINAR APLICACION FIRESTORE
    fun deleteAplicacionFS(
        idAplicacion: String
    ):Boolean {
        var result = false
        val db = Firebase.firestore
        db.collection("Aplicacion").document(idAplicacion).delete().addOnSuccessListener {
            Log.i("firebase-firestore","Se elimino la aplicacion correctamente")
            result = true
        }
            .addOnFailureListener {
                Log.i("firebase-firestore","Fallo la eliminacion de la aplicacion")
            }
        return result
    }


}