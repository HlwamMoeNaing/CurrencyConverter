package com.hmn.currencyconverter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.currencyconverter.data.room.CurrencyEntity
import com.hmn.currencyconverter.data.vos.ExchangeRateVO
import com.hmn.currencyconverter.repository.RepositoryImpl

class ViewModelmpl(application: Application) : AndroidViewModel(application), ExchangeViewModel {


    private val repository = RepositoryImpl(application)

    private lateinit var exchangeRateList: MutableLiveData<ExchangeRateVO>

   // private lateinit var historyRateList: MutableLiveData<ExchangeRateVO>


    override fun getAllCurrency(): LiveData<List<CurrencyEntity>> {
        return repository.getAllCurrecny()
    }

    override fun getExchangeRate(): LiveData<ExchangeRateVO> {
        exchangeRateList = repository.getLatestRate()
        return exchangeRateList
    }

    override fun insertCurrency(currencyEntity: CurrencyEntity) {
        repository.insertCurrency(currencyEntity)
    }

    override fun updateCurrency(currencyEntity: CurrencyEntity) {
        repository.updateCurrency(currencyEntity)
    }

    override fun deleteCurrency(currencyEntity: CurrencyEntity) {
        repository.deleteCurrency(currencyEntity)
    }

    override fun deleteAllCurrency() {
        repository.deleteAllCurrency()
    }
}