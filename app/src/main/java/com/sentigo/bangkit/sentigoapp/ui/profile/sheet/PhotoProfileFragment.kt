package com.sentigo.bangkit.sentigoapp.ui.profile.sheet

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sentigo.bangkit.sentigoapp.databinding.FragmentPhotoProfileBinding
import com.sentigo.bangkit.sentigoapp.di.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PhotoProfileFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentPhotoProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val viewModel: ProfilePhotoViewModel by viewModels { factory }

    private var token: String = ""
    private var isClick = false
    private var isUploade = false
    private lateinit var finalFile: File
    private lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        viewModel.updatePhotoResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is Result.Loading -> {
                        if (isUploade) binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        if (response.data.message == "Photo is updated" && isUploade) {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), response.data.message, Toast.LENGTH_SHORT).show()
                            dismiss()
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), response.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.getUserPref.observe(viewLifecycleOwner) {
            token = it.token
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnGalery.setOnClickListener {
            startGallery()
        }

        binding.btnUpdatePhoto.setOnClickListener {
            uploadImage(token)
            setButton(isClick)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            currentPhotoPath = myFile.path

            val photo = rotatePhoto(myFile)
            finalFile = convertBitmapFile(photo, myFile)
            Log.d("isGaleryOpen", "Final File : $finalFile")
            setButton(isClick)
            binding.imgProfile.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun rotatePhoto(myFile: File) : Bitmap {
        val exifInterface = ExifInterface(currentPhotoPath)
        val rotate: Int = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

        val photo = BitmapFactory.decodeFile(myFile.path)

        val result = when (rotate) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(photo, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(photo, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(photo, 270f)
            else -> photo
        }
        return result
    }

    private fun uploadImage(token: String) {
        finalFile = reduceFileImage(finalFile)

        val requestImageFile = finalFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            finalFile.name,
            requestImageFile
        )

        Log.d("PhotoProfileFragment", finalFile.name)

        viewModel.updatePhotoProfile(token, imageMultipart)
    }

    private fun setButton(value: Boolean) {
        if (value) {
            binding.btnUpdatePhoto.visibility = View.GONE
            binding.btnGalery.visibility = View.VISIBLE
            isClick = false
        } else {
            binding.btnGalery.visibility = View.GONE
            binding.btnUpdatePhoto.visibility = View.VISIBLE
            isClick = true
            isUploade = true
        }
    }

    companion object {
        const val TAG = "PhotoProfileFragment"
    }
}