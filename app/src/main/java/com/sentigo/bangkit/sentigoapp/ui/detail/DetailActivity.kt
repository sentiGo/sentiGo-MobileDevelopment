package com.sentigo.bangkit.sentigoapp.ui.detail

import android.content.Intent
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
import com.sentigo.bangkit.sentigoapp.ui.map.MapActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private lateinit var factory: ViewModelFactory
    private val detailViewModel: DetailViewModel by viewModels { factory }

    private lateinit var fav: FavoriteEntity
    private var lat : Double? = null
    private var lon : Double? = null
    private var name : String? = null

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
                        Log.d("DetailActivity", data.error)
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

        lat = item.lat
        lon = item.lon
        name = item.name

        when (item.category) {
            "Tempat Nongkrong" -> binding.tvCategory.chipText = "Caffe"
            "Tempat nongkrong" -> binding.tvCategory.chipText = "Caffe"
            "Tongkrongan" -> binding.tvCategory.chipText = "Caffe"
            "Tempat Wisata" -> binding.tvCategory.chipText = "Vocation"
            "tempat wisata" -> binding.tvCategory.chipText = "Vocation"
            "makam pahlawan" -> binding.tvCategory.chipText = "Cemetery"
            "Wisata Edukasi" -> binding.tvCategory.chipText = "Education"
            "Wisata Agro" -> binding.tvCategory.chipText = "Agritourism"
            "Wisata Alam" -> binding.tvCategory.chipText = "Nature"
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

        binding.btnMap.setOnClickListener {
            val intent = Intent(this@DetailActivity, MapActivity::class.java)
            intent.putExtra(MapActivity.EXTRA_LAT, lat)
            intent.putExtra(MapActivity.EXTRA_LON, lon)
            intent.putExtra(MapActivity.EXTRA_NAME, name)

            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_DESTINASI = "extra_destinasi"
    }
}