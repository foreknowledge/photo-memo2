package com.foreknowledge.photomemo2.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.EXTRA_PHOTOS
import com.foreknowledge.photomemo2.EXTRA_PHOTO_POSITION
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.PhotoRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityDetailBinding
import com.foreknowledge.photomemo2.util.ToastUtil
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel

@Suppress("UNUSED_PARAMETER")
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MemoViewModel::class.java)
	}

	private val photoRecyclerAdapter = PhotoRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this
		binding.goBefore.setOnClickListener { finish() }
		binding.photoRecyclerView.apply {
			layoutManager = GridLayoutManager(context, 2)
			adapter = photoRecyclerAdapter.apply {
				setOnItemClickListener { startPhotoActivity(it) }
			}
		}

		subscribeUI()
	}

	override fun onResume() {
		super.onResume()
		viewModel.getMemo(intent.getLongExtra(EXTRA_MEMO_ID, 0))
	}

	private fun startPhotoActivity(photoPath: String) {
		Intent(this@DetailActivity, PhotoActivity::class.java)
			.apply {
				putExtra(EXTRA_PHOTOS, viewModel.currentMemo.value?.photoPaths)
				putExtra(EXTRA_PHOTO_POSITION, photoRecyclerAdapter.getItemPosition(photoPath))
			}
			.also { startActivity(it) }
	}

	private fun subscribeUI() = with(viewModel) {
		val owner = this@DetailActivity
		currentMemo.observe(owner, Observer { memo ->
			binding.item = memo
			memo?.photoPaths?.let {
				if (it.isNotBlank())
					photoRecyclerAdapter.replaceItems(it.split(","))
			}
		})
		msg.observe(owner, Observer { ToastUtil.showToast(it) })
	}

	fun startCreateActivity(view: View) {
		Intent(this, CreateActivity::class.java)
			.apply {
				putExtra(EXTRA_MEMO_ID, viewModel.currentMemo.value!!.id)
			}.also { startActivity(it) }
	}

	fun showAlertDialog(view: View) {
		AlertDialog.Builder(this)
			.setMessage(getString(R.string.delete_message))
			.setPositiveButton( getString(R.string.btn_ok_text) ) { _, _ -> deleteMemo() }
			.setNegativeButton( getString(R.string.btn_cancel_text) ) { dialog, _ -> dialog.dismiss() }.show()
	}

	private fun deleteMemo() = viewModel.currentMemo.value?.let { memo ->
		viewModel.deleteMemo(memo) { finish() }
	}
}
