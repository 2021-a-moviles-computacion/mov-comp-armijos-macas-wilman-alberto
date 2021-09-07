package com.example.asintro

import android.os.Parcel
import android.os.Parcelable

class LeagueBean(
    val nombre: String?,
    val description: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LeagueBean> {
        override fun createFromParcel(parcel: Parcel): LeagueBean {
            return LeagueBean(parcel)
        }

        override fun newArray(size: Int): Array<LeagueBean?> {
            return arrayOfNulls(size)
        }
    }


}