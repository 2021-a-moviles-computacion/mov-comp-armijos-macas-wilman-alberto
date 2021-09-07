package com.example.firebaseapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.firebaseapp.Dtos.FirestoreUserDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val CODIGO_INICIO_SESION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        val btn_LogIn = findViewById<Button>(R.id.btn_LogIn)
        btn_LogIn.setOnClickListener {
            llamarLogInUsuario()
        }

        val btn_LogOut = findViewById<Button>(R.id.btn_LogOut)
        btn_LogOut.setOnClickListener {
            llamarLogOutUsuario()
        }

        val btn_IrProductos = findViewById<Button>(R.id.btn_IrProductos)
        btn_IrProductos.setOnClickListener {
            val intent = Intent(
                this,
                Producto::class.java
            )
            startActivity(intent)
        }

        val btn_IrRestaurantes = findViewById<Button>(R.id.btn_IrRestaurantes)
        btn_IrRestaurantes.setOnClickListener {
            val intent = Intent(
                this,
                Restaurante::class.java
            )
            startActivity(intent)
        }

        val btn_IrOrdenes = findViewById<Button>(R.id.btn_IrOrdenes)
        btn_IrOrdenes.setOnClickListener {
            val intent = Intent(
                this,
                Ordenes::class.java
            )
            startActivity(intent)
        }


    }

    fun llamarLogOutUsuario() {
        AuthUI
            .getInstance()
            .signOut(this)
            .addOnCompleteListener {
                AuthUser.usuario = null
                setBienvenida()
            }
    }

    fun setBienvenida() {
        val txv_mensaje = findViewById<TextView>(
            R.id.txv_bienvenida
        )
        val btn_LogIn = findViewById<Button>(R.id.btn_LogIn)
        val btn_LogOut = findViewById<Button>(R.id.btn_LogOut)
        val btn_IrProductos = findViewById<Button>(R.id.btn_IrProductos)
        val btn_IrRestaurantes = findViewById<Button>(R.id.btn_IrRestaurantes)
        val btn_IrOrdenes = findViewById<Button>(R.id.btn_IrOrdenes)

        if (AuthUser.usuario != null) {
            txv_mensaje.text = "Bienvenido a la aplicación ${AuthUser.usuario?.email}"
            btn_LogIn.visibility = View.INVISIBLE
            btn_LogOut.visibility = View.VISIBLE
            btn_IrProductos.visibility = View.VISIBLE
            btn_IrRestaurantes.visibility = View.VISIBLE
            btn_IrOrdenes.visibility = View.VISIBLE
        }
        else {
            txv_mensaje.text = "Ingrese a la aplicación por favor"
            btn_LogIn.visibility = View.VISIBLE
            btn_LogOut.visibility = View.INVISIBLE
            btn_IrProductos.visibility = View.INVISIBLE
            btn_IrRestaurantes.visibility = View.INVISIBLE
            btn_IrOrdenes.visibility = View.INVISIBLE
        }
    }


    fun llamarLogInUsuario() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance().
            createSignInIntentBuilder().
            setAvailableProviders(providers).
            setTosAndPrivacyPolicyUrls(
                "https://example.com/terms.html",
                "https://example.com/privacy.html"
            ).build(),
            CODIGO_INICIO_SESION
        )

    }

    fun registrarUsuarioNuevo(usuario: IdpResponse) {
        val usuarioLogueado = FirebaseAuth.getInstance().currentUser
        if (usuario.email!=null && usuarioLogueado!=null) {
            val db = Firebase.firestore
            val rolesUsuario = arrayListOf("Usuario")
            val identificarUsuario = usuario.email
            val nuevoUsuario = hashMapOf<String, Any>(
                "roles" to rolesUsuario,
                "uid" to usuarioLogueado.uid,
                "email" to identificarUsuario.toString()
            )
            db.collection("Usuario").document(identificarUsuario.toString()).
            set(nuevoUsuario).addOnSuccessListener {
                Log.i("firebase-firestore","Se creo el usuario correctamente")
                setUsuarioFirebase()
            }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Fallo la creacion del usuario")
                }

        } else {
            Log.i("firebase-login","ERROR")
        }
    }


    fun setUsuarioFirebase(){
        val usuarioInstsancia = FirebaseAuth.getInstance()
        val usuarioLocal = usuarioInstsancia.currentUser
        if (usuarioLocal != null) {
            if (usuarioLocal.email != null ){
                val db = Firebase.firestore

                val referencia = db
                    .collection("Usuario")
                    .document(usuarioLocal.email.toString())

                referencia
                    .get()
                    .addOnSuccessListener {
                        val usuarioObtenido: FirestoreUserDto? =
                            it.toObject(FirestoreUserDto::class.java)
                        if (usuarioObtenido != null) {
                            AuthUser.usuario = FirebaseUser(
                                usuarioObtenido.uid,
                                usuarioObtenido.email,
                                usuarioObtenido.roles
                            )
                            setBienvenida()
                        }
                        Log.i("firebase-firestore","Usuario obtenido")
                    }
                    .addOnFailureListener {
                        Log.i("firebase-firestore","Usuario no se pudo obtener")
                    }

            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            CODIGO_INICIO_SESION->{
                if (resultCode==Activity.RESULT_OK) {
                    val usuario = IdpResponse.fromResultIntent(data)
                    if (usuario?.isNewUser==true) {
                        Log.i("firebase-login","El usuario es nuevo")
                        registrarUsuarioNuevo(usuario)
                    } else {
                        Log.i("firebase-login","El usuario es antiguo")
                        setUsuarioFirebase()
                    }
                } else {
                    Log.i("firebase-login","El usuario canceló")
                }
            }
        }

    }

}