package com.foreknowledge.photomemo2.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.foreknowledge.photomemo2.*
import com.foreknowledge.photomemo2.RequestCode.CHOOSE_CAMERA_IMAGE
import com.foreknowledge.photomemo2.RequestCode.CHOOSE_GALLERY_IMAGE
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

	private lateinit var itemTouchHelper: ItemTouchHelper
	private val previewRecyclerAdapter = PreviewRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		itemTouchHelper = ItemTouchHelper(PreviewItemTouchCallback(previewRecyclerAdapter)).apply {
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
		currentMemo.observe(this@CreateActivity, Observer {
			binding.item = it
			it?.photoPaths?.run {
				if (isNotBlank())
					previewRecyclerAdapter.replaceItems(split(","))
			}
		})
		msg.observe(this@CreateActivity, Observer { ToastUtil.showToast(it) })
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		with(binding.urlInputBox.root) {
			if (visibility == View.VISIBLE) visibility = View.GONE
		}

		if (resultCode == Activity.RESULT_OK) {
			val resultPath =
					if (requestCode == CHOOSE_GALLERY_IMAGE && data != null && data.data != null)
						GalleryImporter.getImageFilePath(this, data.data!!)
					else if (requestCode == CHOOSE_CAMERA_IMAGE)
						CameraImporter.getFilePath()
					else null

			resultPath?.let {
				previewRecyclerAdapter.addPath(BitmapUtil.rotateAndCompressImage(it))
			}
		}
	}

	/* ######################### button click listener ######################### */

	private fun EditText.text() = text.toString()
	private fun EditText.isBlank() = this.text.toString().trim().isBlank()
	private fun isEmptyContents(): Boolean = with(binding) {
		editMemoTitle.isBlank() && editMemoContent.isBlank() && previewRecyclerAdapter.itemCount == 0
	}

	private fun hideKeyboard() =
			inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

	fun saveMemo(view: View) = with(memoViewModel) {
		if (isEmptyContents())
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

	fun showMenu(view: View) {
		hideKeyboard()

		if (previewRecyclerAdapter.isFull()) {
			ToastUtil.showToast(MSG_IMAGE_FULL)
			return
		}

		// 권한 체크
		if (PermissionUtil.isPermissionDenied(this)) {
			ToastUtil.showToast(StringUtil.getString(R.string.err_permission_denied))
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
		memoViewModel.clearPath()
		hideKeyboard()
	}

	fun adjustUrl(view: View) {
		val url = binding.urlInputBox.etUrl.text()
		when {
			url.isBlank() ->
				ToastUtil.showToast(StringUtil.getString(R.string.msg_vacant_url))
			!NetworkUtil.isConnected(this) ->
				ToastUtil.showToast(StringUtil.getString(R.string.err_network_disconnect))
			else -> with(memoViewModel) {
				UrlImporter.convertBitmap(
						this@CreateActivity, url,
						success = { bitmap ->
							previewRecyclerAdapter.addPath(BitmapUtil.bitmapToImageFile(this@CreateActivity, bitmap!!))
							hideLoadingBar()
							clearPath()
						},
						failed = {
							ToastUtil.showToast(StringUtil.getString(R.string.err_url_import))
							hideLoadingBar()
						}
				)
				showLoadingBar()
			}
		}

		hideKeyboard()
	}
}
