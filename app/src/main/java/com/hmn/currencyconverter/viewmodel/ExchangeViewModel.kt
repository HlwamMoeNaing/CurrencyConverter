package com.hmn.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import com.hmn.currencyconverter.data.room.CurrencyEntity
import com.hmn.currencyconverter.data.vos.ExchangeRateVO

interface ExchangeViewModel {

    fun getAllCurrency(): LiveData<List<CurrencyEntity>>

    fun getExchangeRate(): LiveData<ExchangeRateVO>


    fun insertCurrency(currencyEntity: CurrencyEntity)

    fun updateCurrency(currencyEntity: CurrencyEntity)

    fun deleteCurrency(currencyEntity: CurrencyEntity)

    fun deleteAllCurrency()
}