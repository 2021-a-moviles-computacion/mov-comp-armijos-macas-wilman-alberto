package com.example.examenibimestre

import android.os.Parcel
import android.os.Parcelable

class Aplicacion(
    private var idAplicacion: Int?=null,
    private var idDesarrolladorAplicacion: Int?=null,
    private var nombre: String?=null,
    private var lenguajeProgramacion: String?=null,
    private var plataforma: String?=null,
    private var publicoObjetivo: String?=null,
    private var terminado: Boolean?=null,
    private var precio: Double?=null
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idAplicacion)
        parcel.writeValue(idDesarrolladorAplicacion)
        parcel.writeString(nombre)
        parcel.writeString(lenguajeProgramacion)
        parcel.writeString(plataforma)
        parcel.writeString(publicoObjetivo)
        parcel.writeValue(terminado)
        parcel.writeValue(precio)
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

    fun getIdAplicacion(): Int? {
        return this.idAplicacion
    }

    fun getDesarrolladorAplicacion(): Int? {
        return this.idDesarrolladorAplicacion
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

    fun setIdAplicacion(idAplicacion: Int?) {
        this.idAplicacion=idAplicacion
    }

    fun setIdDesarrolladorAplicacion(idDesarrolladorAplicacion: Int?) {
        this.idDesarrolladorAplicacion=idDesarrolladorAplicacion
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

    override fun toString(): String {
        return this.getNombre()!!
    }

}
