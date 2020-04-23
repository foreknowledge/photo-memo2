package com.foreknowledge.photomemo2.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.MemoRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityMainBinding
import com.foreknowledge.photomemo2.model.repository.MemoRepository
import com.foreknowledge.photomemo2.viewmodel.MainViewModel
import com.foreknowledge.photomemo2.viewmodel.MainViewModelFactory

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
	private val viewModel by lazy {
		ViewModelProvider(this, MainViewModelFactory(MemoRepository))[MainViewModel::class.java]
	}

	private val memoRecyclerAdapter = MemoRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		with (viewModel) {
			binding.isEmptyList = true
			binding.lifecycleOwner = this@MainActivity
			initMemoList()
		}

		subscribeUI()
	}

	private fun subscribeUI() {
		with (viewModel) {
			memoList.observe(this@MainActivity, Observer {
				memoRecyclerAdapter.replaceItems(it)
				binding.isEmptyList = memoList.value?.size == 0
			})
		}
	}
}
