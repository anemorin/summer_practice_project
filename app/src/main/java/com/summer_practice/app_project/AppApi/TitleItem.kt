package com.summer_practice.app_project.AppApi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TitleItem(
    val en: String?
) : Parcelable
