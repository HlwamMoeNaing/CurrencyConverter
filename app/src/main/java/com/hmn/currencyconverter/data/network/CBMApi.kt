package com.hmn.currencyconverter.data.network


import com.hmn.currencyconverter.data.vos.ExchangeRateVO
import retrofit2.Call
import retrofit2.http.GET

interface CBMApi {
    @GET("latest")
    fun getExchangeRate(): Call<ExchangeRateVO>


}