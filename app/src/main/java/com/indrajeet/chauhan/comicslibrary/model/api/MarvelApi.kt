package com.indrajeet.chauhan.comicslibrary.model.api

import com.indrajeet.chauhan.comicslibrary.model.CharactersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(@Query("nameStartsWith") name: String) : Call<CharactersApiResponse>
}