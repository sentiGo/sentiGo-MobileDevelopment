package com.sentigo.bangkit.sentigoapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.sentigo.bangkit.sentigoapp.data.remote.response.UserData
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.databinding.FragmentProfileBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.ui.profile.sheet.ChangePasswordFragment

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        profileViewModel.getUserPref.observe(viewLifecycleOwner) { user ->
            profileViewModel.getUser(user.token, user.id)
        }

        profileViewModel.getUserResponse.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                when (user) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setUserData(user.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), user.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        setupAction()
    }

    private fun setUserData(user: UserData) {
        binding.tvEmail.text = user.email
        binding.tvUsername.text = user.username
        changePhoto(user.img)
    }

    private fun setupAction() {

        val changePasswordBottomSheet = ChangePasswordFragment()

        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
        }

        binding.btnChangePassword.setOnClickListener {
            changePasswordBottomSheet.show(parentFragmentManager, ChangePasswordFragment.TAG)
        }
    }

    private fun changePhoto(url: String) {
        context?.let {
            Glide.with(it)
                .load(url)
                .into(binding.profileImage)
        }
    }

}