/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2021 YAYO Software Development <yayooo@gmail.com>
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
package com.yayooo.wifianalyzer.permission

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.view.View
import com.yayooo.annotation.OpenClass
import com.yayooo.util.buildMinVersionM
import com.yayooo.util.buildMinVersionP
import com.yayooo.wifianalyzer.R

@OpenClass
class PermissionDialog(private val activity: Activity) {
    fun show() {
        val view = activity.layoutInflater.inflate(R.layout.info_permission, null)
        val visibility = if (buildMinVersionP()) View.VISIBLE else View.GONE
        view.findViewById<View>(R.id.throttling)?.visibility = visibility
        AlertDialog.Builder(activity)
                .setView(view)
                .setTitle(R.string.app_full_name)
                .setIcon(R.drawable.ic_app)
                .setPositiveButton(android.R.string.ok, OkClick(activity))
                .setNegativeButton(android.R.string.cancel, CancelClick(activity))
                .create()
                .show()
    }

    internal class OkClick(private val activity: Activity) : DialogInterface.OnClickListener {
        override fun onClick(alertDialog: DialogInterface, which: Int) {
            alertDialog.dismiss()
            requestPermissionsAndroidM()
        }

        @TargetApi(Build.VERSION_CODES.M)
        private fun requestPermissionsAndroidM() {
            if (buildMinVersionM()) {
                activity.requestPermissions(ApplicationPermission.PERMISSIONS, ApplicationPermission.REQUEST_CODE)
            }
        }

    }

    internal class CancelClick(private val activity: Activity) : DialogInterface.OnClickListener {
        override fun onClick(alertDialog: DialogInterface, which: Int) {
            alertDialog.dismiss()
            activity.finish()
        }

    }

}