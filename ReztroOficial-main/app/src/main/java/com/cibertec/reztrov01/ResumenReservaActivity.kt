package com.cibertec.reztrov01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.reztrov01.data.ReztroDB
import com.cibertec.reztrov01.models.Reserva
import com.cibertec.reztrov01.view.InicioActivity

class ResumenReservaActivity : AppCompatActivity() {

    private lateinit var tvResumen: TextView
    private lateinit var btnConfirmar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_reserva)

        tvResumen = findViewById(R.id.tvResumenReserva)
        btnConfirmar = findViewById(R.id.btnConfirmarReserva)
        btnCancelar = findViewById(R.id.btnCancelar)

        val nombre = intent.getStringExtra("nombreUsuario") ?: ""
        val dni = intent.getStringExtra("dni") ?: ""
        val restaurante = intent.getStringExtra("restaurante") ?: ""
        val fecha = intent.getStringExtra("fecha") ?: ""
        val hora = intent.getStringExtra("hora") ?: ""
        val mesa = intent.getStringExtra("mesa") ?: ""

        val resumenTexto = """
            Nombre: $nombre
            DNI: $dni
            Restaurante: $restaurante
            Fecha: $fecha
            Hora: $hora
            Mesa: $mesa
        """.trimIndent()

        tvResumen.text = resumenTexto

        btnConfirmar.setOnClickListener {
            val reserva = Reserva(
                id=0,//se genera automaticamente
                nomUsuario = nombre,
                dni = dni,
                restaurante = restaurante,
                fecha = fecha,
                horario = hora,
                mesa = mesa,
                qr = "QR_${System.currentTimeMillis()}"
            )

            val db = ReztroDB(this)

            if (db.existeReserva(reserva)) {
                Toast.makeText(this, "Ya tienes una reserva para ese día y hora", Toast.LENGTH_SHORT).show()
            } else if (db.registrarReserva(reserva)) {
                Toast.makeText(this, "¡Reserva confirmada!", Toast.LENGTH_LONG).show()

                val intent = Intent(this, InicioActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Error al registrar la reserva", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }
}
