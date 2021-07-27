package com.example.asintro

class POST_HTTP (
    val id: Integer,
    var UserID: Any,
    val title: String,
    var body: String
        ) {
        init {
            if (UserID is String) {
                UserID = (UserID as String).toInt()
            }
            if (UserID is Int) {
                UserID = UserID
            }
        }

}

