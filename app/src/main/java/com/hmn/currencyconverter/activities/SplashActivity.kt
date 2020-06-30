package com.hmn.currencyconverter.activities

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmn.currencyconverter.R
import com.hmn.currencyconverter.data.room.CurrencyEntity
import com.hmn.currencyconverter.util.utils
import com.hmn.currencyconverter.util.utils.KEY_DB_LATEST_DATE
import com.hmn.currencyconverter.viewmodel.ViewModelmpl
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity : AppCompatActivity() {
    private val PERMISSION_REQUESTS = 1

    private val TAG = "Permission"

    lateinit var mViewModel: ViewModelmpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSharedPreferences = getSharedPreferences("ConfigUtils", Context.MODE_PRIVATE)


        mViewModel = ViewModelProviders.of(this).get(ViewModelmpl::class.java)
        // request permission
        if (!allPermissionsGranted()) {
            getRuntimePermissions()
        }


        if (isConnectingToInternet(this)) {
            if (loadDBLatestDate() < SimpleDateFormat("yyyyMMdd").format(Date()).toLong()) {

                mViewModel.deleteAllCurrency()

                mViewModel.getExchangeRate().observe(this, Observer {

                    for ((key, value) in it.rates) {
                        mViewModel.insertCurrency(CurrencyEntity(0, key, value))
                    }
                    saveDBLatestDate(SimpleDateFormat("yyyyMMdd").format(Date()).toLong())
                    Handler().postDelayed(
                        {
                            startActivity(MainActivity.newInstance(this))
                            finish()
                        },
                        2000
                    )
                })

            } else {
                Handler().postDelayed(
                    {
                        startActivity(MainActivity.newInstance(this))
                        finish()
                    },
                    1000
                )


            }
        } else {

            if(isDatabaseExist()){
                Toast.makeText(
                    this,
                    "No Internet Connection, Please access to internet", Toast.LENGTH_LONG
                ).show()
                Handler().postDelayed(
                    {
                        startActivity(MainActivity.newInstance(this))
                        finish()
                    },
                    1000
                )
            }else{
                Toast.makeText(
                    this,
                    "No Internet Connection, Please access to internet", Toast.LENGTH_LONG
                ).show()
                Handler().postDelayed(
                    {
                        finish()
                    }, 1000
                )
            }

        }


    }


    //check permission
    private fun getRequiredPermissions(): Array<String?> {
        return try {
            val info = this.packageManager
                .getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
            val ps = info.requestedPermissions
            if (ps != null && ps.isNotEmpty()) {
                ps
            } else {
                arrayOfNulls(0)
            }
        } catch (e: Exception) {
            arrayOfNulls(0)
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in getRequiredPermissions()) {
            permission?.let {
                if (!isPermissionGranted(this, it)) {
                    return false
                }
            }
        }
        return true
    }

    private fun getRuntimePermissions() {
        val allNeededPermissions = ArrayList<String>()
        for (permission in getRequiredPermissions()) {
            permission?.let {
                if (!isPermissionGranted(this, it)) {
                    allNeededPermissions.add(permission)
                }
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                this, allNeededPermissions.toTypedArray(), PERMISSION_REQUESTS
            )
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: $permission")
            return true
        }
        Log.i(TAG, "Permission NOT granted: $permission")
        return false
    }


    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }

    companion object {
        private lateinit var mSharedPreferences: SharedPreferences


        fun saveDBLatestDate(date: Long) {
            mSharedPreferences.edit().putLong(KEY_DB_LATEST_DATE, date).apply()
        }

        fun loadDBLatestDate(): Long {
            return mSharedPreferences.getLong(KEY_DB_LATEST_DATE, 0)
        }


    }

    fun isDatabaseExist():Boolean{
        val dbFile = this.getDatabasePath(utils.DB_NAME)
        return dbFile.exists()
    }
}