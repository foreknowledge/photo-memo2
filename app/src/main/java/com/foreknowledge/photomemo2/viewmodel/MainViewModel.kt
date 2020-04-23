package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.model.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create by Yeji on 22,April,2020.
 */
class MainViewModel(
    private val repository: MemoRepository
): ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _memoList = MutableLiveData(mutableListOf<Memo>())
    val memoList: LiveData<MutableList<Memo>> = _memoList

    fun initMemoList() = coroutineScope.launch {
        _memoList.postValue(repository.getAllMemos().toMutableList())
    }
}