package com.nawed.homework.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nawed.homework.R
import com.nawed.homework.databinding.FragmentUploadPhotoBinding
import com.nawed.homework.ui.viewmodels.UploadPhotoViewModel
import com.nawed.homework.utils.UploadRequestBody
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UploadPhotoFragment : Fragment(), UploadRequestBody.UploadCallback {
    private var _binding: FragmentUploadPhotoBinding? = null
    private val binding get() = _binding!!
    private var photoBundle: Bundle? = null
    private lateinit var uploadPhotoViewModel: UploadPhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadPhotoViewModel = ViewModelProvider(this)[UploadPhotoViewModel::class.java]
        if (arguments != null){
            photoBundle = requireArguments().getBundle("photoBundle")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _binding = FragmentUploadPhotoBinding.inflate(inflater,container,false)
       // set toolbar
        binding.toolbarLayout.tvToolbar.text = getString(R.string.upload_photo_screen)
        binding.toolbarLayout.btnToolbar.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_arrow_back))
      // get the data
        if (photoBundle != null) {
            val tmpImg = photoBundle!!["data"] as Bitmap
            binding.ivSelectedPhoto.setImageBitmap(tmpImg)
            binding.btnUploadAgain.setOnClickListener {
                // disable button
                binding.btnUploadAgain.isEnabled = false
                // upload to server
                uploadPhotoToServer(tmpImg)
            }
        }
        return binding.root
    }

    private fun uploadPhotoToServer(tmpImg: Bitmap) {
        // create a temp file in cache dir
        val file = File(context?.cacheDir,"image.jpg")
        file.createNewFile()
        file.outputStream().use {
            tmpImg.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context?.applicationContext, "Saved", Toast.LENGTH_LONG).show()
        }
        // send file to view model
        binding.tvProgress.text = "0%"
        binding.progressBar.progress = 0

        val body = UploadRequestBody(file,"image",this)
        uploadPhotoViewModel.uploadPhoto(file,body)

        uploadPhotoViewModel.uploadImageResponseLiveData.observe(viewLifecycleOwner){
            if (it.success == true){
                // enable button
                binding.btnUploadAgain.isEnabled = true
                binding.tvProgress.text = getString(R.string.percent_100)
                binding.btnUploadAgain.text = getString(R.string.upload_again)
                binding.progressBar.progress = 100
                Toast.makeText(context?.applicationContext, "Uploaded Successfully", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context?.applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onProgressUpdate(percentage: Int) {
        // update the progress
        "${percentage}%".also { binding.tvProgress.text = it }
       binding.progressBar.progress = percentage
    }
}