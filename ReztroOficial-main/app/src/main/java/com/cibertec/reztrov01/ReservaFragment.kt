package com.cibertec.reztrov01

import android.os.Bundle
import android.util.Log
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
        try {
            dbHelper = ReztroDB(requireContext())

            val prefs = requireActivity().getSharedPreferences("usuarioPrefs", android.content.Context.MODE_PRIVATE)
            val usuarioId = prefs.getInt("usuarioId", -1)
            val usuario = dbHelper.obtenerUsuarioPorId(usuarioId)

            if (usuario != null) {
                listaHistorial.addAll(dbHelper.listarReservasPorDni(usuario.dni))
                adapter = ReservaAdapter(listaHistorial)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }

        } catch (e: Exception) {
            Log.e("ReservaFragment", "Error al listar reservas", e)
        }

    }

}
