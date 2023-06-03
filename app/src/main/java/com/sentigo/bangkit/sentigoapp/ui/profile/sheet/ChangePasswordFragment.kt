package com.sentigo.bangkit.sentigoapp.ui.profile.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.databinding.FragmentChangePasswordBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory

class ChangePasswordFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val viewModel: ChangePasswordSheetViewModel by viewModels { factory }

    private var token: String = ""
    private var id = -1

    private var clickBottom = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        viewModel.changePasswordResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Result.Loading -> {
                        if (clickBottom) binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (response.data.message == "Password is updated" && clickBottom){
                            Toast.makeText(requireActivity(), response.data.message, Toast.LENGTH_SHORT).show()
                            clickBottom = false
                            binding.edOldPassword.setText("")
                            binding.edNewPassword.setText("")
                            dismiss()
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), response.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.getUserPref.observe(viewLifecycleOwner) {
            token = it.token
            id = it.id
        }

        setupAction()
    }

    private fun setupAction() {

        binding.btnChangePassword.setOnClickListener {
            val oldPassword = binding.edOldPassword.text.toString()
            val newPassword = binding.edNewPassword.text.toString()

            if (oldPassword == newPassword) {
                Toast.makeText(requireActivity(), getString(R.string.text_change_password_same), Toast.LENGTH_SHORT).show()
            } else if (newPassword.length < 8) {
                binding.edNewPassword.error = getString(R.string.password_6)
            } else {
                clickBottom = true
                viewModel.putChangePassword(token, id, oldPassword, newPassword)
            }
        }
    }

    companion object {
        const val TAG = "ChangePasswordFragment"
    }
}