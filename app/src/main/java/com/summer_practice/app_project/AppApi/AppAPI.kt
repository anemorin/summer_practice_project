package com.summer_practice.app_project.AppApi

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppAPI {
    @GET("/manga/{id}/feed?translatedLanguage[]=en&order[chapter]=asc&limit=300")
    suspend fun getMangaChapters(@Path("id") id:String): ApiItemChapter

    @POST("/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun logIn(@Body creds: LogInAtr) : Response<UserItem>

    @GET("/manga/?includes[]=cover_art")
    suspend fun searchManga(@Query("title") title: String) : ApiMultiItem

    @GET("/manga/{id}?&includes[]=author&includes[]=cover_art")
    suspend fun getMangaById(@Path("id") id : String) : ApiSingleItem

    @POST("/manga/{id}/follow")
    suspend fun addMangaToWishList(@Path("id") id : String, @Header("Authorization") token : String )

    @DELETE("/manga/{id}/follow")
    suspend fun deleteMangaFromWishList(@Path("id") id : String, @Header("Authorization") token : String )

    @GET("/user/follows/manga?includes[]=cover_art&limit=100")
    suspend fun getFollowList(@Header("Authorization")  token : String ) : ApiMultiItem

    //title: Latest Update
    @GET("/manga?includes[]=cover_art")
    suspend fun getListLatestUpdate(@Query("limit") limit : Int) : ApiMultiItem

    //title: Best of 2023
    @GET("/manga?year=2023&includes[]=cover_art")
    suspend fun getList2023(@Query("limit") limit : Int) : ApiMultiItem

    //title: Naruto
    @GET("/manga?title=naruto&includes[]=cover_art")
    suspend fun getListNaruto(@Query("limit") limit : Int) : ApiMultiItem

    //title: By Oda Eiichiro
    @GET("/manga?authors[]=b6045e2c-28f4-4ce0-b4dd-b14070f2f5ae&includes[]=cover_art")
    suspend fun getListByOdaEiichiro(@Query("limit") limit : Int) : ApiMultiItem

    //title: Pokemon
    @GET("/manga?title=pokemon&includes[]=cover_art")
    suspend fun getListPokemon(@Query("limit") limit : Int) : ApiMultiItem

    //title: Ninja
    @GET("/manga?title=ninja&includes[]=cover_art")
    suspend fun getListNinja(@Query("limit") limit : Int) : ApiMultiItem

    //title: Samurai
    @GET("/manga?title=samurai&includes[]=cover_art")
    suspend fun getListSamurai(@Query("limit") limit : Int) : ApiMultiItem

    @GET("/at-home/server/{id}")
    suspend fun getChapterImage(@Path("id") id : String) : ChapterImageItem

    @GET("/user/follows/manga/{id}")
    suspend fun checkUserFollowedManga(@Path("id") id : String, @Header("Authorization")  token : String) : Response<ResultItem>
}