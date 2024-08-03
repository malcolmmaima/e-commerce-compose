package com.shop.utils.app

import android.content.Context
import android.content.pm.PackageManager

fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "N/A"
    }
}
