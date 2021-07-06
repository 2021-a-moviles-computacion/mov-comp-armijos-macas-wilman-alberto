package com.example.asintro

import android.os.Parcel
import android.os.Parcelable

class TrainerBean(
    val name: String?,
    val description: String?,
    val league: LeagueBean?
    ): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(LeagueBean::class.java.classLoader)
    )

    override fun toString(): String {
        return "${name} - ${description}"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flag: Int) {
        parcel?.writeString(name)
        parcel?.writeString(description)
        parcel?.writeParcelable(league, flag)
    }

    companion object CREATOR : Parcelable.Creator<TrainerBean> {
        override fun createFromParcel(parcel: Parcel): TrainerBean {
            return TrainerBean(parcel)
        }

        override fun newArray(size: Int): Array<TrainerBean?> {
            return arrayOfNulls(size)
        }
    }
}