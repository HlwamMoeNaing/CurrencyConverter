package com.hmn.currencyconverter.data.network

import com.hmn.currencyconverter.util.utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroService {

    val retrofit = Retrofit.Builder().baseUrl(utils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>):S{
        return retrofit.create(serviceClass)
    }

}