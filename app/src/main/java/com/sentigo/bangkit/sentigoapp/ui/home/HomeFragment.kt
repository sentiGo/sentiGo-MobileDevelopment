package com.sentigo.bangkit.sentigoapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.databinding.FragmentHomeBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvItems.layoutManager = layoutManager

        homeViewModel.getUser.observe(viewLifecycleOwner) {
            homeViewModel.getListRatingDestinasi(it.token)
        }

        homeViewModel.listRatingDestinasi.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                when (list) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setDestinasiData(list.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), list.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setDestinasiData(listDestinasi: List<ListDestinasiItem>) {
        val data = ArrayList(listDestinasi)
        val adapter = DestinasiAdapter(data)
        binding.rvItems.adapter = adapter
    }
}