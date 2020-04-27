package com.foreknowledge.photomemo2.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.PreviewRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityCreateBinding
import com.foreknowledge.photomemo2.listener.OnItemSingleClickListener
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.util.ToastUtil
import com.foreknowledge.photomemo2.viewmodel.MemoViewModel

@Suppress("UNUSED_PARAMETER")
class CreateActivity : BaseActivity<ActivityCreateBinding>(R.layout.activity_create) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MemoViewModel::class.java)
	}

	private val inputMethodManager by lazy {
		getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	}

	private val previewRecyclerAdapter = PreviewRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this@CreateActivity
		binding.goBefore.setOnClickListener { finish() }
		viewModel.getMemo(intent.getLongExtra(EXTRA_MEMO_ID, 0))

		setPreviewRecyclerView()
		subscribeUI()
	}

	private fun setPreviewRecyclerView() {
		binding.previewRecyclerView.apply {
			layoutManager = LinearLayoutManager(this@CreateActivity)
			adapter = previewRecyclerAdapter.apply {
				onClickListener = setItemClickListener()
			}
		}
	}

	private fun setItemClickListener() = object: OnItemSingleClickListener() {
		override fun onSingleClick(item: Any) {
			// delete preview
		}
	}

	private fun subscribeUI() = with(viewModel) {
		currentMemo.observe(this@CreateActivity, Observer {
			binding.item = it
			previewRecyclerAdapter.replaceItems(it.photoPaths.split(","))
		})
		msg.observe(this@CreateActivity, Observer { ToastUtil.makeToast(it) })
	}

	private fun EditText.text() = text.toString()

	private fun hideKeyboard(view: View) =
			inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

	fun saveMemo(view: View) = with(viewModel) {
		addMemo(Memo(
				currentMemo.value?.id ?: 0L,
				binding.editMemoTitle.text().trim(),
				binding.editMemoContent.text()
		))
		finish()
	}

	fun showMenu(view: View) {
		hideKeyboard(view)

		val options = resources.getStringArray(R.array.option_add_image)

		AlertDialog.Builder(this)
				.setTitle(resources.getString(R.string.text_add_image))
				.setItems(options){ _, i ->
					when (i) {
						0 -> {} // 갤러리
						1 -> {} // 카메라
						2 -> {} // url
					}
				}.show()
	}
}
