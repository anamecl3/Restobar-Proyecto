package com.cibertec.reztrov01.controller

import android.content.Context
import android.content.Intent
import com.cibertec.reztrov01.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.reztrov01.ReservaActivity
import com.cibertec.reztrov01.models.Restaurant
import com.cibertec.reztrov01.view.DetalleRestaurantInicio


class RestaurantsAdapters(
    private val restaurants: List<Restaurant>,
    private val context: Context,
    private val onReservarClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantsAdapters.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.textViewRestaurantName)
        val rating = itemView.findViewById<TextView>(R.id.textViewRating)
        val imagen = itemView.findViewById<ImageView>(R.id.imageViewRestaurant)
        val btnReservar = itemView.findViewById<Button>(R.id.btnReservar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.nombre.text = restaurant.nombre
        holder.rating.text = restaurant.rating.toString()
        holder.imagen.setImageResource(restaurant.imagen)


        holder.btnReservar.setOnClickListener {
            val intent = Intent(context, ReservaActivity::class.java)
            intent.putExtra("nombre", restaurant.nombre)
            intent.putExtra("rating", restaurant.rating)
            intent.putExtra("imagen", restaurant.imagen)
            intent.putExtra("distrito", restaurant.distrito)
            context.startActivity(intent)
        }

        holder.imagen.setOnClickListener {
            val intent = Intent(context, DetalleRestaurantInicio::class.java)
            intent.putExtra("restaurante", restaurant)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = restaurants.size
}
