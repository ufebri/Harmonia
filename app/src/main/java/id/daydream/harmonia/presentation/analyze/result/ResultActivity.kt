package id.daydream.harmonia.presentation.analyze.result

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.daydream.harmonia.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Menerima data dari Intent
        val resultLabel = intent.getStringExtra("RESULT_LABEL")
        val imageUriString = intent.getStringExtra("IMAGE_URI")

        // Menampilkan data pada UI
        binding.resultText.text = resultLabel
        imageUriString?.let {
            val imageUri = Uri.parse(it)
            binding.resultImage.setImageURI(imageUri)
        }
    }


}