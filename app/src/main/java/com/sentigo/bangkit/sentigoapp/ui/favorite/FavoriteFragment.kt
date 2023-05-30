package com.sentigo.bangkit.sentigoapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.data.local.entity.FavoriteEntity
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.databinding.FragmentFavoriteBinding
import com.sentigo.bangkit.sentigoapp.databinding.FragmentHomeBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.ui.home.DestinasiAdapter
import com.sentigo.bangkit.sentigoapp.ui.home.HomeViewModel

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val favViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvItems.layoutManager = layoutManager

        favViewModel.getFavoriteDb().observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) binding.tvEmpty.visibility = View.VISIBLE
            setDestinasiData(list)
        }
    }

    private fun setDestinasiData(listDestinasi: List<FavoriteEntity>) {
        val data = ArrayList(listDestinasi)
        val adapter = FavoriteAdapter(data)
        binding.rvItems.adapter = adapter
    }
}