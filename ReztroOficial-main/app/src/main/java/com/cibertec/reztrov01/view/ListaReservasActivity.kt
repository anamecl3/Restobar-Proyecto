package com.cibertec.reztrov01.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cibertec.reztrov01.R
import com.cibertec.reztrov01.ReservaFragment

class ListaReservasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentosReservas, ReservaFragment())
            .commit()
    }
}
