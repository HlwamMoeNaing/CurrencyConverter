package com.hmn.currencyconverter.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.currencyconverter.data.network.CBMApi
import com.hmn.currencyconverter.data.network.RetroService
import com.hmn.currencyconverter.data.room.CBMDao
import com.hmn.currencyconverter.data.room.CurrencyDb
import com.hmn.currencyconverter.data.room.CurrencyEntity
import com.hmn.currencyconverter.data.vos.ExchangeRateVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(application: Application) : Repository {
    private val currencyDB = CurrencyDb.getDatabase(application)

    private val mCurrencyDao = currencyDB.currencyDao()
    private val allCurrency = mCurrencyDao.getCurrency()

    lateinit var mApi: CBMApi

    init {
        mApi = RetroService().createService(CBMApi::class.java)
    }


    override fun getLatestRate(): MutableLiveData<ExchangeRateVO> {
        val exchangeRate = MutableLiveData<ExchangeRateVO>()
        mApi.getExchangeRate().enqueue(object : Callback<ExchangeRateVO> {
            override fun onFailure(call: Call<ExchangeRateVO>, t: Throwable) {
                Log.i("@Hmn", t.message.toString())

                exchangeRate.value = null
            }

            override fun onResponse(
                call: Call<ExchangeRateVO>,
                response: Response<ExchangeRateVO>
            ) {
                if (response.isSuccessful) {
                    Log.i("@Hmn", "success")
                    exchangeRate.value = response.body()
                }
            }

        })
        return exchangeRate
    }



    fun getAllCurrecny(): LiveData<List<CurrencyEntity>> = allCurrency

    fun insertCurrency(currencyEntity: CurrencyEntity) {

        InsertCurrency(mCurrencyDao).execute(currencyEntity)
    }

    fun updateCurrency(currencyEntity: CurrencyEntity) {
        UpdateCurrency(mCurrencyDao).execute(currencyEntity)

    }

    fun deleteCurrency(currencyEntity: CurrencyEntity) {
        DeleteCurrency(mCurrencyDao).execute(currencyEntity)
    }

    fun deleteAllCurrency() {
        DeleteAllCurrency(mCurrencyDao).execute()
    }


    private class InsertCurrency(dao: CBMDao) : AsyncTask<CurrencyEntity, Void, Void>() {
        private val mAsyncTaskDao: CBMDao = dao
        override fun doInBackground(vararg params: CurrencyEntity?): Void? {
            mAsyncTaskDao.insertCurrency(params[0]!!)
            return null
        }
    }

    private class UpdateCurrency(dao: CBMDao) : AsyncTask<CurrencyEntity, Void, Void>() {
        private val mAsyncTaskDao: CBMDao = dao
        override fun doInBackground(vararg params: CurrencyEntity?): Void? {
            mAsyncTaskDao.updateCurrency(params[0]!!)
            return null
        }
    }

    private class DeleteCurrency(dao: CBMDao) : AsyncTask<CurrencyEntity, Void, Void>() {
        private val mAsyncTaskDao: CBMDao = dao
        override fun doInBackground(vararg params: CurrencyEntity?): Void? {
            mAsyncTaskDao.deleteCurrency(params[0]!!)
            return null
        }
    }

    private class DeleteAllCurrency(dao: CBMDao) : AsyncTask<Void, Void, Void>() {
        private val mAsyncTaskDao: CBMDao = dao
        override fun doInBackground(vararg params: Void?): Void? {
            mAsyncTaskDao.deleteAllCurrency()
            return null
        }
    }
}

