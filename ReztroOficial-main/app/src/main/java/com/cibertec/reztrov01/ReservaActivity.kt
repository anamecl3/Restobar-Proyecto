package com.cibertec.reztrov01

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.reztrov01.data.ReztroDB
import java.text.SimpleDateFormat
import java.util.*

class ReservaActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var spHora: Spinner
    private lateinit var btnContinuar: Button
    private lateinit var tvBienvenida: TextView

    private var fechaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva)

        calendarView = findViewById(R.id.calendarView)
        spHora = findViewById(R.id.spHora)
        btnContinuar = findViewById(R.id.btnContinuar)
        tvBienvenida = findViewById(R.id.tvBienvenidaReserva)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        fechaSeleccionada = sdf.format(Date(calendarView.date))

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            fechaSeleccionada = sdf.format(cal.time)
        }

        val horas = listOf("12:00", "13:00", "14:00", "18:00", "19:00", "20:00")
        spHora.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horas)

        val prefs = getSharedPreferences("usuarioPrefs", MODE_PRIVATE)
        val usuarioId = prefs.getInt("usuarioId", -1)
        val restaurante = intent.getStringExtra("restaurante") ?: "Restaurante X"

        if (usuarioId != -1) {
            val db = ReztroDB(this)
            val usuario = db.obtenerUsuarioPorId(usuarioId)

            if (usuario != null) {
                tvBienvenida.text = getString(R.string.hola_usuario, usuario.nombre)

                btnContinuar.setOnClickListener {
                    val hora = spHora.selectedItem.toString()
                    val mesa = "M1"

                    val intent = Intent(this, ResumenReservaActivity::class.java).apply {
                        putExtra("nombreUsuario", usuario.nombre)
                        putExtra("dni", usuario.dni)
                        putExtra("restaurante", restaurante)
                        putExtra("fecha", fechaSeleccionada)
                        putExtra("hora", hora)
                        putExtra("mesa", mesa)
                    }
                    startActivity(intent)
                }
            }
        } else {
            Toast.makeText(this, "Usuario no logueado", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
