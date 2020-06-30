package com.hmn.currencyconverter.adapter

import android.content.Context
import android.view.ViewGroup
import com.hmn.currencyconverter.R
import com.hmn.currencyconverter.data.room.CurrencyEntity
import com.hmn.currencyconverter.view.holder.ExchangeRateViewHolder

class ExchangeRateAdapter(context: Context): BaseAdapter<ExchangeRateViewHolder, CurrencyEntity>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val view = mLayoutInflater.inflate(R.layout.exchange_rate_view_item, parent, false)
        return ExchangeRateViewHolder(view)
    }
}