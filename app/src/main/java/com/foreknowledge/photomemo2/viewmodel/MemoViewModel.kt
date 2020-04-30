package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.model.repository.MemoRepository
import com.foreknowledge.photomemo2.util.FileUtil
import com.foreknowledge.photomemo2.util.StringUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create by Yeji on 23,April,2020.
 */
class MemoViewModel : ViewModel() {
	private val repository = MemoRepository
	private val coroutineScope = CoroutineScope(Dispatchers.IO)

	private val _msg = MutableLiveData<String>()
	val msg: LiveData<String> = _msg

	private val _currentMemo = MutableLiveData<Memo>()
	val currentMemo: LiveData<Memo> = _currentMemo

	private val _isLoading = MutableLiveData(false)
	val isLoading: LiveData<Boolean> = _isLoading

	private val _urlPath = MutableLiveData<String>()
	val urlPath: LiveData<String> = _urlPath

	fun showLoadingBar() { _isLoading.value = true }
	fun hideLoadingBar() { _isLoading.value = false }

	fun clearPath() { _urlPath.value = "" }

	fun getMemo(id: Long) = coroutineScope.launch {
		_currentMemo.postValue(repository.getMemo(id))
	}

	fun addMemo(memo: Memo, success:() -> Unit) = coroutineScope.launch {
		repository.addMemo(memo)
		_msg.postValue(StringUtil.getString(R.string.msg_save))
		success()
	}

	fun deleteMemo(memo: Memo, success:() -> Unit) = coroutineScope.launch {
		repository.deleteMemo(memo.id)
		if (memo.photoPaths.isNotBlank())
			FileUtil.deleteFiles(memo.photoPaths.split(","))
		_msg.postValue(StringUtil.getString(R.string.msg_delete))
		success()
	}

}