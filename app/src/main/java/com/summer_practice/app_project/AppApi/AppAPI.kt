package com.summer_practice.app_project.AppApi

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppAPI {
    @GET("/manga/{id}/feed?translatedLanguage[]=en&order[chapter]=asc")
    suspend fun getMangaChapters(
        @Path("id") id:String)
    : ApiItemChapter

    @POST("/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun logIn(@Body creds: LogInAtr) : String

    @GET("/manga/?title={title}")
    suspend fun searchManga(@Path("title") title: String) : ApiMultiItem

    @GET("/manga/{id}?&includes[]=author&includes[]=cover_art")
    suspend fun getMangaById(@Path("id") id : String) : ApiSingleItem

//    @POST("/manga/{id}/status")
//    @Headers({
//        "Content-Type: application/json"
//        "Authorization: Bearer ${token}"})
//    suspend fun addMangaToWishList(@Body status : StatusItem)

    @GET("/manga?includes[]=cover_art")
    suspend fun getListLatestUpdate() : ApiMultiItem

    @GET("/at-home/server/{id}")
    suspend fun getChapterImage(@Path("id") id : String) : ChapterImageItem

}