package com.foreknowledge.photomemo2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.RequestCode.PERMISSION_REQUEST_CODE
import com.foreknowledge.photomemo2.adapter.MemoRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityMainBinding
import com.foreknowledge.photomemo2.listener.OnItemSingleClickListener
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.util.StringUtil
import com.foreknowledge.photomemo2.viewmodel.MainViewModel
import com.pedro.library.AutoPermissions
import com.pedro.library.AutoPermissionsListener

@Suppress("UNUSED_PARAMETER")
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
	AutoPermissionsListener {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MainViewModel::class.java)
	}

	private val memoRecyclerAdapter = MemoRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this@MainActivity
		binding.memoRecyclerView.apply {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = memoRecyclerAdapter.apply {
				onClickListener = setItemClickListener()
			}
		}

		subscribeUI()

		AutoPermissions.Companion.loadAllPermissions(this, PERMISSION_REQUEST_CODE)
	}

	override fun onResume() {
		super.onResume()

		viewModel.initMemoList()
	}

	private fun setItemClickListener() = object: OnItemSingleClickListener<Memo>() {
		override fun onSingleClick(item: Memo) {
			startActivity(
					Intent(this@MainActivity, DetailActivity::class.java)
							.apply { putExtra(EXTRA_MEMO_ID, item.id) }
			)
		}
	}

	private fun subscribeUI() {
		with (viewModel) {
			memoList.observe(this@MainActivity, Observer {
				memoRecyclerAdapter.replaceItems(it)
				binding.isEmptyList = it.size == 0
			})
		}
	}

	fun createMemo(view: View) =
		startActivity(Intent(this, CreateActivity::class.java))

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this)
	}

	private fun notifyPermission(msg: String, permissions: Array<String>) {
		if (permissions.isNotEmpty())
			Log.d(javaClass.simpleName,"$msg : ${permissions.size}")
	}

	override fun onDenied(requestCode: Int, permissions: Array<String>) =
		notifyPermission(StringUtil.getString(R.string.msg_permission_denied), permissions)

	override fun onGranted(requestCode: Int, permissions: Array<String>) =
		notifyPermission(StringUtil.getString(R.string.msg_permission_granted), permissions)

}
