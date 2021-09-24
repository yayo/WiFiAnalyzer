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
package com.yayooo.wifianalyzer

import com.yayooo.wifianalyzer.settings.Settings
import com.yayooo.wifianalyzer.vendor.model.VendorService
import com.yayooo.wifianalyzer.wifi.filter.adapter.FiltersAdapter
import com.yayooo.wifianalyzer.wifi.manager.WiFiManagerWrapper
import com.yayooo.wifianalyzer.wifi.scanner.ScannerService
import io.mockk.mockk

enum class MainContextMockkHelper {
    INSTANCE;

    private val saved: MutableMap<Class<*>, Any> = mutableMapOf()
    private val mainContext: MainContext = MainContext.INSTANCE

    val settings: Settings
        get() {
            try {
                saved[Settings::class.java] = mainContext.settings
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.settings = mockk()
            return mainContext.settings
        }

    val vendorService: VendorService
        get() {
            try {
                saved[VendorService::class.java] = mainContext.vendorService
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.vendorService = mockk()
            return mainContext.vendorService
        }

    val scannerService: ScannerService
        get() {
            try {
                saved[ScannerService::class.java] = mainContext.scannerService
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.scannerService = mockk()
            return mainContext.scannerService
        }

    val mainActivity: MainActivity
        get() {
            try {
                saved[MainActivity::class.java] = mainContext.mainActivity
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.mainActivity = mockk()
            return mainContext.mainActivity
        }

    val configuration: Configuration
        get() {
            try {
                saved[Configuration::class.java] = mainContext.configuration
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.configuration = mockk()
            return mainContext.configuration
        }

    val filterAdapter: FiltersAdapter
        get() {
            try {
                saved[FiltersAdapter::class.java] = mainContext.filtersAdapter
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.filtersAdapter = mockk()
            return mainContext.filtersAdapter
        }

    val wiFiManagerWrapper: WiFiManagerWrapper
        get() {
            try {
                saved[WiFiManagerWrapper::class.java] = mainContext.wiFiManagerWrapper
            } catch (e: UninitializedPropertyAccessException) {
                /* do nothing */
            }
            mainContext.wiFiManagerWrapper = mockk()
            return mainContext.wiFiManagerWrapper
        }

    fun restore() {
        saved.entries.forEach {
            when (it.key) {
                Settings::class.java -> mainContext.settings = it.value as Settings
                VendorService::class.java -> mainContext.vendorService = it.value as VendorService
                ScannerService::class.java -> mainContext.scannerService = it.value as ScannerService
                MainActivity::class.java -> mainContext.mainActivity = it.value as MainActivity
                Configuration::class.java -> mainContext.configuration = it.value as Configuration
                FiltersAdapter::class.java -> mainContext.filtersAdapter = it.value as FiltersAdapter
                WiFiManagerWrapper::class.java -> mainContext.wiFiManagerWrapper = it.value as WiFiManagerWrapper
            }
        }
        saved.clear()
    }

}
