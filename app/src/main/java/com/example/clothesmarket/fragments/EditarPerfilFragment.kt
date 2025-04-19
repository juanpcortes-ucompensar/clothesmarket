package com.example.clothesmarket.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.clothesmarket.R

class EditarPerfilFragment : Fragment() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTelefono: EditText
    private lateinit var btnGuardar: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_perfil, container, false)

        etNombre = view.findViewById(R.id.et_nombre)
        etApellido = view.findViewById(R.id.et_apellido)
        etCorreo = view.findViewById(R.id.et_correo)
        etTelefono = view.findViewById(R.id.et_telefono)
        btnGuardar = view.findViewById(R.id.btn_guardar)

        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        cargarDatos()

        btnGuardar.setOnClickListener {
            guardarCambios()
        }

        return view
    }

    private fun cargarDatos() {
        etNombre.setText(sharedPreferences.getString("nombre", ""))
        etApellido.setText(sharedPreferences.getString("apellido", ""))
        etCorreo.setText(sharedPreferences.getString("correo", ""))
        etTelefono.setText(sharedPreferences.getString("telefono", ""))
    }

    private fun guardarCambios() {
        val nombre = etNombre.text.toString()
        val apellido = etApellido.text.toString()
        val correo = etCorreo.text.toString()
        val telefono = etTelefono.text.toString()

        with(sharedPreferences.edit()) {
            putString("nombre", nombre)
            putString("apellido", apellido)
            putString("correo", correo)
            putString("telefono", telefono)
            apply()
        }

        Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()

        // Navegar de regreso al perfil
        findNavController().popBackStack()
    }
}
