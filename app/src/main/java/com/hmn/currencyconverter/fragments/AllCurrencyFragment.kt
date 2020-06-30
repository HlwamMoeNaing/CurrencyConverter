package com.hmn.currencyconverter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmn.currencyconverter.R
import com.hmn.currencyconverter.adapter.ExchangeRateAdapter
import com.hmn.currencyconverter.data.room.CurrencyEntity
import com.hmn.currencyconverter.viewmodel.ExchangeViewModel
import com.hmn.currencyconverter.viewmodel.ViewModelmpl
import kotlinx.android.synthetic.main.fragment_all_currency.view.*


class AllCurrencyFragment : Fragment() {
    lateinit var mViewModel: ExchangeViewModel
    lateinit var mAdapter: ExchangeRateAdapter

    companion object {
        fun newFragment(): AllCurrencyFragment {
            return AllCurrencyFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_currency, container, false)

        mViewModel = ViewModelProviders.of(this).get(ViewModelmpl::class.java)

        mAdapter = ExchangeRateAdapter(context!!)

        view.recyclerExchange.layoutManager = LinearLayoutManager(context)
        view.recyclerExchange.adapter = mAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var curList = ArrayList<CurrencyEntity>()
        mViewModel.getAllCurrency().observe(viewLifecycleOwner, Observer {
            curList = it as ArrayList<CurrencyEntity>
            curList.add(CurrencyEntity(0, "", ""))
            mAdapter.setNewData(curList)
        })

    }


}