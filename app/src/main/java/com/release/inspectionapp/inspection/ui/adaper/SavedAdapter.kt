package com.release.inspectionapp.inspection.ui.adaper

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.release.domain.model.InspectionItem

class SavedAdapter(
    private val inspectionItemClickListener: InspectionItemClickListener
) : RecyclerView.Adapter<SavedViewHolder>() {

    private val items = mutableListOf<InspectionItem>()

    fun updateList(inspectionItem: List<InspectionItem>) {
        items.clear()
        items.addAll(inspectionItem)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        return SavedViewHolder(parent, inspectionItemClickListener)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}