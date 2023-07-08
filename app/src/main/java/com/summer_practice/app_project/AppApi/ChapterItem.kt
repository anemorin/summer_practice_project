package com.summer_practice.app_project.AppApi

data class ChapterItem(
    val id : String,
    val type : String,
    val attributes: AtrributeChapterItem,
    val relationships : List<RelationshipsItem>
)
