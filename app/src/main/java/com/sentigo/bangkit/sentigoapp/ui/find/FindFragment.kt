package com.sentigo.bangkit.sentigoapp.ui.find

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.databinding.FragmentFindBinding

class FindFragment : Fragment() {

    private var _binding : FragmentFindBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding.btnResult.setOnClickListener {
            val desc = binding.edDesc.text.toString()

            if (desc.isEmpty()) binding.textField.error = getString(R.string.text_desc_empty)
            else {
                val intent = Intent(requireContext(), ListFindActivity::class.java)
                intent.putExtra(ListFindActivity.EXTRA_DESC, desc)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.edDesc.setText("")
    }

}