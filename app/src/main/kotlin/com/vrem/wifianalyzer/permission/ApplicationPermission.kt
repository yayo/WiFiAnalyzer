/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2021 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.vrem.wifianalyzer.permission

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import com.vrem.annotation.OpenClass
import com.vrem.util.buildMinVersionM

@OpenClass
class ApplicationPermission(private val activity: Activity, private val permissionDialog: PermissionDialog = PermissionDialog(activity)) {
    fun check() {
        if (granted()) {
            return
        }
        if (activity.isFinishing) {
            return
        }
        permissionDialog.show()
    }

    fun granted(requestCode: Int, grantResults: IntArray): Boolean =
            requestCode == REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

    fun granted(): Boolean = !buildMinVersionM() || grantedAndroidM()

    @TargetApi(Build.VERSION_CODES.M)
    private fun grantedAndroidM(): Boolean =
            activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    companion object {
        internal val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        internal const val REQUEST_CODE = 0x123450
    }

}