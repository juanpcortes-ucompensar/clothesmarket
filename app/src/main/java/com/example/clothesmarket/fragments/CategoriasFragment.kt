package com.example.clothesmarket.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesmarket.R
import com.example.clothesmarket.adapters.CategoriaAdapter
import com.example.clothesmarket.models.Categoria
import com.example.clothesmarket.models.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.navigation.fragment.findNavController

class CategoriasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categorias: List<Categoria>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        recyclerView = view.findViewById(R.id.recycler_categorias)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        categorias = obtenerCategoriasConCantidad()
        recyclerView.adapter = CategoriaAdapter(categorias) { categoria ->
            val bundle = Bundle().apply {
                putString("categoriaNombre", categoria.nombre)
            }

            findNavController().navigate(R.id.productosFragment, bundle)
        }

        return view
    }

    private fun obtenerCategoriasConCantidad(): List<Categoria> {
        val prefs = requireContext().getSharedPreferences("productos_pref", Context.MODE_PRIVATE)
        val json = prefs.getString("productos", null) ?: return emptyList()
        val productos = Gson().fromJson<List<Producto>>(json, object : TypeToken<List<Producto>>() {}.type)

        fun contar(nombreCategoria: String) = productos.count {
            it.categoria.equals(nombreCategoria, ignoreCase = true)
        }

        return listOf(
            Categoria("1", "Invierno", "cat_invierno", contar("Invierno")),
            Categoria("2", "Primavera", "cat_primavera", contar("Primavera")),
            Categoria("3", "Verano", "cat_verano", contar("Verano")),
            Categoria("4", "Otoño", "cat_otono", contar("Otoño")),
        )
    }
}
