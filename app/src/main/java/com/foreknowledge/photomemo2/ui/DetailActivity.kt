package com.foreknowledge.photomemo2.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityDetailBinding
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MemoViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this
		binding.goBefore.setOnClickListener { finish() }

		viewModel.getMemo(intent.getLongExtra(EXTRA_MEMO_ID, 0))

		subscribeUI()
	}

	private fun subscribeUI() = with(viewModel) {
		currentMemo.observe(this@DetailActivity, Observer { binding.item = it })
	}
}
