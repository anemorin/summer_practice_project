package com.summer_practice.app_project.AppApi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiMultiItem(
    val result: String,
    val response : String,
    val data : List<MangaItem>
) : Parcelable
