package com.cibertec.reztrov01.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.reztrov01.R
class ScrollDistrictAdapter( private val districts: List<String>,
                             private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ScrollDistrictAdapter.DistrictViewHolder>() {

    inner class DistrictViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistrict: TextView = itemView.findViewById(R.id.tvDistrict)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_district, parent, false)
        return DistrictViewHolder(view)
    }
    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val district = districts[position]
        holder.tvDistrict.text = district
        holder.tvDistrict.setOnClickListener {
            onClick(district)
        }
    }

    override fun getItemCount(): Int = districts.size
}