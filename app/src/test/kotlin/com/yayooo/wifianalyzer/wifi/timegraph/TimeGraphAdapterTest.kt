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
package com.yayooo.wifianalyzer.wifi.timegraph

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yayooo.wifianalyzer.RobolectricUtil
import com.yayooo.wifianalyzer.wifi.band.WiFiBand
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TimeGraphAdapterTest {

    @Test
    fun testGraphViewNotifiers() {
        // setup
        RobolectricUtil.INSTANCE.activity
        val fixture = TimeGraphAdapter()
        // execute
        val graphViewNotifiers = fixture.graphViewNotifiers()
        // validate
        assertEquals(WiFiBand.values().size, graphViewNotifiers.size)
    }

    @Test
    fun testGraphViews() {
        // setup
        RobolectricUtil.INSTANCE.activity
        val fixture = TimeGraphAdapter()
        // execute
        val graphViews = fixture.graphViews()
        // validate
        assertEquals(WiFiBand.values().size, graphViews.size)
    }
}