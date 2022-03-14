package com.ksa.unticovid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel>(private val layoutId: Int) :
    Fragment() {

    protected abstract val viewModel: VM

    // in case we needed access to the views
    lateinit var binder: T

    /*abstract fun getLayoutId(): Int*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: T = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
        binder = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun setup()
}
