package com.summer_practice.app_project.AppApi

import org.json.JSONObject
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

    /*@GET("/manga/?title={title}")
    suspend fun searchManga(@Path("title") title: String) : ApiMultiItem*/

    @GET("/manga/?includes[]=cover_art")
    suspend fun searchManga(@Query("title") title : String) : ApiMultiItem

    @GET("/manga/{id}?&includes[]=author&includes[]=cover_art")
    suspend fun getMangaById(@Path("id") id : String) : ApiSingleItem

//    @POST("/manga/{id}/status")
//    @Headers({
//        "Content-Type: application/json"
//        "Authorization: Bearer ${token}"})
//    suspend fun addMangaToWishList(@Body status : StatusItem)

    //title: Latest Update
    @GET("/manga?includes[]=cover_art")
    suspend fun getListLatestUpdate() : ApiMultiItem


    //title: Best of 2023
    @GET("/manga?year=2023&includes[]=cover_art")
    suspend fun getList2023() : ApiMultiItem

    //title: Naruto
    @GET("/manga?title=naruto&includes[]=cover_art")
    suspend fun getListNaruto() : ApiMultiItem

    //title: By Oda Eiichiro
    @GET("/manga?authors[]=b6045e2c-28f4-4ce0-b4dd-b14070f2f5ae&includes[]=cover_art")
    suspend fun getListByOdaEiichiro() : ApiMultiItem

    //title: Pokemon
    @GET("/manga?title=pokemon&includes[]=cover_art")
    suspend fun getListPokemon() : ApiMultiItem

    //title: Ninja
    @GET("/manga?title=ninja&includes[]=cover_art")
    suspend fun getListNinja() : ApiMultiItem

    //title: Samurai
    @GET("/manga?title=samurai&includes[]=cover_art")
    suspend fun getListSamurai() : ApiMultiItem

    @GET("/at-home/server/{id}")
    suspend fun getChapterImage(@Path("id") id : String) : ChapterImageItem

}