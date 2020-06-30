package com.hmn.currencyconverter.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CBMDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertCurrency(currencyEntity: CurrencyEntity)

    @Update
    fun updateCurrency(currencyEntity: CurrencyEntity)

    @Delete
    fun deleteCurrency(currencyEntity: CurrencyEntity)

    @Query("DELETE From currency_table")
    fun deleteAllCurrency()

    @Query("SELECT * From currency_table")
    fun getCurrency(): LiveData<List<CurrencyEntity>>
}