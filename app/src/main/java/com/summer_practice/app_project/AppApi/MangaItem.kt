package com.summer_practice.app_project.AppApi

import java.io.Serializable

data class MangaItem(
    val id : String,
    val type : String,
    val attributes: AttributeItem,
    val relationships : List<RelationshipsItem>
)
