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
package com.yayooo.wifianalyzer.wifi.channelrating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.yayooo.util.buildVersionP
import com.yayooo.wifianalyzer.MainContext
import com.yayooo.wifianalyzer.R
import com.yayooo.wifianalyzer.databinding.ChannelRatingContentBinding

class ChannelRatingFragment : Fragment(), OnRefreshListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var channelRatingAdapter: ChannelRatingAdapter
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ChannelRatingContentBinding = ChannelRatingContentBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.channelRatingRefresh
        swipeRefreshLayout.setOnRefreshListener(this)
        if (buildVersionP()) {
            swipeRefreshLayout.isRefreshing = false
            swipeRefreshLayout.isEnabled = false
        }
        val bestChannels: TextView = binding.channelRatingBest.channelRatingBestChannels
        channelRatingAdapter = ChannelRatingAdapter(requireActivity(), bestChannels)
        val listView: ListView = binding.channelRatingRefresh.findViewById(R.id.channelRatingView)
        listView.adapter = channelRatingAdapter
        MainContext.INSTANCE.scannerService.register(channelRatingAdapter)
        return binding.root
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        MainContext.INSTANCE.scannerService.update()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onDestroy() {
        MainContext.INSTANCE.scannerService.unregister(channelRatingAdapter)
        super.onDestroy()
    }

}