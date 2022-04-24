package com.ksa.unticovid.modules.core.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.ksa.unticovid.R
import com.ksa.unticovid.core.utils.Consumer
import com.ksa.unticovid.databinding.ViewQuestionBinding

class QuestionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ViewQuestionBinding? = null

    init {
        binding = ViewQuestionBinding.inflate(LayoutInflater.from(context), null, false)
        addView(binding?.root)
    }


    fun init(
        question: String,
        isSelected: Boolean,
        onItemSelect: Consumer<Boolean>
    ) {
        binding?.tvQuestion?.text = question
        binding?.layoutGenderView?.tvMale?.text = context.getString(R.string.no)
        binding?.layoutGenderView?.tvFemale?.text = context.getString(R.string.yes)

        binding?.layoutGenderView?.tvMale?.setOnClickListener {
            onItemSelect(false)
        }

        binding?.layoutGenderView?.tvFemale?.setOnClickListener {
            onItemSelect(true)
        }
        change(isSelected)

    }

    private fun change(isSelected: Boolean) {
        binding?.layoutGenderView?.tvMale?.setBackgroundResource(
            if (isSelected) R.drawable.bg_white_corners_5_dp else R.drawable.bg_enabled_button
        )
        binding?.layoutGenderView?.tvMale?.setTextColor(
            ContextCompat.getColor(
                context,
                if (isSelected) R.color.black else R.color.white
            )
        )

        binding?.layoutGenderView?.tvFemale?.setBackgroundResource(
            if (isSelected) R.drawable.bg_enabled_button else R.drawable.bg_white_corners_5_dp

        )
        binding?.layoutGenderView?.tvFemale?.setTextColor(
            ContextCompat.getColor(
                context,
                if (isSelected) R.color.white else R.color.black
            )
        )
    }
}
