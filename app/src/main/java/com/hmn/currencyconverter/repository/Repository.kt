package com.hmn.currencyconverter.repository

import androidx.lifecycle.MutableLiveData
import com.hmn.currencyconverter.data.vos.ExchangeRateVO

interface Repository {
    fun getLatestRate(): MutableLiveData<ExchangeRateVO>


}