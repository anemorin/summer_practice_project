package com.summer_practice.app_project.AppApi

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AppAPI {
    @GET("/manga/{id}/feed")
    suspend fun getMangaChapters(@Path("id") id:String): MangaItem


    @POST("/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun logIn(@Body creds: LogInAtr) : String

    @GET("/manga")
    suspend fun searchManga(params: ContextFunctionTypeParams) : List<MangaItem>
}