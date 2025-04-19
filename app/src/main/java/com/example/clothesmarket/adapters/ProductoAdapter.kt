package com.example.clothesmarket.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesmarket.R
import com.example.clothesmarket.models.CarritoItem
import com.example.clothesmarket.models.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductoAdapter(private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.nombre_producto)
        val descripcion = itemView.findViewById<TextView>(R.id.descripcion_producto)
        val precio = itemView.findViewById<TextView>(R.id.precio_producto)
        val cantidad = itemView.findViewById<TextView>(R.id.cantidad_producto)
        val imagen = itemView.findViewById<ImageView>(R.id.imagen_producto)
        val boton = itemView.findViewById<Button>(R.id.boton_agregar_carrito)

        // Nuevos elementos para selección de cantidad
        val btnDecrementar = itemView.findViewById<Button>(R.id.btn_decrementar)
        val btnIncrementar = itemView.findViewById<Button>(R.id.btn_incrementar)
        val tvCantidadSeleccionada = itemView.findViewById<TextView>(R.id.tv_cantidad_seleccionada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        val context = holder.itemView.context

        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "Precio: $${producto.precio}"
        holder.cantidad.text = "Disponible: ${producto.cantidad}"
        var id = context.resources.getIdentifier(producto.imagenName, "drawable", context.packageName)
        holder.imagen.setImageResource(id)

        // Inicializar cantidad seleccionada
        var cantidadSeleccionada = 1
        holder.tvCantidadSeleccionada.text = cantidadSeleccionada.toString()

        // Configurar botón decrementar
        holder.btnDecrementar.setOnClickListener {
            if (cantidadSeleccionada > 1) {
                cantidadSeleccionada--
                holder.tvCantidadSeleccionada.text = cantidadSeleccionada.toString()
            }
        }

        // Configurar botón incrementar
        holder.btnIncrementar.setOnClickListener {
            if (cantidadSeleccionada < producto.cantidad) {
                cantidadSeleccionada++
                holder.tvCantidadSeleccionada.text = cantidadSeleccionada.toString()
            } else {
                Toast.makeText(
                    it.context,
                    "No puedes agregar más de ${producto.cantidad} unidades",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.boton.setOnClickListener {
            val context = it.context
            val sharedPrefs = context.getSharedPreferences("carrito_pref", Context.MODE_PRIVATE)
            val gson = Gson()

            val carritoJson = sharedPrefs.getString("carrito", null)
            val carrito: MutableList<CarritoItem> = if (carritoJson != null) {
                val type = object : TypeToken<MutableList<CarritoItem>>() {}.type
                gson.fromJson(carritoJson, type)
            } else {
                mutableListOf()
            }

            // Revisar si el producto ya está en el carrito
            val existente = carrito.find { it.producto.id == producto.id }
            if (existente != null) {
                existente.cantidad += cantidadSeleccionada
            } else {
                carrito.add(CarritoItem(producto, cantidadSeleccionada))
            }

            // Guardar carrito actualizado
            sharedPrefs.edit().putString("carrito", gson.toJson(carrito)).apply()

            Toast.makeText(
                context,
                "$cantidadSeleccionada ${if (cantidadSeleccionada == 1) "unidad" else "unidades"} de ${producto.nombre} añadidas al carrito",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun getItemCount(): Int = productos.size
}