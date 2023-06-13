package com.example.foodex.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionManager {
    fun checkConnectivity(context: Context):Boolean{
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //gives info about the currently active network
        val activeNetwork:NetworkInfo?=connectivityManager.activeNetworkInfo
        if(activeNetwork?.isConnected!=null)
        {
            return activeNetwork.isConnected
        }
        else
        {
            return false
        }

    }

}