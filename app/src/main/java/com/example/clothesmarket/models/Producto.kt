package com.example.clothesmarket.models

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidad: Int,
    val imagenName: String,
    val categoria: String
)