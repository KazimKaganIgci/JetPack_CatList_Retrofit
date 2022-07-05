package com.kazim.catretrofitjetpack.service

import com.kazim.catretrofitjetpack.model.CatModelItem
import retrofit2.Call
import retrofit2.http.GET

interface CatAPI {
    @GET("v1/breeds?attach_breed=2a7ba349-a4ac-4ab6-9e8f-?????????")
    fun  getData():Call<List<CatModelItem>>
}
