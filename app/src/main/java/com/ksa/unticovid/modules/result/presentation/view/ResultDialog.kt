package com.ksa.unticovid.modules.result.presentation.view

import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity.BOTTOM
import android.view.Gravity.CENTER
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseDialogFragment
import com.ksa.unticovid.core.extentions.callPhoneNumber
import com.ksa.unticovid.core.extentions.getScreenHeight
import com.ksa.unticovid.core.extentions.getScreenWidth
import com.ksa.unticovid.core.extentions.isTablet
import com.ksa.unticovid.core.utils.Action
import com.ksa.unticovid.databinding.DialogResultBinding
import com.ksa.unticovid.modules.result.domain.entity.param.ResultParams
import com.ksa.unticovid.modules.result.presentation.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultDialog :
    BaseDialogFragment<DialogResultBinding, ResultViewModel>(R.layout.dialog_result) {

    private val screenHeight by lazy {
        requireActivity().getScreenHeight() - requireContext().resources.getDimensionPixelSize(R.dimen.dimen_result_dialog_margin_top)
    }
    private val screenWidth by lazy {
        if (requireActivity().isTablet())
            requireContext().resources.getDimensionPixelSize(R.dimen.dimen_result_dialog_width)
        else requireActivity().getScreenWidth()
    }

    private val params by lazy {
        arguments?.getParcelable<ResultParams>(RESULT_PARAM)
    }

    var onDismissListener: Action? = null

    override val viewModel: ResultViewModel by viewModels()

    override fun setup() {
        initActions()
        initViews()
    }

    private fun initViews() {

        binder.lyDoctor.isVisible = params!!.isInfected
        binder.tvAddress.text = params!!.doctorAddress
        binder.tvMobile.text = params!!.doctorNumber
        binder.tvName.text = params!!.doctorName

        binder.btnStroke.isVisible = params!!.isInfected
        binder.tvResultDescription.text = params?.resultText
        binder.btnSolid.text =
            if (params!!.isInfected) getString(R.string.callDoctorLabel) else getString(R.string.done)
        binder.tvResultTitle.text =
            if (params!!.isInfected) getString(R.string.result_title_positive) else getString(R.string.result_title_negative)
        binder.ivResult.setImageDrawable(
            if (params!!.isInfected) ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_sad,
                null
            ) else ResourcesCompat.getDrawable(resources, R.drawable.ic_smile, null)
        )
    }

    private fun initActions() {
        binder.btnStroke.setOnClickListener { dismiss() }
        binder.btnSolid.setOnClickListener { if (params!!.isInfected) callEmergency() else dismiss() }
    }

    private fun callEmergency() =
        params?.doctorNumber?.let { requireActivity().callPhoneNumber(it) }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener?.invoke()
        super.onDismiss(dialog)
    }


    companion object {
        private const val RESULT_PARAM: String = "RESULT_PARAM"
        fun newInstance(param: ResultParams): ResultDialog {
            val dialog = ResultDialog()
            val args = Bundle()
            args.putParcelable(RESULT_PARAM, param)
            dialog.arguments = args
            return dialog
        }
    }

    override fun isFragmentCancelable(): Boolean = true

    override fun getDialogBackground(): Drawable =
        AppCompatResources.getDrawable(requireContext(), R.drawable.bg_white_corners_5_dp)!!

    override fun getDialogWidth(): Int = screenWidth

    override fun getDialogHeight(): Int = screenHeight

    override fun isTablet(): Boolean = true

    override fun getDialogGravity(): Int =
        if (requireActivity().isTablet())
            CENTER
        else
            BOTTOM
}