package com.summer_practice.app_project.AppApi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverAtributesItem(
    val name: String?,
    val fileName: String?
) : Parcelable
