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
package com.vrem.wifianalyzer.navigation.availability

import com.vrem.util.compatColor
import com.vrem.util.compatTint
import com.vrem.wifianalyzer.MainContext
import com.vrem.wifianalyzer.R

internal val navigationOptionFilterOff: NavigationOption = {
    it.optionMenu.menu?.let { menu -> menu.findItem(R.id.action_filter).isVisible = false }
}

internal val navigationOptionFilterOn: NavigationOption = {
    it.optionMenu.menu?.let { menu ->
        val menuItem = menu.findItem(R.id.action_filter)
        menuItem.isVisible = true
        val color = if (MainContext.INSTANCE.filtersAdapter.isActive()) R.color.selected else R.color.regular
        val tint = it.compatColor(color)
        menuItem.icon.compatTint(tint)
    }
}
