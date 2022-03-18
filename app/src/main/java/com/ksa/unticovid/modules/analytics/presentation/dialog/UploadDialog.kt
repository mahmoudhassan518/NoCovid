package com.ksa.unticovid.modules.analytics.presentation.dialog

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.Gravity.BOTTOM
import android.view.Gravity.CENTER
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.BuildConfig
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseDialogFragment
import com.ksa.unticovid.core.extentions.getScreenHeight
import com.ksa.unticovid.core.extentions.getScreenWidth
import com.ksa.unticovid.core.extentions.isTablet
import com.ksa.unticovid.core.utils.Action
import com.ksa.unticovid.databinding.DialogUploadBinding
import com.ksa.unticovid.modules.analytics.domain.entity.UploadImageType
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsUIModel
import com.ksa.unticovid.modules.analytics.presentation.viewmodel.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class UploadDialog :
    BaseDialogFragment<DialogUploadBinding, UploadViewModel>(R.layout.dialog_upload) {

    override val viewModel: UploadViewModel by viewModels()

    private var type: UploadImageType = UploadImageType.CAMERA

    private val screenHeight by lazy {
        requireActivity().getScreenHeight() - requireContext().resources.getDimensionPixelSize(R.dimen.dimen_result_dialog_margin_top)
    }
    private val screenWidth by lazy {
        if (requireActivity().isTablet())
            requireContext().resources.getDimensionPixelSize(R.dimen.dimen_result_dialog_width)
        else requireActivity().getScreenWidth()
    }

    var onDismissListener: Action? = null

    private var latestTmpUri: Uri? = null

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                changeUploadButtonVisibility(latestTmpUri)
                latestTmpUri?.let { uri ->
                    setTempImageUri(uri)
                }
            }
        }

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            changeUploadButtonVisibility(latestTmpUri)
            uri?.let { setTempImageUri(uri) }
        }

    private val requestSinglePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) checkPermissionGrantedAction(type)
            else viewModel.showError(R.string.accessStorageAndCameraErrorMessage)
        }

    private fun checkPermissionGrantedAction(type: UploadImageType) {
        viewModel.showError(null)
        when (type) {
            UploadImageType.GALLERY -> selectImageFromGallery()
            UploadImageType.CAMERA -> takeImage()
        }
    }

    override fun setup() {
        initActions()
        initViews()
        initObservations()
    }

    private fun initViews() {

    }

    private fun initActions() {
        binder.btnCamera.setOnClickListener {
            type = UploadImageType.CAMERA
            requestSinglePermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        binder.btnGallery.setOnClickListener {
            type = UploadImageType.GALLERY
            requestSinglePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        binder.btnUploadImage.setOnClickListener {

        }
    }

    private fun initObservations() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUIModel(it)
            }
        }

    }

    private fun renderUIModel(it: AnalyticsUIModel) {
        binder.tvError.isVisible = it.errorMessage != null
        binder.layoutLoading.isVisible = it.isLoading
        it.errorMessage?.let {
            binder.tvError.text = getString(it)
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    fun hasExternalStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED


    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener?.invoke()
        super.onDismiss(dialog)
    }


    private fun changeUploadButtonVisibility(imgUri: Uri?) {
        binder.btnUploadImage.isVisible = imgUri != null
    }

    private fun setTempImageUri(imgUri: Uri) =
        binder.ivPreview.setImageURI(imgUri)

    companion object {
        fun newInstance(): UploadDialog = UploadDialog()
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