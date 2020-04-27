package com.foreknowledge.photomemo2.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityDetailBinding
import com.foreknowledge.photomemo2.util.ToastUtil
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel

@Suppress("UNUSED_PARAMETER")
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MemoViewModel::class.java)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this
		binding.goBefore.setOnClickListener { finish() }

		subscribeUI()
	}

	override fun onResume() {
		super.onResume()
		viewModel.getMemo(intent.getLongExtra(EXTRA_MEMO_ID, 0))
	}

	private fun subscribeUI() = with(viewModel) {
		currentMemo.observe(this@DetailActivity, Observer { binding.item = it })
		msg.observe(this@DetailActivity, Observer { ToastUtil.showToast(it) })
	}

	private fun deleteMemo() = with (viewModel) {
		deleteMemo(currentMemo.value!!.id) { finish() }
	}

	fun showAlertDialog(view: View) {
		AlertDialog.Builder(this)
				.setMessage(getString(R.string.delete_message))
				.setPositiveButton( getString(R.string.btn_ok_text) ) { _, _ -> deleteMemo() }
				.setNegativeButton( getString(R.string.btn_cancel_text) ) { dialog, _ -> dialog.dismiss() }.show()
	}

	fun editMemo(view: View) =
			startActivity(Intent(this, CreateActivity::class.java).apply {
				putExtra(EXTRA_MEMO_ID, viewModel.currentMemo.value!!.id)
			})
}
