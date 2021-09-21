package com.example.examen2bimestre

import android.content.DialogInterface
import android.content.Intent
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

class EditarAplicacion : AppCompatActivity() {
    private lateinit var map: GoogleMap
    var permisos = false
    lateinit var marker: Marker
    var latitud = "0"
    var longitud = "0"
    var idAplicacion = ""
    var idDesarrolladorAplicacion = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_aplicacion)

        val txt_desarrolladorAplicacion = findViewById<EditText>(
            R.id.txt_desarrolladorEditarAplicacionEdit
        )

        val txt_nombreAplicacion = findViewById<EditText>(
            R.id.txt_NombreAplicacionEdit
        )

        val desarrolladorID = intent.getStringExtra("idDesarrollador")
        idDesarrolladorAplicacion = desarrolladorID!!

        val desarrollador = intent.getStringExtra("desarrollador")
        txt_desarrolladorAplicacion.setText(desarrollador)


        val aplicacion = intent.getParcelableExtra<Aplicacion>("Aplicacion")
        txt_nombreAplicacion.setText(aplicacion!!.getNombre()!!)
        idAplicacion = aplicacion.getIdAplicacion()!!

        txt_desarrolladorAplicacion.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        txt_nombreAplicacion.filters = arrayOf(
            InputFilter { src, start, end, dst, dstart, dend ->
                if (src.length < 1) dst.subSequence(
                    dstart,
                    dend
                ) else ""
            }
        )

        val txt_LenguajeProgramacion = findViewById<EditText>(
            R.id.txt_LenguajeAplicacionEdit
        )

        txt_LenguajeProgramacion.setText(aplicacion.getLenguajeProgramacion())

        val txt_Plataforma = findViewById<EditText>(
            R.id.txt_PlataformaAplicacionEdit
        )

        txt_Plataforma.setText(aplicacion.getPlataforma())

        val txt_PublicoObjetivo = findViewById<EditText>(
            R.id.txt_ObjetivoAplicacionEdit
        )

        txt_PublicoObjetivo.setText(aplicacion.getPublicoObjetivo())

        val txt_Terminado = findViewById<EditText>(
            R.id.txt_TerminadoAplicacionEdit
        )

        txt_Terminado.setText(aplicacion.isTerminado().toString())


        val txt_Precio = findViewById<EditText>(
            R.id.txt_PrecioAplicacionEdit
        )

        txt_Precio.setText(aplicacion.getPrecio().toString())

        val txt_Latitud = findViewById<EditText>(
            R.id.txv_latitudEdit
        )
        txt_Latitud.setText(aplicacion.getLatitud())

        val txt_Longitud = findViewById<EditText>(
            R.id.txv_longitudEdit
        )
        txt_Longitud.setText(aplicacion.getLongitud())

        val editarAplicacion = findViewById<Button>(
            R.id.btn_EditarAplicacion
        )

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            if (it != null) {
                map = it
                val titulo = "Selección"
                marker=map.addMarker(
                    MarkerOptions()
                        .position(LatLng(aplicacion.getLatitud()!!.toDouble(),aplicacion.getLongitud()!!.toDouble()))
                        .title(titulo)
                )
                map.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(LatLng(aplicacion.getLatitud()!!.toDouble(),aplicacion.getLongitud()!!.toDouble()),15f)
                )
                marker.isDraggable=false
                configurarPermisos()
                map.setOnMapClickListener {
                    this.latitud = it.latitude.toString()
                    this.longitud = it.longitude.toString()
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

        editarAplicacion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Aplicación")
            builder.setMessage("¿Está seguro que desea editar la Aplicación?")
            builder.setPositiveButton(
                "Sí", DialogInterface.OnClickListener { dialog, id ->
                    var terminadoValor=true
                    if (txt_Terminado.text.toString()=="false") {
                        terminadoValor=false
                    }
                    FirestoreDatabase().updateAplicacionFS(
                        idAplicacion,
                        txt_nombreAplicacion.text.toString(),
                        txt_LenguajeProgramacion.text.toString(),
                        txt_Plataforma.text.toString(),
                        txt_PublicoObjetivo.text.toString(),
                        terminadoValor,
                        txt_Precio.text.toString().toDouble(),
                        this.latitud,
                        this.longitud
                    )
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
            R.id.btn_regresarVerAplicacion
        )

        volverAplicacion.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Aplicación")
            builder.setMessage("¿Está seguro que desea volver a la lista de Aplicaciones?")
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

    fun configurarPermisos() {
        val context = this.applicationContext
        with(map) {
            val permissionsLocation = androidx.core.content.ContextCompat
                .checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val isPermitted =
                permissionsLocation == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (isPermitted) {
                map.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }


}