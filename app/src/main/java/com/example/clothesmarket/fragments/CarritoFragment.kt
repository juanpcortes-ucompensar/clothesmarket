package com.example.clothesmarket.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesmarket.R
import com.example.clothesmarket.adapters.CarritoAdapter
import com.example.clothesmarket.models.CarritoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CarritoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalProductos: TextView
    private lateinit var tvTotalPrecio: TextView
    private lateinit var btnPagar: Button
    private lateinit var carritoAdapter: CarritoAdapter
    private val carrito = mutableListOf<CarritoItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        recyclerView = view.findViewById(R.id.recycler_carrito)
        tvTotalProductos = view.findViewById(R.id.tv_total_productos)
        tvTotalPrecio = view.findViewById(R.id.tv_total_precio)
        btnPagar = view.findViewById(R.id.btn_pagar)

        recyclerView.layoutManager = LinearLayoutManager(context)
        cargarCarrito()

        carritoAdapter = CarritoAdapter(carrito) { item ->
            eliminarItem(item)
        }
        recyclerView.adapter = carritoAdapter

        btnPagar.setOnClickListener {
            Toast.makeText(context, "Funcionalidad de pago no implementada aún", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun cargarCarrito() {
        val prefs = requireContext().getSharedPreferences("carrito_pref", Context.MODE_PRIVATE)
        val json = prefs.getString("carrito", null) ?: return
        val type = object : TypeToken<MutableList<CarritoItem>>() {}.type
        carrito.clear()
        carrito.addAll(Gson().fromJson(json, type))
        actualizarResumen()
    }

    private fun actualizarResumen() {
        val totalProductos = carrito.sumOf { it.cantidad }
        val totalPrecio = carrito.sumOf { it.cantidad * it.producto.precio }
        tvTotalProductos.text = "Total productos: $totalProductos"
        tvTotalPrecio.text = "Precio total: $${"%.2f".format(totalPrecio)}"
    }

    private fun eliminarItem(item: CarritoItem) {
        carrito.remove(item)
        guardarCarrito()
        carritoAdapter.notifyDataSetChanged()
        actualizarResumen()
    }

    private fun guardarCarrito() {
        val prefs = requireContext().getSharedPreferences("carrito_pref", Context.MODE_PRIVATE)
        val json = Gson().toJson(carrito)
        prefs.edit().putString("carrito", json).apply()
    }
}
