package com.ksa.unticovid.modules.analytics.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ksa.unticovid.core.utils.Consumer
import com.ksa.unticovid.databinding.ItemAnalyticsBinding
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsItemUIModel
import javax.inject.Inject

class AnalyticsAdapter @Inject constructor() :
    ListAdapter<AnalyticsItemUIModel, AnalyticsAdapter.ViewHolder>(
        AdapterDiffUtil
    ) {

    lateinit var itemClickListener: Consumer<AnalyticsItemUIModel>

    private object AdapterDiffUtil :
        DiffUtil.ItemCallback<AnalyticsItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: AnalyticsItemUIModel,
            newItem: AnalyticsItemUIModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AnalyticsItemUIModel,
            newItem: AnalyticsItemUIModel
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAnalyticsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    inner class ViewHolder(private val bind: ItemAnalyticsBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindViews(item: AnalyticsItemUIModel) {
            bindLayout(item)
        }

        private fun bindLayout(item: AnalyticsItemUIModel) {
            bind.apply {
                bind.tvDate.text = item.date
                bind.ivImage.load(item.image)
                bind.root.setOnClickListener {
                    itemClickListener(
                        getItem(adapterPosition)
                    )
                }
            }
        }
    }
}
