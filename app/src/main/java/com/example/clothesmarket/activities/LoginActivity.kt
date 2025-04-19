package com.example.clothesmarket.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clothesmarket.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var textViewSignUp: TextView
    private lateinit var textViewRecovery: TextView
    private lateinit var buttonLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("LoginActivity", "onCreate: Iniciando Login Activity")
        editTextEmail = findViewById(R.id.etuser_login)
        editTextPassword = findViewById(R.id.etuser_password)
        textViewSignUp = findViewById(R.id.tv_sign_up)
        textViewRecovery = findViewById(R.id.tv_recovery)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        buttonLogin = findViewById(R.id.bt_login)

        textViewSignUp.setOnClickListener{
            //redireccionamiento
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        textViewRecovery.setOnClickListener{
            //redireccionamiento
            val intent = Intent(this, RecoveryPassActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener{
            if (validarCredenciales()){
                //redireccionamiento
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LoginActivity", "onStart: Login Activity está en primer plano")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LoginActivity", "onPause: Login Activity está pausada")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LoginActivity", "onStop: Login Activity está pausada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LoginActivity", "onDestroy: Login Activity está destruida")
    }

    private fun validarCredenciales(): Boolean {
        val correo = editTextEmail.text.toString().trim()
        val contrasena = editTextPassword.text.toString().trim()
        val correoRegistrado = sharedPreferences.getString("correo","")
        val contrasebaRegistrado = sharedPreferences.getString("contraseña","")

        if (correo == correoRegistrado && contrasena == contrasebaRegistrado) {
            Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show()
            Log.d("LoginActivity", "validarCredenciales: Correo se ha enviado")
            return true
        } else {
            Toast.makeText(this, "Correo y/o contraseña ingresados incorrectos", Toast.LENGTH_SHORT).show()
            Log.d("LoginActivity", "validarCredenciales: Correo y/o contraseña ingresados incorrectos")
            return false
        }
    }
}