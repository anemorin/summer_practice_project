package com.summer_practice.app_project.AppApi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RelationshipsItem(
    val id : String,
    val type : String,
    val attributes: CoverAtributesItem?
) : Parcelable
