package com.sentigo.bangkit.sentigoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sentigo.bangkit.sentigoapp.databinding.ItemCityBinding
import com.sentigo.bangkit.sentigoapp.model.City

class CityAdapter(
    private val listCity: ArrayList<City>
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listCity.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCity[position])
    }

    class ViewHolder (binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root){
        private val tvCity: TextView = binding.tvCity
        private val imgCity : ImageView = binding.imgCity

        fun bind(item: City) {
            Glide.with(itemView.context)
                .load(item.img)
                .centerCrop()
                .into(imgCity)

            tvCity.text = item.name

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, item.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}