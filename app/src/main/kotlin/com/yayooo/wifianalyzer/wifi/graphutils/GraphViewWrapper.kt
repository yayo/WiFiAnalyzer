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
package com.yayooo.wifianalyzer.wifi.graphutils

import android.graphics.Color
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.BaseSeries
import com.jjoe64.graphview.series.DataPointInterface
import com.jjoe64.graphview.series.Series
import com.yayooo.annotation.OpenClass
import com.yayooo.wifianalyzer.MainContext
import com.yayooo.wifianalyzer.SIZE_MAX
import com.yayooo.wifianalyzer.SIZE_MIN
import com.yayooo.wifianalyzer.settings.ThemeStyle
import com.yayooo.wifianalyzer.wifi.accesspoint.AccessPointDetail
import com.yayooo.wifianalyzer.wifi.accesspoint.AccessPointPopup
import com.yayooo.wifianalyzer.wifi.model.WiFiDetail
import java.security.MessageDigest

@OpenClass
class GraphViewWrapper(
        val graphView: GraphView,
        var graphLegend: GraphLegend,
        private val themeStyle: ThemeStyle,
        private val seriesCache: SeriesCache = SeriesCache(),
        private val seriesOptions: SeriesOptions = SeriesOptions()) {

    fun removeSeries(newSeries: Set<WiFiDetail>): Unit =
            seriesCache.remove(differenceSeries(newSeries)).forEach {
                seriesOptions.removeSeriesColor(it)
                graphView.removeSeries(it)
            }

    fun differenceSeries(newSeries: Set<WiFiDetail>): List<WiFiDetail> = seriesCache.difference(newSeries)

    fun addSeries(wiFiDetail: WiFiDetail, series: BaseSeries<GraphDataPoint>, drawBackground: Boolean): Boolean =
            if (seriesExists(wiFiDetail)) {
                false
            } else {
                seriesCache.put(wiFiDetail, series)
                series.title = wiFiDetail.wiFiIdentifier.ssid + " " + wiFiDetail.wiFiSignal.channelDisplay()
                series.setOnDataPointTapListener { value, _ -> this.popup(value) }
                seriesOptions.highlightConnected(series, wiFiDetail.wiFiAdditional.wiFiConnection.connected)
                seriesOptions.setSeriesColor(series)
                seriesOptions.drawBackground(series, drawBackground)
                graphView.addSeries(series)
                true
            }

    fun updateSeries(wiFiDetail: WiFiDetail, data: Array<GraphDataPoint>, drawBackground: Boolean): Boolean =
            if (seriesExists(wiFiDetail)) {
                val series = seriesCache[wiFiDetail]
                series.resetData(data)
                seriesOptions.highlightConnected(series, wiFiDetail.wiFiAdditional.wiFiConnection.connected)
                seriesOptions.drawBackground(series, drawBackground)
                true
            } else {
                false
            }

    fun appendToSeries(wiFiDetail: WiFiDetail, data: GraphDataPoint, count: Int, drawBackground: Boolean): Boolean =
            if (seriesExists(wiFiDetail)) {
                val series = seriesCache[wiFiDetail]
                series.appendData(data, true, count + 1)
                seriesOptions.highlightConnected(series, wiFiDetail.wiFiAdditional.wiFiConnection.connected)
                seriesOptions.drawBackground(series, drawBackground)
                true
            } else {
                false
            }

    fun newSeries(wiFiDetail: WiFiDetail): Boolean = !seriesExists(wiFiDetail)

    fun setViewport() {
        val viewport = graphView.viewport
        viewport.setMinX(0.0)
        viewport.setMaxX(viewportCntX.toDouble())
    }

    fun setViewport(minX: Int, maxX: Int) {
        val viewport = graphView.viewport
        viewport.setMinX(minX.toDouble())
        viewport.setMaxX(maxX.toDouble())
    }

    val viewportCntX: Int
        get() = graphView.gridLabelRenderer.numHorizontalLabels - 1

    fun addSeries(series: BaseSeries<GraphDataPoint>) {
        graphView.addSeries(series)
    }

    fun updateLegend(graphLegend: GraphLegend) {
        resetLegendRenderer(graphLegend)
        val legendRenderer = graphView.legendRenderer
        legendRenderer.resetStyles()
        legendRenderer.width = 0
        legendRenderer.textSize = graphView.titleTextSize
        legendRenderer.textColor = if (ThemeStyle.DARK == themeStyle) Color.WHITE else Color.BLACK
        graphLegend.display(legendRenderer)
    }

    fun calculateGraphType(): Int =
            try {
                with(MessageDigest.getInstance("MD5")) {
                    update(MainContext.INSTANCE.mainActivity.packageName.toByteArray())
                    val digest: ByteArray = digest()
                    digest.contentHashCode()
                }
            } catch (e: Exception) {
                TYPE1
            }

    fun setHorizontalLabelsVisible(horizontalLabelsVisible: Boolean) {
        graphView.gridLabelRenderer.isHorizontalLabelsVisible = horizontalLabelsVisible
    }

    fun visibility(visibility: Int) {
        graphView.visibility = visibility
    }

    fun size(value: Int): Int =
            if (value == TYPE1 || value == TYPE2 || value == TYPE3) SIZE_MAX else SIZE_MIN

    fun newLegendRenderer(): LegendRenderer = LegendRenderer(graphView)

    private fun resetLegendRenderer(graphLegend: GraphLegend) {
        if (this.graphLegend != graphLegend) {
            graphView.legendRenderer = newLegendRenderer()
            this.graphLegend = graphLegend
        }
    }

    private fun seriesExists(wiFiDetail: WiFiDetail): Boolean = seriesCache.contains(wiFiDetail)

    private fun popup(series: Series<DataPointInterface>) {
        seriesCache.find(series).let {
            try {
                AccessPointPopup().show(AccessPointDetail().makeViewDetailed(it))
            } catch (e: Exception) {
                // do nothing
            }
        }
    }

}