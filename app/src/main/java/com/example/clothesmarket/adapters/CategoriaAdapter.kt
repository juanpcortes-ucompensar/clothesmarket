package com.example.clothesmarket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesmarket.R
import com.example.clothesmarket.models.Categoria

class CategoriaAdapter(
    private val categorias: List<Categoria>,
    private val onItemClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.imagen_categoria)
        val nombre = itemView.findViewById<TextView>(R.id.nombre_categoria)
        val contador = itemView.findViewById<TextView>(R.id.contador_productos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.nombre.text = categoria.nombre
        holder.contador.text = "${categoria.cantidadProductos} productos"

        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(
            categoria.imagenNombre,
            "drawable",
            context.packageName
        )
        holder.imagen.setImageResource(if (resId != 0) resId else R.drawable.ic_launcher_foreground)

        holder.itemView.setOnClickListener {
            onItemClick(categoria)
        }
    }

    override fun getItemCount(): Int = categorias.size
}
