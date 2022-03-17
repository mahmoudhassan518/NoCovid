package com.ksa.unticovid.modules.main.report.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ksa.unticovid.core.utils.Consumer
import com.ksa.unticovid.databinding.ItemReportBinding
import com.ksa.unticovid.modules.main.report.presentation.model.ReportItemUIModel
import javax.inject.Inject

class ReportAdapter @Inject constructor() : ListAdapter<ReportItemUIModel, ReportAdapter.ViewHolder>(
    AdapterDiffUtil
) {

    lateinit var itemClickListener: Consumer<ReportItemUIModel>

    private object AdapterDiffUtil :
        DiffUtil.ItemCallback<ReportItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: ReportItemUIModel,
            newItem: ReportItemUIModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ReportItemUIModel,
            newItem: ReportItemUIModel
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReportBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    inner class ViewHolder(private val bind: ItemReportBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindViews(item: ReportItemUIModel) {
            bindLayout(item)
        }

        private fun bindLayout(item: ReportItemUIModel) {
            bind.apply {
                bind.tvTitle.text = item.title
                bind.tvDate.text = item.date
                bind.root.setOnClickListener {
                    itemClickListener(
                        getItem(adapterPosition)
                    )
                }
            }
        }
    }
}
