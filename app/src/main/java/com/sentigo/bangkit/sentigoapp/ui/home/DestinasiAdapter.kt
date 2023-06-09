package com.sentigo.bangkit.sentigoapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.databinding.ItemRowBinding
import com.sentigo.bangkit.sentigoapp.ui.detail.DetailActivity

class DestinasiAdapter(
    private val listDestinasi: List<ListDestinasiItem>
) : RecyclerView.Adapter<DestinasiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listDestinasi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDestinasi[position])
    }

    class ViewHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imgPhoto: ImageView = binding.imgItemPhoto
        private val tvName: TextView = binding.tvName
        private val tvCity: TextView = binding.tvCity
        private val tvRating: TextView = binding.tvRating

        fun bind(item: ListDestinasiItem) {
            Glide.with(itemView.context)
                .load(item.img)
                .centerCrop()
                .into(imgPhoto)

            tvName.text = item.name
            tvCity.text = item.city
            tvRating.text = item.rating.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DESTINASI, item.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}
