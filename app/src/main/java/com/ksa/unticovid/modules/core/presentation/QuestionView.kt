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
        isLeftSelected: Boolean,
        leftText: String? = null,
        rightText: String? = null,
        onItemSelect: Consumer<Boolean>,
    ) {
        binding?.tvQuestion?.text = question

        binding?.layoutGenderView?.tvLeft?.text = leftText ?: context.getString(R.string.no)
        binding?.layoutGenderView?.tvRight?.text = rightText ?: context.getString(R.string.yes)

        binding?.layoutGenderView?.tvLeft?.setOnClickListener {
            onItemSelect(false)
        }

        binding?.layoutGenderView?.tvRight?.setOnClickListener {
            onItemSelect(true)
        }
        change(isLeftSelected)

    }

    private fun change(isLeftSelected: Boolean) {
        binding?.layoutGenderView?.tvLeft?.setBackgroundResource(
            if (isLeftSelected) R.drawable.bg_white_corners_5_dp else R.drawable.bg_enabled_button
        )
        binding?.layoutGenderView?.tvLeft?.setTextColor(
            ContextCompat.getColor(
                context,
                if (isLeftSelected) R.color.black else R.color.white
            )
        )

        binding?.layoutGenderView?.tvRight?.setBackgroundResource(
            if (isLeftSelected) R.drawable.bg_enabled_button else R.drawable.bg_white_corners_5_dp

        )
        binding?.layoutGenderView?.tvRight?.setTextColor(
            ContextCompat.getColor(
                context,
                if (isLeftSelected) R.color.white else R.color.black
            )
        )
    }
}
