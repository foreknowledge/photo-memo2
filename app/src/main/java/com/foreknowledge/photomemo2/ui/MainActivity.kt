package com.foreknowledge.photomemo2.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.MemoRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityMainBinding
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.viewmodel.MainViewModel

@Suppress("UNUSED_PARAMETER")
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MainViewModel::class.java)
	}

	private val memoRecyclerAdapter = MemoRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this@MainActivity
		binding.memoRecyclerView.apply {
			layoutManager = LinearLayoutManager(this@MainActivity).apply {
				reverseLayout = true
				stackFromEnd = true
			}
			adapter = memoRecyclerAdapter.apply {
				setOnItemClickListener { startDetailActivity(it) }
			}
		}

		subscribeUI()
	}

	private fun startDetailActivity(memo: Memo) {
		Intent(this@MainActivity, DetailActivity::class.java)
			.apply { putExtra(EXTRA_MEMO_ID, memo.id) }
			.also { startActivity(it) }
	}

	private fun subscribeUI() {
		with (viewModel) {
			memoList.observe(this@MainActivity, Observer {
				memoRecyclerAdapter.replaceItems(it)
				binding.isEmptyList = it.size == 0
			})
		}
	}

	override fun onResume() {
		super.onResume()

		viewModel.initMemoList()
	}

	fun createMemo(view: View) {
		Intent(this, CreateActivity::class.java)
			.also { startActivity(it) }
	}

	override fun onDestroy() {
		// 안 쓰는 이미지 파일 제거
		viewModel.deleteUnusedImageFiles(this)

		super.onDestroy()
	}
}
