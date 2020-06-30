package com.hmn.currencyconverter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hmn.currencyconverter.fragments.AllCurrencyFragment
import com.hmn.currencyconverter.fragments.CyCalculateFragment


class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 ->AllCurrencyFragment.newFragment()
            else -> CyCalculateFragment.newFragment()
        }
    }

    override fun getCount(): Int {
       return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {

            0 -> "All"
            else -> "Calculator"
        }
    }
}