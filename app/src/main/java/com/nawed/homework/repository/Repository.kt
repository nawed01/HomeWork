package com.nawed.homework.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nawed.homework.api.ApiService
import com.nawed.homework.model.UploadImageResponse
import com.nawed.homework.utils.UploadRequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    private var _uploadImageResponseLiveData = MutableLiveData<UploadImageResponse>()
    val uploadImageResponseLiveData: LiveData<UploadImageResponse>
    get() = _uploadImageResponseLiveData

    suspend fun uploadImageFile(file: File, body: UploadRequestBody){
       try {
           val image = MultipartBody.Part.createFormData("image",file.name,body)
           val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),"media")
           val response = apiService.uploadImage(image,requestBody)
           if (response.isSuccessful && response.body() != null){
               println("RESPONSEE: ${response.body()}")
               _uploadImageResponseLiveData.postValue(response.body())
           }
       } catch (e: IOException){
           e.printStackTrace()
       } catch (e: HttpException){
           e.printStackTrace()
       }
    }
}