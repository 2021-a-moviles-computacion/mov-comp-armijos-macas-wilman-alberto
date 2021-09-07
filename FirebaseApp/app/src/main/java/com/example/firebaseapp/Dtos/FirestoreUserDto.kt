package com.example.firebaseapp.Dtos

data class FirestoreUserDto(
    val uid: String = "",
    val email: String = "",
    val roles: ArrayList<String> = arrayListOf()
) {

}