package com.nawed.homework.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nawed.homework.databinding.ActivityMainBinding
import com.nawed.homework.ui.fragments.UploadPhotoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding?  = null
    private val binding get() = _binding!!
    private val MY_CAMERA_PERMISSION_CODE = 100
    private val CAMERA_REQUEST = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            handlePermissionsAndCamera()
        }

    }

    private fun handlePermissionsAndCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // not granted ask for permission
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), MY_CAMERA_PERMISSION_CODE
            )
        } else {
            // granted, start the camera
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                // granted, start the camera
                startCamera()
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // get the captured image
            // pop the backstack
            supportFragmentManager.popBackStack()
            val photoBundle = data?.extras!!
            val args = Bundle()
            args.putBundle("photoBundle",photoBundle)
            openUploadPhotoFragment(args)
        }
    }

    private fun openUploadPhotoFragment(args: Bundle) {
        val uploadPhotoFragment = UploadPhotoFragment()
        uploadPhotoFragment.arguments = args
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.mainFragmentContainer.id, uploadPhotoFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}