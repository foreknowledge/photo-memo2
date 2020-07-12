package com.foreknowledge.photomemo2.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.MAX_IMAGE_COUNT
import com.foreknowledge.photomemo2.MSG_IMAGE_FULL
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.RequestCode.CHOOSE_CAMERA_IMAGE
import com.foreknowledge.photomemo2.adapter.PreviewRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityCreateBinding
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.util.*
import com.foreknowledge.photomemo2.util.importer.CameraImporter
import com.foreknowledge.photomemo2.util.importer.GalleryImporter
import com.foreknowledge.photomemo2.util.importer.UrlImporter
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel

@Suppress("UNUSED_PARAMETER")
class CreateActivity : BaseActivity<ActivityCreateBinding>(R.layout.activity_create) {
	private val memoViewModel by lazy {
		ViewModelProvider(this)[MemoViewModel::class.java]
	}

	private val inputMethodManager by lazy {
		getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	}

	private val previewRecyclerAdapter = PreviewRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val itemTouchHelper = ItemTouchHelper(PreviewItemTouchCallback(previewRecyclerAdapter)).apply {
			attachToRecyclerView(binding.previewRecyclerView)
		}

		binding.run {
			lifecycleOwner = this@CreateActivity
			viewModel = memoViewModel
			goBefore.setOnClickListener { restoreAndFinish() }
			previewRecyclerView.apply {
				setHasFixedSize(true)
				layoutManager = GridLayoutManager(context, 4)
				adapter = previewRecyclerAdapter.apply {
					setOnItemDragListener { itemTouchHelper.startDrag(it) }
				}
			}
		}

		memoViewModel.getMemo(intent.getLongExtra(EXTRA_MEMO_ID, 0))

		subscribeUI()
	}

	override fun onBackPressed() {
		super.onBackPressed()
		restoreAndFinish()
	}

	private fun restoreAndFinish() {
		previewRecyclerAdapter.restoreImages()
		finish()
	}

	private fun subscribeUI() = with(memoViewModel) {
		val owner = this@CreateActivity
		currentMemo.observe(owner, Observer { memo ->
			binding.item = memo
			replaceItemsWithNullCheck(memo)
		})
		msg.observe(owner, Observer { ToastUtil.showToast(it) })
	}

	private fun replaceItemsWithNullCheck(memo: Memo?) {
		memo?.photoPaths?.let {
			if (it.isNotBlank())
				previewRecyclerAdapter.replaceItems(it.split(","))
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		hideUrlInputBox()

		if (resultCode == Activity.RESULT_OK && requestCode == CHOOSE_CAMERA_IMAGE)
			addCameraImage(CameraImporter.getFilePath())
	}

	private fun hideUrlInputBox() {
		with(binding.urlInputBox.root) {
			if (visibility == View.VISIBLE) visibility = View.GONE
		}
	}

	private fun addCameraImage(filePath: String) {
		if (filePath.isNotBlank()) {
			previewRecyclerAdapter.addPath(BitmapUtil.rotateAndCompressImage(filePath))
			focusToBottom()
		}
	}

	fun saveMemo(view: View) = with(memoViewModel) {
		if (isEmptyMemo())
			ToastUtil.showToast(StringUtil.getString(R.string.msg_vacant_content))
		else {
			addMemo(Memo(
				currentMemo.value?.id ?: 0L,
				binding.editMemoTitle.text().trim(),
				binding.editMemoContent.text(),
				previewRecyclerAdapter.getImages().joinToString(",")
			)) { finish() }
			showLoadingBar()
		}
	}

	private fun isEmptyMemo(): Boolean = with(binding) {
		editMemoTitle.isBlank() && editMemoContent.isBlank() && previewRecyclerAdapter.itemCount == 0
	}

	private fun EditText.isBlank() = this.text.toString().trim().isBlank()

	private fun EditText.text() = text.toString()

	fun showMenu(view: View) {
		hideKeyboard()

		if (previewRecyclerAdapter.isFull()) {
			ToastUtil.showToast(MSG_IMAGE_FULL)
			return
		}

		val options = StringUtil.getStringArray(R.array.option_add_image)

		AlertDialog.Builder(this)
			.setTitle(StringUtil.getString(R.string.text_add_image))
			.setItems(options) { _, index ->
				when (index) {
					GALLERY -> {
						val leftCount = MAX_IMAGE_COUNT - previewRecyclerAdapter.itemCount
						GalleryImporter.startMultiImage(this, leftCount) { addSelectedImage(it) }
					}
					CAMERA -> CameraImporter.switchToCamera(this)
					URL -> UrlImporter.fadeIn(binding.urlInputBox.root)
				}
			}.show()
	}

	private fun addSelectedImage(list: List<Uri>) {
		previewRecyclerAdapter.addPaths(list.map { it.toImagePath() })
		focusToBottom()
	}

	private fun Uri.toImagePath(): String {
		GalleryImporter.getFilePath(this@CreateActivity, this).let {
			if (it.isNotBlank())
				return BitmapUtil.rotateAndCompressImage(it)
		}
		return ""
	}

	fun hideBox(view: View) {
		UrlImporter.fadeOut(binding.urlInputBox.root)
		clearUrlPath()
		hideKeyboard()
	}

	fun getUrlImage(view: View) {
		val url = binding.urlInputBox.etUrl.text()
		when {
			url.isBlank() -> ToastUtil.showToast(StringUtil.getString(R.string.msg_vacant_url))
			!NetworkUtil.isConnected(this) -> ToastUtil.showToast(StringUtil.getString(R.string.err_network_disconnect))
			else -> addImage(url)
		}

		hideKeyboard()
	}

	private fun hideKeyboard() {
		inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
	}

	private fun addImage(url: String) = with(memoViewModel) {
		UrlImporter.convertBitmap(
			this@CreateActivity, url,
			success = { bitmap ->
				addBitmapAtRecyclerView(bitmap!!)
				memoViewModel.hideLoadingBar()
				clearUrlPath()
				focusToBottom()
			},
			failed = {
				ToastUtil.showToast(StringUtil.getString(R.string.err_url_import))
				memoViewModel.hideLoadingBar()
			}
		)
		showLoadingBar()
	}

	private fun addBitmapAtRecyclerView(bitmap: Bitmap) {
		previewRecyclerAdapter.addPath(
			BitmapUtil.bitmapToImageFile(this@CreateActivity, bitmap)
		)
	}

	private fun clearUrlPath() {
		binding.urlInputBox.etUrl.setText("")
	}

	private fun focusToBottom() = with(binding) {
		scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
	}

	companion object {
		private const val GALLERY = 0
		private const val CAMERA = 1
		private const val URL = 2
	}
}
