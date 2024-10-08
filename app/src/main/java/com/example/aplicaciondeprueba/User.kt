package com.example.aplicaciondeprueba

data class User(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val rut: String = "",
    val phone: String = "",
    val fullName: String = "",
    val address: String = ""
) {
    // Constructor sin argumentos
    constructor() : this("", "", "", "", "", "", "")
}
