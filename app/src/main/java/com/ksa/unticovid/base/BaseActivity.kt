package com.ksa.unticovid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : AppCompatActivity() {

    protected abstract val viewModel: VM

    // in case we needed to access the views
    lateinit var binder: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, layoutId)

        setup()
    }

    abstract fun setup()
}
