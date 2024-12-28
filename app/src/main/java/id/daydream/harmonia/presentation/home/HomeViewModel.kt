package id.daydream.harmonia.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: AppUseCase) : ViewModel() {

    val allNews = useCase.getAllNews("cancer").asLiveData()
}