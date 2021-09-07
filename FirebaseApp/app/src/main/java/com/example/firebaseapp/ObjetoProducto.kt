package com.example.firebaseapp

class ObjetoProducto(
    val nombre: String,
    val precio: String
) {

    override fun toString(): String {
        return "$"+precio+" - "+nombre
    }

}