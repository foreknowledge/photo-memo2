package com.foreknowledge.photomemo2.viewmodel

import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.model.repository.MemoRepository
import com.foreknowledge.photomemo2.util.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Yeji on 22,April,2020.
 */
class MainViewModel : ViewModel() {
    private val repository = MemoRepository
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _memoList = MutableLiveData<MutableList<Memo>>()
    val memoList: LiveData<MutableList<Memo>> = _memoList

    fun initMemoList() = coroutineScope.launch {
        _memoList.postValue(repository.getAllMemos().toMutableList())
    }

    fun deleteUnusedImageFiles(context: Context) = coroutineScope.launch {
        val usedPhotoPaths = memoList.value?.flatMap { it.photoPaths.split(",") } ?: emptyList()
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.listFiles()?.forEach { file ->
            if (file.path !in usedPhotoPaths) {
                FileUtil.deleteFile(file.path)
            }
        }
    }
}