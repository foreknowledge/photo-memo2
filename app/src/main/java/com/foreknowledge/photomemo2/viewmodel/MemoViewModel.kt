package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.model.repository.MemoRepository
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

	fun getMemo(id: Long) = coroutineScope.launch {
		_currentMemo.postValue(repository.getMemo(id))
	}

	fun addMemo(memo: Memo) = coroutineScope.launch {
		repository.addMemo(memo)
		_msg.postValue(StringUtil.getString(R.string.msg_save))
	}

	fun deleteMemo(id: Long) = coroutineScope.launch {
		repository.deleteMemo(id)
		_msg.postValue(StringUtil.getString(R.string.msg_delete))
	}

}