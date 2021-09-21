package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaAplicacion : AppCompatActivity() {
    private lateinit var map: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_aplicacion)

        solicitarPermisos()

        val nombreAplicacion = intent.getStringExtra("nombreAplicacion")
        val latitudAplicacion = intent.getStringExtra("latitudMapa")
        val longitudAplicacion = intent.getStringExtra("longitudMapa")


        val txv_nombreAplicacion = findViewById<EditText>(
            R.id.txv_NombreAplicacionMapa
        )

        txv_nombreAplicacion.setText(nombreAplicacion)

        val btn_VolverAplicacionMapa = findViewById<Button>(
            R.id.btn_VolverAplicacionMapa
        )

        txv_nombreAplicacion.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            if (it!=null) {
                map = it
                configurarPermisos()
                Log.i("google-map","La latitud es ${latitudAplicacion!!.toDouble()}")
                Log.i("google-map","La longitud es ${longitudAplicacion!!.toDouble()}")
                val puntoMapa = LatLng(latitudAplicacion!!.toDouble(), longitudAplicacion!!.toDouble())
                val titulo = "Aquí está ${nombreAplicacion}"
                map.addMarker(
                    MarkerOptions()
                        .position(puntoMapa)
                        .title(titulo)
                )
                map.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(puntoMapa,15f)
                )
                map.uiSettings.isScrollGesturesEnabled = false
            }
        }

        btn_VolverAplicacionMapa.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Mapa Aplicación")
            builder.setMessage("¿Está seguro que desea volver a la Aplicacion?")
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

    fun solicitarPermisos() {
        val context = this.applicationContext
        val permissionsLocation = ContextCompat
            .checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val isPermitted = permissionsLocation == PackageManager.PERMISSION_GRANTED
        if (isPermitted) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

    fun configurarPermisos() {
        val context = this.applicationContext
        with (map) {
            val permissionsLocation = ContextCompat
                .checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val isPermitted = permissionsLocation == PackageManager.PERMISSION_GRANTED
            if (isPermitted) {
                map.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = false
        }
    }

}