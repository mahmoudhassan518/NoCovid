package com.ksa.unticovid.base

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment


abstract class BaseDialogFragment<T : ViewDataBinding, VM : BaseViewModel>(private val layoutId: Int) :
    DialogFragment() {

    protected abstract val viewModel: VM

    // in case we needed access to the views
    lateinit var binder: T

    /*abstract fun getLayoutId(): Int*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return initDialog()
    }

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
        if (isFullScreen() && isTablet())
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        setup()
    }

    private fun initDialog(): Dialog {
        val root = RelativeLayout(requireActivity())
        isCancelable = isFragmentCancelable()
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(getDialogBackground())
        val dialogWidth = getDialogWidth()
        val dialogHeight = getDialogHeight()
        dialog.window!!.setLayout(dialogWidth, dialogHeight)
        dialog.window!!.setGravity(getDialogGravity())
        getTransitionsAnimation()?.let {
            dialog.window!!.attributes.windowAnimations = getTransitionsAnimation()!!
        }
        return dialog
    }


    private fun isFullScreen() =
        getDialogHeight() == resources.displayMetrics.heightPixels && getDialogWidth() == resources.displayMetrics.widthPixels


    abstract fun setup()
    protected abstract fun getDialogBackground(): Drawable
    protected abstract fun getDialogWidth(): Int
    protected abstract fun getDialogHeight(): Int
    protected open fun getDialogGravity(): Int = Gravity.BOTTOM
    protected open fun getTransitionsAnimation(): Int? = null
    protected open fun isFragmentCancelable(): Boolean = false
    protected abstract fun isTablet(): Boolean


}
