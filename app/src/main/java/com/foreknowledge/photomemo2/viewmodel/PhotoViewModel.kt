package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Create by Yeji on 27,April,2020.
 */
class PhotoViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _urlPath = MutableLiveData<String>()
    val urlPath: LiveData<String> = _urlPath

    var isFull = false
//    val cameraImporter = CameraImporter()
//    val galleryImporter = GalleryImporter()
//    val urlImporter = UrlImporter()

    fun clearPath() { _urlPath.value = "" }
}