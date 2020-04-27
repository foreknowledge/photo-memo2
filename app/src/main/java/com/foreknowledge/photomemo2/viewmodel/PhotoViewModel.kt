package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.photomemo2.MAX_IMAGE_COUNT

/**
 * Create by Yeji on 27,April,2020.
 */
class PhotoViewModel : ViewModel() {
    private val photos = mutableListOf<String>()
    var isFull = photos.size >= MAX_IMAGE_COUNT

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _urlPath = MutableLiveData<String>()
    val urlPath: LiveData<String> = _urlPath

    private val _photoPaths = MutableLiveData<List<String>>()
    val photoPaths: LiveData<List<String>> = _photoPaths

    fun setPhotoPaths(paths: String?) {
        if (paths != null) {
            photos.addAll(paths.split(","))
            _photoPaths.value = photos
        }
    }

    fun showLoadingBar() { _isLoading.value = true }
    fun hideLoadingBar() { _isLoading.value = false }

    fun clearPath() { _urlPath.value = "" }
}