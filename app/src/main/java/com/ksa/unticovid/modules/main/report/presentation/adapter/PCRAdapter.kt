package com.ksa.unticovid.modules.main.report.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ksa.unticovid.core.extentions.loadImage
import com.ksa.unticovid.core.utils.Consumer
import com.ksa.unticovid.databinding.ItemImageBinding
import com.ksa.unticovid.modules.main.report.presentation.model.ReportPCRUIModel
import javax.inject.Inject

class PCRAdapter @Inject constructor() :
    ListAdapter<ReportPCRUIModel, PCRAdapter.ViewHolder>(
        AdapterDiffUtil
    ) {

    lateinit var itemClickListener: Consumer<ReportPCRUIModel>

    private object AdapterDiffUtil :
        DiffUtil.ItemCallback<ReportPCRUIModel>() {
        override fun areItemsTheSame(
            oldItem: ReportPCRUIModel,
            newItem: ReportPCRUIModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ReportPCRUIModel,
            newItem: ReportPCRUIModel
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    inner class ViewHolder(private val bind: ItemImageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindViews(item: ReportPCRUIModel) {
            bindLayout(item)
        }

        private fun bindLayout(item: ReportPCRUIModel) {
            bind.apply {
                imageView.loadImage(item.image)
                root.setOnClickListener {
                    itemClickListener(
                        getItem(adapterPosition)
                    )
                }
            }
        }
    }
}
