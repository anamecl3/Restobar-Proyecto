package com.cibertec.reztrov01.view

import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.reztrov01.R
import com.cibertec.reztrov01.ReztroDB
import com.cibertec.reztrov01.controller.RestaurantsAdapters
import com.cibertec.reztrov01.controller.ScrollDistrictAdapter

class InicioActivity : AppCompatActivity() {

    private lateinit var db: ReztroDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        db = ReztroDB(this)

        // --- Dark mode switch ---
        val switchModoOscuro = findViewById<Switch>(R.id.switchModoOscuro)
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        switchModoOscuro.isChecked = isDarkMode

        switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
        }

        // --- Saludo al usuario ---
        val sharedPref = getSharedPreferences("usuarioPrefs", MODE_PRIVATE)
        val usuarioId = sharedPref.getInt("usuarioId", -1)
        val nombreUsuario: String? = db.obtenerUsuarioPorId(usuarioId)?.nombre

        if (nombreUsuario != null) {
            Toast.makeText(this, "Hola, $nombreUsuario", Toast.LENGTH_LONG).show()
        }

        // --- Asignar saludo en el TextView ---
        val mensaje = getString(R.string.hola_usuario, nombreUsuario ?: "Usuario")
        findViewById<TextView>(R.id.textViewGreeting).text = mensaje

        // --- Inicializar vistas ---
        setupDistrictRecyclerView()
        setupRestaurantSections()

        // --- Insets ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupDistrictRecyclerView() {
        val recyclerViewDistricts = findViewById<RecyclerView>(R.id.recyclerViewDistricts)
        val districts = listOf("Miraflores", "San Isidro", "Barranco", "Surco", "Callao")

        val adapter = ScrollDistrictAdapter(districts) { selectedDistrict ->
            Toast.makeText(this, "Seleccionaste $selectedDistrict", Toast.LENGTH_SHORT).show()
            // Aquí podrías filtrar los restaurantes por distrito
        }

        recyclerViewDistricts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDistricts.adapter = adapter
    }

    private fun setupRestaurantSections() {
        val pollos = db.listarRestaurantesPorTipo("pollos")
        val trattorias = db.listarRestaurantesPorTipo("trattoria")
        val nikkei = db.listarRestaurantesPorTipo("nikkei")

        findViewById<RecyclerView>(R.id.recyclerViewPollosBrasa).apply {
            layoutManager = LinearLayoutManager(this@InicioActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = RestaurantsAdapters(pollos, this@InicioActivity) {
                Toast.makeText(context, "Reservar en ${it.nombre}", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<RecyclerView>(R.id.recyclerViewTrattorias).apply {
            layoutManager = LinearLayoutManager(this@InicioActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = RestaurantsAdapters(trattorias, this@InicioActivity) {
                Toast.makeText(context, "Reservar en ${it.nombre}", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<RecyclerView>(R.id.recyclerViewNikkei).apply {
            layoutManager = LinearLayoutManager(this@InicioActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = RestaurantsAdapters(nikkei, this@InicioActivity) {
                Toast.makeText(context, "Reservar en ${it.nombre}", Toast.LENGTH_SHORT).show()
            }
        }

    }


}
