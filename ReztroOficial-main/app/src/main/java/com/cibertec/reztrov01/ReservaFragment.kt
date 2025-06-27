package com.cibertec.reztrov01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.reztrov01.data.ReztroDB
import com.cibertec.reztrov01.models.Reserva

class ReservaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReservaAdapter
    private lateinit var dbHelper: ReztroDB
    private val listaHistorial = mutableListOf<Reserva>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerHistorial)

        dbHelper = ReztroDB(requireContext())

        listaHistorial.addAll(dbHelper.listarReservas())
        adapter = ReservaAdapter(listaHistorial)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

    }

}
