package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.photomemo2.model.data.Memo

/**
 * Create by Yeji on 22,April,2020.
 */
class MainViewModel: ViewModel() {
    private val _memoList = MutableLiveData(mutableListOf<Memo>())
    val memoList: LiveData<MutableList<Memo>> = _memoList
}