package com.example.firebaseforma2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

class MainActivity : AppCompatActivity() {

    val CODIGO_INICIO_SESION = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_LogIn = findViewById<Button> (
            R.id.brn_LogIn
        )

        btn_LogIn.setOnClickListener {
            llamarLoginUsuario()
        }


    }


    fun llamarLoginUsuario(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .build()
            , CODIGO_INICIO_SESION)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODIGO_INICIO_SESION -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario = IdpResponse.fromResultIntent(data)
                    if (usuario?.isNewUser == true) {
                        Log.i("firebase_login","Nuevo Usuario")
                    }
                    else {
                        Log.i("firebase_login","Usuario Antiguo")
                    }
                }
                else {
                    Log.i("firebase_login","El usuario canceló")
                }
            }
        }
    }


}