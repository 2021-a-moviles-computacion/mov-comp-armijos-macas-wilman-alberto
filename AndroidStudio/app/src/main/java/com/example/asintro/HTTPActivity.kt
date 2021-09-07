package com.example.asintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.*
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.*

class HTTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_httpactivity)

        //metodoGet()
        metodoPost()

    }

    fun metodoGet() {
        "https://jsonplaceholder.typicode.com/posts/1"
            .httpGet()
            .responseString { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {
                        val getString = result.get()
                        Log.i("http-klaxon", "${getString}")
                        val post = Klaxon()
                            .parse<POST_HTTP>(getString)
                        Log.i("http-klaxon","${post?.body}")
                    }
                }

            }
    }

    fun metodoPost() {
        val parametros: List<Pair<String, *>> = listOf(
        "title" to "titulo: Wilman",
        "body" to "descripcion: POST HTTP",
        "UserId" to 1
        )

        "https://jsonplaceholder.typicode.com/posts".httpPost(parametros)
            .responseString() { req, res, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon","Error: ${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon","${postString}")
                        val post = Klaxon()
                            .parse<POST_HTTP>(postString)
                        Log.i("http-klaxon","${post?.body}")
                    }
                }

            }

    }

}