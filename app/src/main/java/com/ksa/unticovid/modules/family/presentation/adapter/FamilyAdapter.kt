package com.ksa.unticovid.modules.family.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ksa.unticovid.core.utils.Consumer
import com.ksa.unticovid.databinding.ItemFamilyMemberBinding
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel
import javax.inject.Inject

class FamilyAdapter @Inject constructor() :
    ListAdapter<FamilyMemberDataUIModel, FamilyAdapter.ViewHolder>(
        AdapterDiffUtil
    ) {

    private object AdapterDiffUtil :
        DiffUtil.ItemCallback<FamilyMemberDataUIModel>() {
        override fun areItemsTheSame(
            oldItem: FamilyMemberDataUIModel,
            newItem: FamilyMemberDataUIModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FamilyMemberDataUIModel,
            newItem: FamilyMemberDataUIModel
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

    fun submitData(family: MutableList<FamilyMemberDataUIModel>) {
        submitList(family)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val bind: ItemFamilyMemberBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindViews(item: FamilyMemberDataUIModel) {
            bindLayout(item)
        }

        private fun bindLayout(item: FamilyMemberDataUIModel) {
            bind.apply {
                with(item) {
                    tvName.text = name
                    tvAge.text = age
                    tvMobile.text = mobile
                    tvNationalId.text = nationalIdentity
                    tvRelation.text = relation
                    tvGender.text = bind.root.context.getString(gender)
                }
            }
        }
    }
}
