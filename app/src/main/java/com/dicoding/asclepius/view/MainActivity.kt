package com.dicoding.asclepius.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var currentImageUri: Uri? = null

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        imageClassifierHelper = ImageClassifierHelper(this)

        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    currentImageUri = result.data?.data
                    showImage()
                } else {
                    showToast("Gambar tidak ditemukan. Silakan coba lagi.")
                }
            }

        binding.apply {
            galleryButton.setOnClickListener { startGallery() }
            analyzeButton.setOnClickListener { analyzeImage() }
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        pickImageLauncher.launch(intent)
    }

    private fun showImage() {
        currentImageUri?.let {
            // Menampilkan gambar di UI (misalnya ImageView)
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        currentImageUri?.let {
            val (resultLabel, imageUriString) = imageClassifierHelper.classifyStaticImage(it)
                ?: return showToast("Klasifikasi gagal. Silakan coba lagi.")
            moveToResult(resultLabel, imageUriString)
        }
    }

    private fun moveToResult(resultLabel: String, imageUriString: String) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("RESULT_LABEL", resultLabel)
            putExtra("IMAGE_URI", imageUriString)
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}