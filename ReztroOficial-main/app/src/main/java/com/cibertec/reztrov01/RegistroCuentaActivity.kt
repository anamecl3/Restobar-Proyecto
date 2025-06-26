package com.cibertec.reztrov01

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class RegistroCuentaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cuenta)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmento, UsuarioFragment())
            .commit()
    }
}
