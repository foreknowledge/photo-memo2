package com.foreknowledge.photomemo2.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.foreknowledge.photomemo2.*
import com.foreknowledge.photomemo2.adapter.PreviewRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityCreateBinding
import com.foreknowledge.photomemo2.listener.OnItemSingleClickListener
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.util.importer.CameraImporter
import com.foreknowledge.photomemo2.util.importer.GalleryImporter
import com.foreknowledge.photomemo2.util.importer.UrlImporter
import com.foreknowledge.photomemo2.util.StringUtil
import com.foreknowledge.photomemo2.util.ToastUtil
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel
import com.foreknowledge.photomemo2.viewmodel.PhotoViewModel

@Suppress("UNUSED_PARAMETER")
class CreateActivity : BaseActivity<ActivityCreateBinding>(R.layout.activity_create) {
	private val memoViewModel by lazy {
		ViewModelProvider(this)[MemoViewModel::class.java]
	}

	private val photoViewModel by lazy {
		ViewModelProvider(this)[PhotoViewModel::class.java]
	}

	private val inputMethodManager by lazy {
		getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	}

	private val previewRecyclerAdapter = PreviewRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.run {
			lifecycleOwner = this@CreateActivity
			viewModel = photoViewModel
			goBefore.setOnClickListener { finish() }
		}

		memoViewModel.getMemo(intent.getLongExtra(EXTRA_MEMO_ID, 0))

		initView()
	}

	private fun initView() {
		setPreviewRecyclerView()
		subscribeUI()
	}

	private fun setPreviewRecyclerView() {
		binding.previewRecyclerView.apply {
			setHasFixedSize(true)
			layoutManager = GridLayoutManager(context, 4)
			adapter = previewRecyclerAdapter.apply {
				onClickListener = setItemClickListener()
			}
		}
	}

	private fun setItemClickListener() = object: OnItemSingleClickListener() {
		override fun onSingleClick(item: Any) {
			// TODO: delete preview
		}
	}

	private fun subscribeUI() {
		with(memoViewModel) {
			currentMemo.observe(this@CreateActivity, Observer {
				binding.item = it
				photoViewModel.setPhotoPaths(it?.photoPaths)
			})
			msg.observe(this@CreateActivity, Observer { ToastUtil.makeToast(it) })
		}

		with(photoViewModel) {
			photoPaths.observe(this@CreateActivity, Observer { previewRecyclerAdapter.replaceItems(it) })
		}
	}

	/* ######################### button click listener ######################### */

	private fun EditText.text() = text.toString()
	private fun EditText.isBlank() = this.text.toString().trim().isBlank()
	private fun isEmptyContents(): Boolean = with(binding) {
		editMemoTitle.isBlank() && editMemoContent.isBlank()
	}

	private fun hideKeyboard() =
			inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

	fun saveMemo(view: View) = with(memoViewModel) {
		if (isEmptyContents())
			ToastUtil.makeToast(MSG_VACANT_CONTENT)
		else
			addMemo(Memo(
					currentMemo.value?.id ?: 0L,
					binding.editMemoTitle.text().trim(),
					binding.editMemoContent.text()
			))
		finish()
	}

	fun showMenu(view: View) {
		hideKeyboard()

		if (photoViewModel.isFull) {
			ToastUtil.makeToast(MSG_IMAGE_FULL)
			return
		}

		val options = StringUtil.getStringArray(R.array.option_add_image)

		AlertDialog.Builder(this)
				.setTitle(StringUtil.getString(R.string.text_add_image))
				.setItems(options) { _, index ->
					when (index) {
						0 -> GalleryImporter.switchToAlbum(this) // 갤러리
						1 -> CameraImporter.switchToCamera(this) // 카메라
						2 -> UrlImporter.fadeIn(binding.urlInputBox.root) // url
					}
				}.show()
	}

	fun hideBox(view: View) {
		UrlImporter.fadeOut(binding.urlInputBox.root)
		photoViewModel.clearPath()
		hideKeyboard()
	}

	fun adjustUrl(view: View) = with(photoViewModel) {
		val url = binding.urlInputBox.etUrl.text()
		if (isFull)
			ToastUtil.makeToast(MSG_IMAGE_FULL)
		else if (url.isBlank())
			ToastUtil.makeToast(MSG_VACANT_URL)
		else {
			// TODO: add preview
			showLoadingBar()
			clearPath()
		}

		hideKeyboard()
	}
}
