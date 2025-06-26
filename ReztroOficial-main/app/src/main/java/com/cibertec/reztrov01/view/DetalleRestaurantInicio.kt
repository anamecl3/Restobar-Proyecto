package com.cibertec.reztrov01.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cibertec.reztrov01.R
import com.cibertec.reztrov01.ReservaActivity
import com.cibertec.reztrov01.models.Restaurant

class DetalleRestaurantInicio : AppCompatActivity() {
    private lateinit var btnReservar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_restaurant_inicio)

        val restaurant = intent.getParcelableExtra<Restaurant>("restaurante")
        if (restaurant == null) {
            Toast.makeText(this, "No se encontró el restaurante", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnReservar = findViewById(R.id.btnReservar)
        btnReservar.setOnClickListener{
            val intent = Intent(this, ReservaActivity::class.java)
            startActivity(intent)
        }



        val nombreTextView = findViewById<TextView>(R.id.textDetalleNombre)
        val ratingTextView = findViewById<TextView>(R.id.textDetalleRating)
        val imagenView = findViewById<ImageView>(R.id.imageDetalle)
        val distritoTextView = findViewById<TextView>(R.id.textDetalleDistrito)

        nombreTextView.text = restaurant.nombre
        ratingTextView.text = "★ ${restaurant.rating}"
        imagenView.setImageResource(restaurant.imagen)
        distritoTextView.text = restaurant.distrito
    }

}