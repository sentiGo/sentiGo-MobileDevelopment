package com.sentigo.bangkit.sentigoapp.ui.detail

import com.sentigo.bangkit.sentigoapp.di.Result
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.data.local.entity.FavoriteEntity
import com.sentigo.bangkit.sentigoapp.data.remote.response.DetailDestinasi
import com.sentigo.bangkit.sentigoapp.databinding.ActivityDetailBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private lateinit var factory: ViewModelFactory
    private val detailViewModel: DetailViewModel by viewModels { factory }

    private lateinit var fav: FavoriteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        factory = ViewModelFactory.getInstance(this)

        val id = intent.getIntExtra(EXTRA_DESTINASI, 0)

        detailViewModel.getUserPref.observe(this) {
            Log.d("DetailActivity", id.toString())
            detailViewModel.getDetailDestinasi(it.token, id)
        }

        detailViewModel.getDetailDestinasiResponse.observe(this) { data ->
            if (data != null) {
                when (data) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setData(data.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, data.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        detailViewModel.getFavoriteDb().observe(this) {
            setFavorite(it, id)
        }

        setupAction()
    }

    private fun setFavorite(data: List<FavoriteEntity>, id: Int) {
        val favorite = data.any { it.id == id }
        setFavoriteIcon(favorite)

        binding.btnFavorite.setOnClickListener {
            if (!favorite) {
                detailViewModel.saveFavoriteDb(fav)
                showToast(getString(R.string.add_favorite))
            }
            else {
                detailViewModel.deleteFavoriteDb(id)
                showToast(getString(R.string.delete_favorite))
            }
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        binding.btnFavorite.apply {
            if (isFavorite) {
                setImageDrawable(ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.full_favorite
                ))
            } else {
                setImageDrawable(ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.outline_favorite
                ))
            }
        }
    }

    private fun setData(item: DetailDestinasi) {
        binding.tvCity.text = item.city
        binding.tvAddress.text = item.address
        binding.tvName.text = item.name
        binding.tvDesc.text = item.description
        binding.tvRating.text = item.rating.toString()

        fav = FavoriteEntity(
            item.id,
            item.name,
            item.rating,
            item.city,
            item.img,
            true
        )

        when (item.category) {
            "Tempat Nongkrong" -> binding.tvCategory.chipText = "Caffe"
            "Tempat Wisata" -> binding.tvCategory.chipText = "Vocation"
            else -> binding.tvCategory.chipText = item.category
        }

        Glide.with(this.applicationContext)
            .load(item.img)
            .into(binding.imgDetailPhoto)
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_DESTINASI = "extra_destinasi"
    }
}