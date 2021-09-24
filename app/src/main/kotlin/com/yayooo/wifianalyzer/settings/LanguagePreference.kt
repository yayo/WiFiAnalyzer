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
package com.yayooo.wifianalyzer.settings

import android.content.Context
import android.util.AttributeSet
import com.yayooo.util.defaultLanguageTag
import com.yayooo.util.supportedLanguages
import com.yayooo.util.toCapitalize
import com.yayooo.util.toLanguageTag
import java.util.*

private fun data(): List<Data> = supportedLanguages()
        .map { map(it) }
        .sorted()

private fun map(it: Locale): Data =
        Data(toLanguageTag(it), it.getDisplayName(it).toCapitalize(Locale.getDefault()))

class LanguagePreference(context: Context, attrs: AttributeSet) :
        CustomPreference(context, attrs, data(), defaultLanguageTag())