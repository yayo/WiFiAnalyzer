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
package com.yayooo.wifianalyzer.navigation.items

import android.view.View
import com.yayooo.wifianalyzer.about.AboutFragment
import com.yayooo.wifianalyzer.export.Export
import com.yayooo.wifianalyzer.settings.SettingsFragment
import com.yayooo.wifianalyzer.vendor.VendorFragment
import com.yayooo.wifianalyzer.wifi.accesspoint.AccessPointsFragment
import com.yayooo.wifianalyzer.wifi.channelavailable.ChannelAvailableFragment
import com.yayooo.wifianalyzer.wifi.channelgraph.ChannelGraphFragment
import com.yayooo.wifianalyzer.wifi.channelrating.ChannelRatingFragment
import com.yayooo.wifianalyzer.wifi.timegraph.TimeGraphFragment

val navigationItemAccessPoints: NavigationItem = FragmentItem(AccessPointsFragment())
val navigationItemChannelRating: NavigationItem = FragmentItem(ChannelRatingFragment())
val navigationItemChannelGraph: NavigationItem = FragmentItem(ChannelGraphFragment())
val navigationItemTimeGraph: NavigationItem = FragmentItem(TimeGraphFragment())
val navigationItemExport: NavigationItem = ExportItem(Export())
val navigationItemChannelAvailable: NavigationItem = FragmentItem(ChannelAvailableFragment(), false)
val navigationItemVendors: NavigationItem = FragmentItem(VendorFragment(), false, View.GONE)
val navigationItemSettings: NavigationItem = FragmentItem(SettingsFragment(), false, View.GONE)
val navigationItemAbout: NavigationItem = FragmentItem(AboutFragment(), false, View.GONE)
val navigationItemPortAuthority: NavigationItem = PortAuthorityItem()
