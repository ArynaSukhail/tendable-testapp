package com.release.inspectionapp.utils.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(
    parent: ViewGroup,
    @LayoutRes itemLayoutId: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
)