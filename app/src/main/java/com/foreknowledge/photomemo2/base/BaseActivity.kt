package com.foreknowledge.photomemo2.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by Yeji on 22,April,2020.
 */
abstract class BaseActivity<B: ViewDataBinding>(@LayoutRes val layoutId: Int) : AppCompatActivity() {
	protected lateinit var binding: B
		private set

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = DataBindingUtil.setContentView(this, layoutId)
	}
}