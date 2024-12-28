package id.daydream.harmonia.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.daydream.harmonia.databinding.ActivityMainBinding
import id.daydream.harmonia.helper.ImageClassifierHelper

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private val viewModel: MainViewModel by viewModels()
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        imageClassifierHelper = ImageClassifierHelper(this)

        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.currentImageUri = result.data?.data
                    viewModel.currentImageUri?.let { uri ->
                        showImage(uri)
                    }
                } else {
                    showToast("Gambar tidak ditemukan. Silakan coba lagi.")
                }
            }

        binding.apply {
            galleryButton.setOnClickListener { startGallery() }
            analyzeButton.setOnClickListener { analyzeImage() }
        }

        viewModel.currentImageUri?.let { uri ->
            showImage(uri)
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        pickImageLauncher.launch(intent)
    }

    private fun showImage(uri: Uri?) {
        uri.let {
            // Menampilkan gambar di UI (misalnya ImageView)
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        viewModel.currentImageUri?.let {
            val (resultLabel, imageUriString) = imageClassifierHelper.classifyStaticImage(it)
                ?: return showToast("Klasifikasi gagal. Silakan coba lagi.")

            viewModel.resultLabel = resultLabel

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