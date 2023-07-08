package com.summer_practice.app_project.AppApi

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AppAPI {
    @GET("/manga/{id}/feed")
    suspend fun getMangaChapters(@Path("id") id:String): ApiItemChapter

    @POST("/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun logIn(@Body creds: LogInAtr) : String

    @GET("/manga/?title={title}")
    suspend fun searchManga(@Path("title") title: String) : ApiItem

    @GET("/manga/{id}")
    suspend fun getMangaById(@Path("id") id : String) : ApiItem

//    @POST("/manga/{id}/status")
//    @Headers({
//        "Content-Type: application/json"
//        "Authorization: Bearer ${token}"})
//    suspend fun addMangaToWishList(@Body status : StatusItem)

    @GET("/manga?includes[]=cover_art")
    suspend fun getListLatestUpdate() : ApiItem

}