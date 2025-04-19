package com.example.clothesmarket.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clothesmarket.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordRepeat: EditText
    private lateinit var buttonRegister: Button
    private lateinit var checkBoxTerms: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.d("RegisterActivity", "onCreate: Iniciando Activity Register")

        //Inicializar las variables
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        editTextName = findViewById(R.id.et_name)
        editTextLastName = findViewById(R.id.et_lastname)
        editTextEmail = findViewById(R.id.et_email)
        editTextPhone = findViewById(R.id.et_phone)
        editTextPassword = findViewById(R.id.et_password)
        editTextPasswordRepeat = findViewById(R.id.et_password_repeat)
        buttonRegister = findViewById(R.id.bt_registry)

        //Configuración listener boton de registro
        buttonRegister.setOnClickListener{
            if (validarCampos()){
                //metodo de guardar datos de usuario
                guardarDatosUsuario()
                //redireccionamiento
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun validarCampos(): Boolean {
        val nombres = editTextName.text.toString().trim()
        val apellido = editTextLastName.text.toString().trim()
        val correo = editTextEmail.text.toString().trim()
        val telefono = editTextPhone.text.toString().trim()
        val contrasena = editTextPassword.text.toString().trim()
        val contrasenaRepeat = editTextPasswordRepeat.text.toString().trim()
        if (nombres.isEmpty()){
            Toast.makeText(this, "El campo Nombres es requerido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (apellido.isEmpty()){
            Toast.makeText(this, "El campo Apellido es requerido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "El campo correo es requerido y debe tener un formato correcto: 'ejemplo@ejemplo.com'", Toast.LENGTH_SHORT).show()
            return false
        }
        if (telefono.isEmpty()){
            Toast.makeText(this, "El campo Telefono es requerido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (contrasena.isEmpty()){
            Toast.makeText(this, "El campo Contraseña es requerido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (contrasenaRepeat.isEmpty()){
            Toast.makeText(this, "El campo Repetir contraseña es requerido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (contrasena != contrasenaRepeat){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun guardarDatosUsuario(){
        val editor = sharedPreferences.edit()
        editor.putString("nombre",editTextName.text.toString().trim())
        editor.putString("apellido",editTextLastName.text.toString().trim())
        editor.putString("correo",editTextEmail.text.toString().trim())
        editor.putString("telefono",editTextPhone.text.toString().trim())
        editor.putString("contraseña",editTextPassword.text.toString().trim())
        editor.apply()
        Log.d("Register Activity", "guardarDatosUsuario: Datos del usuario guardados")
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
    }
}