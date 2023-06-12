package com.sentigo.bangkit.sentigoapp.ui.find

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.databinding.ActivityListFindBinding
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.ui.home.DestinasiAdapter

class ListFindActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListFindBinding

    private lateinit var factory: ViewModelFactory
    private val findViewModel: FindViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFindBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        factory = ViewModelFactory.getInstance(this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvItems.layoutManager = layoutManager

        val desc = intent.getStringExtra(EXTRA_DESC)


        findViewModel.getUserPref.observe(this) {
            findViewModel.getListFind(it.token, desc!!)
        }

        findViewModel.listFindResponse.observe(this) { list ->
            if (list != null) {
                when (list) {
                    is Result.Loading -> { binding.progressBar.visibility = View.VISIBLE}

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        setDataList(list.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, list.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setDataList(listData: List<ListDestinasiItem>) {
        val data = ArrayList(listData)
        val adapter = DestinasiAdapter(data)
        binding.rvItems.adapter = adapter
    }

    companion object {
        const val EXTRA_DESC = "extra_desc"
    }
}