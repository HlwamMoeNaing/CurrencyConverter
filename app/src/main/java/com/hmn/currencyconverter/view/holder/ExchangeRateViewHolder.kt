package com.hmn.currencyconverter.view.holder

import android.view.View
import com.hmn.currencyconverter.data.room.CurrencyEntity
import kotlinx.android.synthetic.main.exchange_rate_view_item.view.*

class ExchangeRateViewHolder(itemView: View) : BaseViewHolder<CurrencyEntity>(itemView) {
    override fun setData(data: CurrencyEntity) {
        itemView.tvCurrencyCode.text = data.currency_code
        itemView.tvSellRate.text = data.mmk
    }

    override fun onClick(p0: View?) {

    }
}