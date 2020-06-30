package com.hmn.currencyconverter.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmn.currencyconverter.R
import com.hmn.currencyconverter.data.vos.RateVO
import com.hmn.currencyconverter.viewmodel.ExchangeViewModel
import com.hmn.currencyconverter.viewmodel.ViewModelmpl
import kotlinx.android.synthetic.main.fragment_cy_calculate.view.*


class CyCalculateFragment : Fragment() {
    private lateinit var mViewModel: ExchangeViewModel
    private var currencyType = "USD"

    private lateinit var mSpinner: Spinner

    private lateinit var mAmount: EditText

    private lateinit var mMMK: TextView



    var currencyList = ArrayList<RateVO>()

    var usdRate = 0.0
    var sgdRate = 0.0
    var eurRate = 0.0
    var thbRate = 0.0
    var jpyRate = 0.0

    companion object {
        fun newFragment(): CyCalculateFragment {
            return CyCalculateFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cy_calculate, container, false)
        mSpinner = view.findViewById(R.id.spnCurrency) as Spinner
        mAmount = view.findViewById(R.id.etAmount) as EditText
        mMMK = view.findViewById(R.id.tv_MMK) as TextView


        mViewModel = ViewModelProviders.of(this).get(ViewModelmpl::class.java)

        mSpinner.isEnabled = false
        view.progressCalculate.visibility = View.VISIBLE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.getAllCurrency().observe(viewLifecycleOwner, Observer {


            view.progressCalculate.visibility = View.GONE
            mSpinner.isEnabled = true
            for (currency in it) {
                val v = currency.mmk.replace(",", "")
                if (isFavouriteCurrency(currency.currency_code, v.toDouble()))
                    currencyList.add(RateVO(currency.currency_code, v))

            }
            Log.d("usd rate", usdRate.toString())

            mAmount.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                    try {
                        val rate = s.toString().toDouble()
                        calculateCurrency(rate, usdRate)
                        currencyTypeGenerator()
                    } catch (e: NumberFormatException) {
                        mMMK.text = "0.0"
                    }

                    currencyTypeGenerator()

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

        })

        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currencyType = mSpinner.selectedItem.toString()
                Log.d("current", currencyType)
                currencyTypeGenerator()


            }

        }
    }


    private fun currencyTypeGenerator() {
        try {
            when (currencyType) {
                "USD" -> {
                    if (!mAmount.text.toString().isBlank()) {
                        mMMK.text = calculateCurrency(mAmount.text.toString().toDouble(), usdRate).toString()
                    } else {
                        mMMK.text = "0.0"
                    }
                }
                "EUR" -> {
                    if (!mAmount.text.toString().isBlank()) {
                        mMMK.text = calculateCurrency(mAmount.text.toString().toDouble(), eurRate).toString()
                    }else{
                        mMMK.text = "0.0"
                    }
                }
                "SGD" -> {

                    if (!mAmount.text.toString().isBlank()) {
                        mMMK.text = calculateCurrency(mAmount.text.toString().toDouble(), sgdRate).toString()
                    }else{
                        mMMK.text = "0.0"
                    }
                }
                "THB" -> {
                    if (!mAmount.text.toString().isBlank()) {
                        mMMK.text = calculateCurrency(mAmount.text.toString().toDouble(), thbRate).toString()
                    }else{
                        mMMK.text = "0.0"
                    }
                }
                "JPY" -> {
                    if (!mAmount.text.toString().isBlank()) {
                        mMMK.text = calculateCurrency(mAmount.text.toString().toDouble(), jpyRate).toString()
                    }else{
                        mMMK.text = "0.0"
                    }
                }
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }


    }

    private fun calculateCurrency(amount: Double, rate: Double): Double =
                                amount * rate


                            private fun isFavouriteCurrency(
                                currency: String,
                                value: Double
                            ): Boolean {
                                val isCurrency: Boolean

                                when (currency) {
                                    "USD" -> {
                                        usdRate = value
                                        isCurrency = true
                                    }
                                    "EUR" -> {
                                        eurRate = value
                                        isCurrency = true
                                    }
                                    "SGD" -> {
                                        sgdRate = value
                                        isCurrency = true
                                    }
                                    "THB" -> {
                                        thbRate = value
                                        isCurrency = true
                                    }
                                    "JPY" -> {
                                        jpyRate = value
                                        isCurrency = true
                                    }
                                    else -> isCurrency = false

                                }

                                return isCurrency


                            }

                        }
