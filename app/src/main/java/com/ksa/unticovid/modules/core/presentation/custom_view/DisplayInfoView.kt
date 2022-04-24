package com.ksa.unticovid.modules.core.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ksa.unticovid.R
import com.ksa.unticovid.databinding.ViewDisplayInfoBinding

class DisplayInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var binding: ViewDisplayInfoBinding? = null

    init {
        binding = ViewDisplayInfoBinding.inflate(LayoutInflater.from(context), null, false)
        addView(binding?.root)
        init(attrs)
    }

//    fun getKeyTextView(): TextView? = binding?.cellKey

    private fun init(set: AttributeSet?) {
        val ta = context.obtainStyledAttributes(set, R.styleable.ViewDisplayInfo)
//        val itemImage = ta.getInteger(R.styleable.ViewDisplayInfo_item_image, R.drawable.ic_history)
//        val itemLabel = ta.getString(R.styleable.ViewDisplayInfo_item_label)

        binding?.ivIcon?.setImageResource(ta.getResourceId(R.styleable.ViewDisplayInfo_item_image, -1))
        binding?.tvLabel?.text = ta.getString(R.styleable.ViewDisplayInfo_item_label)

//        binding?.ivIcon?.setImageResource(itemImage)
//        binding?.tvLabel?.text = itemLabel

        ta.recycle()
    }
}
