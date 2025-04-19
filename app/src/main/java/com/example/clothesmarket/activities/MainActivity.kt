package com.example.clothesmarket.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.clothesmarket.R
import com.example.clothesmarket.models.Producto
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Limpiar SharedPreferences
        val keys = listOf("productos_pref", "categorias_pref", "carrito_pref")
        for (key in keys) {
            val prefs = getSharedPreferences(key, MODE_PRIVATE)
            prefs.edit().clear().apply()
        }

        inicializarProductos()
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicioFragment,
                R.id.categoriasFragment,
                R.id.carritoFragment,
                R.id.perfilFragment,
                R.id.productosFragment
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { menuItem ->
            val handled: Boolean

            // Cierra el menú lateral
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.categoriasFragment -> {
                    // Evita múltiples copias en el back stack
                    navController.popBackStack(R.id.categoriasFragment, true)
                    navController.navigate(R.id.categoriasFragment)
                    handled = true
                }

                R.id.inicioFragment -> {
                    navController.popBackStack(R.id.inicioFragment, false)
                    navController.navigate(R.id.inicioFragment)
                    handled = true
                }

                R.id.carritoFragment -> {
                    navController.popBackStack(R.id.carritoFragment, true)
                    navController.navigate(R.id.carritoFragment)
                    handled = true
                }

                R.id.perfilFragment -> {
                    navController.popBackStack(R.id.perfilFragment, true)
                    navController.navigate(R.id.perfilFragment)
                    handled = true
                }

                R.id.productosFragment -> {
                    navController.popBackStack(R.id.productosFragment, true)
                    navController.navigate(R.id.productosFragment)
                    handled = true
                }

                else -> handled = false
            }

            handled
        }

        // Configurar accion del regreso a la pantalla de perfil al ingresar a editar perfil
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.editarPerfilFragment) {
                // Ocultar el botón de hamburguesa
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            } else {
                // Mostrar el botón de hamburguesa
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                drawerToggle.syncState()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun inicializarProductos() {
        val sharedPrefs = getSharedPreferences("productos_pref", MODE_PRIVATE)
        sharedPrefs.edit().clear().apply()
        if (!sharedPrefs.contains("productos")) {
            val productos = listOf(
                // Invierno
                Producto("1", "Abrigo Largo", "Abrigo térmico para el invierno", 79.99, 10, "inv_abrigolargo", "Invierno"),
                Producto("2", "Guantes de Lana", "Guantes gruesos para bajas temperaturas", 14.99, 25, "inv_guanteslana", "Invierno"),
                Producto("3", "Bufanda Invernal", "Bufanda suave y cálida", 19.99, 15, "inv_bufandainvernal", "Invierno"),
                Producto("4", "Gorro de Lana", "Gorro estilo clásico para el frío", 12.99, 20, "inv_gorrolana", "Invierno"),
                Producto("5", "Chaqueta Acolchada", "Chaqueta resistente al viento", 49.99, 8, "inv_chaquetaacolchada", "Invierno"),
                Producto("6", "Calcetines Térmicos", "Calcetines para mantener los pies calientes", 9.99, 30, "inv_calcetines", "Invierno"),
                Producto("7", "Pantalón de Invierno", "Pantalón aislante para el frío extremo", 34.99, 12, "inv_pantaloninvernal", "Invierno"),
                Producto("8", "Botas de Nieve", "Botas impermeables y aislantes", 59.99, 7, "inv_botasnieve", "Invierno"),

                // Primavera
                Producto("9", "Vestido Floral", "Vestido ligero con estampado floral", 39.99, 15, "pri_vestidofloral", "Primavera"),
                Producto("10", "Camisa Casual", "Camisa de algodón manga larga", 24.99, 20, "pri_camisacasual", "Primavera"),
                Producto("11", "Falda Midi", "Falda cómoda y elegante", 29.99, 10, "pri_faldamidi", "Primavera"),
                Producto("12", "Chaqueta Liviana", "Chaqueta ideal para el clima templado", 34.99, 10, "pri_chaquetaliviana", "Primavera"),
                Producto("13", "Blusa de Encaje", "Blusa fresca y delicada", 22.99, 12, "pri_blusaencaje", "Primavera"),
                Producto("14", "Zapatos de Tela", "Zapatos ligeros para días soleados", 24.99, 18, "pri_zapatos", "Primavera"),
                Producto("15", "Shorts de Primavera", "Shorts cómodos para actividades al aire libre", 19.99, 15, "pri_shorts", "Primavera"),
                Producto("16", "Sombrero de Ala Ancha", "Sombrero protector del sol", 14.99, 25, "pri_sombrero", "Primavera"),

                // Otoño
                Producto("17", "Suéter de Punto", "Suéter cálido para tardes frescas", 29.99, 12, "oto_sueterpunto", "Otoño"),
                Producto("18", "Pantalón Chino", "Pantalón elegante pero cómodo", 39.99, 8, "oto_pantalonchino", "Otoño"),
                Producto("19", "Chaqueta de Cuero", "Chaqueta clásica para clima fresco", 69.99, 5, "oto_chaquetacuero", "Otoño"),
                Producto("20", "Camisa Cuadros", "Camisa estilo otoñal", 24.99, 20, "oto_camiscacuadros", "Otoño"),
                Producto("21", "Botines", "Botines elegantes y resistentes", 49.99, 9, "oto_botines", "Otoño"),
                Producto("22", "Bufanda Estampada", "Bufanda ligera con estilo", 19.99, 15, "oto_bufanda", "Otoño"),
                Producto("23", "Vestido Largo", "Vestido de tela ligera para otoño", 44.99, 10, "oto_vestidolargo", "Otoño"),
                Producto("24", "Sombrero Fedora", "Sombrero moderno para otoño", 34.99, 8, "oto_sombrerofedora", "Otoño"),

                // Verano
                Producto("25", "Camiseta sin Mangas", "Camiseta fresca para días calurosos", 15.99, 20, "ver_camisetamangas", "Verano"),
                Producto("26", "Shorts Livianos", "Shorts para disfrutar del verano", 19.99, 25, "ver_shorts", "Verano"),
                Producto("27", "Sandalias", "Sandalias cómodas y elegantes", 29.99, 18, "ver_sandalias", "Verano"),
                Producto("28", "Vestido de Playa", "Vestido ligero para la playa", 34.99, 8, "ver_vestidoplaya", "Verano"),
                Producto("29", "Sombrero de Paja", "Sombrero fresco para protegerse del sol", 14.99, 20, "ver_sombreropaja", "Verano"),
                Producto("30", "Traje de Baño", "Traje cómodo para nadar", 24.99, 15, "ver_trajebanio", "Verano"),
                Producto("31", "Camiseta Tropical", "Camiseta con estampado vibrante", 17.99, 12, "ver_camisetatro", "Verano"),
                Producto("32", "Gafas de Sol", "Gafas protectoras y modernas", 19.99, 25, "ver_gafassol", "Verano")
            )

            val gson = Gson()
            val json = gson.toJson(productos)
            sharedPrefs.edit().putString("productos", json).apply()
        }
    }
}