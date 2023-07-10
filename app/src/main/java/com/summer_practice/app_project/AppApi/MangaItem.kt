package com.summer_practice.app_project.AppApi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class MangaItem(
    val id : String,
    val type : String,
    val attributes: AttributeItem,
    val relationships : List<RelationshipsItem>
) : Parcelable
