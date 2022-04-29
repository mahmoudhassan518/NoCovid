package com.ksa.unticovid.modules.questions.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.isNull
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.databinding.ActivityQuestionsBinding
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsDataUIModel
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsEffects
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsResultUIModel
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsUIModel
import com.ksa.unticovid.modules.questions.presentation.viewmodel.QuestionsViewModel
import com.ksa.unticovid.modules.result.domain.entity.param.ResultParams
import com.ksa.unticovid.modules.result.presentation.view.ResultDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class QuestionsActivity :
    BaseActivity<ActivityQuestionsBinding, QuestionsViewModel>(R.layout.activity_questions) {
    override val viewModel: QuestionsViewModel by viewModels()

    private var resultDialog: ResultDialog? = null

    override fun setup() {
        initViews()
        initActions()
        renderObservations()
    }

    private fun initViews() {
    }

    private fun initActions() {
        binder.actionBar.backImageButton.setOnClickListener { finish() }
        binder.btnSubmit.setOnClickListener {
            viewModel.submitQuestions()
        }
    }

    private fun renderObservations() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUIModel(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }
    }

    private fun renderUIModel(uiModel: QuestionsUIModel) = with(uiModel) {
        binder.layoutStateView.root.isVisible = isLoading
        binder.layoutStateView.cvLoading.isVisible = isLoading

        with(question) {
            renderQuestionsData()
        }

        binder.tvAge.text = age
        binder.tvGender.text = gender?.let { getString(it) }
        questionsResult?.showResultDialog()
    }

    private fun QuestionsResultUIModel.showResultDialog() {
        resultDialog.isNull {
            resultDialog = ResultDialog.newInstance(
                ResultParams(
                    resultText = message,
                    isInfected = hasCovid,
                    doctorName = doctorName,
                    doctorNumber = doctorMobile,
                    doctorAddress = doctorAddress
                )
            )
            resultDialog?.show(
                supportFragmentManager,
                ResultDialog::class.java.name
            )
            resultDialog?.onDismissListener = {
                resultDialog = null
                finish()
            }
        }
    }

    private fun QuestionsDataUIModel.renderQuestionsData() {
        binder.viewCough.init(getString(R.string.coughQuestion), hasCough, onItemSelect = {
            viewModel.updateCoughState(it)
        })
        binder.viewDiarrhea.init(getString(R.string.diarrheaQuestion), hasDiarrhea, onItemSelect = {
            viewModel.updateDiarrheaState(it)
        })
        binder.viewDyspnea.init(getString(R.string.dyspneaQuestion), hasDyspnea, onItemSelect = {
            viewModel.updateDyspneaState(it)
        })
        binder.viewFever.init(getString(R.string.feverQuestion), hasFever, onItemSelect = {
            viewModel.updateFeverState(it)
        })
    }

    private fun renderEffects(questionsEffects: QuestionsEffects) =
        when (questionsEffects) {
            is QuestionsEffects.ShowQuestionsError -> showAlerterError(getString(questionsEffects.message))
        }

    companion object {

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, QuestionsActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
