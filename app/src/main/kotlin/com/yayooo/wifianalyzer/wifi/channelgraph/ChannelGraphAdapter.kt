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
package com.yayooo.wifianalyzer.wifi.channelgraph

import com.yayooo.annotation.OpenClass
import com.yayooo.wifianalyzer.wifi.band.WiFiBand
import com.yayooo.wifianalyzer.wifi.graphutils.GraphAdapter
import com.yayooo.wifianalyzer.wifi.model.WiFiData

private fun channelGraphViews(): List<ChannelGraphView> =
        WiFiBand.values().flatMap { wiFiBand ->
            wiFiBand.wiFiChannels.wiFiChannelPairs().map { ChannelGraphView(wiFiBand, it) }
        }

@OpenClass
class ChannelGraphAdapter(private val channelGraphNavigation: ChannelGraphNavigation) : GraphAdapter(channelGraphViews()) {
    override fun update(wiFiData: WiFiData) {
        super.update(wiFiData)
        channelGraphNavigation.update()
    }
}

