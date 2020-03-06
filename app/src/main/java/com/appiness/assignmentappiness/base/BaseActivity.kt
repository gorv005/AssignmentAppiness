package com.appiness.assignmentappiness.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModel
import com.appiness.assignmentappiness.interfaces.LayoutImplemment
import com.appiness.assignmentappiness.interfaces.PermissionStatus
import com.appiness.assignmentappiness.utils.CommonInt
import com.appiness.assignmentappiness.utils.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.appiness.assignmentappiness.utils.CommonInt.DENIED
import com.appiness.assignmentappiness.utils.CommonInt.GRANTED
import com.appiness.assignmentappiness.utils.UiUtils
import kotlinx.android.synthetic.main.heading_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.jetbrains.anko.textColor
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass


abstract class BaseActivity<T : ViewModel>(clazz: KClass<T>) : AppCompatActivity(),
    LayoutImplemment {


    val model: T by viewModel(clazz)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout())

        toolbar?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.run {
                setDefaultDisplayHomeAsUpEnabled(false)
                setDisplayHomeAsUpEnabled(false)
                title = title()
            }
        }

        back?.run {
            ImageViewCompat.setImageTintMode(this, PorterDuff.Mode.SRC_ATOP)
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(ContextCompat.getColor(context, titleColor()))
            )
            setOnClickListener {
                hideKeyboard()
                finish()
            }
        }
        titleOne?.run {
            textColor = ContextCompat.getColor(context, titleColor())
            text = title()
        }

    }

    protected fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, CommonInt.Zero)
        }
    }

    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        }
        else super.onOptionsItemSelected(item)
    }

    protected fun checkPermissions(vararg permissions: String): Boolean {

        permissions.forEach {
            val permission = ContextCompat.checkSelfPermission(this, it)
            if (permission != 0)
                return false
        }

        return true
    }


    @PermissionStatus
    protected fun getPermissionStatus(androidPermissionName: String): Int {
        return if (ContextCompat.checkSelfPermission(
                this,
                androidPermissionName
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, androidPermissionName)) {
                BLOCKED_OR_NEVER_ASKED
            } else DENIED
        } else GRANTED
    }

    protected fun goToSettings() {
        val myAppSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(myAppSettings)
        finish()
    }

    protected fun checkGPSEnabled(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    protected fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
            .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    protected fun isLocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun showProgressDialog(title: String?, message: String?, cancelable: Boolean = false) {
        UiUtils.showProgressDialog(this, title, message, cancelable)
    }
    fun hideProgressDialog() {
        UiUtils.dismissProgressDialog()
    }
}