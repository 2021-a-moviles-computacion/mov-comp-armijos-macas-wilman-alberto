package com.example.asintro

class Usuario(
    var id: Int,
    var nombre: String,
    var descripcion: String
) {

    override fun toString(): String {
        return "id: ${id}, nombre: ${nombre}, descripcion: ${descripcion} "
    }
}