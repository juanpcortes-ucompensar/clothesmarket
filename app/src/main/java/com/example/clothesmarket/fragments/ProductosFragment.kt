package com.example.clothesmarket.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesmarket.R
import com.example.clothesmarket.adapters.ProductoAdapter
import com.example.clothesmarket.models.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private val productos = mutableListOf<Producto>()
    private lateinit var spinnerCategorias: Spinner
    private lateinit var listaCategorias: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)

        recyclerView = view.findViewById(R.id.recycler_productos)
        recyclerView.layoutManager = LinearLayoutManager(context)

        spinnerCategorias = view.findViewById(R.id.spinner_categorias)

        listaCategorias = listOf("Todas las categorías", "Invierno", "Primavera", "Verano", "Otoño")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaCategorias)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorias.adapter = adapterSpinner

        // Inicializa el adapter antes de cargar productos
        productoAdapter = ProductoAdapter(productos)
        recyclerView.adapter = productoAdapter

        // Leer categoría del argumento (si llega desde CategoriasFragment)
        val categoriaArgumento = arguments?.getString("categoriaNombre")

        // Si vino desde CategoriasFragment, selecciona la categoría
        if (!categoriaArgumento.isNullOrEmpty()) {
            val index = listaCategorias.indexOfFirst { it.equals(categoriaArgumento, ignoreCase = true) }
            if (index != -1) spinnerCategorias.setSelection(index)
        }

        // Cargar productos inicialmente
        cargarProductos(spinnerCategorias.selectedItem.toString())

        // Listener para cuando el usuario cambia la categoría
        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = listaCategorias[position]
                cargarProductos(categoriaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        return view
    }

    private fun cargarProductos(categoria: String?) {
        val prefs = requireContext().getSharedPreferences("productos_pref", Context.MODE_PRIVATE)
        val json = prefs.getString("productos", null)

        if (json != null) {
            val tipo = object : TypeToken<List<Producto>>() {}.type
            val lista = Gson().fromJson<List<Producto>>(json, tipo)
            productos.clear()

            if (categoria.isNullOrEmpty() || categoria == "Todas las categorías") {
                productos.addAll(lista)
            } else {
                productos.addAll(lista.filter { it.categoria.equals(categoria, ignoreCase = true) })
            }

            productoAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, "No hay productos cargados", Toast.LENGTH_SHORT).show()
        }
    }
}
