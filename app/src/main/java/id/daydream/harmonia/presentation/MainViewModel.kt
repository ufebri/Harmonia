package id.daydream.harmonia.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var currentImageUri: Uri? = null
    var resultLabel: String? = null
}