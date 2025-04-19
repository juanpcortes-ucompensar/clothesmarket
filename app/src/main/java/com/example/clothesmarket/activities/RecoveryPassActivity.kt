package com.example.clothesmarket.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clothesmarket.R

class RecoveryPassActivity: AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var buttonSend: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_pass)

        Log.d("RecoveryPassActivity", "onCreate: Iniciando Activity Recovery Pass")

        //Inicializar las variables
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSend = findViewById(R.id.bt_recovery_pass)


        buttonSend.setOnClickListener{
            if (validarCorreo()){
                //metodo de guardar datos de usuario
                verificarCorreo()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("RecoveryPassActivity", "onStart: RecoveryPass Activity está en primer plano")
    }

    override fun onPause() {
        super.onPause()
        Log.d("RecoveryPassActivity", "onPause: RecoveryPass Activity está pausada")
    }

    override fun onStop() {
        super.onStop()
        Log.d("RecoveryPassActivity", "onStop: RecoveryPass Activity está pausada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("RecoveryPassActivity", "onDestroy: RecoveryPass Activity está destruida")
    }

    private fun validarCorreo(): Boolean {
        val correo = editTextEmail.text.toString().trim()
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "El campo correo es requerido y debe tener un formato correcto: 'ejemplo@ejemplo.com'", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verificarCorreo() {
        val correo = editTextEmail.text.toString().trim()
        val correoRegistrado = sharedPreferences.getString("correo","")

        if (correo == correoRegistrado) {
            Toast.makeText(this, "Se ha enviado correo", Toast.LENGTH_SHORT).show()
            Log.d("RecoveryPassActivity", "verificarCorreo: Correo se ha enviado")
            buttonSend.postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1500)
        } else {
            Toast.makeText(this, "El correo ingresado no se encuentra registrado en el sistema", Toast.LENGTH_SHORT).show()
            Log.d("RecoveryPassActivity", "verificarCorreo: Correo no registrado")
        }
    }

}
