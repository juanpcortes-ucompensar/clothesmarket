package com.example.clothesmarket.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.clothesmarket.R

class SplashActivity : AppCompatActivity() {

    private val SPLAH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splah)
        Log.d("SplahActivity", "onCreate: Iniciando Activity Splah")

        //Configurar el temporizador de redireccion a Welcome Activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        },SPLAH_TIME_OUT)
    }

    override fun onStart() {
        super.onStart()
        Log.d("SplahActivity", "onStart: Activity Splah está en primer plano")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SplahActivity", "onPause: Activity Splah está pausada")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SplahActivity", "onStop: Activity Splah está pausada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SplahActivity", "onDestroy: Activity Splah está destruida")
    }
}