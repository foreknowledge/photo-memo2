package com.foreknowledge.photomemo2.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityCreateBinding
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel

class CreateActivity : BaseActivity<ActivityCreateBinding>(R.layout.activity_create) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MemoViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this@CreateActivity
		binding.goBefore.setOnClickListener { finish() }

		viewModel.getMemo(intent.getLongExtra("memo_id", 0))

		subscribeUI()
	}

	private fun subscribeUI() = with(viewModel) {
		currentMemo.observe(this@CreateActivity, Observer { binding.item = it })
	}

	fun saveMemo(view: View) {
		with(binding) {
			val memo = Memo(editMemoTitle.text.toString(), editMemoContent.text.toString())
			viewModel.addMemo(memo)
		}

		finish()
	}
}
