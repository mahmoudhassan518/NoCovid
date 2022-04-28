package com.ksa.unticovid.modules.family.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ksa.unticovid.core.utils.Consumer
import com.ksa.unticovid.databinding.ItemFamilyMemberBinding
import com.ksa.unticovid.modules.family.presentation.model.FamilyDataUIModel
import javax.inject.Inject

class FamilyAdapter @Inject constructor() :
    ListAdapter<FamilyDataUIModel, FamilyAdapter.ViewHolder>(
        AdapterDiffUtil
    ) {

    lateinit var itemClickListener: Consumer<FamilyDataUIModel>

    private object AdapterDiffUtil :
        DiffUtil.ItemCallback<FamilyDataUIModel>() {
        override fun areItemsTheSame(
            oldItem: FamilyDataUIModel,
            newItem: FamilyDataUIModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FamilyDataUIModel,
            newItem: FamilyDataUIModel
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFamilyMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    inner class ViewHolder(private val bind: ItemFamilyMemberBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindViews(item: FamilyDataUIModel) {
            bindLayout(item)
        }

        private fun bindLayout(item: FamilyDataUIModel) {
            bind.apply {
                with(item) {
                    tvName.text = item.name
                    tvAge.text = item.age
                    tvMobile.text = item.mobile
                    tvNationalId.text = item.nationalIdentity
                    tvRelation.text = item.relation
                    tvGender.text = bind.root.context.getString(item.gender)

                    root.setOnClickListener {
                        itemClickListener(
                            getItem(adapterPosition)
                        )
                    }
                }
            }
        }
    }
}
