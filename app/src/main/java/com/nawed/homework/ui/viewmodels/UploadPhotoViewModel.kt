package com.nawed.homework.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nawed.homework.model.UploadImageResponse
import com.nawed.homework.repository.Repository
import com.nawed.homework.utils.UploadRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val uploadImageResponseLiveData: LiveData<UploadImageResponse>
    get() = repository.uploadImageResponseLiveData

    fun uploadPhoto(file: File, body: UploadRequestBody){
        viewModelScope.launch {
            repository.uploadImageFile(file,body)
        }
    }
}