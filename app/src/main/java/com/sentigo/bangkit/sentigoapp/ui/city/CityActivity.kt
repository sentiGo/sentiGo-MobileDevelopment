package com.sentigo.bangkit.sentigoapp.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.databinding.ActivityCityBinding
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.ui.home.DestinasiAdapter

class CityActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCityBinding

    private lateinit var factory: ViewModelFactory
    private val cityViewModel: CityViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        factory = ViewModelFactory.getInstance(this)

        val cityName = intent.getStringExtra(EXTRA_CITY)

        val layoutManager = GridLayoutManager(this, 2)
        binding.rvCity.layoutManager = layoutManager

        cityViewModel.getUserPref.observe(this) {
            cityViewModel.getCityList(it.token, cityName!!)
        }

        cityViewModel.listCityResponse.observe(this) { list ->
            if (list != null) {
                when (list) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvCity.text = cityName
                        setCityListData(list.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, list.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun setCityListData(listCity: List<ListDestinasiItem>) {
        val data = ArrayList(listCity)
        val adapter = DestinasiAdapter(data)
        binding.rvCity.adapter = adapter
    }

    companion object {
        const val EXTRA_CITY = "extra_city"
    }
}