package com.example.firebaseforma1

class ObjetoOrden(
    val nombreRestaurante: String,
    val nombreProducto: String,
    val precioProducto: String,
    val cantidadProducto: String,
) {

    override fun toString(): String {
        return nombreRestaurante}, " +
                "Producto: ${nombreProducto}, " +
                "Precio: ${precioProducto}, " +
                "Cantidad: ${cantidadProducto}"
    }

}