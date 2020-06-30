package com.hmn.currencyconverter.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hmn.currencyconverter.R
import com.hmn.currencyconverter.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    lateinit var mPagerAdapter: ViewPagerAdapter
    companion object {

        fun newInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        mPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = mPagerAdapter

        tabLayout.setupWithViewPager(viewPager)

    }



}