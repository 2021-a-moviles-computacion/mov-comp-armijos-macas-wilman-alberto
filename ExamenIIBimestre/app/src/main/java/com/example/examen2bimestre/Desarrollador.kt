package com.example.examen2bimestre

import android.os.Parcel
import android.os.Parcelable

class Desarrollador(
    private var idDesarrollador: String?=null,
    private var nombre: String?=null,
    private var instruccion: String?=null,
    private var empresa: String?=null,
    private var edad: Int?=null,
    private var sexo: Char?=null
):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Char::class.java.classLoader) as? Char
    ) {
    }

    public fun getIdDesarrollador(): String? {
        return this.idDesarrollador
    }

    public fun getNombre(): String? {
        return this.nombre
    }

    public fun getInstruccion(): String? {
        return this.instruccion
    }

    public fun getEmpresa(): String? {
        return this.empresa
    }

    public fun getEdad(): Int? {
        return this.edad
    }

    public fun getSexo(): Char? {
        return this.sexo
    }

    public fun setIdDesarrollador(idDesarrollador: String?) {
        this.idDesarrollador=idDesarrollador
    }

    public fun setNombre(nombre: String?) {
        this.nombre=nombre
    }

    public fun setInstruccion(instruccion: String?) {
        this.instruccion=instruccion
    }

    public fun setEmpresa(empresa: String?) {
        this.empresa=empresa
    }

    public fun setEdad(edad: Int?) {
        this.edad=edad
    }

    public fun setSexo(sexo: Char?) {
        this.sexo=sexo
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idDesarrollador)
        parcel.writeString(nombre)
        parcel.writeString(instruccion)
        parcel.writeString(empresa)
        parcel.writeValue(edad)
        parcel.writeValue(sexo)
    }

    override fun toString(): String {
        return return this.getNombre()!!
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Desarrollador> {
        override fun createFromParcel(parcel: Parcel): Desarrollador {
            return Desarrollador(parcel)
        }

        override fun newArray(size: Int): Array<Desarrollador?> {
            return arrayOfNulls(size)
        }
    }

}