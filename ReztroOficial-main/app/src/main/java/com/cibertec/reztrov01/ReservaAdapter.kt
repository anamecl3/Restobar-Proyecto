package com.cibertec.reztrov01

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.reztrov01.models.Reserva


class ReservaAdapter(private val lista: List<Reserva>) :
    RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuarioReserva)
        val tvRestaurante: TextView = itemView.findViewById(R.id.tvRestauranteReserva)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaReserva)
        val tvHora: TextView = itemView.findViewById(R.id.tvHorarioReserva)
        val tvMesa: TextView = itemView.findViewById(R.id.tvMesaReserva)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reserva, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.tvUsuario.text = "Reserva :  ${item.id} - ${item.nomUsuario}"
        holder.tvRestaurante.text = "Restaurante : ${item.restaurante}"
        holder.tvFecha.text = "Fecha: ${item.fecha}"
        holder.tvHora.text = "Hora: ${item.horario} "
        holder.tvMesa.text = "Mesa: ${item.mesa } "

    }

    override fun getItemCount(): Int = lista.size
}
