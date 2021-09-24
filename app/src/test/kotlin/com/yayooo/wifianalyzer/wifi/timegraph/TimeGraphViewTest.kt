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
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jjoe64.graphview.GraphView
import com.nhaarman.mockitokotlin2.*
import com.yayooo.wifianalyzer.MainContext
import com.yayooo.wifianalyzer.MainContextHelper
import com.yayooo.wifianalyzer.RobolectricUtil
import com.yayooo.wifianalyzer.settings.ThemeStyle
import com.yayooo.wifianalyzer.wifi.band.WiFiBand
import com.yayooo.wifianalyzer.wifi.graphutils.GraphLegend
import com.yayooo.wifianalyzer.wifi.graphutils.GraphViewWrapper
import com.yayooo.wifianalyzer.wifi.graphutils.MAX_Y
import com.yayooo.wifianalyzer.wifi.model.SortBy
import com.yayooo.wifianalyzer.wifi.model.WiFiConnection
import com.yayooo.wifianalyzer.wifi.model.WiFiData
import com.yayooo.wifianalyzer.wifi.model.WiFiDetail
import com.yayooo.wifianalyzer.wifi.predicate.Predicate
import com.yayooo.wifianalyzer.wifi.predicate.truePredicate
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class TimeGraphViewTest {
    private val dataManager: DataManager = mock()
    private val graphViewWrapper: GraphViewWrapper = mock()
    private val fixture: TimeGraphView = spy(TimeGraphView(WiFiBand.GHZ2, dataManager, graphViewWrapper))

    @After
    fun tearDown() {
        verifyNoMoreInteractions(dataManager)
        verifyNoMoreInteractions(graphViewWrapper)
        MainContextHelper.INSTANCE.restore()
    }

    @Test
    fun testUpdate() {
        // setup
        val settings = MainContextHelper.INSTANCE.settings
        val wiFiDetails: List<WiFiDetail> = listOf()
        val newSeries: Set<WiFiDetail> = setOf()
        val wiFiData = WiFiData(wiFiDetails, WiFiConnection.EMPTY)
        val predicate: Predicate = truePredicate
        doReturn(predicate).whenever(fixture).predicate(settings)
        whenever(dataManager.addSeriesData(graphViewWrapper, wiFiDetails, MAX_Y)).thenReturn(newSeries)
        whenever(settings.sortBy()).thenReturn(SortBy.SSID)
        whenever(settings.timeGraphLegend()).thenReturn(GraphLegend.LEFT)
        whenever(settings.wiFiBand()).thenReturn(WiFiBand.GHZ2)
        whenever(settings.graphMaximumY()).thenReturn(MAX_Y)
        whenever(settings.themeStyle()).thenReturn(ThemeStyle.DARK)
        // execute
        fixture.update(wiFiData)
        // validate
        verify(fixture).predicate(settings)
        verify(dataManager).addSeriesData(graphViewWrapper, wiFiDetails, MAX_Y)
        verify(graphViewWrapper).removeSeries(newSeries)
        verify(graphViewWrapper).updateLegend(GraphLegend.LEFT)
        verify(graphViewWrapper).visibility(View.VISIBLE)
        verify(settings).sortBy()
        verify(settings).timeGraphLegend()
        verify(settings).graphMaximumY()
        verify(settings).wiFiBand()
        verifyNoMoreInteractions(settings)
    }

    @Test
    fun testGraphView() {
        // setup
        val expected: GraphView = mock()
        whenever(graphViewWrapper.graphView).thenReturn(expected)
        // execute
        val actual = fixture.graphView()
        // validate
        Assert.assertEquals(expected, actual)
        verify(graphViewWrapper).graphView
        verifyNoMoreInteractions(expected)
    }

    @Test
    fun testMakeGraphView() {
        // setup
        RobolectricUtil.INSTANCE.activity
        // execute
        val actual = makeGraphView(MainContext.INSTANCE, 10, ThemeStyle.DARK)
        // validate
        Assert.assertNotNull(actual)
    }

    @Test
    fun testMakeGraphViewWrapper() {
        // setup
        RobolectricUtil.INSTANCE.activity
        // execute
        val actual = makeGraphViewWrapper()
        // validate
        Assert.assertNotNull(actual)
    }
}