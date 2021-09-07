package com.example.firebaseapp

class ObjetoOrden(
    val ordenID: String,
    val nombreRestaurante: String,
    val nombreProducto: String,
    val precioProducto: String,
    val cantidadProducto: String,
) {

    override fun toString(): String {
        return ordenID+".-"+nombreRestaurante+": "+precioProducto+" - "+nombreProducto+" x"+cantidadProducto
    }
}