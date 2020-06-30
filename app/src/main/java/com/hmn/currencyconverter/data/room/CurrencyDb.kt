package com.hmn.currencyconverter.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hmn.currencyconverter.util.utils


@Database(entities = arrayOf(CurrencyEntity::class), version = 1, exportSchema = false)
abstract class CurrencyDb : RoomDatabase() {
    abstract fun currencyDao(): CBMDao

    companion object {
        private var INSTANCE: CurrencyDb? = null

        fun getDatabase(context: Context): CurrencyDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, CurrencyDb::class.java, utils.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }


}