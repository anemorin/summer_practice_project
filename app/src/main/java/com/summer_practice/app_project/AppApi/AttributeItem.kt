package com.summer_practice.app_project.AppApi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttributeItem(
    val title: TitleItem,
    val altTitles: List<TitleItem>,
    val description : DescriptionItem,
    val originalLanguage: String,
    val status: String,
    val year: Int,
    val createdAt: String,
    val availableTranslatedLanguages : List<String>,
) : Parcelable
