package com.example.examen2bimestre

import android.os.Parcel
import android.os.Parcelable

class Aplicacion(
    private var idAplicacion: String?=null,
    private var idDesarrolladorAplicacion: String?=null,
    private var nombreDesarrolladorAplicacion: String?=null,
    private var nombre: String?=null,
    private var lenguajeProgramacion: String?=null,
    private var plataforma: String?=null,
    private var publicoObjetivo: String?=null,
    private var terminado: Boolean?=null,
    private var precio: Double?=null,
    private var latitud: String?=null,
    private var longitud: String?=null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idAplicacion)
        parcel.writeString(idDesarrolladorAplicacion)
        parcel.writeString(nombreDesarrolladorAplicacion)
        parcel.writeString(nombre)
        parcel.writeString(lenguajeProgramacion)
        parcel.writeString(plataforma)
        parcel.writeString(publicoObjetivo)
        parcel.writeValue(terminado)
        parcel.writeValue(precio)
        parcel.writeString(latitud)
        parcel.writeString(longitud)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Aplicacion> {
        override fun createFromParcel(parcel: Parcel): Aplicacion {
            return Aplicacion(parcel)
        }

        override fun newArray(size: Int): Array<Aplicacion?> {
            return arrayOfNulls(size)
        }
    }

    fun getIdAplicacion(): String? {
        return this.idAplicacion
    }

    fun getDesarrolladorAplicacion(): String? {
        return this.idDesarrolladorAplicacion
    }

    fun getNombreDesarrolladorAplicacion(): String? {
        return this.nombreDesarrolladorAplicacion
    }

    fun getNombre(): String? {
        return this.nombre
    }

    fun getLenguajeProgramacion(): String? {
        return this.lenguajeProgramacion
    }

    fun getPlataforma(): String? {
        return this.plataforma
    }

    fun getPublicoObjetivo(): String? {
        return this.publicoObjetivo
    }

    fun isTerminado(): Boolean? {
        return this.terminado
    }

    fun getPrecio(): Double? {
        return this.precio
    }

    fun getLatitud(): String? {
        return this.latitud
    }

    fun getLongitud(): String? {
        return this.longitud
    }

    fun setIdAplicacion(idAplicacion: String?) {
        this.idAplicacion=idAplicacion
    }

    fun setIdDesarrolladorAplicacion(idDesarrolladorAplicacion: String?) {
        this.idDesarrolladorAplicacion=idDesarrolladorAplicacion
    }

    fun setNombreDesarrolladorAplicacion(nombreDesarrolladorAplicacion: String?) {
        this.nombreDesarrolladorAplicacion=nombreDesarrolladorAplicacion
    }

    fun setNombre(nombre: String?) {
        this.nombre=nombre
    }

    fun setLenguajeProgramacion(lenguajeProgramacion: String?) {
        this.lenguajeProgramacion=lenguajeProgramacion
    }

    fun setPlataforma(plataforma: String?) {
        this.plataforma=plataforma
    }

    fun setPublicoObjetivo(publicoObjetivo: String?) {
        this.publicoObjetivo=publicoObjetivo
    }

    fun setTerminado(terminado: Boolean?) {
        this.terminado=terminado
    }

    fun setPrecio(precio: Double?) {
        this.precio=precio
    }

    fun setLatitud(latitud: String?) {
        this.latitud=latitud
    }

    fun setLongitud(longitud: String?) {
        this.longitud=longitud
    }

    override fun toString(): String {
        return this.getNombre()!!
    }

}
