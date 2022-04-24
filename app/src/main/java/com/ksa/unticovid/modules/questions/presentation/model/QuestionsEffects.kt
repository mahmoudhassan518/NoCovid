package com.ksa.unticovid.modules.questions.presentation.model

import androidx.annotation.StringRes

sealed class QuestionsEffects {
    data class ShowQuestionsError(@StringRes val message: Int) : QuestionsEffects()
}
