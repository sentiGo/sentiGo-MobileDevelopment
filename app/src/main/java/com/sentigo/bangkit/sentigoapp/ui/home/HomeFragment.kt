package com.sentigo.bangkit.sentigoapp.ui.home

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sentigo.bangkit.sentigoapp.data.remote.response.ListDestinasiItem
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.databinding.FragmentHomeBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.di.DataKota

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvItems.layoutManager = layoutManager

        val layoutManagerLocation = GridLayoutManager(requireActivity(), 2)
        binding.rvLocation.layoutManager = layoutManagerLocation

        val layoutManagerCity = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCity.layoutManager = layoutManagerCity

        binding.rvCity.adapter = CityAdapter(DataKota.getCity())

        getMyLocation()

        homeViewModel.getUser.observe(viewLifecycleOwner) {
            homeViewModel.getListRatingDestinasi(it.token)
            homeViewModel.getListLocationDestinasi(it.token, it.lat, it.lon)
        }

        homeViewModel.listRatingDestinasi.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                when (list) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setDestinasiDataRating(list.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), list.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        homeViewModel.listLocationDestinasi.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                when (list) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setDestinasiDataLocation(list.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), list.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLocation()
                }
                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLocation()
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLocation() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    homeViewModel.setLocationPref(location.latitude, location.longitude)
                } else {
                    showToast("Location is not found. Try Again")
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun setDestinasiDataRating(listDestinasi: List<ListDestinasiItem>) {
        val data = ArrayList(listDestinasi)
        val adapter = DestinasiAdapter(data.filterNot { it.id == 256 })
        binding.rvItems.adapter = adapter
    }

    private fun setDestinasiDataLocation(listDestinasi: List<ListDestinasiItem>) {
        val data = ArrayList(listDestinasi)
        val adapter = DestinasiAdapter(data)
        binding.rvLocation.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}