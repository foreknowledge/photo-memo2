package com.foreknowledge.photomemo2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.photomemo2.model.repository.MemoRepository

/**
 * Create by Yeji on 22,April,2020.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
		private val memoRepository: MemoRepository
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(memoRepository) as T
}