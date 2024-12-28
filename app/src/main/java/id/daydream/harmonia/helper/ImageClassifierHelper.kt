package id.daydream.harmonia.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media.getBitmap
import id.daydream.harmonia.ml.CancerClassification
import org.tensorflow.lite.support.image.TensorImage


class ImageClassifierHelper(private val context: Context) {

    fun classifyStaticImage(imageUri: Uri): Pair<String, String>? {
        // Mengonversi Uri menjadi Bitmap
        val bitmap = convertUriToBitmap(imageUri)
        val tensorImage = TensorImage.fromBitmap(bitmap)

        // Membuat instance model
        val model = CancerClassification.newInstance(context)

        // Menjalankan inferensi dan mendapatkan hasil
        val outputs = model.process(tensorImage)
        val probability = outputs.probabilityAsCategoryList

        // Menemukan label dengan probabilitas tertinggi
        val maxEntry = probability.maxByOrNull { it.score }
        model.close()

        // Mengembalikan hasil klasifikasi dan URI gambar sebagai Pair dengan string format
        return if (maxEntry != null) {
            val resultText = "${maxEntry.label} ${maxEntry.score.times(100).toInt()}%"
            Pair(resultText, imageUri.toString())
        } else {
            null
        }
    }

    private fun convertUriToBitmap(imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
        } else {
            @Suppress("DEPRECATION")
            getBitmap(context.contentResolver, imageUri).copy(Bitmap.Config.ARGB_8888, true)
        }
    }
}