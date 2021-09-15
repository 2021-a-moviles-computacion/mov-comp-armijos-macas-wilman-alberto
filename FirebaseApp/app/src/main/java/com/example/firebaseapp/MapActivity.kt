package com.example.firebaseapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.api.Context

class MapActivity : AppCompatActivity() {

    private lateinit var map: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        solicitarPermisos()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            if (it!=null) {
                map=it
                configurarPermisos()
                val puntoMapa = LatLng(0.0020206111617214607, -78.45119364286981)
                val titulo = "Lugar Importante"
                val zoom = 17f
                setPoint(puntoMapa, titulo)
                moveCameraZoom(puntoMapa, zoom)

                val polyLine = map
                    .addPolyline(
                        PolylineOptions()
                            .clickable(true)
                            .add(
                                LatLng(0.0020206111617214607, -78.45119364286981),
                                LatLng(0.0021206111617214607, -78.45119364286981),
                                LatLng(0.0022206111617214607, -78.45119364286981)
                            )
                    )
                polyLine.tag = "Linea"

                val polygon = map
                    .addPolygon(
                        PolygonOptions()
                            .clickable(true)
                            .add(
                                LatLng(0.0021206111617214607, -78.45119364286981),
                                LatLng(0.0020206111617214607, -78.46119364286981),
                                LatLng(0.0021206111617214607, -78.45119364286981)
                            )
                    )
                polygon.tag = "Triangulo"
                polygon.fillColor = -0xc771c4


                mapListeners()

            }
            else {

            }
        }

        val btn_IrUbicacion = findViewById<Button>(R.id.btn_IrUbicacion)
        btn_IrUbicacion.setOnClickListener {
            val ubicacion = LatLng(-0.2460866278530882, -78.50843580595406)
            val zoom = 17f
            moveCameraZoom(ubicacion,zoom)
        }
    }

    fun mapListeners(){
        map.setOnPolygonClickListener {
            Log.i("map-listener","Listener de PolygonClick")
        }

        map.setOnPolylineClickListener {
            Log.i("map-listener","Listener de PolylineClick")
        }

        map.setOnMarkerClickListener {
            Log.i("map-listener","Listener de MarkerClick")
            return@setOnMarkerClickListener true
        }

        map.setOnCameraMoveListener {
            Log.i("map-listener","Listener de CameraMove")
        }

        map.setOnCameraMoveStartedListener {
            Log.i("map-listener","Listener de CameraMoveStarted")
        }

        map.setOnCameraIdleListener {
            Log.i("map-listener","Listener de CameraIdle")
        }

    }

    fun setPoint(point: LatLng, title: String) {
        map.addMarker(
            MarkerOptions()
                .position(point)
                .title(title)
        )
    }

    fun moveCameraZoom(point: LatLng, zoom: Float=10f) {
        map.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(point,zoom)
        )
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
            uiSettings.isMyLocationButtonEnabled = true
        }
    }


}