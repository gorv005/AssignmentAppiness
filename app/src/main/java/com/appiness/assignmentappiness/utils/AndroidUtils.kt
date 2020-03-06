package com.appiness.assignmentappiness.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.appiness.assignmentappiness.R
import com.appiness.assignmentappiness.base.BaseApplication

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


fun EditText?.getTextTrimmed() = this?.text?.toString()?.trim() ?: ""

class AndroidUtils {

    companion object {

        fun getColor(@ColorRes id: Int) = ContextCompat.getColor(BaseApplication.getInstance(), id)

        fun getDimension(@DimenRes id: Int) =
            BaseApplication.getInstance().resources.getDimensionPixelSize(id)

        fun getInteger(@IntegerRes id: Int) = BaseApplication.getInstance().resources.getInteger(id)

        fun getBackgroundDrawable(@DrawableRes id: Int) =
            BaseApplication.getInstance().resources.getDrawable(id)

        @JvmStatic
        fun getString(@StringRes id: Int, vararg objects: Any?) = if (objects.isEmpty()) {
            BaseApplication.getInstance().resources.getString(id)
        } else {
            BaseApplication.getInstance().resources.getString(id, *objects)
        }

    }
}