package com.appiness.assignmentappiness.utils

import android.content.Context
import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import android.text.format.DateFormat;
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.appiness.assignmentappiness.R
import com.appiness.assignmentappiness.interfaces.NetworkListener
import org.jetbrains.anko.layoutInflater
import java.math.BigDecimal
import java.math.RoundingMode


object Util {



    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }
    fun onShowDialog(context: Context, networkListener: NetworkListener, int: Int) {
        val builder = AlertDialog.Builder(context)
        val view = context.layoutInflater.inflate(R.layout.network_issue_layout, null)

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val tryAgain = view.findViewById<Button>(R.id.tryAgain)

        builder.setView(view)
        val dialog = builder.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        tryAgain.setOnClickListener {
            if(isInternetAvailable(context)) {
                networkListener.tryAgain(int)
                dialog.dismiss()
            }
        }



    }

    const val ONE = 1
    const val TWO = 2
    const val THREE = 3


}