package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class CrearAplicacion : AppCompatActivity() {
    private lateinit var map: GoogleMap
    var permisos = false
    lateinit var marker: Marker
    var latitud = "0"
    var longitud = "0"
    var idDesarrollador = ""
    var nombreDesarrollador = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_aplicacion)

        val desarrolladorID = intent.getStringExtra("idDesarrollador")
        idDesarrollador = desarrolladorID!!

        val desarrolladorNombre = intent.getStringExtra("desarrollador")
        nombreDesarrollador = desarrolladorNombre!!

        val txt_NombreDesarrollador = findViewById<EditText>(
            R.id.txt_desarrolladorCrearAplicacion
        )

        txt_NombreDesarrollador.setText(nombreDesarrollador)

        txt_NombreDesarrollador.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )


        val txt_Nombre = findViewById<EditText>(
            R.id.txt_NombreAplicacion
        )

        val txt_LenguajeProgramacion = findViewById<EditText>(
            R.id.txt_LenguajeAplicacion
        )

        val txt_Plataforma = findViewById<EditText>(
            R.id.txt_PlataformaAplicacion
        )

        val txt_PublicoObjetivo = findViewById<EditText>(
            R.id.txt_ObjetivoAplicacion
        )

        val txt_Terminado = findViewById<EditText>(
            R.id.txt_TerminadoAplicacion
        )


        val txt_Precio = findViewById<EditText>(
            R.id.txt_PrecioAplicacion
        )

        val btnCrearAplicacion = findViewById<Button>(
            R.id.btn_CrearAplicacion
        )

        val txt_Latitud = findViewById<EditText>(
            R.id.txv_latitudEdit
        )

        val txt_Longitud = findViewById<EditText>(
            R.id.txv_longitudEdit
        )

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            if (it != null) {
                map = it
                marker=map.addMarker(
                    MarkerOptions()
                        .position(LatLng(0.0,0.0))
                        .title("")
                )
                marker.isDraggable=false
                configurarPermisos()
                map.setOnMapClickListener {
                    this.latitud = it.latitude.toString()
                    this.longitud = it.longitude.toString()
                    val titulo = "Selección"
                    if (marker.isVisible) {
                        marker.setPosition(LatLng(this.latitud.toDouble(), this.longitud.toDouble()))
                    } else {
                        marker.isVisible=true
                    }
                    txt_Latitud.setText(this.latitud)
                    txt_Longitud.setText(this.longitud)
                }

            }
        }

        btnCrearAplicacion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Crear Aplicación")
            builder.setMessage("¿Está seguro que desea crear la Aplicación?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    var terminadoValor = true
                    if (txt_Terminado.text.toString().toInt() == 0) {
                        terminadoValor = false
                    }
                    FirestoreDatabase().createAplicacionFS(
                            idDesarrollador + nombreDesarrollador + txt_Nombre.text.toString(),
                            idDesarrollador,
                            nombreDesarrollador,
                            txt_Nombre.text.toString(),
                            txt_LenguajeProgramacion.text.toString(),
                            txt_Plataforma.text.toString(),
                            txt_PublicoObjetivo.text.toString(),
                            terminadoValor,
                            txt_Precio.text.toString().toDouble(),
                            this.latitud,
                            this.longitud
                        )
                    txt_Nombre.setText("")
                    txt_LenguajeProgramacion.setText("")
                    txt_Plataforma.setText("")
                    txt_PublicoObjetivo.setText("")
                    txt_Terminado.setText("")
                    txt_Precio.setText("")
                    val mapFragment =
                        supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync {
                        if (it != null) {
                            map = it
                            configurarPermisos()
                            val titulo = "Selección"
                            if (marker.isVisible) {
                                marker.isVisible=false
                                marker.title=titulo
                            } else {
                                marker.setPosition(LatLng(0.0, 0.0))
                                marker.isVisible=true
                            }
                            map.moveCamera(
                                CameraUpdateFactory
                                    .newLatLngZoom(LatLng(0.0, 0.0), 5f)
                            )
                        }
                    }
                    txt_Latitud.setText("0.0")
                    txt_Longitud.setText("0.0")
                    dialog.cancel()
                }
            )
            builder.setNegativeButton(
                "No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                }
            )
            builder.show()

        }


        val volverAplicacion = findViewById<Button>(
            R.id.btn_RegresarCrearAplicacion
        )

        volverAplicacion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Crear Aplicación")
            builder.setMessage("¿Está seguro que desea volver a la lista de Aplicaciones?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    // Aquí la lógica
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
        with(map) {
            val permissionsLocation = ContextCompat
                .checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val isPermitted =
                permissionsLocation == PackageManager.PERMISSION_GRANTED
            if (isPermitted) {
                map.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

}