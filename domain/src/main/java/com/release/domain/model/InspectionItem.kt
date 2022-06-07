package com.release.domain.model

data class InspectionItem(
    val id: Int,
    val type: String? = "",
    val area: String? = "",
    val access: String? = ""
)