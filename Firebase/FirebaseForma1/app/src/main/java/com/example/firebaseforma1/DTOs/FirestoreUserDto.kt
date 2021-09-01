package com.example.firebaseforma1.DTOs

data class FirestoreUserDto(
    val uid: String = "",
    val email: String = "",
    val roles: ArrayList<String> = arrayListOf()
) {

}

