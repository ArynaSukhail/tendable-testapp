package com.release.inspectionapp.inspection.ui.adaper

import android.view.ViewGroup
import com.release.domain.model.InspectionItem
import com.release.inspectionapp.R
import com.release.inspectionapp.databinding.ItemInspectionBinding
import com.release.inspectionapp.utils.presentation.BaseViewHolder

class SavedViewHolder(
    parent: ViewGroup,
    private val inspectionItemClickListener: InspectionItemClickListener
) : BaseViewHolder(parent, R.layout.item_inspection) {

    private val viewBinding = ItemInspectionBinding.bind(itemView)

    fun bind(inspectionItems: InspectionItem) {
        viewBinding.inspectionItemModel = inspectionItems
        viewBinding.clickListener = inspectionItemClickListener
    }
}