package com.example.clothesmarket.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.clothesmarket.R
import androidx.navigation.fragment.findNavController

class PerfilFragment  : Fragment() {

    private lateinit var textViewName: TextView
    private lateinit var textViewLastName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var buttonEdit: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        textViewName = view.findViewById(R.id.tv_name)
        textViewLastName = view.findViewById(R.id.tv_lastname)
        textViewEmail = view.findViewById(R.id.tv_email)
        textViewPhone = view.findViewById(R.id.tv_phone)
        buttonEdit = view.findViewById(R.id.bt_edit)

        llenarDatosUsuario()

        buttonEdit.setOnClickListener {
            findNavController().navigate(R.id.editarPerfilFragment)
        }

        return view
    }

    private fun llenarDatosUsuario() {
        val nombres = sharedPreferences.getString("nombre","")
        val apellido = sharedPreferences.getString("apellido","")
        val correo = sharedPreferences.getString("correo","")
        val telefono = sharedPreferences.getString("telefono","")

        textViewName.setText("Nombres: " + nombres)
        textViewLastName.setText("Apellidos: " + apellido)
        textViewEmail.setText("Correo: " + correo)
        textViewPhone.setText("Telefono: " + telefono)
    }

    override fun onResume() {
        super.onResume()
        llenarDatosUsuario()
    }
}