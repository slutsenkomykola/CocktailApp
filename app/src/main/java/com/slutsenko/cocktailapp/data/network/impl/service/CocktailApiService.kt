package com.slutsenko.cocktailapp.data.network.impl.service

import com.slutsenko.cocktailapp.data.network.model.responce.CocktailListResponseNetModel
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface CocktailApiService {

    @GET("search.php")
    suspend fun searchCocktail(@Query("s") query: String): CocktailListResponseNetModel

    @GET("lookup.php")
    suspend fun searchCocktailById(@Query("i") id: Long): CocktailListResponseNetModel
}