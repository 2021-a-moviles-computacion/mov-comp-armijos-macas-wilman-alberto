package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Restaurante : AppCompatActivity() {

    var query: Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurante)

        val btn_CrearRestaurante = findViewById<Button>(R.id.btn_CrearRestaurante)
        btn_CrearRestaurante.setOnClickListener {
            crearRestaurante()
        }

        val btn_Consultar = findViewById<Button>(R.id.btn_Consultar)
        btn_Consultar.setOnClickListener {
            consultar()
        }

        val btn_Transacciones = findViewById<Button>(R.id.btn_Transacciones)
        btn_Transacciones.setOnClickListener {
            //crearDatosPrueba()
            realizarTransaccion()
        }

    }

    fun realizarTransaccion() {
        val db = Firebase.firestore
        val citiesRef = db.collection("cities").document("SF")
        db.runTransaction { transact ->
            if (transact.get(citiesRef).getDouble("population")!=null){
                transact.update(citiesRef, "population",transact.get(citiesRef).getDouble("population")!!+1.0)
            }
        }
            .addOnSuccessListener {
                Log.i("transaccion","Transaccion completada")
            }
            .addOnFailureListener {
                Log.i("transaccion","Transaccion completada")
            }
    }

    fun consultar() {
        val db = Firebase.firestore
        val citiesRef = db
            .collection("cities")
            .orderBy("population")
            .limit(2)
        var task: Task<QuerySnapshot>? = null
        /*
         < : less than
        <= : less than or equal to
        == : equal to

        //Buscar un documento
        citiesRef.document("BJ") //ID
            .get().addOnSuccessListener {
                Log.i("consultas","${it.data}")
            }.addOnFailureListener{

            }


        //Buscar por un solo campo ==
        citiesRef
            .whereEqualTo("country","China").get().addOnSuccessListener {
            Log.i("consultas","${it.documents}")
            for(ciudad in it){
                Log.i("consultas","${ciudad.data}")
                Log.i("consultas","${ciudad.id}")
            }
        }
            .addOnFailureListener{}


        //Buscar por  dos o mas elementos del campo '== 'array-contains'
        citiesRef
            .whereEqualTo("capital",false)
            .whereArrayContainsAny("regions", arrayListOf("socal","norcal"))
            .get().addOnSuccessListener {
                for(city in it){
                    Log.i("consultas","== array- contain : ${city.data}")
                }
            }
            .addOnFailureListener{}


        //Buscar por dos o mas campos '==' '>='
        citiesRef
            .whereEqualTo("capital",true)
            .whereGreaterThanOrEqualTo("population",1000000)
            .get()
            .addOnSuccessListener {
                for(city in it) {
                    Log.i("consultas","== array- contain : ${city.data}")
                }
            }
            .addOnFailureListener{}

        // Busqueda por dos o mas campos y orden
        citiesRef
            .whereEqualTo("capital",true)
            .whereLessThanOrEqualTo("population",4000000)
            .orderBy("population", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for(city in it) {
                    Log.i("consultas","== array- contain : ${city.data}")
                }
            }
            .addOnFailureListener{}

         */
        if (query==null) { task = citiesRef.get() }
        else { task = query!!.get() }

        if (task!=null) {
            task
                .addOnSuccessListener { docSnaps ->
                    guardarConsulta(docSnaps, citiesRef)
                    for (city in docSnaps) {
                        Log.i("consultas","${city.data}")
                    }
                }
                .addOnFailureListener{
                    Log.i("consultas","ERROR")
                }
        }
    }

    fun guardarConsulta(querySnap: QuerySnapshot, citiesRef: Query) {
        if (querySnap.size()>0) {
            query=citiesRef.startAfter(querySnap.documents[querySnap.size()-1])
        } else  {}
    }

    fun crearDatosPrueba() {
        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast")
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu")
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei")
        )
        cities.document("BJ").set(data5)
    }


    fun crearRestaurante() {
        val txt_NombreRestaurante = findViewById<TextView>(R.id.txv_NombreRestaurante)
        if ((txt_NombreRestaurante.text != null) || (txt_NombreRestaurante.text!="")) {
            val nuevoRestaurante = hashMapOf<String, Any>(
                "nombre" to txt_NombreRestaurante.text.toString(),
            )
            val db = Firebase.firestore
            val referencia = db.collection("Restaurante")
                .add(nuevoRestaurante)
                .addOnSuccessListener {
                    txt_NombreRestaurante.text=""
                }
                .addOnFailureListener {

                }
        }
    }

}