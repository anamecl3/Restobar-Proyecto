package com.cibertec.reztrov01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.reztrov01.data.ReztroDB
import com.cibertec.reztrov01.view.InicioActivity

class MainActivity : AppCompatActivity() {
    private lateinit var db: ReztroDB
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnIngresar: Button
    private lateinit var tvCrearCuenta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = ReztroDB(this)
        etCorreo = findViewById<EditText>(R.id.etCorreo)
        etPassword = findViewById<EditText>(R.id.etPassword)
        btnIngresar = findViewById<Button>(R.id.btnIngresar)
        tvCrearCuenta = findViewById<TextView>(R.id.tvCrearCuenta)

        tvCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistroCuentaActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString()

            if (correo.isEmpty()) {
                etCorreo.error = "El correo es obligatorio"
                return@setOnClickListener
            }

            if (!correo.contains("@") || !correo.contains(".")) {
                etCorreo.error = "Correo no válido"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "La contraseña es obligatoria"
                return@setOnClickListener
            }

            if (password.length < 6) {
                etPassword.error = "La contraseña debe tener al menos 6 caracteres"
                return@setOnClickListener
            }

            val usuario = db.obtenerUsuarioPorCredenciales(correo, password)

            if (usuario != null) {
                val sharedPref = getSharedPreferences("usuarioPrefs", MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putInt("usuarioId", usuario.codigo)
                    apply()
                }

                Toast.makeText(this, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
