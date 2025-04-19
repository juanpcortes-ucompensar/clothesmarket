package com.example.clothesmarket.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesmarket.R
import com.example.clothesmarket.models.CarritoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CarritoAdapter(
    private val items: MutableList<CarritoItem>,
    private val onDelete: (CarritoItem) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.nombre_producto)
        val precio = itemView.findViewById<TextView>(R.id.precio_producto)
        val cantidad = itemView.findViewById<TextView>(R.id.cantidad_producto)
        val subtotal = itemView.findViewById<TextView>(R.id.subtotal_producto)
        val eliminar = itemView.findViewById<ImageButton>(R.id.btn_eliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nombre.text = item.producto.nombre
        holder.precio.text = "Precio: $${item.producto.precio}"
        holder.cantidad.text = "Cantidad: ${item.cantidad}"
        holder.subtotal.text = "Subtotal: $${item.producto.precio * item.cantidad}"

        holder.eliminar.setOnClickListener {
            onDelete(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
